package ca.jrvs.apps.trading.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.MarketOrder;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceUnitTest {

  @Mock
  SecurityOrderDao mockSecurityDao;
  @Mock
  QuoteDao mockQuoteDao;
  @Mock
  PositionDao mockPositionDao;
  @Mock
  AccountDao mockAccountDao;
  Account sampleAccount;
  Quote sampleQuote;
  MarketOrder sampleMarketOrder;
  SecurityOrder sampleSecurityOrder;
  List<Position> samplePositions;
  Position samplePosition1;
  double amountBefore;

  @InjectMocks
  OrderService testOrderService;

  @Before
  public void setup() {
    sampleQuote = new Quote();
    sampleQuote.setTicker("AAPL");
    sampleQuote.setLastPrice(15.15);
    sampleQuote.setBidPrice(14.55);
    sampleQuote.setAskPrice(16.88);
    sampleQuote.setBidSize(30);
    sampleQuote.setAskSize(33);

    sampleMarketOrder = new MarketOrder();
    sampleMarketOrder.setAccountId(1);
    sampleMarketOrder.setSymbol("AAPL");
    sampleMarketOrder.setSize(100);
    sampleMarketOrder.setSellOrder(false);

    sampleSecurityOrder = new SecurityOrder();
    sampleSecurityOrder.setId(1);
    sampleSecurityOrder.setTicker("AAPL");
    sampleSecurityOrder.setAccountId(1);
    sampleSecurityOrder.setStatus("PENDING");
    sampleSecurityOrder.setPrice(1.00);
    sampleSecurityOrder.setSize(100);
    sampleSecurityOrder.setNotes("");

    sampleAccount = new Account();
    sampleAccount.setId(1);
    sampleAccount.setTraderId(1);
    sampleAccount.setAmount(1000000.00);
    amountBefore = 1000000.00;

    samplePositions = new ArrayList<>();
    samplePosition1 = new Position();
    samplePosition1.setId(1);
    samplePosition1.setTicker("AAPL");
    samplePosition1.setPosition(300);
    samplePositions.add(samplePosition1);

    when(mockSecurityDao.save(any())).then(AdditionalAnswers.returnsFirstArg());
    when(mockAccountDao.existsById(any())).thenReturn(false);
    when(mockAccountDao.existsById(1)).thenReturn(true);
    when(mockQuoteDao.existsById(any())).thenReturn(false);
    when(mockQuoteDao.existsById("AAPL")).thenReturn(true);
    when(mockQuoteDao.findById(any())).thenReturn(Optional.of(sampleQuote));
    when(mockPositionDao.findAllById(any())).thenReturn(samplePositions);
    when(mockAccountDao.findById(any())).thenReturn(Optional.empty());
    when(mockAccountDao.findById(1)).thenReturn(Optional.of(sampleAccount));
    when(mockAccountDao.updateEntity(any())).thenReturn(1);
  }

  @Test
  public void executeOrder_Buy() {
    SecurityOrder returnedOrder;
    returnedOrder = testOrderService.executeOrder(sampleMarketOrder);
    assertEquals("FILLED", returnedOrder.getStatus().toUpperCase());
    assertTrue(returnedOrder.getNotes().length() > 0);
    assertTrue(amountBefore > sampleAccount.getAmount());

  }

  @Test
  public void executeOrder_Sell() {
    sampleMarketOrder.setSellOrder(true);
    sampleSecurityOrder = testOrderService.executeOrder(sampleMarketOrder);
    assertEquals("FILLED", sampleSecurityOrder.getStatus().toUpperCase());
    assertTrue(sampleSecurityOrder.getNotes().length() > 0);
    assertTrue(amountBefore < sampleAccount.getAmount());
  }

  @Test
  public void executeOrder_BuyNoMoney() {
    sampleAccount.setAmount(0.00);
    SecurityOrder returnedOrder;
    returnedOrder = testOrderService.executeOrder(sampleMarketOrder);
    assertEquals("CANCELLED", returnedOrder.getStatus().toUpperCase());
    assertTrue(0 < returnedOrder.getNotes().length());
    assertEquals(0.00, sampleAccount.getAmount(), 0);
  }

  @Test
  public void executeOrder_SellNoPosition() {
    samplePosition1.setPosition(0);
    sampleMarketOrder.setSellOrder(true);
    SecurityOrder returnedOrder;
    returnedOrder = testOrderService.executeOrder(sampleMarketOrder);
    assertEquals("CANCELLED", returnedOrder.getStatus().toUpperCase());
    assertTrue(0 < returnedOrder.getNotes().length());
    assertEquals(amountBefore, sampleAccount.getAmount(), 0.0);

  }

  @Test(expected = EntityNotFoundException.class)
  public void executeOrder_BadTicker() {
    sampleMarketOrder.setSymbol("B A D");
    testOrderService.executeOrder(sampleMarketOrder);
  }

  @Test(expected = IllegalArgumentException.class)
  public void executeOrder_BadSize() {
    sampleMarketOrder.setSize(0);
    testOrderService.executeOrder(sampleMarketOrder);

  }

  @Test(expected = EntityNotFoundException.class)
  public void executeOrder_BadAccount() {
    sampleMarketOrder.setAccountId(9999);
    testOrderService.executeOrder(sampleMarketOrder);

  }
}