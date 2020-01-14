package ca.jrvs.apps.trading.model.domain;

import ca.jrvs.apps.trading.model.Entity;

public class Account implements Entity<Integer> {

  private int id;
  private int traderId;
  private double amount;

  @Override
  public Integer getId() {
    return id;
  }

  @Override
  public void setId(Integer id) {
    this.id = id;
  }
}
