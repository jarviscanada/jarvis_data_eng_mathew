package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.service.QuoteService;
import ca.jrvs.apps.trading.utils.ResponseExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.IncorrectResultSetColumnCountException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/quote")
public class QuoteController {

  QuoteService quoteService;

  @Autowired
  public QuoteController(QuoteService quoteService) {
    this.quoteService = quoteService;
  }

  /**
   * Requests one quote from IEX. Example: GET /quote/iex/ticker/goog/
   *
   * @param symbol The symbol to query IEX for
   * @return A Quote from IEX
   */
  @GetMapping(path = "/iex/ticker/{symbol}")
  @ResponseStatus(value = HttpStatus.OK)
  @ResponseBody
  public IexQuote getIexQuoteBySymbol(@PathVariable String symbol) {
    try {
      return quoteService.getIexQuoteBySymbol(symbol);
    } catch (IllegalArgumentException iaex) {
      throw ResponseExceptionUtils.getResponseStatusException(iaex);
    }
  }

  /**
   * Requests a quote from IEX for all specified symbols. Example: GET
   * /quote/iex/ticker/?symbols=aapl,goog,T
   *
   * @param symbols The symbols to request a quote from IEX for
   * @return A list of quotes from IEX
   */
  @GetMapping(path = "/iex/ticker")
  @ResponseStatus(value = HttpStatus.OK)
  @ResponseBody
  public Iterable<IexQuote> getMultipleIexQuoteBySymbol(@RequestParam("symbols") String[] symbols) {
    try {
      return quoteService.getMultipleIexQuoteBySymbol(symbols);
    } catch (IllegalArgumentException iaex) {
      throw ResponseExceptionUtils.getResponseStatusException(iaex);
    }
  }

  /**
   * Updates the internal Database with data from IEX. Usage: PUT /quote/iexMarketData
   */
  @PutMapping(path = "/iexMarketData")
  @ResponseStatus(HttpStatus.OK)
  public void updateMarketData() {
    try {
      quoteService.updateMarketData();
    } catch (IllegalArgumentException | IncorrectResultSetColumnCountException ex) {
      throw ResponseExceptionUtils.getResponseStatusException(ex);
    }
  }

  /**
   * Updates the data for a symbol in the Database using data received from the user Sample usage:
   * PUT /quote/update Body:{'ticker': 'AAPL', 'lastPrice': 125.34 ...}
   *
   * @param quote The quote to persist in the DB
   * @return the Quote sent by the user
   */
  @PutMapping("/update")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public Quote createQuote(@RequestBody Quote quote) {
    try {
      return quoteService.saveQuote(quote);
    } catch (Exception ex) {
      throw ResponseExceptionUtils.getResponseStatusException(ex);
    }
  }

  /**
   * Adds a new symbol to track to the internal database, or updates the Symbol's information if its
   * already present Sample usage: POST /quote/iex/ticker/track?symbols=AAPL,MSFT,T
   *
   * @param ticker the Symbol to track
   * @return the saved quote
   */
  @PostMapping("/track/{ticker}")
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  public Quote createQuote(@PathVariable("ticker") String ticker) {
    try {
      return quoteService.saveQuote(ticker);
    } catch (Exception ex) {
      throw ResponseExceptionUtils.getResponseStatusException(ex);
    }
  }

  /**
   * Gets locally persisted data on all tracked Symbols. Sample usage: GET /quote/dailylist
   *
   * @return A collection of quotes.
   */
  @GetMapping("/dailylist")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public Iterable<Quote> getDailyList() {
    try {
      return quoteService.getAllQuotes();
    } catch (Exception ex) {
      throw ResponseExceptionUtils.getResponseStatusException(ex);
    }
  }
}
