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
import com.adnanbal.fxdedektifi.forex.domain.model.UserSignal;
import com.adnanbal.fxdedektifi.forex.domain.repository.SignalRepository;
import com.fernandocejas.arrow.checks.Preconditions;
import io.reactivex.Observable;
import java.util.List;
import javax.inject.Inject;


public class GetUser_SignalList extends UseCase<List<UserSignal>, GetUser_SignalList.Params> {

  /**
   * This class is an implementation of {@link UseCase} that represents a use case for
   * retrieving a collection of all {@link Signal}.
   */
  private final SignalRepository signalRepository;

  @Inject
  GetUser_SignalList(SignalRepository signalRepository, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.signalRepository = signalRepository;
  }


  @Override
  Observable<List<UserSignal>> buildUseCaseObservable(Params params) {
    Preconditions.checkNotNull(params);
    return this.signalRepository.getUser_SignalList(params.userUid);
  }

  public static final class Params {

    private String userUid;

    private Params(String userId) {
      this.userUid = userId;
    }

    public static GetUser_SignalList.Params forUser(String userUid) {
      return new GetUser_SignalList.Params(userUid);
    }
  }
}
