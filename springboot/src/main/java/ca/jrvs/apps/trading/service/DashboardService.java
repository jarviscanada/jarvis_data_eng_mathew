package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.TraderAccountView;
import ca.jrvs.apps.trading.model.TraderPortfolioView;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.Trader;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

  private AccountDao accountDao;
  private TraderDao traderDao;
  private PositionDao positionDao;
  private QuoteDao quoteDao;

  @Autowired
  public DashboardService(AccountDao accountDao, TraderDao traderDao, PositionDao positionDao,
      QuoteDao quoteDao) {
    this.accountDao = accountDao;
    this.traderDao = traderDao;
    this.positionDao = positionDao;
    this.quoteDao = quoteDao;
  }

  /**
   * Retrieve the trader's profile and account data.
   *
   * @param traderId The ID of the trader to look up
   * @return The Trader's associated TraderAccountView
   */
  public TraderAccountView getTraderAccountView(int traderId) {
    Trader trader = traderDao.findById(traderId).orElseThrow(EntityNotFoundException::new);
    Account account = accountDao.findById(traderId).orElseThrow(EntityNotFoundException::new);
    return new TraderAccountView(trader, account);
  }

  /**
   * Retrieve a given Trader's Portfolio (The Traders' order history and currently available funds)
   *
   * @param traderId The ID of the trader to look up.
   * @return The trader's portfolio
   */
  public TraderPortfolioView getTraderPortfolioView(int traderId) {
    TraderPortfolioView portfolioView = new TraderPortfolioView();
    portfolioView.setTraderId(traderId);
    List<Position> positions = positionDao.findAllForId(traderId);
    for (Position p : positions) {
      portfolioView.addDetailedPosition(p, quoteDao.findById(p.getTicker())
          .orElseThrow(EntityNotFoundException::new));
    }
    portfolioView.setFunds(accountDao.findById(traderId).orElseThrow(EntityNotFoundException::new)
        .getAmount());
    return portfolioView;
  }
}
