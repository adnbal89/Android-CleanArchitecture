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

package com.adnanbal.fxdedektifi.forex.domain.interactor;

import com.adnanbal.fxdedektifi.forex.domain.executor.PostExecutionThread;
import com.adnanbal.fxdedektifi.forex.domain.executor.ThreadExecutor;
import com.adnanbal.fxdedektifi.forex.domain.repository.NewSignalRepository;
import com.fernandocejas.arrow.checks.Preconditions;
import io.reactivex.Observable;
import java.util.Map;
import javax.inject.Inject;


public class NewSignal extends UseCase<Boolean, NewSignal.Params> {

  /**
   * This class is an implementation of {@link UseCase} .
   */
  private final NewSignalRepository newSignalRepository;

  @Inject
  NewSignal(NewSignalRepository newSignalRepository,
      ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.newSignalRepository = newSignalRepository;
  }

  @Override
  Observable<Boolean> buildUseCaseObservable(Params params) {
    Preconditions.checkNotNull(params);
    return this.newSignalRepository
        .createNewSignal(params.positionId, params.pair, params.volume, params.buy_sell,
            params.openingPrice,
            params.open,
            params.status, params.comment, params.date, params.operationOnSignal,
            params.take_profit_price, params.stop_loss_price, params.closingPrice,
            params.hitStopLoss, params.hitTakeProfit);
  }


  public static final class Params {

    String positionId;
    String pair;
    double volume;
    boolean buy_sell;
    double openingPrice;
    boolean open;
    String status;
    String comment;
    Map<String, String> date;
    String operationOnSignal;
    Double take_profit_price;
    Double stop_loss_price;
    double closingPrice;
    boolean hitStopLoss;
    boolean hitTakeProfit;

    private Params(String positionId, String pair, double volume, boolean buy_sell,
        double openingPrice, boolean open, String status, String comment,
        Map<String, String> date, String operationOnSignal, Double take_profit_price,
        Double stop_loss_price, double closingPrice, boolean hitStopLoss,
        boolean hitTakeProfit) {

      this.positionId = positionId;
      this.pair = pair;
      this.volume = volume;
      this.buy_sell = buy_sell;
      this.openingPrice = openingPrice;
      this.open = open;
      this.status = status;
      this.comment = comment;
      this.date = date;
      this.operationOnSignal = operationOnSignal;
      this.take_profit_price = take_profit_price;
      this.stop_loss_price = stop_loss_price;
      this.closingPrice = closingPrice;
      this.hitStopLoss = hitStopLoss;
      this.hitTakeProfit = hitTakeProfit;
    }

    public static NewSignal.Params forPosition(String positionId, String pair, double volume,
        boolean buy_sell,
        double openingPrice, boolean open, String status, String comment,
        Map<String, String> date, String operationOnSignal, Double take_profit_price,
        Double stop_loss_price, double closingPrice, boolean hitStopLoss,
        boolean hitTakeProfit) {
      return new NewSignal.Params(positionId, pair, volume, buy_sell, openingPrice, open, status,
          comment, date, operationOnSignal, take_profit_price, stop_loss_price, closingPrice,
          hitStopLoss, hitTakeProfit);
    }
  }
}
