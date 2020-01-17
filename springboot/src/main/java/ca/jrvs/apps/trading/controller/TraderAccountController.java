package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.model.TraderAccountView;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.service.TraderAccountService;
import ca.jrvs.apps.trading.utils.ResponseExceptionUtils;
import io.swagger.annotations.Api;
import java.sql.Date;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/trader")
@Api(value = "Trader", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TraderAccountController {

  private TraderAccountService traderAccountService;

  @Autowired
  public TraderAccountController(TraderAccountService traderAccountService) {
    this.traderAccountService = traderAccountService;
  }

  /**
   * Creates a new account using request parameters as data. Sample Usage: POST
   * /trader/create?firstname=First&lastname=Last&email=flast@email.org&birthdate=11-05-1994
   *
   * @param firstName the trader's first name
   * @param lastName  the trader's last name
   * @param email     the trader's email address
   * @param country   the trader's home country
   * @param dob       the trader's date of birth
   * @return The trader's profile and account info
   */
  @PostMapping("/create")
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  public TraderAccountView createNewTraderAccount(@RequestParam("firstname") String firstName,
      @RequestParam("lastname") String lastName, @RequestParam("email") String email,
      @RequestParam("country") String country,
      @RequestParam("birthdate") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dob) {
    Trader newTrader = new Trader();
    newTrader.setFirstName(firstName);
    newTrader.setLastName(lastName);
    newTrader.setCountry(country);
    newTrader.setEmail(email);
    newTrader.setDob(Date.valueOf(dob));
    try {
      return traderAccountService.createNewTraderAccount(newTrader);
    } catch (Exception ex) {
      throw ResponseExceptionUtils.getResponseStatusException(ex);
    }
  }

  /**
   * Deletes the account of the specified trader. May only be used if they have no finds and no
   * outstanding orders. Sample Usage: DELETE /trader/delete/14576
   *
   * @param traderId The ID of the trader to delete.
   */
  @DeleteMapping("/delete/{traderId}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteTraderAccount(@PathVariable int traderId) {
    try {
      traderAccountService.deleteTraderById(traderId);
    } catch (Exception ex) {
      throw ResponseExceptionUtils.getResponseStatusException(ex);
    }
  }

  /**
   * Deposit amount into the specified trader's account. Sample usage: PUT
   * /trader/deposit/15987?amount=1745.23
   *
   * @param traderId The ID of the trader making the deposit
   * @param amount   The amount of money to add to their account
   * @return The user's updated account
   */
  @PutMapping("/deposit/{traderId}")
  @ResponseBody
  public Account depositFunds(@PathVariable int traderId, @RequestParam("amount") double amount) {
    try {
      return traderAccountService.deposit(traderId, amount);
    } catch (Exception ex) {
      throw ResponseExceptionUtils.getResponseStatusException(ex);
    }
  }

  /**
   * Withdraws amount from the specified Trader's account. Sample usage: PUT
   * /trader/withdraw/15987?amount=101.01
   *
   * @param traderId The ID of the trader account to withdraw from
   * @param amount   The amount of money to withdraw
   * @return The updated account info
   */
  @PutMapping("/withdraw/{traderId}")
  @ResponseBody
  public Account withdrawFunds(@PathVariable int traderId, @RequestParam("amount") double amount) {
    try {
      return traderAccountService.withdraw(traderId, amount);
    } catch (Exception ex) {
      throw ResponseExceptionUtils.getResponseStatusException(ex);
    }

  }
}
