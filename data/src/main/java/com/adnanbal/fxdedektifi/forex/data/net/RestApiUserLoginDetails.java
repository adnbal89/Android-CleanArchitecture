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

import com.adnanbal.fxdedektifi.forex.data.entity.UserLoginDetailsEntity;
import io.reactivex.Observable;

public interface RestApiUserLoginDetails {

  String API_BASE_URL =
      "https://fxingsign.firebaseio.com/";

  String JSON_EXTENSION = ".json";

  String API_URL_POST_USER_LOGIN_DETAILS = API_BASE_URL + "login";

  /**
   * Retrieves an {@link Observable} which will emit a result
   * {@link UserLoginDetailsEntity} if add
   * operation is successful.
   *
   * @param userLoginDetailsEntity The userLoginDetailsEntity entity used to add
   * userLoginDetailsEntity data to cloud.
   */
  Observable<Boolean> addEntityToCloud(UserLoginDetailsEntity userLoginDetailsEntity);

}
