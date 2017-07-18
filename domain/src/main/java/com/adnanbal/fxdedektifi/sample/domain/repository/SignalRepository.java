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

package com.adnanbal.fxdedektifi.sample.domain.repository;

import com.adnanbal.fxdedektifi.sample.domain.model.Signal;
import io.reactivex.Observable;
import java.util.List;

/**
 * Interface that represents a Repository for getting {@link Signal} related data.
 */
public interface SignalRepository {

  /**
   * Get an {@link Observable} which will emit a List of {@link Signal}.
   */
  Observable<List<Signal>> getSignalsList();

  /**
   * Get an {@link Observable} which will emit a {@link Signal}.
   *
   * @param signalId The signal id used to retrieve signal data.
   */
  Observable<Signal> getOneSignal(final int signalId);

  /**
   * Get an {@link Observable} which will emit a closed {@link Signal}.
   */
  Observable<Boolean> openSignal(int positionId, String pair, double volume, boolean buy_sell,
      double openingPrice, boolean open, String status, String comment, double statusChangePrice,
      String term);


}
