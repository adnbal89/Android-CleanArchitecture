package com.adnanbal.fxdedektifi.sample.domain.model;

/**
 * Class that represents a position in the domain layer.
 */
public class Position {

  private final int positionId;

  private String pair;
  // buy : 1 , sell : 0
  private boolean buy_sell;
  private double volume;
  private double profit;

  public Position(int positionId) {
    this.positionId = positionId;
  }

  public Position(int positionId, String pair, double volume, boolean buy_sell) {
    this.positionId = positionId;
    this.pair = pair;
    this.volume = volume;
    this.buy_sell = buy_sell;
  }

  public int getPositionId() {
    return positionId;
  }

  public String getPair() {
    return pair;
  }

  public void setPair(String pair) {
    this.pair = pair;
  }

  public boolean isBuy_sell() {
    return buy_sell;
  }

  public void setBuy_sell(boolean buy_sell) {
    this.buy_sell = buy_sell;
  }

  public double getVolume() {
    return volume;
  }

  public void setVolume(double volume) {
    this.volume = volume;
  }

  public double getProfit() {
    return profit;
  }

  public void setProfit(double profit) {
    this.profit = profit;
  }
}
