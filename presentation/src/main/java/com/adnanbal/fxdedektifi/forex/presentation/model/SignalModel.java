/*
 * *
 *  * Copyright (C) 2017 Adnan BAL Open Source Project
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.adnanbal.fxdedektifi.forex.presentation.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SignalModel extends PositionModel implements Serializable {


  private double statusChangePrice;
  private String term;
  private Map<String, Boolean> users;
  private Map<String, List<String>> changedFields;

  public SignalModel() {
    //empty constructor
  }

  public SignalModel(String id) {
    super(id);
  }

  public SignalModel(String id, String pair, double volume, boolean buy_sell,
      double statusChangePrice, String term, double openingPrice, boolean open, String status,
      String comment, Date date, Map<String, Boolean> users,
      Map<String, List<String>> changedFields, Double take_profit_price, Double stop_loss_price,
      boolean hitStopLoss,
      boolean hitTakeProfit) {
    super(id, pair, volume, buy_sell, openingPrice, open, status, comment, date, take_profit_price,
        stop_loss_price, hitStopLoss,
        hitTakeProfit);
    this.statusChangePrice = statusChangePrice;
    this.term = term;
    this.users = users;
    this.changedFields = changedFields;
  }

  public double getStatusChangePrice() {
    return statusChangePrice;
  }

  public void setStatusChangePrice(double statusChangePrice) {
    this.statusChangePrice = statusChangePrice;
  }

  public String getTerm() {
    return term;
  }

  public void setTerm(String term) {
    this.term = term;
  }

  public Map<String, Boolean> getUsers() {
    return users;
  }

  public void setUsers(Map<String, Boolean> users) {
    this.users = users;
  }

  public Map<String, List<String>> getChangedFields() {
    return changedFields;
  }

  public void setChangedFields(
      Map<String, List<String>> changedFields) {
    this.changedFields = changedFields;
  }

  @Override
  public String toString() {
    return "SignalModel{" +
        "statusChangePrice=" + statusChangePrice +
        ", term='" + term + '\'' +
        '}';

  }


}
