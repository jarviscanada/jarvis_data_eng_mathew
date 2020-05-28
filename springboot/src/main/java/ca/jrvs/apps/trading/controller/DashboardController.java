package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.model.TraderAccountView;
import ca.jrvs.apps.trading.model.TraderPortfolioView;
import ca.jrvs.apps.trading.service.DashboardService;
import ca.jrvs.apps.trading.utils.ResponseExceptionUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@CrossOrigin
@RequestMapping("/dashboard")
public class DashboardController {

  private DashboardService dashboardService;

  @Autowired
  public DashboardController(DashboardService dashboardService) {
    this.dashboardService = dashboardService;
  }

  /**
   * Get the Requested trader's profile and return it. Sample Usage: GET /dashboard/1543/profile
   *
   * @param traderId The ID of the trader to look up
   * @return A TraderAccountView corresponding to the trader
   */
  @GetMapping("/{traderId}/profile")
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "Get the Trader's profile", notes = "The Trader ID must exist",
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public TraderAccountView getTraderAccountView(@PathVariable int traderId) {
    try {
      return dashboardService.getTraderAccountView(traderId);
    } catch (Exception ex) {
      throw ResponseExceptionUtils.getResponseStatusException(ex);
    }
  }

  /**
   * Get a specific trader's portfolio and return it. Sample Usage: GET /dashboard/15432/portfolio
   *
   * @param traderId The ID of the trader to look up
   * @return The TraderPortfolioView corresponding to the trader
   */
  @GetMapping("/{traderId}/portfolio")
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "Get the trader's Portfolio", notes = "The trader must exist",
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public TraderPortfolioView getTraderPortfolioView(@PathVariable int traderId) {
    try {
      return dashboardService.getTraderPortfolioView(traderId);
    } catch (Exception ex) {
      throw ResponseExceptionUtils.getResponseStatusException(ex);
    }
  }
}
