

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

import com.adnanbal.fxdedektifi.forex.data.entity.PositionEntity;
import io.reactivex.Observable;
import java.util.List;

public interface RestApiPositionHistory {

  String API_BASE_URL =
      "https://fxingsign.firebaseio.com/";

  String JSON_EXTENSION = ".json";

  /**
   * Api url for getting all positions
   */
  String API_URL_GET_POSITION_LIST = API_BASE_URL + "positionHistory" + JSON_EXTENSION;
//  + "?_sort=id&_order=desc";
  /**
   * Api url for getting a position profile: Remember to concatenate id + 'json'
   */
  String API_URL_GET_POSITION_DETAILS = API_BASE_URL + "positionHistory" + "/";

  /**
   * Retrieves an {@link Observable} which will emit a List of {@link PositionEntity}.
   */
  Observable<List<PositionEntity>> positionHistoryEntityList();

  /**
   * Retrieves an {@link Observable} which will emit a {@link PositionEntity}.
   *
   * @param positionId The positions id used to get position data.
   */
  Observable<PositionEntity> positionHistoryEntityById(final String positionId);


}
