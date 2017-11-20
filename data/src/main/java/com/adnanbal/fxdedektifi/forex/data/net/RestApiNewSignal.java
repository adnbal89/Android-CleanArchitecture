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

package com.adnanbal.fxdedektifi.forex.data.net;

import com.adnanbal.fxdedektifi.forex.data.entity.NewPositionEntity;
import io.reactivex.Observable;

public interface RestApiNewSignal {

  String API_BASE_URL =
      "https://fxingsign.firebaseio.com/";
  String JSON_EXTENSION = ".json";

  String API_URL_POST_SIGNAL = API_BASE_URL + "signal";


  /**
   * Retrieves an {@link Observable} which will emit a result
   * {@link com.adnanbal.fxdedektifi.forex.data.entity.NewPositionEntity} if add
   * operation is successful.
   *
   * @param newPositionEntity The position entity used to add position data to cloud.
   */
  Observable<Boolean> createNewSignal(NewPositionEntity newPositionEntity,
      String operationOnSignal);

}
