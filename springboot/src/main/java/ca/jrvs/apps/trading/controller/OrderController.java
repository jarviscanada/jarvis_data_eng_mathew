package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.model.domain.MarketOrder;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.service.OrderService;
import ca.jrvs.apps.trading.utils.ResponseExceptionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/order")
@Api(value = "Order", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OrderController {

  private OrderService orderService;

  @Autowired
  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  /**
   * Creates a Buy order for the given Account Id, provided that account has the funds to execute
   * the order. If it does not, the order is saved as pending. Params are sent in a query string.
   * <p>
   * Sample Usage: POST /order/buy?symbol="TSLA"&amount=50&price=110.37&accountId=4
   *
   * @param symbol    The Ticker symbol to buy from
   * @param amount    The amount of shares to buy
   * @param price     The price point at which you are buying shares
   * @param accountId The ID of the account buying shares
   * @return The order as saved in the DB
   */
  @PostMapping("/buy")
  @ResponseBody
  @ApiOperation(value = "Create a new Buy order",
      notes = "The account and Ticker must both exist, price must be at least one cent, and amount"
          + "must be at least one. If not enough money, order is made Pending, else Filled")
  public SecurityOrder makeBuyOrder(@RequestParam("symbol") String symbol,
      @RequestParam("amount") int amount, @RequestParam("price") double price,
      @RequestParam("accountid") int accountId) {
    MarketOrder marketOrder = getOrder(symbol, amount, price, accountId);
    marketOrder.setSellOrder(false);
    try {
      return orderService.executeOrder(marketOrder);
    } catch (Exception ex) {
      throw ResponseExceptionUtils.getResponseStatusException(ex);
    }
  }

  /**
   * Creates a Sell order for the given Account Id, provided that account has the position to
   * execute the order. If it does not, the order is saved as pending. Params are sent in a query
   * string.
   * <p>
   * Sample Usage: POST /order/sell?symbol="TSLA"&amount=50&price=110.37&accountId=4
   *
   * @param symbol    The Ticker symbol to buy from
   * @param amount    The amount of shares to buy
   * @param price     The price point at which you are buying shares
   * @param accountId The ID of the account buying shares
   * @return The order as saved in the DB
   */
  @PostMapping("/sell")
  @ResponseBody
  @ApiOperation(value = "Create a new Sell order",
      notes = "Symbol and account ID must exist, price must be at least 1 cent, and amount must be"
          + "greater than zero. Order saved as Pending if not enough Position, otherwise Filled")
  public SecurityOrder makeSellOrder(@RequestParam("symbol") String symbol,
      @RequestParam("amount") int amount, @RequestParam("price") double price,
      @RequestParam("accountid") int accountId) {
    MarketOrder marketOrder = getOrder(symbol, amount, price, accountId);
    marketOrder.setSellOrder(true);
    try {
      return orderService.executeOrder(marketOrder);
    } catch (Exception ex) {
      throw ResponseExceptionUtils.getResponseStatusException(ex);
    }
  }

  private MarketOrder getOrder(String symbol, int amount, double price, int accountId) {
    MarketOrder marketOrder = new MarketOrder();
    marketOrder.setAccountId(accountId);
    marketOrder.setSize(amount);
    marketOrder.setPrice(price);
    marketOrder.setSymbol(symbol);
    return marketOrder;
  }
}
