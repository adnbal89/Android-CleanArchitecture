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

package com.adnanbal.fxdedektifi.forex.data.entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Signal Entity used in the data layer.
 */
public class SignalEntity extends PositionEntity {

  private double statusChangePrice;
  private String term;
  public Map<String, Boolean> users;
  private Map<String, List<String>> changedFields;

  public SignalEntity() {
    //empty constructor
  }

  public SignalEntity(String id, String pair, double volume, boolean buy_sell,
      double statusChangePrice, String term, double openingPrice, boolean open, String status,
      String comment, Date date, Map<String, Boolean> users,
      Map<String, List<String>> changedFields) {
    super(id, pair, volume, buy_sell, openingPrice, open, status, comment, date);
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
    return "SignalEntity{" +
        "statusChangePrice=" + statusChangePrice +
        ", term='" + term + '\'' +
        '}' + "  ID : " + super.getId() + "  PAIR : " + super.getPair();
  }


}
