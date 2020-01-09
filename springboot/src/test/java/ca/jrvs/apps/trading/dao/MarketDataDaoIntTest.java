package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.config.MarketDataConfig;
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
  public void findAllById() {
    List<String> symbols = Arrays.asList("aapl", "msft", "t", "tsla");
    Iterable<IexQuote> quotes = testDao.findAllById(symbols);
    for (IexQuote iex : quotes) {
      assertTrue(symbols.contains(iex.getSymbol().toLowerCase()));
    }
  }
}