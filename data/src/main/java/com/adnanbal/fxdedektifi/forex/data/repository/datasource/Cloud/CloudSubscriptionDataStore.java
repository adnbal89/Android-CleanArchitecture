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

import com.adnanbal.fxdedektifi.forex.data.entity.SubscriptionEntity;
import com.adnanbal.fxdedektifi.forex.data.net.RestApiSubscription;
import com.adnanbal.fxdedektifi.forex.data.repository.datasource.Datastore.SubscriptionDataStore;
import io.reactivex.Observable;
import java.util.List;

/**
 * {@link SubscriptionDataStore} implementation based on connections to the api (Cloud).
 */
public class CloudSubscriptionDataStore implements SubscriptionDataStore {

  private final RestApiSubscription restApiSubscription;


  /**
   * Construct a {@link SubscriptionDataStore} based on connections to the api (Cloud).
   *
   * @param restApiSubscription The {@link RestApiSubscription} implementation to use.
   */
  public CloudSubscriptionDataStore(RestApiSubscription restApiSubscription) {
    this.restApiSubscription = restApiSubscription;
  }

  @Override
  public Observable<List<SubscriptionEntity>> subscriptionEntityList() {
    return this.restApiSubscription.subscriptionEntityList();
  }

  @Override
  public Observable<List<SubscriptionEntity>> purchasedSubscriptionsList(
      String authenticatedUserUid) {
    return this.restApiSubscription.purchasedSubscriptionsList(authenticatedUserUid);
  }
}
