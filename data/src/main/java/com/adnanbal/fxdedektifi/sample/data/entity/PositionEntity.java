package com.adnanbal.fxdedektifi.sample.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Position Entity used in the data layer.
 */
public class PositionEntity {

  @SerializedName("id")
  private int positionId;

  @SerializedName("pair")
  private String pair;

  // buy : 1 , sell : 0
  @SerializedName("buy_sell")
  private boolean buy_sell;

  @SerializedName("volume")
  private double volume;

  @SerializedName("profit")
  private double profit;

  public PositionEntity() {
    //empty
  }

  public int getPositionId() {
    return positionId;
  }

  public void setPositionId(int positionId) {
    this.positionId = positionId;
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
