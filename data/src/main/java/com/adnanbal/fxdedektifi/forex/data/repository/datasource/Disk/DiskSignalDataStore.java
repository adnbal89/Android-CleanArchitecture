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

package com.adnanbal.fxdedektifi.forex.data.repository.datasource.Disk;

import com.adnanbal.fxdedektifi.forex.data.cache.SignalCache;
import com.adnanbal.fxdedektifi.forex.data.entity.SignalEntity;
import com.adnanbal.fxdedektifi.forex.data.entity.UserSignalEntity;
import com.adnanbal.fxdedektifi.forex.data.repository.datasource.Datastore.SignalDataStore;
import io.reactivex.Observable;
import java.util.List;
import java.util.Map;

/**
 * {@link SignalDataStore} implementation based on file system data store.
 */
public class DiskSignalDataStore implements SignalDataStore {

  private final SignalCache signalCache;

  /**
   * Construct a {@link SignalDataStore} based file system data store.
   *
   * @param signalCache A {@link SignalCache} to cache data retrieved from the api.
   */
  public DiskSignalDataStore(SignalCache signalCache) {
    this.signalCache = signalCache;
  }

  @Override
  public Observable<List<SignalEntity>> signalEntityList() {
    //TODO: implement simple cache for storing/retrieving collections of signals.
    throw new UnsupportedOperationException("Operation is not available!!!");
  }

  @Override
  public Observable<SignalEntity> signalEntityDetails(final String signalId) {
    return this.signalCache.get(signalId);
  }

  @Override
  public Observable<Boolean> addSignalEntity(SignalEntity signalEntity) {
    return this.signalCache.put(signalEntity);
  }

  @Override
  public Observable<List<UserSignalEntity>> userSignalEntityList(String authenticatedUserUid) {
    //ToDo : complete cache
    return null;
  }

  @Override
  public Observable<Boolean> patchUserSignalEntity(Map<String, Boolean> userSignalMap, String authenticatedUserUid, boolean openOrClose) {
    //ToDo : complete cache
    return null;
  }
}
