package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import ca.jrvs.apps.trading.config.TestConfig;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.domain.Trader;
import java.sql.Date;
import java.time.Instant;
import java.util.Collections;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@Sql({"classpath:schema.sql"})
@SpringBootTest(classes = {TestConfig.class})
public class PositionDaoIntTest {

  @Autowired
  PositionDao positionDao;
  @Autowired
  SecurityOrderDao securityOrderDao;
  @Autowired
  TraderDao traderDao;
  @Autowired
  AccountDao accountDao;
  @Autowired
  QuoteDao quoteDao;

  @Before
  public void setup() {
    Quote quote = new Quote();
    quote.setId("aapl");
    quote.setLastPrice(11.11);
    quote.setAskPrice(15.15);
    quote.setAskSize(55);
    quote.setBidPrice(10.00);
    quote.setBidSize(60);
    quoteDao.save(quote);
    Trader trader = new Trader();
    trader.setFirstName("first");
    trader.setLastName("last");
    trader.setCountry("Cowntree");
    trader.setEmail("eemail@real.web");
    trader.setDob(new Date(Instant.now().toEpochMilli()));
    traderDao.save(trader);
    Account account = new Account();
    account.setTraderId(1);
    account.setAmount(123456.99);
    accountDao.save(account);
    SecurityOrder order = new SecurityOrder();
    order.setAccountId(1);
    order.setTicker("aapl");
    order.setStatus("FILLED");
    order.setPrice(123.34);
    order.setSize(152);
    order.setNotes("NOTED");
    securityOrderDao.save(order);
  }

  @Test
  public void getJdbcTemplate() {
    assertNotNull(positionDao.getJdbcTemplate());
  }

  @Test(expected = UnsupportedOperationException.class)
  public void getSimpleJdbcInsert() {
    positionDao.getSimpleJdbcInsert();
  }

  @Test
  public void getTableName() {
    assertEquals("position", positionDao.getTableName().toLowerCase());
  }

  @Test
  public void getIdColumnName() {
    assertEquals("account_id", positionDao.getIdColumnName().toLowerCase());
  }

  @Test
  public void getEntityClass() {
    assertEquals(Position.class, positionDao.getEntityClass());
  }

  @Test
  public void findById() {
    assertTrue(positionDao.findById(1).isPresent());
  }

  @Test
  public void findAllById() {
    assertTrue(positionDao.findAllById(Collections.singletonList(1)).size() >= 1);
  }

  @Test
  public void findAll() {
    assertTrue(positionDao.findAll().size() > 0);
  }

  /**
   * Explicitly testing functions that have been overridden from JdbcCrudDao to make sure it's done
   * properly.
   */
  @Test(expected = UnsupportedOperationException.class)
  public void updateEntity() {
    positionDao.updateEntity(null);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void save() {
    positionDao.save(null);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void saveAll() {
    positionDao.saveAll(null);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void deleteById() {
    positionDao.deleteById(0);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void delete() {
    positionDao.delete(new Position());
  }

  @Test(expected = UnsupportedOperationException.class)
  public void deleteAll() {
    positionDao.deleteAll();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testDeleteAll() {
    positionDao.deleteAll(null);
  }
}