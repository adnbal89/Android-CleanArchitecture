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

package com.adnanbal.fxdedektifi.forex.data.repository.datasource.DatastoreFactory;

import android.content.Context;
import android.support.annotation.NonNull;
import com.adnanbal.fxdedektifi.forex.data.cache.PositionCache;
import com.adnanbal.fxdedektifi.forex.data.entity.mapper.PositionEntityJsonMapper;
import com.adnanbal.fxdedektifi.forex.data.net.RestApiPosition;
import com.adnanbal.fxdedektifi.forex.data.net.RestApiPositionImpl;
import com.adnanbal.fxdedektifi.forex.data.repository.datasource.Cloud.CloudPositionDataStore;
import com.adnanbal.fxdedektifi.forex.data.repository.datasource.Datastore.PositionDataStore;
import com.adnanbal.fxdedektifi.forex.data.repository.datasource.Disk.DiskPositionDataStore;
import javax.inject.Inject;

/**
 * Factory that creates different implementations of {@link PositionDataStore}.
 */
public class PositionDataStoreFactory {

  private final Context context;
  private final PositionCache positionCache;

  @Inject
  PositionDataStoreFactory(@NonNull Context context, @NonNull PositionCache positionCache) {
    this.context = context.getApplicationContext();
    this.positionCache = positionCache;
  }

  /**
   * Create {@link PositionDataStore} from a positionid.
   */
  public PositionDataStore create(String positionId) {
    PositionDataStore positionDataStore;

    if (!this.positionCache.isExpired() && this.positionCache.isCached(positionId)) {
      positionDataStore = new DiskPositionDataStore(this.positionCache);
    } else {
      positionDataStore = createCloudDataStore();
    }
    return positionDataStore;
  }


  /**
   * Create {@link PositionDataStore} to retrieve data from the Cloud.
   */
  public PositionDataStore createCloudDataStore() {
    final PositionEntityJsonMapper positionEntityJsonMapper = new PositionEntityJsonMapper();
    final RestApiPosition restApi = new RestApiPositionImpl(this.context, positionEntityJsonMapper);

    return new CloudPositionDataStore(restApi, this.positionCache);
  }
}
