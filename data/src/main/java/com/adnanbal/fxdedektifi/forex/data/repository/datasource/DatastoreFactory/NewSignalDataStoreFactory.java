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
import com.adnanbal.fxdedektifi.forex.data.entity.mapper.NewSignalEntityJsonMapper;
import com.adnanbal.fxdedektifi.forex.data.net.RestApiNewSignal;
import com.adnanbal.fxdedektifi.forex.data.net.RestApiNewSignalImpl;
import com.adnanbal.fxdedektifi.forex.data.repository.datasource.Cloud.CloudNewSignalDataStore;
import com.adnanbal.fxdedektifi.forex.data.repository.datasource.Datastore.NewSignalDataStore;
import com.google.firebase.database.FirebaseDatabase;
import javax.inject.Inject;

public class NewSignalDataStoreFactory {

  private final Context context;

  @Inject
  NewSignalDataStoreFactory(@NonNull Context context) {
    this.context = context.getApplicationContext();

  }

  /**
   * Create {@link NewSignalDataStore} from a signalid.
   */
  public NewSignalDataStore create() {
    NewSignalDataStore newSignalDataStore;
    newSignalDataStore = createCloudDataStore();

    return newSignalDataStore;
  }

  /**
   * Create {@link NewSignalDataStore} to post data from the Cloud.
   */
  public NewSignalDataStore createCloudDataStore() {
    final NewSignalEntityJsonMapper newSignalEntityJsonMapper = new NewSignalEntityJsonMapper();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    final RestApiNewSignal restApi = new RestApiNewSignalImpl(firebaseDatabase, this.context,
        newSignalEntityJsonMapper);

    return new CloudNewSignalDataStore(restApi);
  }
}
