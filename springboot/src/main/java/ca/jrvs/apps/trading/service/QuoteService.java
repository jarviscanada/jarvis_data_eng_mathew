package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuoteService {

  private static final Logger logger = LoggerFactory.getLogger(QuoteService.class);

  private MarketDataDao marketDataDao;

  @Autowired
  public QuoteService(MarketDataDao marketDataDao) {
    this.marketDataDao = marketDataDao;
  }

  public IexQuote getIexQuoteBySymbol(String symbol) {
    return marketDataDao.findById(symbol).orElseThrow(() ->
        new IllegalArgumentException("IEX Data could not be retrieved for Symbol" + symbol));
  }

  public Iterable<IexQuote> getMultipleIexQuoteBySymbol(String... symbols) {
    return marketDataDao.findAllById(Arrays.asList(symbols));
  }
}
