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
import com.adnanbal.fxdedektifi.forex.data.entity.mapper.SubscriptionEntityJsonMapper;
import com.adnanbal.fxdedektifi.forex.data.net.RestApiSubscription;
import com.adnanbal.fxdedektifi.forex.data.net.RestApiSubscriptionImpl;
import com.adnanbal.fxdedektifi.forex.data.repository.datasource.Cloud.CloudSubscriptionDataStore;
import com.adnanbal.fxdedektifi.forex.data.repository.datasource.Datastore.SubscriptionDataStore;
import com.google.firebase.database.FirebaseDatabase;
import javax.inject.Inject;

/**
 * Factory that creates different implementations of {@link SubscriptionDataStore}.
 */
public class SubscriptionDataStoreFactory {

  private Context context;

  @Inject
  SubscriptionDataStoreFactory(@NonNull Context context) {
    this.context = context.getApplicationContext();
  }
  

  /**
   * Create {@link SubscriptionDataStore} to retrieve data from the Cloud.
   */
  public SubscriptionDataStore createCloudDataStore() {
    final SubscriptionEntityJsonMapper subscriptionEntityJsonMapper = new SubscriptionEntityJsonMapper();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    final RestApiSubscription restApi = new RestApiSubscriptionImpl(this.context,
        subscriptionEntityJsonMapper);

    return new CloudSubscriptionDataStore(restApi);
  }
  
}
