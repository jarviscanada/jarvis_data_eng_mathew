package ca.jrvs.apps.trading.model;

import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.ArrayList;
import java.util.List;

public class TraderPortfolioView {

  private int traderId;
  private List<DetailedPosition> positions = new ArrayList<>();
  private double funds;

  static class DetailedPosition {

    private Position position;
    private Quote quote;

    DetailedPosition(Position p, Quote q) {
      position = p;
      quote = q;
    }

    public Position getPosition() {
      return position;
    }

    public void setPosition(Position position) {
      this.position = position;
    }

    public Quote getQuote() {
      return quote;
    }

    public void setQuote(Quote quote) {
      this.quote = quote;
    }
  }

  public void addDetailedPosition(Position p, Quote q) {
    positions.add(new DetailedPosition(p, q));
  }

  public int getTraderId() {
    return traderId;
  }

  public void setTraderId(int traderId) {
    this.traderId = traderId;
  }

  public List<DetailedPosition> getPositions() {
    return positions;
  }

  public void setPositions(List<DetailedPosition> positions) {
    this.positions = positions;
  }

  public double getFunds() {
    return funds;
  }

  public void setFunds(double funds) {
    this.funds = funds;
  }
}
