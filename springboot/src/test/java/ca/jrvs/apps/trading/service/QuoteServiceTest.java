package ca.jrvs.apps.trading.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import ca.jrvs.apps.trading.config.TestConfig;
import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
@ActiveProfiles("test")
public class QuoteServiceTest {

  QuoteService testQuoteService;
  @Autowired
  QuoteDao quoteDao;
  @Autowired
  MarketDataDao marketDataDao;
  String[] symbols;

  @Before
  public void setup() {
    symbols = new String[]{"aapl", "goog", "t", "fb"};
    testQuoteService = new QuoteService(marketDataDao, quoteDao);
  }

  @Test
  public void getIexQuoteBySymbol() {
    IexQuote iexQuote = testQuoteService.getIexQuoteBySymbol("aapl");
    assertEquals("aapl", iexQuote.getSymbol().toLowerCase());
    System.out.println(iexQuote.toString());
  }

  @Test
  public void getMultipleIexQuoteBySymbol() {
    Iterable<IexQuote> iexQuotes = testQuoteService.getMultipleIexQuoteBySymbol(symbols);
    List<String> symbolList = Arrays.asList(symbols);
    int count = 0;
    for (IexQuote iexQuote : iexQuotes) {
      assertTrue(symbolList.contains(iexQuote.getSymbol().toLowerCase()));
      count++;
    }
    assertEquals(symbols.length, count);
  }

  @Test
  public void updateMarketData() {
    Quote appleQuote = new Quote();
    Quote attQuote = new Quote();
    Quote googleQuote = new Quote();
    appleQuote.setTicker("AAPL");
    attQuote.setTicker("T");
    googleQuote.setTicker("GOOG");
    quoteDao.saveAll(Arrays.asList(appleQuote, googleQuote, attQuote));
    testQuoteService.updateMarketData();
  }
}