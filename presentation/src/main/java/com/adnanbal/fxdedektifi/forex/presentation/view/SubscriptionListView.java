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

package com.adnanbal.fxdedektifi.forex.presentation.view;

import com.adnanbal.fxdedektifi.forex.presentation.model.SubscriptionModel;
import java.util.Collection;

/**
 * Interface representing a View in a model view presenter (MVP) pattern. In this case is used as a
 * view representing a list of {@link SubscriptionModel}.
 */
public interface SubscriptionListView extends LoadDataView {
  
  /**
   * Render a subscription list in the UI.
   *
   * @param subscriptionModelCollection The collection of {@link SubscriptionModel} that will be shown.
   */
  void renderSubscriptionList(Collection<SubscriptionModel> subscriptionModelCollection);

  /**
   * View a {@link SubscriptionModel} profile/details.
   *
   * @param subscriptionModel The subscription that will be shown.
   */
  void viewSubscription(SubscriptionModel subscriptionModel);

}
