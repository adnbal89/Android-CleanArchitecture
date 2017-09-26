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


import com.adnanbal.fxdedektifi.forex.data.cache.PositionCache;
import com.adnanbal.fxdedektifi.forex.data.entity.PositionEntity;
import com.adnanbal.fxdedektifi.forex.data.net.RestApiPosition;
import com.adnanbal.fxdedektifi.forex.data.repository.datasource.Datastore.PositionDataStore;
import io.reactivex.Observable;
import java.util.List;

/**
 * {@link PositionDataStore} implementation based on connections to the api (Cloud).
 */
public class CloudPositionDataStore implements PositionDataStore {

  private final RestApiPosition restApiPosition;
  private final PositionCache positionCache;


  /**
   * Construct a {@link PositionDataStore} based on connections to the api (Cloud).
   *
   * @param restApiPosition The {@link RestApiPosition} implementation to use.
   * @param positionCache A {@link PositionCache} to cache data retrieved from the api.
   */
  public CloudPositionDataStore(RestApiPosition restApiPosition,
      PositionCache positionCache) {
    this.restApiPosition = restApiPosition;
    this.positionCache = positionCache;
  }

  @Override
  public Observable<List<PositionEntity>> positionEntityList() {
    return this.restApiPosition.positionEntityList();
  }

  @Override
  public Observable<PositionEntity> positionEntityDetails(final String positionId) {
    return this.restApiPosition.positionEntityById(positionId)
        .doOnNext(CloudPositionDataStore.this.positionCache::put);
  }


  //TODO : implement cache
  @Override
  public Observable<Boolean> removePositionEntity(final String positionId) {
    CloudPositionDataStore.this.positionCache.delete(positionId);
    return this.restApiPosition.removeEntityById(positionId);
  }

  //Todo : implement the method
  @Override
  public Observable<Boolean> addPositionEntity(PositionEntity positionEntity) {
    CloudPositionDataStore.this.positionCache.put(positionEntity);
    return this.restApiPosition.addEntityToCloud(positionEntity);

  }
}
