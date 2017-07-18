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
import com.adnanbal.fxdedektifi.sample.domain.model.Position;
import com.adnanbal.fxdedektifi.sample.domain.repository.PositionRepository;
import com.fernandocejas.arrow.checks.Preconditions;
import io.reactivex.Observable;
import javax.inject.Inject;

public class OpenPosition extends UseCase<Boolean, OpenPosition.Params> {


  /**
   * This class is an implementation of {@link UseCase} that represents a use case for
   * retrieving a collection of all {@link Position}.
   */
  private final PositionRepository positionRepository;

  @Inject
  OpenPosition(PositionRepository positionRepository, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.positionRepository = positionRepository;
  }

  @Override
  Observable<Boolean> buildUseCaseObservable(Params params) {
    Preconditions.checkNotNull(params);
    return this.positionRepository
        .openPosition(params.positionId, params.pair, params.volume, params.buy_sell,
            params.openingPrice,
            params.open,
            params.status, params.comment);
  }

  public static final class Params {

    int positionId;
    String pair;
    double volume;
    boolean buy_sell;
    double openingPrice;
    boolean open;
    String status;
    String comment;

    private Params(int positionId, String pair, double volume, boolean buy_sell,
        double openingPrice, boolean open, String status, String comment) {

      this.positionId = positionId;
      this.pair = pair;
      this.volume = volume;
      this.buy_sell = buy_sell;
      this.openingPrice = openingPrice;
      this.open = open;
      this.status = status;
      this.comment = comment;

    }

    public static OpenPosition.Params forPosition(int positionId, String pair, double volume,
        boolean buy_sell,
        double openingPrice, boolean open, String status, String comment) {
      return new OpenPosition.Params(positionId, pair, volume, buy_sell, openingPrice, open, status,
          comment);
    }
  }

}
