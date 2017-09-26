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

import java.util.Date;

public class ProfitModel {

  Date date;
  double open_price;
  double close_price;

  public ProfitModel(Date date, double open_price, double close_price) {
    this.date = date;
    this.open_price = open_price;
    this.close_price = close_price;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public double getOpen_price() {
    return open_price;
  }

  public void setOpen_price(double open_price) {
    this.open_price = open_price;
  }

  public double getClose_price() {
    return close_price;
  }

  public void setClose_price(double close_price) {
    this.close_price = close_price;
  }

  @Override
  public String toString() {
    return "ProfitModel{" +
        "date=" + date +
        ", open_price=" + open_price +
        ", close_price=" + close_price +
        '}';
  }
}
