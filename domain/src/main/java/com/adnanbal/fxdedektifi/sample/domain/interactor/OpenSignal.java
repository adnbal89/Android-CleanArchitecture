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

package com.adnanbal.fxdedektifi.sample.domain.interactor;

import com.adnanbal.fxdedektifi.sample.domain.executor.PostExecutionThread;
import com.adnanbal.fxdedektifi.sample.domain.executor.ThreadExecutor;
import com.adnanbal.fxdedektifi.sample.domain.model.Signal;
import com.adnanbal.fxdedektifi.sample.domain.repository.SignalRepository;
import com.fernandocejas.arrow.checks.Preconditions;
import io.reactivex.Observable;
import javax.inject.Inject;

public class OpenSignal extends UseCase<Boolean, OpenSignal.Params> {


  /**
   * This class is an implementation of {@link UseCase} that represents a use case for
   * retrieving a collection of all {@link Signal}.
   */
  private final SignalRepository signalRepository;

  @Inject
  OpenSignal(SignalRepository signalRepository, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.signalRepository = signalRepository;
  }

  @Override
  Observable<Boolean> buildUseCaseObservable(Params params) {
    Preconditions.checkNotNull(params);
    return this.signalRepository
        .openSignal(params.signalId, params.pair, params.volume, params.buy_sell,
            params.openingPrice,
            params.open,
            params.status, params.comment, params.statusChangePrice, params.term);
  }

  public static final class Params {

    int signalId;
    String pair;
    double volume;
    boolean buy_sell;
    double openingPrice;
    boolean open;
    String status;
    String comment;
    double statusChangePrice;
    String term;

    private Params(int signalId, String pair, double volume, boolean buy_sell,
        double openingPrice, boolean open, String status, String comment, double statusChangePrice
        , String term) {

      this.signalId = signalId;
      this.pair = pair;
      this.volume = volume;
      this.buy_sell = buy_sell;
      this.openingPrice = openingPrice;
      this.open = open;
      this.status = status;
      this.comment = comment;
      this.statusChangePrice = statusChangePrice;
      this.term = term;

    }

    public static OpenSignal.Params forSignal(int signalId, String pair, double volume,
        boolean buy_sell,
        double openingPrice, boolean open, String status, String comment, double statusChangePrice
        , String term) {
      return new OpenSignal.Params(signalId, pair, volume, buy_sell, openingPrice, open, status,
          comment
          , statusChangePrice, term);
    }
  }

}
