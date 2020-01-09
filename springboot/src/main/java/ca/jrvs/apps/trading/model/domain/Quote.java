package ca.jrvs.apps.trading.model.domain;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public class Quote {

  private static final String DB_SYMBOL = "ticker";
  private static final String DB_ASKPRICE = "ask_price";
  private static final String DB_ASKSIZE = "ask_size";
  private static final String DB_BIDPRICE = "bid_price";
  private static final String DB_BIDSIZE = "bid_size";
  private static final String DB_LASTPRICE = "last_price";

  private int id;
  private String ticker;
  private double askPrice;
  private int askSize;
  private double bidPrice;
  private int bidSize;
  private double lastPrice;

  public SqlParameterSource getSqlValues() {
    MapSqlParameterSource valueMap = new MapSqlParameterSource();
    valueMap.addValue(DB_SYMBOL, ticker);
    valueMap.addValue(DB_LASTPRICE, lastPrice);
    valueMap.addValue(DB_BIDPRICE, bidPrice);
    valueMap.addValue(DB_BIDSIZE, bidSize);
    valueMap.addValue(DB_ASKPRICE, askPrice);
    valueMap.addValue(DB_ASKSIZE, askSize);
    return valueMap;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTicker() {
    return ticker;
  }

  public void setTicker(String ticker) {
    this.ticker = ticker;
  }

  public double getAskPrice() {
    return askPrice;
  }

  public void setAskPrice(double askPrice) {
    this.askPrice = askPrice;
  }

  public int getAskSize() {
    return askSize;
  }

  public void setAskSize(int askSize) {
    this.askSize = askSize;
  }

  public double getBidPrice() {
    return bidPrice;
  }

  public void setBidPrice(double bidPrice) {
    this.bidPrice = bidPrice;
  }

  public int getBidSize() {
    return bidSize;
  }

  public void setBidSize(int bidSize) {
    this.bidSize = bidSize;
  }

  public double getLastPrice() {
    return lastPrice;
  }

  public void setLastPrice(double lastPrice) {
    this.lastPrice = lastPrice;
  }

  @Override
  public String toString() {
    return "Quote{" + "symbol='" + ticker + '\'' + ", askPrice=" + askPrice + ", askSize=" + askSize
        + ", bidPrice=" + bidPrice + ", bidSize=" + bidSize + ", lastPrice=" + lastPrice + '}';
  }
}
