package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import ca.jrvs.apps.trading.config.TestConfig;
import ca.jrvs.apps.trading.model.domain.Trader;
import java.sql.Date;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
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
public class TraderDaoIntTest {

  @Autowired
  TraderDao testTraderDao;
  Trader testTrader;
  Trader testTrader2;

  @Before
  public void setup() {
    testTrader = new Trader();
    testTrader.setId(-1);
    testTrader.setFirstName("First");
    testTrader.setLastName("Last");
    testTrader.setCountry("Cannedda");
    testTrader.setEmail("email@email.email");
    testTrader.setDob(new Date(Instant.now().toEpochMilli()));
    testTraderDao.save(testTrader);

    testTrader2 = new Trader();
    testTrader2.setId(-1);
    testTrader2.setFirstName("Kann-e");
    testTrader2.setLastName("Sanna");
    testTrader2.setCountry("Gridania");
    testTrader2.setEmail("Tree-Hugginz@eorzea.net");
    testTrader2.setDob(new Date(Instant.now().toEpochMilli() - 10000));
  }

  @After
  public void teardown() {
    testTraderDao.deleteAll();
  }

  @Test
  public void save() {
    Trader inserted = testTraderDao.save(testTrader2);
    assertEquals(testTrader2.getCountry(), inserted.getCountry());
    assertEquals(testTrader2.getFirstName(), inserted.getFirstName());
    assertEquals(testTrader2.getLastName(), inserted.getLastName());
  }

  @Test
  public void saveAll() {
    testTraderDao.saveAll(Arrays.asList(testTrader, testTrader2));
    assertEquals(2, testTraderDao.count());
  }

  @Test
  public void findById() {
    Trader found = testTraderDao.findById(1).orElse(null);
    assertNotNull(found);
    assertEquals("Cannedda", found.getCountry());
  }

  @Test
  public void existsById() {
    assertTrue(testTraderDao.existsById(1));
    assertFalse(testTraderDao.existsById(-15987));
  }

  @Test
  public void findAll() {
    assertEquals(1, testTraderDao.findAll().size());
  }

  @Test
  public void findAllById() {
    testTraderDao.save(testTrader2);
    List<Trader> traders = testTraderDao.findAllById(Arrays.asList(1, 2));
    assertEquals(2, traders.size());
    assertEquals(testTrader.getFirstName(), traders.get(0).getFirstName());
    assertEquals(testTrader2.getFirstName(), traders.get(1).getFirstName());
  }

  @Test
  public void count() {
    assertEquals(1, testTraderDao.count());
  }

  @Test
  public void deleteById() {
    testTraderDao.save(testTrader2);
    testTraderDao.deleteById(2);
  }

  @Test
  public void deleteAll() {
    testTraderDao.deleteAll();
    assertEquals(0, testTraderDao.count());
  }

  @Test
  public void getJdbcTemplate() {
    assertEquals(testTraderDao.template, testTraderDao.getJdbcTemplate());
  }

  @Test
  public void getSimpleJdbcInsert() {
    assertEquals(testTraderDao.simpleInsert, testTraderDao.getSimpleJdbcInsert());
  }

  @Test
  public void getTableName() {
    assertEquals(testTraderDao.tableName, testTraderDao.getTableName());
  }

  @Test
  public void getIdColumnName() {
    assertEquals(testTraderDao.idColumnName, testTraderDao.getIdColumnName());
  }

  @Test
  public void getEntityClass() {
    assertEquals(Trader.class, testTraderDao.getEntityClass());
  }

  @Test
  public void updateEntity() {
    testTrader.setFirstName("Tataru");
    testTrader.setLastName("Taru");
    testTrader.setEmail("money_money@7scions.dawn");
    testTraderDao.updateEntity(testTrader);
  }
}