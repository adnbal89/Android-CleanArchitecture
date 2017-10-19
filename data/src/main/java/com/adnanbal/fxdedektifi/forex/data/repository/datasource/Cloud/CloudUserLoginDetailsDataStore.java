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

package com.adnanbal.fxdedektifi.forex.data.repository.datasource.Cloud;

import com.adnanbal.fxdedektifi.forex.data.entity.UserLoginDetailsEntity;
import com.adnanbal.fxdedektifi.forex.data.net.RestApiUserLoginDetails;
import com.adnanbal.fxdedektifi.forex.data.repository.datasource.Datastore.UserLoginDetailsDataStore;
import io.reactivex.Observable;

/**
 * {@link UserLoginDetailsDataStore} implementation based on connections to the api (Cloud).
 */
public class CloudUserLoginDetailsDataStore implements UserLoginDetailsDataStore {

  private final RestApiUserLoginDetails restApiUserLoginDetails;

  /**
   * Construct a {@link UserLoginDetailsDataStore} based on connections to the api (Cloud).
   *
   * @param restApiUserLoginDetails The {@link RestApiUserLoginDetails} implementation to use.
   */
  public CloudUserLoginDetailsDataStore(RestApiUserLoginDetails restApiUserLoginDetails) {
    this.restApiUserLoginDetails = restApiUserLoginDetails;
  }

  @Override
  public Observable<Boolean> addUserLoginDetailsEntity(
      UserLoginDetailsEntity userLoginDetailsEntity) {
    return this.restApiUserLoginDetails.addEntityToCloud(userLoginDetailsEntity);

  }

}
