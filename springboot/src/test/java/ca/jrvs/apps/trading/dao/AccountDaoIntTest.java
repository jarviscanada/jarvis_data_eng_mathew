package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import ca.jrvs.apps.trading.config.TestConfig;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Trader;
import java.sql.Date;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@Sql({"classpath:schema.sql"})
@SpringBootTest(classes = {TestConfig.class})
public class AccountDaoIntTest {

  @Autowired
  JdbcCrudDao<Account> accountDao;
  @Autowired
  TraderDao traderDao;

  @Before
  public void setup() {
    Trader trader = new Trader();
    trader.setId(1);
    trader.setFirstName("first");
    trader.setLastName("last");
    trader.setCountry("Countree");
    trader.setDob(new Date(Instant.now().toEpochMilli()));
    trader.setEmail("e@m.ail");
    accountDao.deleteAll();
    traderDao.save(trader);
  }

  @Test
  public void getJdbcTemplate() {
    assertNotNull(accountDao.getJdbcTemplate());
  }

  @Test
  public void getSimpleJdbcInsert() {
    assertNotNull(accountDao.getSimpleJdbcInsert());
  }

  @Test
  public void getTableName() {
    assertEquals("account", accountDao.getTableName());
  }

  @Test
  public void getIdColumnName() {
    assertEquals("id", accountDao.getIdColumnName());
  }

  @Test
  public void getEntityClass() {
    assertEquals(Account.class, accountDao.getEntityClass());
  }

  @Test
  public void updateEntity() {
    Account account = new Account();
    account.setId(1);
    account.setTraderId(1);
    account.setAmount(15214.93);
    accountDao.save(account);
    account.setAmount(100.01);
    assertEquals(1, accountDao.updateEntity(account));
  }

  @Test
  public void save_new() {
    Account account = new Account();
    account.setId(-1);
    account.setTraderId(1);
    account.setAmount(1000.00);
    assertNotEquals(-1, accountDao.save(account).getId().intValue());
  }

  @Test
  public void save_update() {
    Account account = new Account();
    account.setId(-1);
    account.setTraderId(1);
    account.setAmount(1000.00);
    account = accountDao.save(account);
    account.setAmount(123456.11);
    assertNotEquals(1000.00, accountDao.save(account).getAmount());
  }

  @Test
  public void saveAll() {
    Account account1 = new Account();
    Account account2 = new Account();
    Account account3 = new Account();
    account1.setId(-1);
    account1.setTraderId(1);
    account1.setAmount(123.12);
    account2.setId(-1);
    account2.setTraderId(1);
    account2.setAmount(456.45);
    account3.setId(50);
    account3.setTraderId(1);
    account3.setAmount(789.78);
    List<Account> accounts = Arrays.asList(account1, account2, account3);
    accountDao.saveAll(accounts);
    accountDao.findAll().forEach(System.out::println);
  }

  @Test
  public void findById() {
    Account account = new Account();
    account.setId(1);
    account.setTraderId(1);
    account.setAmount(159.95);
    accountDao.save(account);
    assertNotNull(accountDao.findById(1).orElse(null));
  }

  @Test
  public void findById_NotExist() {
    assertEquals(Optional.empty(), accountDao.findById(99954));
  }

  @Test
  public void existsById() {
    Account account = new Account();
    account.setId(1);
    account.setTraderId(1);
    account.setAmount(159.95);
    accountDao.save(account);
    assertTrue(accountDao.existsById(1));
  }

  @Test
  public void findAll() {
    Account account = new Account();
    account.setId(1);
    account.setTraderId(1);
    account.setAmount(159.95);
    accountDao.save(account);
    assertTrue(accountDao.findAll().size() >= 1);
  }

  @Test
  public void findAllById() {
    Account account = new Account();
    account.setId(1);
    account.setTraderId(1);
    account.setAmount(159.95);
    accountDao.save(account);
    Account account2 = new Account();
    account2.setId(2);
    account2.setTraderId(1);
    account2.setAmount(159.95);
    accountDao.save(account2);
    assertEquals(2, accountDao.findAllById(Arrays.asList(1, 2)).size());
  }

  @Test
  public void count() {
    Account account1 = new Account();
    account1.setId(1);
    account1.setTraderId(1);
    account1.setAmount(159.95);
    accountDao.save(account1);
    Account account2 = new Account();
    account2.setId(2);
    account2.setTraderId(1);
    account2.setAmount(159.95);
    accountDao.save(account2);
    assertEquals(2, accountDao.count());
  }

  @Test
  public void deleteById() {
    Account account = new Account();
    account.setId(1);
    account.setTraderId(1);
    account.setAmount(159.95);
    accountDao.save(account);
    accountDao.deleteById(1);
    accountDao.findAll().forEach(System.out::println);
  }

//  @Test
//  public void delete() {
//  }
//
//  @Test
//  public void deleteAll() {
//  }

  @Test
  public void testDeleteAll() {
    accountDao.deleteAll();
  }
}