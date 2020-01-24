package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.MarketOrder;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

  private SecurityOrderDao orderDao;
  private QuoteDao quoteDao;
  private PositionDao positionDao;
  private AccountDao accountDao;
  private Logger logger = LoggerFactory.getLogger(OrderService.class);

  @Autowired
  public OrderService(SecurityOrderDao securityOrderDao, QuoteDao quoteDao, PositionDao positionDao,
      AccountDao accountDao) {
    orderDao = securityOrderDao;
    this.quoteDao = quoteDao;
    this.positionDao = positionDao;
    this.accountDao = accountDao;
  }

  /**
   * Executes an order received by the user. If the order is a Buy order, the user is deducted the
   * appropriate amount if possible. If a Buy order cannot be completed, the order is made Pending.
   * If the order is a Sell order, The user is credited the appropriate amount if they have enough
   * of a position to sell.
   * <p>
   * If an order cannot be filled it is saved with the status "PENDING" For the sake of simplicity,
   * It is always assumed that there is a willing buyer/seller
   *
   * @param order The order received by the user
   * @return The processed order
   */
  public SecurityOrder executeOrder(MarketOrder order) {
    SecurityOrder securityOrder = makeSecurityOrder(order);
    Account account = accountDao.findById(order.getAccountId()).orElseThrow(
        EntityNotFoundException::new);
    if (order.isSellOrder()) {
      return executeSell(securityOrder, account);
    } else {
      return executeBuy(securityOrder, account);
    }
  }

  private SecurityOrder executeSell(SecurityOrder order, Account account) {
    List<Position> positions = positionDao.findAllById(Collections.singletonList(account.getId()));
    for (Position p : positions) {
      if (order.getTicker().equals(p.getTicker()) && p.getPosition() >= Math.abs(order.getSize())) {
        order.setStatus("FILLED");
        order.setNotes("Filled on: " + LocalDateTime.now().toString());
        account.setAmount(account.getAmount() + (order.getPrice() * Math.abs(order.getSize())));
        accountDao.updateEntity(account);
        return orderDao.save(order);
      }
    }
    order.setStatus("CANCELLED");
    order.setNotes("No position to sell with");
    return order;
  }

  private SecurityOrder executeBuy(SecurityOrder order, Account account) {
    if (account.getAmount() >= order.getPrice() * order.getSize()) {
      order.setStatus("FILLED");
      order.setNotes("Filled on: " + LocalDateTime.now().toString());
      account.setAmount(account.getAmount() - (order.getPrice() * order.getSize()));
      accountDao.updateEntity(account);
      return orderDao.save(order);
    } else {
      order.setStatus("CANCELLED");
      order.setNotes("You don't have enough money to complete this order");
      return order;
    }
  }

  private SecurityOrder makeSecurityOrder(MarketOrder marketOrder) {
    SecurityOrder order = new SecurityOrder();
    if (marketOrder.getSize() > 0) {
      if (quoteDao.existsById(marketOrder.getSymbol())
          && accountDao.existsById(marketOrder.getAccountId())) {
        order.setTicker(marketOrder.getSymbol());
        // Negative size is used to reduce Position when order is filled
        if (marketOrder.isSellOrder()) {
          order.setSize(marketOrder.getSize() * -1);
          order.setPrice(quoteDao.findById(marketOrder.getSymbol())
              .orElseThrow(EntityNotFoundException::new).getBidPrice());
        } else {
          order.setSize(marketOrder.getSize());
          order.setPrice(quoteDao.findById(marketOrder.getSymbol())
              .orElseThrow(EntityNotFoundException::new).getAskPrice());
        }
        order.setAccountId(marketOrder.getAccountId());
        order.setStatus("PENDING");
        order.setNotes("");
        return order;
      } else {
        throw new EntityNotFoundException("Account or Symbol could not be found");
      }
    } else {
      throw new IllegalArgumentException("Invalid order amount");
    }
  }
}
