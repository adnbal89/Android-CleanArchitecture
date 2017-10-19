package com.adnanbal.fxdedektifi.forex.presentation.model;

import java.util.Date;

/**
 * Class that represents a position in the presentation layer.
 */
public class PositionModel {

  private String id;
  private String pair;
  // buy : 1 , sell : 0
  private boolean buy_sell;
  private double volume;
  private double profit;
  private double openingPrice;
  private double closingPrice;
  private boolean open;
  private String status;
  private String comment;
  private Date date;

  public PositionModel() {
    //empty constructor for Gson Mapper
  }

  public PositionModel(String id, String pair, double volume, boolean buy_sell,
      double openingPrice, boolean open, String status, String comment, Date date) {
    this.id = id;
    this.pair = pair;
    this.volume = volume;
    this.buy_sell = buy_sell;
    this.openingPrice = openingPrice;
    this.open = open;
    this.status = status;
    this.comment = comment;
    this.date = date;
  }

  public PositionModel(String pair, double volume, boolean buy_sell,
      double openingPrice, boolean open, String status, String comment, Date date) {
    this.pair = pair;
    this.volume = volume;
    this.buy_sell = buy_sell;
    this.openingPrice = openingPrice;
    this.open = open;
    this.status = status;
    this.comment = comment;
    this.date = date;
  }


  public PositionModel(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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


  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  @Override
  public String toString() {
    return "PositionModel{" +
        "id=" + id +
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

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    PositionModel that = (PositionModel) o;

    return id.equals(that.id);
  }
}