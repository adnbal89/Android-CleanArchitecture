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

package com.adnanbal.fxdedektifi.forex.data.repository.datasource.Datastore;

import com.adnanbal.fxdedektifi.forex.data.entity.SignalEntity;
import com.adnanbal.fxdedektifi.forex.data.entity.UserSignalEntity;
import io.reactivex.Observable;
import java.util.List;
import java.util.Map;

/**
 * Interface that represents a data store from where signal data is retrieved.
 */
public interface SignalDataStore {

  /**
   * Get an {@link Observable} which will emit a List of {@link SignalEntity}.
   */
  Observable<List<SignalEntity>> signalEntityList();

  /**
   * Get an {@link Observable} which will emit a {@link SignalEntity} by its id.
   *
   * @param signalId The id to retrieve signal data.
   */
  Observable<SignalEntity> signalEntityDetails(final String signalId);

  /**
   * Get an {@link Observable} which will emit a {@link SignalEntity} .
   *
   * @param signalEntity The position data to remove.
   */
  Observable<Boolean> addSignalEntity(SignalEntity signalEntity);

  /**
   * Get an {@link Observable} which will emit a {@link UserSignalEntity} .
   *
   * @param authenticatedUserUid the Uid for current user to get open positions.
   */
  Observable<List<UserSignalEntity>> userSignalEntityList(String authenticatedUserUid);


  /**
   * Get an {@link Observable} which will emit a {@link Map} .
   *
   * @param userSignalMap The position data to remove.
   */
  Observable<Boolean> patchUserSignalEntity(Map<String, Boolean> userSignalMap,
      String authenticatedUserUid, boolean openOrClose);


  /**
   * Get an {@link Observable} which will emit a {@link SignalEntity} when a signal is updated in
   * firebase DB.
   */
  Observable<SignalEntity> getUpdatedSignal();

}
