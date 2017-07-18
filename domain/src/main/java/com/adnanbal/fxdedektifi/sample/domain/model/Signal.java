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

package com.adnanbal.fxdedektifi.sample.domain.model;

public class Signal extends Position {

  private double statusChangePrice;
  private String term;


  public Signal(int positionId) {
    super(positionId);
  }

  public Signal(int positionId, String pair, double volume, boolean buy_sell, double openingPrice,
      double statusChangePrice, String term, String status, boolean open, String comment) {
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
