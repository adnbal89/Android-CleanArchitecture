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
import com.adnanbal.fxdedektifi.forex.domain.model.Signal;
import com.adnanbal.fxdedektifi.forex.domain.repository.SignalRepository;
import com.fernandocejas.arrow.checks.Preconditions;
import io.reactivex.Observable;
import java.util.Map;
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
//    return this.signalRepository
//        .openSignal(params.signalId, params.pair, params.volume, params.buy_sell,
//            params.openingPrice,
//            params.open,
//            params.status, params.comment, params.statusChangePrice, params.term, params.date,
//            params.users, params.changedFields);

    return this.signalRepository
        .patchUserSignal(params.userSignalMap, params.authenticatedUserUid, params.openOrClose);
  }

  public static final class Params {


    Map<String, Boolean> userSignalMap;
    String authenticatedUserUid;
    Boolean openOrClose;

    private Params(Map<String, Boolean> userSignalMap, String authenticatedUserUid,
        boolean openOrClose) {
      this.userSignalMap = userSignalMap;
      this.authenticatedUserUid = authenticatedUserUid;
      this.openOrClose = openOrClose;
    }

    public static OpenSignal.Params forSignal(Map<String, Boolean> userSignalMap,
        String authenticatedUserUid, boolean openOrClose) {
      return new OpenSignal.Params(userSignalMap, authenticatedUserUid, openOrClose);
    }
  }

}
