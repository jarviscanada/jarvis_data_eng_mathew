package ca.jrvs.apps.trading.model.domain;

import java.util.List;

public class TraderPortfolioView {

  private int traderId;
  private List<SecurityOrder> orders;
  private double funds;

  public int getTraderId() {
    return traderId;
  }

  public void setTraderId(int traderId) {
    this.traderId = traderId;
  }

  public List<SecurityOrder> getOrders() {
    return orders;
  }

  public void setOrders(List<SecurityOrder> orders) {
    this.orders = orders;
  }

  public double getFunds() {
    return funds;
  }

  public void setFunds(double funds) {
    this.funds = funds;
  }
}
