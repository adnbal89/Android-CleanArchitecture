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

package com.adnanbal.fxdedektifi.forex.domain.repository;

import com.adnanbal.fxdedektifi.forex.domain.model.Signal;
import com.adnanbal.fxdedektifi.forex.domain.model.UserSignal;
import io.reactivex.Observable;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
  Observable<Signal> getOneSignal(final String signalId);

  /**
   * Get an {@link Observable} which will emit a closed {@link Signal}.
   */
  Observable<Boolean> openSignal(String positionId, String pair, double volume, boolean buy_sell,
      double openingPrice, boolean open, String status, String comment, double statusChangePrice,
      String term, Date date, Map<String, Boolean> users, Map<String, List<String>> changedFields);

  /**
   * Get an {@link Observable} which will emit a User_signal List{@link List<UserSignal>}.
   */
  Observable<List<UserSignal>> getUser_SignalList(String authenticatedUserUid);


  /**
   * Get an {@link Observable} which will emit a User_signal List{@link Boolean}.
   */
  Observable<Boolean> patchUserSignal(Map<String, Boolean> userSignalMap,
      String authenticatedUserUid, boolean openOrClose);


  /**
   * Get an {@link Observable} which will emit a {@link Signal}.
   */
  Observable<Signal> getUpdatedSignal();


}
