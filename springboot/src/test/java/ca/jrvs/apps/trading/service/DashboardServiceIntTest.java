package ca.jrvs.apps.trading.service;

import static org.junit.Assert.assertEquals;

import ca.jrvs.apps.trading.config.TestConfig;
import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.TraderAccountView;
import ca.jrvs.apps.trading.model.TraderPortfolioView;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.domain.Trader;
import java.sql.Date;
import java.time.LocalDate;
import javax.persistence.EntityNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfig.class)
@Sql({"classpath:schema.sql"})
public class DashboardServiceIntTest {

  @Autowired
  public DashboardService testDashboardService;
  @Autowired
  public AccountDao accountDao;
  @Autowired
  public TraderDao traderDao;
  @Autowired
  public QuoteDao quoteDao;
  @Autowired
  public SecurityOrderDao securityOrderDao;

  Trader sampleTrader;
  Account sampleAccount;
  SecurityOrder sampleSecurityOrder;
  Quote sampleQuote;

  @Before
  public void setup() {
    sampleQuote = new Quote();
    sampleQuote.setTicker("TSLA");
    sampleQuote.setLastPrice(10.55);
    sampleQuote.setAskPrice(11.11);
    sampleQuote.setBidPrice(10.10);
    sampleQuote.setAskSize(20);
    sampleQuote.setBidSize(15);
    quoteDao.save(sampleQuote);

    sampleTrader = new Trader();
    sampleTrader.setFirstName("FNAME");
    sampleTrader.setLastName("LNAME");
    sampleTrader.setEmail("E@M.AIL");
    sampleTrader.setCountry("COUNTRY");
    sampleTrader.setDob(Date.valueOf(LocalDate.now()));
    traderDao.save(sampleTrader);

    sampleAccount = new Account();
    sampleAccount.setTraderId(1);
    sampleAccount.setAmount(25000);
    accountDao.save(sampleAccount);

    sampleSecurityOrder = new SecurityOrder();
    sampleSecurityOrder.setTicker("TSLA");
    sampleSecurityOrder.setAccountId(1);
    sampleSecurityOrder.setStatus("FILLED");
    sampleSecurityOrder.setSize(25);
    sampleSecurityOrder.setPrice(12.25);
    sampleSecurityOrder.setNotes("NOTES");
    securityOrderDao.save(sampleSecurityOrder);
  }

  @Test
  public void getTraderAccountView() {
    TraderAccountView tav = testDashboardService.getTraderAccountView(1);
    assertEquals(25000, tav.getAccount().getAmount(), 0.0);
    assertEquals("FNAME", tav.getTrader().getFirstName());
  }

  @Test(expected = EntityNotFoundException.class)
  public void getTraderAccountView_NotFound() {
    testDashboardService.getTraderAccountView(-500);
  }

  @Test
  public void getTraderPortfolioView() {
    TraderPortfolioView tpv = testDashboardService.getTraderPortfolioView(1);
    assertEquals(1, tpv.getPositions().size());
    assertEquals(25000, tpv.getFunds(), 0.0);
  }

  @Test(expected = EntityNotFoundException.class)
  public void getTraderPortfolioView_NotFound() {
    testDashboardService.getTraderPortfolioView(-500);
  }
}