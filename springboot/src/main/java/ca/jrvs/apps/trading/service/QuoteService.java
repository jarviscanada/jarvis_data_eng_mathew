package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class QuoteService {

  private static final Logger logger = LoggerFactory.getLogger(QuoteService.class);

  private MarketDataDao marketDataDao;
  private QuoteDao quoteDao;

  @Autowired
  public QuoteService(MarketDataDao marketDataDao, QuoteDao quoteDao) {
    this.marketDataDao = marketDataDao;
    this.quoteDao = quoteDao;
  }

  public IexQuote getIexQuoteBySymbol(String symbol) {
    return marketDataDao.findById(symbol).orElseThrow(() ->
        new IllegalArgumentException("IEX Data could not be retrieved for Symbol " + symbol));
  }

  public Iterable<IexQuote> getMultipleIexQuoteBySymbol(String... symbols) {
    return marketDataDao.findAllById(Arrays.asList(symbols));
  }

  /**
   * Updates internal DB using data from IEX using the following process: - Read all quotes from
   * internal DB to get tracked symbols - Query IEX for updated data on all symbols - Convert IEX
   * Quotes to internal format - Update internal DB records
   */
  public void updateMarketData() {
    Iterable<IexQuote> iexQuotes = marketDataDao.findAllById(getSymbolsFromDB());
    List<Quote> newQuotes = iexToQuote(iexQuotes);
    quoteDao.saveAll(newQuotes);
  }

  /**
   * Saves a quote in the internal Database by looking up a Symbol's data on IEX
   *
   * @param symbol the symbol to get data for
   * @return the saved quote
   */
  public Quote saveQuote(String symbol) {
    return quoteDao.save(iexToQuote(getIexQuoteBySymbol(symbol)));
  }

  /**
   * Save a pre-built quote in the Database
   *
   * @param quote the quote to save
   * @return the saved quote
   */
  public Quote saveQuote(Quote quote) {
    return quoteDao.save(quote);
  }

  public Iterable<Quote> getAllQuotes() {
    return quoteDao.findAll();
  }

  private List<String> getSymbolsFromDB() {
    Iterable<Quote> quotes = quoteDao.findAll();
    List<String> symbols = new ArrayList<>();
    for (Quote q : quotes) {
      symbols.add(q.getId());
    }
    return symbols;
  }

  private List<Quote> iexToQuote(Iterable<IexQuote> iexQuotes) {
    List<Quote> quotes = new ArrayList<>();
    for (IexQuote iex : iexQuotes) {
      Quote quote = iexToQuote(iex);
      quotes.add(quote);
    }
    return quotes;
  }

  private Quote iexToQuote(IexQuote iex) {
    Quote quote = new Quote();
    quote.setTicker(iex.getSymbol());
    quote.setLastPrice(iex.getLatestPrice());
    quote.setBidPrice(iex.getIexBidPrice());
    quote.setBidSize(iex.getIexBidSize());
    quote.setAskPrice(iex.getIexAskPrice());
    quote.setAskSize(iex.getIexAskSize());

    return quote;
  }
}
