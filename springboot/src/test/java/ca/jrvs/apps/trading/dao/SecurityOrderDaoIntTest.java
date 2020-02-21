package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import ca.jrvs.apps.trading.config.TestConfig;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.domain.Trader;
import java.sql.Date;
import java.time.Instant;
import java.util.Arrays;
import org.junit.After;
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
public class SecurityOrderDaoIntTest {

  @Autowired
  SecurityOrderDao securityOrderTestDao;
  @Autowired
  QuoteDao quoteDao;
  @Autowired
  TraderDao traderDao;
  @Autowired
  AccountDao accountDao;

  SecurityOrder testOrder;
  SecurityOrder testOrder2;
  Quote testQuote;
  Trader testTrader;
  Account testAccount;


  @Before
  public void setUp() throws Exception {
    testQuote = new Quote();
    testTrader = new Trader();
    testAccount = new Account();
    testOrder = new SecurityOrder();

    testQuote.setId("MSFT");
    testQuote.setLastPrice(111.11);
    testQuote.setAskPrice(115.55);
    testQuote.setBidPrice(100.99);
    testQuote.setAskSize(15987);
    testQuote.setBidSize(14561);
    quoteDao.save(testQuote);

    testTrader.setId(-1);
    testTrader.setFirstName("First");
    testTrader.setLastName("Last");
    testTrader.setCountry("Cow'n'tree");
    testTrader.setEmail("ey@m.ail");
    testTrader.setDob(new Date(Instant.now().toEpochMilli()));
    testTrader = traderDao.save(testTrader);

    testAccount.setId(-1);
    testAccount.setTraderId(testTrader.getId());
    testAccount.setAmount(9876543.21);
    testAccount = accountDao.save(testAccount);

    testOrder.setId(-1);
    testOrder.setTicker("MSFT");
    testOrder.setPrice(111.11);
    testOrder.setSize(15420);
    testOrder.setStatus("PENDING");
    testOrder.setAccountId(testAccount.getId());
    testOrder.setNotes("NOTABLE NOTES");

    testOrder2 = new SecurityOrder();
    testOrder2.setId(-1);
    testOrder2.setTicker("MSFT");
    testOrder2.setPrice(99.99);
    testOrder2.setSize(3007);
    testOrder2.setStatus("FILLED");
    testOrder2.setAccountId(testAccount.getId());
    testOrder2.setNotes("NOTARY NOTEABLES");
  }

  @After
  public void tearDown() throws Exception {
    securityOrderTestDao.deleteAll();
  }

  @Test
  public void save() {
    securityOrderTestDao.save(testOrder);
    assertTrue(testOrder.getId() >= 1);
  }

  @Test
  public void saveAll() {
    securityOrderTestDao.saveAll(Arrays.asList(testOrder, testOrder2));
    assertEquals(2, securityOrderTestDao.count());
  }

  @Test
  public void findById() {
    securityOrderTestDao.save(testOrder);
    SecurityOrder found = securityOrderTestDao.findById(testOrder.getId()).orElse(null);
    assertNotNull(found);
    assertEquals(testOrder.getNotes(), found.getNotes());
    assertEquals(testOrder.getTicker(), found.getTicker());
    assertEquals(testOrder.getStatus(), found.getStatus());
    assertEquals(testOrder.getAccountId(), found.getAccountId());
  }

  @Test
  public void existsById() {
    securityOrderTestDao.save(testOrder);
    assertTrue(securityOrderTestDao.existsById(testOrder.getId()));
  }

  @Test
  public void findAll() {
    securityOrderTestDao.saveAll(Arrays.asList(testOrder, testOrder2));
    assertEquals(2, securityOrderTestDao.findAll().size());
  }

  @Test
  public void findAllById() {
    securityOrderTestDao.saveAll(Arrays.asList(testOrder, testOrder2));
    assertEquals(2, securityOrderTestDao.findAllById(Arrays.asList(1, 2)).size());
  }

  @Test
  public void findAllById_Single() {
    securityOrderTestDao.saveAll(Arrays.asList(testOrder, testOrder2));
    assertEquals(2, securityOrderTestDao.findAllById(1).size());
  }

  @Test
  public void count() {
    securityOrderTestDao.saveAll(Arrays.asList(testOrder, testOrder2));
    assertEquals(2, securityOrderTestDao.count());
  }

  @Test
  public void deleteById() {
    securityOrderTestDao.saveAll(Arrays.asList(testOrder, testOrder2));
    securityOrderTestDao.deleteById(1);
    assertEquals(1, securityOrderTestDao.count());
  }

  @Test
  public void deleteAll() {
    securityOrderTestDao.saveAll(Arrays.asList(testOrder, testOrder2));
    securityOrderTestDao.deleteAll();
    assertEquals(0, securityOrderTestDao.count());
  }

  @Test
  public void getJdbcTemplate() {
    assertEquals(securityOrderTestDao.template, securityOrderTestDao.getJdbcTemplate());
  }

  @Test
  public void getSimpleJdbcInsert() {
    assertEquals(securityOrderTestDao.simpleInsert, securityOrderTestDao.getSimpleJdbcInsert());
  }

  @Test
  public void getTableName() {
    assertEquals(securityOrderTestDao.tableName, securityOrderTestDao.getTableName());
  }

  @Test
  public void getIdColumnName() {
    assertEquals(securityOrderTestDao.idColumnName, securityOrderTestDao.getIdColumnName());
  }

  @Test
  public void getEntityClass() {
    assertEquals(SecurityOrder.class, securityOrderTestDao.getEntityClass());
  }

  @Test
  public void updateEntity() {
    testOrder.setStatus("FILLED");
    testOrder.setNotes("FILLED ON " + Instant.now().toString());
    securityOrderTestDao.updateEntity(testOrder);
  }
}