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

package com.adnanbal.fxdedektifi.forex.data.repository;

import com.adnanbal.fxdedektifi.forex.data.entity.mapper.SubscriptionEntityDataMapper;
import com.adnanbal.fxdedektifi.forex.data.repository.datasource.Datastore.SubscriptionDataStore;
import com.adnanbal.fxdedektifi.forex.data.repository.datasource.DatastoreFactory.SubscriptionDataStoreFactory;
import com.adnanbal.fxdedektifi.forex.domain.model.Subscription;
import com.adnanbal.fxdedektifi.forex.domain.repository.SubscriptionRepository;
import io.reactivex.Observable;
import java.util.List;
import javax.inject.Inject;

/**
 * {@link SubscriptionRepository} for retrieving subscription data.
 */
public class SubscriptionDataRepository implements SubscriptionRepository {

  private final SubscriptionEntityDataMapper subscriptionEntityDataMapper;
  private final SubscriptionDataStoreFactory subscriptionDataStoreFactory;


  /**
   * Constructs a {@link SubscriptionRepository}.
   *
   * @param dataStoreFactory A factory to construct different data source implementations.
   * @param subscriptionEntityDataMapper {@link SubscriptionEntityDataMapper}.
   */
  @Inject
  SubscriptionDataRepository(SubscriptionDataStoreFactory dataStoreFactory,
      SubscriptionEntityDataMapper subscriptionEntityDataMapper) {
    this.subscriptionDataStoreFactory = dataStoreFactory;
    this.subscriptionEntityDataMapper = subscriptionEntityDataMapper;
  }

  @Override
  public Observable<List<Subscription>> getSubscriptionsList() {
    //we always get all signals from the cloud
    final SubscriptionDataStore subscriptionDataStore = this.subscriptionDataStoreFactory
        .createCloudDataStore();
    return subscriptionDataStore.subscriptionEntityList()
        .map(this.subscriptionEntityDataMapper::transform);

  }

  @Override
  public Observable<List<Subscription>> getPurchasedSubscriptionsList(String authenticatedUserUid) {
    //we always get all signals from the cloud
    final SubscriptionDataStore subscriptionDataStore = this.subscriptionDataStoreFactory
        .createCloudDataStore();
    return subscriptionDataStore.purchasedSubscriptionsList(authenticatedUserUid)
        .map(this.subscriptionEntityDataMapper::transform);

  }
}
