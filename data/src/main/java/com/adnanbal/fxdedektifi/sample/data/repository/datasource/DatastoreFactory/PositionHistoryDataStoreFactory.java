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

package com.adnanbal.fxdedektifi.sample.data.repository.datasource.DatastoreFactory;

import android.content.Context;
import android.support.annotation.NonNull;
import com.adnanbal.fxdedektifi.sample.data.cache.PositionCache;
import com.adnanbal.fxdedektifi.sample.data.entity.mapper.PositionEntityJsonMapper;
import com.adnanbal.fxdedektifi.sample.data.net.RestApiPositionHistory;
import com.adnanbal.fxdedektifi.sample.data.net.RestApiPositionHistoryImpl;
import com.adnanbal.fxdedektifi.sample.data.repository.datasource.Cloud.CloudPositionHistoryDataStore;
import com.adnanbal.fxdedektifi.sample.data.repository.datasource.Datastore.PositionHistoryDataStore;
import com.adnanbal.fxdedektifi.sample.data.repository.datasource.Disk.DiskPositionHistoryDataStore;
import javax.inject.Inject;

/**
 * Factory that creates different implementations of {@link PositionHistoryDataStore}.
 */
public class PositionHistoryDataStoreFactory {

  private final Context context;
  private final PositionCache positionCache;

  @Inject
  PositionHistoryDataStoreFactory(@NonNull Context context, @NonNull PositionCache positionCache) {
    this.context = context.getApplicationContext();
    this.positionCache = positionCache;
  }

  /**
   * Create {@link PositionHistoryDataStore} from a positionid.
   */
  public PositionHistoryDataStore create(int positionId) {
    PositionHistoryDataStore PositionHistoryDataStore;

    if (!this.positionCache.isExpired() && this.positionCache.isCached(positionId)) {
      PositionHistoryDataStore = new DiskPositionHistoryDataStore(this.positionCache);
    } else {
      PositionHistoryDataStore = createCloudDataStore();
    }

    return PositionHistoryDataStore;
  }

  /**
   * Create {@link PositionHistoryDataStore} to retrieve data from the Cloud.
   */
  public PositionHistoryDataStore createCloudDataStore() {
    final PositionEntityJsonMapper positionEntityJsonMapper = new PositionEntityJsonMapper();
    final RestApiPositionHistory restApi = new RestApiPositionHistoryImpl(this.context, positionEntityJsonMapper);

    return new CloudPositionHistoryDataStore(restApi, this.positionCache);
  }
}
