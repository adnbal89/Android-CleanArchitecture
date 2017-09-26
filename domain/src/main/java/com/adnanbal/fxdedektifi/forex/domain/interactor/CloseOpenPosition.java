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
import com.adnanbal.fxdedektifi.forex.domain.model.Position;
import com.adnanbal.fxdedektifi.forex.domain.repository.PositionRepository;
import com.fernandocejas.arrow.checks.Preconditions;
import io.reactivex.Observable;
import javax.inject.Inject;

public class CloseOpenPosition extends UseCase<Boolean, CloseOpenPosition.Params> {

  /**
   * This class is an implementation of {@link UseCase} that represents a use case for
   * retrieving a collection of all {@link Position}.
   */
  private final PositionRepository positionRepository;

  @Inject
  CloseOpenPosition(PositionRepository positionRepository, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.positionRepository = positionRepository;
  }

  @Override
  Observable<Boolean> buildUseCaseObservable(Params params) {
    Preconditions.checkNotNull(params);
    return this.positionRepository.closeOpenPosition(params.positionId);
  }

  public static final class Params {

    private final String positionId;

    private Params(String positionId) {
      this.positionId = positionId;
    }

    public static CloseOpenPosition.Params forPosition(String positionId) {
      return new CloseOpenPosition.Params(positionId);
    }
  }
}
