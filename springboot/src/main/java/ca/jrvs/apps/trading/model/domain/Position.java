package ca.jrvs.apps.trading.model.domain;

import ca.jrvs.apps.trading.model.Entity;

public class Position implements Entity<Integer> {

  private int id;
  private String ticker;
  private long position;

  @Override
  public Integer getId() {
    return id;
  }

  @Override
  public void setId(Integer id) {
    this.id = id;
  }

  public String getTicker() {
    return ticker;
  }

  public void setTicker(String ticker) {
    this.ticker = ticker;
  }

  public long getPosition() {
    return position;
  }

  public void setPosition(long position) {
    this.position = position;
  }
}
