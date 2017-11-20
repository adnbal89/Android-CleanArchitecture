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

import com.adnanbal.fxdedektifi.forex.data.entity.NewPositionEntity;
import com.adnanbal.fxdedektifi.forex.data.net.RestApiNewSignal;
import com.adnanbal.fxdedektifi.forex.data.repository.datasource.Datastore.NewSignalDataStore;
import io.reactivex.Observable;

public class CloudNewSignalDataStore implements NewSignalDataStore {

  private final RestApiNewSignal restApiNewSignal;

  /**
   * Construct a {@link NewSignalDataStore} based on connections to the api (Cloud).
   *
   * @param restApiNewSignal The {@link RestApiNewSignal} implementation to use.
   */
  public CloudNewSignalDataStore(RestApiNewSignal restApiNewSignal
  ) {
    this.restApiNewSignal = restApiNewSignal;
  }


  @Override
  public Observable<Boolean> createNewSignalEntity(NewPositionEntity newPositionEntity,
      String operationOnSignal) {
    return this.restApiNewSignal.createNewSignal(newPositionEntity, operationOnSignal);
  }
}
