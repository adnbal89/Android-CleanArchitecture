package com.adnanbal.fxdedektifi.sample.presentation.model;

/**
 * Class that represents a position in the presentation layer.
 */
public class PositionModel {

  private final int positionId;

  private String pair;
  // buy : 1 , sell : 0
  private boolean buy_sell;
  private double volume;
  private double profit;


  public PositionModel(int positionId, String pair, double volume, boolean buy_sell) {
    this.positionId = positionId;
    this.pair = pair;
    this.volume = volume;
    this.buy_sell = buy_sell;
  }

  public PositionModel(int positionId) {
    this.positionId = positionId;
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


  @Override
  public String toString() {
    return "PositionModel{" +
        "positionId=" + positionId +
        ", pair='" + pair + '\'' +
        ", buy_sell=" + buy_sell +
        ", volume=" + volume +
        ", profit=" + profit +
        '}';
  }
}
