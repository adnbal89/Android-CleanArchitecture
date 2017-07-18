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

package com.adnanbal.fxdedektifi.sample.data.repository.datasource.Disk;

import com.adnanbal.fxdedektifi.sample.data.cache.PositionCache;
import com.adnanbal.fxdedektifi.sample.data.entity.PositionEntity;
import com.adnanbal.fxdedektifi.sample.data.repository.datasource.Datastore.PositionDataStore;
import io.reactivex.Observable;
import java.util.List;

/**
 * {@link PositionDataStore} implementation based on file system data store.
 */
public class DiskPositionDataStore implements PositionDataStore {

  private final PositionCache positionCache;

  /**
   * Construct a {@link PositionDataStore} based file system data store.
   *
   * @param positionCache A {@link PositionCache} to cache data retrieved from the api.
   */
  public DiskPositionDataStore(PositionCache positionCache) {
    this.positionCache = positionCache;
  }

  @Override
  public Observable<List<PositionEntity>> positionEntityList() {
    //TODO: implement simple cache for storing/retrieving collections of positions.
    throw new UnsupportedOperationException("Operation is not available!!!");
  }

  @Override
  public Observable<PositionEntity> positionEntityDetails(final int positionId) {
    return this.positionCache.get(positionId);
  }

  //TODO : correct the method
  @Override
  public Observable<Boolean> removePositionEntity(final int positionId) {
      return this.positionCache.delete(positionId);
  }

  @Override
  public Observable<Boolean> addPositionEntity(PositionEntity positionEntity) {
    return this.positionCache.put(positionEntity);
  }
}
