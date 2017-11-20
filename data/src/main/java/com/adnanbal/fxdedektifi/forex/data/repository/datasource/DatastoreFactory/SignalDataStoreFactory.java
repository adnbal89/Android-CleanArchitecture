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
import com.adnanbal.fxdedektifi.forex.data.cache.SignalCache;
import com.adnanbal.fxdedektifi.forex.data.entity.mapper.SignalEntityJsonMapper;
import com.adnanbal.fxdedektifi.forex.data.net.RestApiSignal;
import com.adnanbal.fxdedektifi.forex.data.net.RestApiSignalImpl;
import com.adnanbal.fxdedektifi.forex.data.repository.datasource.Cloud.CloudSignalDataStore;
import com.adnanbal.fxdedektifi.forex.data.repository.datasource.Datastore.SignalDataStore;
import com.adnanbal.fxdedektifi.forex.data.repository.datasource.Disk.DiskSignalDataStore;
import com.google.firebase.database.FirebaseDatabase;
import javax.inject.Inject;

/**
 * Factory that creates different implementations of {@link SignalDataStore}.
 */
public class SignalDataStoreFactory {

  private final Context context;
  private final SignalCache signalCache;

  @Inject
  SignalDataStoreFactory(@NonNull Context context, @NonNull SignalCache signalCache) {
    this.context = context.getApplicationContext();
    this.signalCache = signalCache;
  }

  /**
   * Create {@link SignalDataStore} from a signalid.
   */
  public SignalDataStore create(String signalId) {
    SignalDataStore signalDataStore;

    if (!this.signalCache.isExpired() && this.signalCache.isCached(signalId)) {
      signalDataStore = new DiskSignalDataStore(this.signalCache);
    } else {
      signalDataStore = createCloudDataStore();
    }

    return signalDataStore;
  }

  /**
   * Create {@link SignalDataStore} to retrieve data from the Cloud.
   */
  public SignalDataStore createCloudDataStore() {
    final SignalEntityJsonMapper signalEntityJsonMapper = new SignalEntityJsonMapper();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    final RestApiSignal restApi = new RestApiSignalImpl(firebaseDatabase, this.context,
        signalEntityJsonMapper);

    return new CloudSignalDataStore(restApi, this.signalCache);
  }
}
