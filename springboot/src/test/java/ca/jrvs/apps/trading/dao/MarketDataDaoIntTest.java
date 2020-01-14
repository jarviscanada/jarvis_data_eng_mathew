package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import ca.jrvs.apps.trading.config.MarketDataConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.junit.Before;
import org.junit.Test;

public class MarketDataDaoIntTest {

  MarketDataConfig marketDataConfig;
  MarketDataDao testDao;

  @Before
  public void testInit() {
    marketDataConfig = new MarketDataConfig();
    marketDataConfig.setHost("https://cloud.iexapis.com/stable/");
    marketDataConfig.setToken(System.getenv("IEX_TOKEN"));
    testDao = new MarketDataDao(new PoolingHttpClientConnectionManager(), marketDataConfig);
  }

  @Test
  public void findById() {
    IexQuote quote = testDao.findById("aapl").orElse(null);
    assertNotNull(quote);
    assertEquals("AAPL", quote.getSymbol());
  }

  @Test
  public void findById_NotExists() {
    IexQuote quote = testDao.findById("Nonexistent").orElse(null);
    assertNull(quote);
  }

  @Test
  public void findAllById() {
    List<String> symbols = Arrays.asList("aapl", "msft", "t", "tsla");
    Iterable<IexQuote> quotes = testDao.findAllById(symbols);
    for (IexQuote iex : quotes) {
      assertTrue(symbols.contains(iex.getSymbol().toLowerCase()));
    }
  }

  @Test(expected = UnsupportedOperationException.class)
  public void save() {
    testDao.save(new IexQuote());
  }

  @Test(expected = UnsupportedOperationException.class)
  public void saveAll() {
    testDao.saveAll(new ArrayList<>());
  }

  @Test(expected = UnsupportedOperationException.class)
  public void existsById() {
    testDao.existsById("");
  }

  @Test(expected = UnsupportedOperationException.class)
  public void findAll() {
    testDao.findAll();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void count() {
    testDao.count();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void deleteById() {
    testDao.deleteById("");
  }

  @Test(expected = UnsupportedOperationException.class)
  public void delete() {
    testDao.delete(new IexQuote());
  }

  @Test(expected = UnsupportedOperationException.class)
  public void deleteAll() {
    testDao.deleteAll();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testDeleteAll() {
    testDao.deleteAll(new ArrayList<>());
  }
}