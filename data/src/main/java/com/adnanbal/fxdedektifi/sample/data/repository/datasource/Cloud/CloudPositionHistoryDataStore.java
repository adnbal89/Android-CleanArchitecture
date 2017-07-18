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

package com.adnanbal.fxdedektifi.sample.data.repository.datasource.Cloud;


import com.adnanbal.fxdedektifi.sample.data.cache.PositionCache;
import com.adnanbal.fxdedektifi.sample.data.entity.PositionEntity;
import com.adnanbal.fxdedektifi.sample.data.net.RestApiPositionHistory;
import com.adnanbal.fxdedektifi.sample.data.repository.datasource.Datastore.PositionHistoryDataStore;
import io.reactivex.Observable;
import java.util.List;

/**
 * {@link PositionHistoryDataStore} implementation based on connections to the api (Cloud).
 */
public class CloudPositionHistoryDataStore implements PositionHistoryDataStore {

  private final RestApiPositionHistory restApiPositionHistory;
  private final PositionCache positionCache;


  /**
   * Construct a {@link PositionHistoryDataStore} based on connections to the api (Cloud).
   *
   * @param restApiPositionHistory The {@link RestApiPositionHistory} implementation to use.
   * @param positionCache A {@link PositionCache} to cache data retrieved from the api.
   */
  public CloudPositionHistoryDataStore(RestApiPositionHistory restApiPositionHistory,
      PositionCache positionCache) {
    this.restApiPositionHistory = restApiPositionHistory;
    this.positionCache = positionCache;
  }

  @Override
  public Observable<List<PositionEntity>> positionHistoryEntityList() {
    return this.restApiPositionHistory.positionHistoryEntityList();
  }

  @Override
  public Observable<PositionEntity> positionHistoryEntityDetails(final int positionId) {
    return this.restApiPositionHistory.positionHistoryEntityById(positionId)
        .doOnNext(CloudPositionHistoryDataStore.this.positionCache::put);
  }
}
