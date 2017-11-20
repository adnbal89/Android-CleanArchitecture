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

package com.adnanbal.fxdedektifi.forex.domain.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Signal extends Position {

  private double statusChangePrice;
  private String term;
  public Map<String, Boolean> users;
  private Map<String, List<String>> changedFields;

  public Signal(String positionId) {
    super(positionId);
  }

  public Signal(String positionId, String pair, double volume, boolean buy_sell,
      double openingPrice,
      double statusChangePrice, String term, String status, boolean open, String comment, Date date,
      Map<String, Boolean> users, Map<String, List<String>> changedFields, Double take_profit_price,
      Double stop_loss_price, boolean hitStopLoss,
      boolean hitTakeProfit) {
    super(positionId, pair, volume, buy_sell, openingPrice, open, status, comment, date,
        take_profit_price, stop_loss_price, hitStopLoss,
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
}
