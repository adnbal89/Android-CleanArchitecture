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


import com.adnanbal.fxdedektifi.forex.data.cache.SignalCache;
import com.adnanbal.fxdedektifi.forex.data.entity.SignalEntity;
import com.adnanbal.fxdedektifi.forex.data.entity.UserSignalEntity;
import com.adnanbal.fxdedektifi.forex.data.net.RestApiSignal;
import com.adnanbal.fxdedektifi.forex.data.repository.datasource.Datastore.SignalDataStore;
import io.reactivex.Observable;
import java.util.List;
import java.util.Map;

/**
 * {@link SignalDataStore} implementation based on connections to the api (Cloud).
 */
public class CloudSignalDataStore implements SignalDataStore {

  private final RestApiSignal restApiSignal;
  private final SignalCache signalCache;


  /**
   * Construct a {@link SignalDataStore} based on connections to the api (Cloud).
   *
   * @param restApiSignal The {@link RestApiSignal} implementation to use.
   * @param signalCache A {@link SignalCache} to cache data retrieved from the api.
   */
  public CloudSignalDataStore(RestApiSignal restApiSignal,
      SignalCache signalCache) {
    this.restApiSignal = restApiSignal;
    this.signalCache = signalCache;
  }

  @Override
  public Observable<List<SignalEntity>> signalEntityList() {
    return this.restApiSignal.signalEntityList();
  }

  @Override
  public Observable<SignalEntity> signalEntityDetails(final String signalId) {
    return this.restApiSignal.signalEntityById(signalId)
        .doOnNext(CloudSignalDataStore.this.signalCache::put);
  }

  @Override
  public Observable<Boolean> addSignalEntity(SignalEntity signalEntity) {
    CloudSignalDataStore.this.signalCache.put(signalEntity);
    return this.restApiSignal.addEntityToCloud(signalEntity);

  }

  @Override
  public Observable<List<UserSignalEntity>> userSignalEntityList(String authenticatedUserUid) {
    return this.restApiSignal.userSignalEntityList(authenticatedUserUid);
  }

  @Override
  public Observable<Boolean> patchUserSignalEntity(Map<String, Boolean> userSignalMap,
      String authenticatedUserUid, boolean openOrClose) {
    return this.restApiSignal.patchUserSignalMapToCloud(userSignalMap, authenticatedUserUid,
        openOrClose);

  }

  @Override
  public Observable<SignalEntity> getUpdatedSignal() {
    return this.restApiSignal.getUpdatedSignal()
        .doOnNext(CloudSignalDataStore.this.signalCache::put);
  }

}
