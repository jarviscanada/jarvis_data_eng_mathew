package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.TraderAccountView;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.domain.TraderPortfolioView;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

  private AccountDao accountDao;
  private TraderDao traderDao;
  private SecurityOrderDao securityOrderDao;
  private PositionDao positionDao;

  @Autowired
  public DashboardService(AccountDao accountDao, SecurityOrderDao securityOrderDao,
      TraderDao traderDao, PositionDao positionDao) {
    this.accountDao = accountDao;
    this.securityOrderDao = securityOrderDao;
    this.traderDao = traderDao;
    this.positionDao = positionDao;
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
    portfolioView.setPositions(positionDao.findAllForId(traderId));
    portfolioView.setFunds(accountDao.findById(traderId).orElseThrow(EntityNotFoundException::new)
        .getAmount());
    return portfolioView;
  }
}
