package com.adnanbal.fxdedektifi.forex.domain.model;

import java.util.Map;


public class NewPosition {

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
  private Map<String, String> date;
  private Double take_profit_price;
  private Double stop_loss_price;
  private boolean hitStopLoss;
  private boolean hitTakeProfit;

  public boolean isHitStopLoss() {
    return hitStopLoss;
  }

  public void setHitStopLoss(boolean hitStopLoss) {
    this.hitStopLoss = hitStopLoss;
  }

  public boolean isHitTakeProfit() {
    return hitTakeProfit;
  }

  public void setHitTakeProfit(boolean hitTakeProfit) {
    this.hitTakeProfit = hitTakeProfit;
  }

  public NewPosition() {
    //empty constructor for Gson Mapper
  }

  public NewPosition(String id, String pair, double volume, boolean buy_sell,
      double openingPrice, boolean open, String status, String comment,
      Map<String, String> date, Double take_profit_price, Double stop_loss_price,
      boolean hitStopLoss,
      boolean hitTakeProfit) {
    this.id = id;
    this.pair = pair;
    this.volume = volume;
    this.buy_sell = buy_sell;
    this.openingPrice = openingPrice;
    this.open = open;
    this.status = status;
    this.comment = comment;
    this.date = date;
    this.take_profit_price = take_profit_price;
    this.stop_loss_price = stop_loss_price;
    this.hitStopLoss = hitStopLoss;
    this.hitTakeProfit = hitTakeProfit;

  }

  public NewPosition(String pair, double volume, boolean buy_sell,
      double openingPrice, boolean open, String status, String comment,
      Map<String, String> date, Double take_profit_price, Double stop_loss_price,
      boolean hitStopLoss,
      boolean hitTakeProfit) {
    this.pair = pair;
    this.volume = volume;
    this.buy_sell = buy_sell;
    this.openingPrice = openingPrice;
    this.open = open;
    this.status = status;
    this.comment = comment;
    this.date = date;
    this.take_profit_price = take_profit_price;
    this.stop_loss_price = stop_loss_price;
    this.hitStopLoss = hitStopLoss;
    this.hitTakeProfit = hitTakeProfit;
  }


  public NewPosition(String id) {
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


  public Map<String, String> getDate() {
    return date;
  }

  public void setDate(Map<String, String> date) {
    this.date = date;
  }

  public Double getTake_profit_price() {
    return take_profit_price;
  }

  public void setTake_profit_price(Double take_profit_price) {
    this.take_profit_price = take_profit_price;
  }

  public Double getStop_loss_price() {
    return stop_loss_price;
  }

  public void setStop_loss_price(Double stop_loss_price) {
    this.stop_loss_price = stop_loss_price;
  }

  @Override
  public String toString() {
    return "NewPosition{" +
        "id='" + id + '\'' +
        ", pair='" + pair + '\'' +
        ", buy_sell=" + buy_sell +
        ", volume=" + volume +
        ", profit=" + profit +
        ", openingPrice=" + openingPrice +
        ", closingPrice=" + closingPrice +
        ", open=" + open +
        ", status='" + status + '\'' +
        ", comment='" + comment + '\'' +
        ", date=" + date +
        ", take_profit_price=" + take_profit_price +
        ", stop_loss_price=" + stop_loss_price +
        ", hitStopLoss=" + hitStopLoss +
        ", hitTakeProfit=" + hitTakeProfit +
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

    NewPosition that = (NewPosition) o;

    return id.equals(that.id);
  }
}



