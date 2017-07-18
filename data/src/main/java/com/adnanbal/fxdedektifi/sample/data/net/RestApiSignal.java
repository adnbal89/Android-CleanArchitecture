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

package com.adnanbal.fxdedektifi.sample.data.net;

import com.adnanbal.fxdedektifi.sample.data.entity.SignalEntity;
import io.reactivex.Observable;
import java.util.List;

public interface RestApiSignal {

  String API_BASE_URL =
      "http://94.102.9.6:3001/";

  String API_POSITION_BASE_URL =
      "http://94.102.9.6:3000/";

  /**
   * Api url for getting all signals
   */
  String API_URL_GET_SIGNAL_LIST = API_BASE_URL + "signal"+"?_sort=id&_order=desc";
  /**
   * Api url for getting a signal profile: Remember to concatenate id + 'json'
   */
  String API_URL_GET_SIGNAL_DETAILS = API_BASE_URL + "signal" + "/";

  String API_URL_POST_SIGNAL = API_BASE_URL + "signal";

  String API_URL_POST_SIGNAL_POSITION = API_POSITION_BASE_URL + "position";



  /**
   * Retrieves an {@link Observable} which will emit a List of {@link SignalEntity}.
   */
  Observable<List<SignalEntity>> signalEntityList();

  /**
   * Retrieves an {@link Observable} which will emit a {@link SignalEntity}.
   *
   * @param signalId The signals id used to get signal data.
   */
  Observable<SignalEntity> signalEntityById(final int signalId);


  /**
   * Retrieves an {@link Observable} which will emit a result
   * {@link SignalEntity} if add
   * operation is successful.
   *
   * @param signalEntity The position entity used to add position data to cloud.
   */
  Observable<Boolean> addEntityToCloud(SignalEntity signalEntity);


}
