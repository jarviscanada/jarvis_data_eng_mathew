package ca.jrvs.apps.trading.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import ca.jrvs.apps.trading.config.TestConfig;
import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
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
  Quote appleQuote;
  Quote microsoftQuote;
  Quote attQuote;

  @Before
  public void setup() {
    symbols = new String[]{"aapl", "msft", "t", "fb"};
    testQuoteService = new QuoteService(marketDataDao, quoteDao);
    appleQuote = new Quote();
    attQuote = new Quote();
    microsoftQuote = new Quote();
    appleQuote.setTicker("AAPL");
    appleQuote.setLastPrice(123.45);
    appleQuote.setAskSize(1101L);
    appleQuote.setAskPrice(551.00);
    appleQuote.setBidSize(55L);
    appleQuote.setBidPrice(101.99);
    attQuote.setTicker("T");
    attQuote.setLastPrice(51.15);
    attQuote.setAskSize(101L);
    attQuote.setBidSize(500L);
    attQuote.setAskPrice(60.00);
    attQuote.setBidPrice(50.00);
    microsoftQuote.setTicker("MSFT");
    microsoftQuote.setLastPrice(5.55);
    microsoftQuote.setAskSize(10);
    microsoftQuote.setBidSize(2);
    microsoftQuote.setAskPrice(10.10);
    microsoftQuote.setBidPrice(3.33);
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
    quoteDao.saveAll(Arrays.asList(appleQuote, microsoftQuote, attQuote));
    testQuoteService.updateMarketData();
  }

  @Test
  public void saveQuote_Object() {
    Quote returned = testQuoteService.saveQuote(appleQuote);
    assertEquals("AAPL", returned.getTicker());
  }

  @Test
  public void saveQuote_Symbol() {
    Quote returned = testQuoteService.saveQuote("AAPL");
    assertNotNull(returned);
  }

  @Test
  public void getAllQuotes() {
    Iterable<Quote> quotes = testQuoteService.getAllQuotes();
    quotes.forEach(Assert::assertNotNull);
  }
}