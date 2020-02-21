package ca.jrvs.apps.trading.model.domain;

public class MarketOrder {

  private String symbol;
  private long size;
  private int accountId;
  private boolean isSellOrder;

  public int getAccountId() {
    return accountId;
  }

  public void setAccountId(int accountId) {
    this.accountId = accountId;
  }

  public boolean isSellOrder() {
    return isSellOrder;
  }

  public void setSellOrder(boolean sellOrder) {
    isSellOrder = sellOrder;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public long getSize() {
    return size;
  }

  public void setSize(long size) {
    this.size = size;
  }
}
