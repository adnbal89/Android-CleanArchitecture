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

package com.adnanbal.fxdedektifi.forex.presentation.mapper;

import com.adnanbal.fxdedektifi.forex.domain.model.Subscription;
import com.adnanbal.fxdedektifi.forex.presentation.internal.di.PerActivity;
import com.adnanbal.fxdedektifi.forex.presentation.model.SubscriptionModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import javax.inject.Inject;

@PerActivity
public class SubscriptionModelDataMapper {

  @Inject
  public SubscriptionModelDataMapper() {
  }

  /**
   * Transform a {@link Subscription} into an {@link SubscriptionModel}.
   *
   * @param subscription Object to be transformed.
   * @return {@link SubscriptionModel}.
   */
  public SubscriptionModel transform(Subscription subscription) {
    if (subscription == null) {
      throw new IllegalArgumentException("Cannot transform a null value");
    }
    SubscriptionModel subscriptionModel = new SubscriptionModel();
    subscriptionModel.setName(subscription.getName());
    subscriptionModel.setPrice(subscription.getPrice());
    subscriptionModel.setDetail(subscription.getDetail());

    return subscriptionModel;
  }

  /**
   * Transform a Collection of {@link Subscription} into a Collection of {@link SubscriptionModel}.
   *
   * @param subscriptionsCollection Objects to be transformed.
   * @return List of {@link SubscriptionModel}.
   */
  public Collection<SubscriptionModel> transform(Collection<Subscription> subscriptionsCollection) {
    Collection<SubscriptionModel> subscriptionsModelsCollection;

    if (subscriptionsCollection != null && !subscriptionsCollection.isEmpty()) {
      subscriptionsModelsCollection = new ArrayList<>();
      for (Subscription subscription : subscriptionsCollection) {
        subscriptionsModelsCollection.add(transform(subscription));
      }
    } else {
      subscriptionsModelsCollection = Collections.emptyList();
    }

    return subscriptionsModelsCollection;
  }

}
