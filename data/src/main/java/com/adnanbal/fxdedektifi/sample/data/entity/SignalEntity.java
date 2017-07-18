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

package com.adnanbal.fxdedektifi.sample.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Signal Entity used in the data layer.
 */
public class SignalEntity extends PositionEntity {

  @SerializedName("statusChangePrice")
  private double statusChangePrice;
  @SerializedName("term")
  private String term;


  public SignalEntity() {
    //empty
  }

  public SignalEntity(int positionId, String pair, double volume, boolean buy_sell,
      double statusChangePrice, String term, double openingPrice, boolean open, String status,
      String comment) {
    super(positionId, pair, volume, buy_sell, openingPrice, open, status, comment);
    this.statusChangePrice = statusChangePrice;
    this.term = term;
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


}
