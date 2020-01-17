package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.service.QuoteService;
import ca.jrvs.apps.trading.utils.ResponseExceptionUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.IncorrectResultSetColumnCountException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/quote")
@Api(value = "Quote", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
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
  @GetMapping(path = "/iex/ticker/")
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
}
