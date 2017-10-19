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
import com.adnanbal.fxdedektifi.forex.data.entity.mapper.UserLoginDetailsEntityJsonMapper;
import com.adnanbal.fxdedektifi.forex.data.net.RestApiUserLoginDetails;
import com.adnanbal.fxdedektifi.forex.data.net.RestApiUserLoginDetailsImpl;
import com.adnanbal.fxdedektifi.forex.data.repository.datasource.Cloud.CloudUserLoginDetailsDataStore;
import com.adnanbal.fxdedektifi.forex.data.repository.datasource.Datastore.UserLoginDetailsDataStore;
import javax.inject.Inject;

/**
 * Factory that creates different implementations of {@link UserLoginDetailsDataStore}.
 */
public class UserLoginDetailsDataStoreFactory {

  private final Context context;

  @Inject
  UserLoginDetailsDataStoreFactory(@NonNull Context context) {
    this.context = context.getApplicationContext();
  }

  /**
   * Create {@link UserLoginDetailsDataStore} from a userLoginDetailsid.
   */
  public UserLoginDetailsDataStore create(String userLoginDetailsId) {
    UserLoginDetailsDataStore userLoginDetailsDataStore;

    userLoginDetailsDataStore = createCloudDataStore();

    return userLoginDetailsDataStore;
  }

  /**
   * Create {@link UserLoginDetailsDataStore} to retrieve data from the Cloud.
   */
  public UserLoginDetailsDataStore createCloudDataStore() {
    final UserLoginDetailsEntityJsonMapper userLoginDetailsEntityJsonMapper = new UserLoginDetailsEntityJsonMapper();
    final RestApiUserLoginDetails restApi = new RestApiUserLoginDetailsImpl(this.context,
        userLoginDetailsEntityJsonMapper);

    return new CloudUserLoginDetailsDataStore(restApi);
  }
}
