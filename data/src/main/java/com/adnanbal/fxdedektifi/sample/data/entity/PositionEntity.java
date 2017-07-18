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

  @SerializedName("openingPrice")
  private double openingPrice;

  @SerializedName("closingPrice")
  private double closingPrice;

  @SerializedName("open")
  private boolean open;

  @SerializedName("status")
  private String status;

  @SerializedName("comment")
  private String comment;

  public PositionEntity() {
    //empty
  }


  public PositionEntity(int positionId, String pair, double volume, boolean buy_sell,
      double openingPrice, boolean open, String status, String comment) {
    this.positionId = positionId;
    this.pair = pair;
    this.volume = volume;
    this.buy_sell = buy_sell;
    this.openingPrice = openingPrice;
    this.open = open;
    this.status = status;
    this.comment = comment;
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

  public double getOpeningPrice() {
    return openingPrice;
  }

  public void setOpeningPrice(double openingPrice) {
    this.openingPrice = openingPrice;
  }

  public double getClosingPrice() {
    return closingPrice;
  }

  public void setClosingPrice(double closingPrice) {
    this.closingPrice = closingPrice;
  }

  public boolean isOpen() {
    return open;
  }

  public void setOpen(boolean open) {
    this.open = open;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  @Override
  public String toString() {
    return "PositionEntity{" +
        "positionId=" + positionId +
        ", pair='" + pair + '\'' +
        ", buy_sell=" + buy_sell +
        ", volume=" + volume +
        ", profit=" + profit +
        ", openingPrice=" + openingPrice +
        ", closingPrice=" + closingPrice +
        ", open=" + open +
        ", status='" + status + '\'' +
        ", comment='" + comment + '\'' +
        '}';
  }
}
