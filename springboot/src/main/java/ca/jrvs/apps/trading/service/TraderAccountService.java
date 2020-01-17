package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.TraderAccountView;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.domain.Trader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TraderAccountService {

  TraderDao traderDao;
  AccountDao accountDao;
  SecurityOrderDao securityOrderDao;
  PositionDao positionDao;

  @Autowired
  public TraderAccountService(TraderDao traderDao, AccountDao accountDao,
      SecurityOrderDao securityOrderDao, PositionDao positionDao) {
    this.traderDao = traderDao;
    this.accountDao = accountDao;
    this.securityOrderDao = securityOrderDao;
    this.positionDao = positionDao;
  }

  /**
   * Create a new Trader in the DB using data obtained from the controller. A new account is created
   * for the user as well.
   *
   * @param newTrader A Trader made by the Controller
   * @return The trader after having made their account. The trader's ID should be updated
   * @throws IllegalArgumentException if the user's email is already registered to an account
   */
  public TraderAccountView createNewTraderAccount(Trader newTrader) {
    if (traderExists(newTrader)) {
      throw new IllegalArgumentException("An account with that email already exists!");
    } else {
      Account newAccount = new Account();
      newTrader = traderDao.save(newTrader);
      newAccount.setTraderId(newTrader.getId());
      newAccount.setAmount(0.00);
      newAccount = accountDao.save(newAccount);
      return new TraderAccountView(newTrader, newAccount);
    }
  }

  private boolean traderExists(Trader trader) {
    for (Trader t : traderDao.findAll()) {
      if (trader.getEmail().toLowerCase().equals(t.getEmail().toLowerCase())) {
        return true;
      }
    }
    return false;
  }

  /**
   * Deletes a trader if and only if they have no money and no outstanding orders on their account.
   *
   * @param id The ID of the trader to delete.
   * @throws EntityNotFoundException if the ID to delete doesn't exist
   * @throws IllegalStateException   if the Account has remaining funds or an outstanding order
   */
  public void deleteTraderById(int id) {
    Trader traderToDelete = traderDao.findById(id).orElseThrow(
        () -> new EntityNotFoundException("No Trader with that ID exists!")
    );
    Account accountToDelete = accountDao.findById(id).orElseThrow(
        () -> new EntityNotFoundException("No account with that ID exists!")
    );
    List<SecurityOrder> accountOrders = getAccountOrders(id);
    if (accountToDelete.getAmount() != 0 || hasPendingOrders(accountOrders)) {
      throw new IllegalStateException("Account may not be deleted. It may still have money in it"
          + " or it may have an outstanding order.");
    } else {
      for (SecurityOrder order : accountOrders) {
        securityOrderDao.deleteById(order.getId());
      }
      accountDao.deleteById(accountToDelete.getId());
      traderDao.deleteById(traderToDelete.getId());
    }
  }

  private List<SecurityOrder> getAccountOrders(int id) {
    List<SecurityOrder> orders = new ArrayList<>();
    for (SecurityOrder order : securityOrderDao.findAll()) {
      if (id == order.getAccountId()) {
        orders.add(order);
      }
    }
    return orders;
  }

  private boolean hasPendingOrders(List<SecurityOrder> orderList) {
    for (SecurityOrder order : orderList) {
      if (order.getStatus().toLowerCase().equals("pending")) {
        return true;
      }
    }
    return false;
  }

  /**
   * Deposits funds into an account.
   *
   * @param accountId     The ID of the account to deposit into
   * @param depositAmount The amount of money to deposit into the account
   * @return Updated account information
   * @throws IllegalArgumentException if the deposit amount is 0 or lower
   * @throws EntityNotFoundException  if the provided ID doesn't belong to a user
   */
  public Account deposit(int accountId, double depositAmount) {
    if (depositAmount <= 0) {
      throw new IllegalArgumentException("You must deposit at least one cent");
    }
    Optional<Account> accountOpt = accountDao.findById(accountId);
    Account account;
    if (accountOpt.isPresent()) {
      account = accountOpt.get();
      account.setAmount(account.getAmount() + depositAmount);
      accountDao.updateEntity(account);
      return account;
    } else {
      throw new EntityNotFoundException("No account with that ID exists!");
    }
  }

  /**
   * Withdraws withdrawAmount from the specified account, provided the user is not withdrawing more
   * than they have
   *
   * @param accountId      The ID of the account to deposit into
   * @param withdrawAmount the amount of money to deposit
   * @return The updated account
   * @throws IllegalArgumentException if withdraw Amount is 0 or lower
   * @throws IllegalStateException    if the withdrawal is larger than remaining funds
   * @throws EntityNotFoundException  if the given ID does not have an account tied to it
   */
  public Account withdraw(int accountId, double withdrawAmount) {
    if (withdrawAmount <= 0) {
      throw new IllegalArgumentException("Funds to withdraw must be greater than 0");
    }
    Account account = accountDao.findById(accountId).orElseThrow(
        () -> new EntityNotFoundException("No account with that ID exists!")
    );
    if (withdrawAmount > account.getAmount()) {
      throw new IllegalArgumentException("You cannot withdraw more money than you have!");
    }
    account.setAmount(account.getAmount() - withdrawAmount);
    accountDao.updateEntity(account);
    return account;
  }
}