package ca.jrvs.apps.trading.service;

import static org.junit.Assert.assertEquals;

import ca.jrvs.apps.trading.config.TestConfig;
import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.TraderAccountView;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.domain.Trader;
import java.sql.Date;
import java.time.LocalDate;
import javax.persistence.EntityNotFoundException;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@ActiveProfiles("test")
@Sql({"classpath:schema.sql"})
public class TraderAccountServiceIntTest {

  Logger logger = LoggerFactory.getLogger(TraderAccountServiceIntTest.class);

  @Autowired
  TraderAccountService traderAccountTestService;
  @Autowired
  QuoteDao quoteDao;
  @Autowired
  AccountDao accountDao;
  @Autowired
  TraderDao traderDao;
  @Autowired
  SecurityOrderDao securityOrderDao;

  @After
  public void cleanup() {
    securityOrderDao.deleteAll();
    accountDao.deleteAll();
    traderDao.deleteAll();
    quoteDao.deleteAll();
  }

  @Test
  public void createNewTraderAccount() {
    Trader trader = new Trader();
    trader.setId(-1);
    trader.setFirstName("First");
    trader.setLastName("Last");
    trader.setCountry("Cannedda");
    trader.setEmail("flast@email.email");
    trader.setDob(Date.valueOf(LocalDate.now()));
    logger.info("Attempting to save new Trader, 'First Last' with id -1");
    TraderAccountView traderAccountView = traderAccountTestService.createNewTraderAccount(trader);
    logger.info(traderAccountView.getTrader().toString()
        + '\n' + traderAccountView.getAccount().toString());
    assertEquals(traderAccountView.getTrader().getId(), traderAccountView.getAccount().getId());
    assertEquals(traderAccountView.getAccount().getId(),
        traderAccountView.getAccount().getTraderId());
  }

  @Test(expected = IllegalArgumentException.class)
  public void createNewTraderAccount_AlreadyExists() {
    createNewTraderAccount();
    createNewTraderAccount(); // Should throw IllegalArgumentException
  }

  @Test
  public void deleteTraderById() {
    createNewTraderAccount();
    traderAccountTestService.deleteTraderById(1);
    assertEquals(0, accountDao.count());
    assertEquals(0, traderDao.count());
  }

  @Test(expected = EntityNotFoundException.class)
  public void deleteTraderById_NotFound() {
    traderAccountTestService.deleteTraderById(-1);
  }

  @Test(expected = IllegalStateException.class)
  public void deleteTraderById_HasMoney() {
    deposit();
    traderAccountTestService.deleteTraderById(1);
  }

  @Test
  public void deleteTraderById_HasFilledOrders() {
    createNewTraderAccount();
    prepareOrder("FILLED");
    traderAccountTestService.deleteTraderById(1);
    assertEquals(0, traderDao.count());
    assertEquals(0, accountDao.count());
    assertEquals(0, securityOrderDao.count());
  }

  @Test(expected = IllegalStateException.class)
  public void deleteTraderById_PendingOrders() {
    createNewTraderAccount();
    prepareOrder("PENDING");
    traderAccountTestService.deleteTraderById(1);
  }

  private void prepareOrder(String status) {
    Quote quote = new Quote();
    quote.setTicker("MSFT");
    quote.setLastPrice(111.15);
    quote.setAskPrice(115.55);
    quote.setBidPrice(100.01);
    quote.setAskSize(111);
    quote.setBidSize(100);
    quoteDao.save(quote);

    SecurityOrder order = new SecurityOrder();
    order.setAccountId(1);
    order.setStatus(status);
    order.setTicker("MSFT");
    order.setPrice(111.11);
    order.setNotes("NOTABLY NOTED");
    securityOrderDao.save(order);
  }

  @Test
  public void deposit() {
    createNewTraderAccount();
    Account account = traderAccountTestService.deposit(1, 5000.01);
    assertEquals(5000.01, account.getAmount(), 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void deposit_Negative() {
    createNewTraderAccount();
    Account account = traderAccountTestService.deposit(1, -5000);
  }

  @Test(expected = EntityNotFoundException.class)
  public void deposit_NotExist() {
    traderAccountTestService.deposit(-1, 100);
  }

  @Test
  public void withdraw() {
    deposit();
    Account account = traderAccountTestService.withdraw(1, 300.01);
    assertEquals(5000.01 - 300.01, account.getAmount(), 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void withdraw_Negative() {
    deposit();
    traderAccountTestService.withdraw(1, -100);
  }

  @Test(expected = IllegalArgumentException.class)
  public void withdraw_Overdraft() {
    deposit();
    traderAccountTestService.withdraw(1, Integer.MAX_VALUE);
  }

  @Test(expected = EntityNotFoundException.class)
  public void withdraw_NotExist() {
    traderAccountTestService.withdraw(-1, 100);
  }
}