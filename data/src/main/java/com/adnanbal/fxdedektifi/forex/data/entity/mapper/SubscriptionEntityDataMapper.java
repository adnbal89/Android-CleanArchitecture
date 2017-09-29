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

package com.adnanbal.fxdedektifi.forex.data.entity.mapper;

import com.adnanbal.fxdedektifi.forex.data.entity.SubscriptionEntity;
import com.adnanbal.fxdedektifi.forex.domain.model.Subscription;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;

public class SubscriptionEntityDataMapper {

  @Inject
  SubscriptionEntityDataMapper() {
  }

  /**
   * Transform a {@link SubscriptionEntity} into an {@link Subscription}.
   *
   * @param subscriptionEntity Object to be transformed.
   * @return {@link Subscription} if valid {@link SubscriptionEntity} otherwise null.
   */
  public Subscription transform(SubscriptionEntity subscriptionEntity) {
    Subscription subscription = null;
    if (subscriptionEntity != null) {
      subscription = new Subscription();
      subscription.setName(subscriptionEntity.getName());
      subscription.setPrice(subscriptionEntity.getPrice());
      subscription.setDetail(subscriptionEntity.getDetail());

    }
    return subscription;
  }

  /**
   * Transform a List of {@link SubscriptionEntity} into a Collection of {@link Subscription}.
   *
   * @param subscriptionEntityCollection Object Collection to be transformed.
   * @return {@link Subscription} if valid {@link SubscriptionEntity} otherwise null.
   */
  public List<Subscription> transform(Collection<SubscriptionEntity> subscriptionEntityCollection) {
    final List<Subscription> subscriptionList = new ArrayList<>(20);
    for (SubscriptionEntity subscriptionEntity : subscriptionEntityCollection) {
      final Subscription subscription = transform(subscriptionEntity);
      if (subscription != null) {
        subscriptionList.add(subscription);
      }
    }
    return subscriptionList;
  }

}
