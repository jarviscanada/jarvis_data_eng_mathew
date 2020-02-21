package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.assertEquals;

import ca.jrvs.apps.trading.config.TestConfig;
import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfig.class)
@Sql({"classpath:schema.sql"})
public class QuoteDaoIntTest {

  @Autowired
  private QuoteDao testQuoteDao;
  private Logger logger = LoggerFactory.getLogger(QuoteDaoIntTest.class);
  private Quote sampleQuote;

  @Before
  public void setUp() {
    sampleQuote = new Quote();
    sampleQuote.setId("BMO");
    sampleQuote.setLastPrice(110.58);
    sampleQuote.setBidPrice(105.50);
    sampleQuote.setBidSize(10);
    sampleQuote.setAskPrice(115.25);
    sampleQuote.setAskSize(5);
    testQuoteDao.save(sampleQuote);
  }

  @After
  public void tearDown() {
    testQuoteDao.deleteAll();
  }

  private void printQuoteTable() {
    Iterable<Quote> quotes = testQuoteDao.findAll();
    logger.info("Quote table contents:");
    for (Quote q : quotes) {
      logger.info(q.toString());
    }
  }

  @Test
  public void save() {
    testQuoteDao.save(sampleQuote);
    printQuoteTable();
  }

  @Test
  public void saveAll() {
    Quote sample1 = new Quote();
    Quote sample2 = new Quote();
    sample1.setId("T1");
    sample1.setLastPrice(12.34);
    sample2.setId("T2");
    sample2.setLastPrice(43.21);
    List<Quote> quotes = Arrays.asList(sample1, sample2);
    testQuoteDao.saveAll(quotes);
    printQuoteTable();
  }

  @Test
  public void findById() {
    Quote foundQuote = testQuoteDao.findById("BMO").orElseThrow(
        () -> new IllegalArgumentException("Symbol not found"));
    assertEquals("BMO", foundQuote.getId());
    assertEquals(110.58, foundQuote.getLastPrice(), 0.001);
  }

  @Test(expected = IncorrectResultSizeDataAccessException.class)
  public void findById_NotExists() {
    Quote foundQuote = testQuoteDao.findById("QQQQ").orElseThrow(
        () -> new IllegalArgumentException("Symbol not found"));
  }

  @Test
  public void findAll() {
    int rows = 0;
    saveAll();
    Iterable<Quote> quotes = testQuoteDao.findAll();
    for (Quote q : quotes) {
      rows++;
    }
    assertEquals(3, rows);
  }

  @Test
  public void count() {
    long rows = testQuoteDao.count();
    assertEquals(1, rows);
  }

  @Test
  public void deleteById() {
    saveAll();
    testQuoteDao.deleteById("BMO");
    printQuoteTable();
  }

  @Test
  public void deleteById_NotExist() {
    testQuoteDao.deleteById("QQQQ");
  }

  @Test
  public void deleteAll() {
    testQuoteDao.deleteAll();
    printQuoteTable();
  }
}