package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.assertEquals;

import ca.jrvs.apps.trading.config.TestConfig;
import ca.jrvs.apps.trading.model.domain.Quote;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfig.class)
@Sql({"classpath:schema.xml"})
public class QuoteDaoIntTest {

  @Autowired
  private QuoteDao testQuoteDao;

  private Quote sampleQuote;

  @Before
  public void setUp() throws Exception {
    sampleQuote = new Quote();
    sampleQuote.setSymbol("BMO");
    sampleQuote.setLastPrice(110.58);
    sampleQuote.setBidPrice(105.50);
    sampleQuote.setBidSize(10);
    sampleQuote.setAskPrice(115.25);
    sampleQuote.setAskSize(5);
    testQuoteDao.save(sampleQuote);
  }

  @After
  public void tearDown() throws Exception {
    testQuoteDao.deleteAll();
  }

  @Test(expected = SQLException.class)
  public void save() {
    testQuoteDao.save(sampleQuote);
  }

  @Test
  public void saveAll() {
    Quote sample1 = new Quote();
    Quote sample2 = new Quote();
    sample1.setSymbol("T1");
    sample1.setLastPrice(12.34);
    sample2.setSymbol("T2");
    sample2.setLastPrice(43.21);
    List<Quote> quotes = Arrays.asList(sample1, sample2);
    testQuoteDao.saveAll(quotes);
  }

  @Test
  public void findById() {
    Quote foundQuote = testQuoteDao.findById("BMO").orElseThrow(
        () -> new IllegalArgumentException("Symbol not found"));
    assertEquals("BMO", foundQuote.getSymbol());
    assertEquals(110.58, foundQuote.getLastPrice(), 0.001);
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
    testQuoteDao.deleteById("BMO");
  }

  @Test
  public void deleteAll() {
    testQuoteDao.deleteAll();
  }
}