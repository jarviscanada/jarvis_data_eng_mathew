package ca.jrvs.apps.trading.model.domain;

import java.util.List;

public class TraderPortfolioView {

  private int traderId;
  private List<Position> positions;
  private double funds;

  public int getTraderId() {
    return traderId;
  }

  public void setTraderId(int traderId) {
    this.traderId = traderId;
  }

  public List<Position> getPositions() {
    return positions;
  }

  public void setPositions(List<Position> positions) {
    this.positions = positions;
  }

  public double getFunds() {
    return funds;
  }

  public void setFunds(double funds) {
    this.funds = funds;
  }
}
