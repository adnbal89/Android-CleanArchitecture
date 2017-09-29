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

package com.adnanbal.fxdedektifi.forex.data.net;

import com.adnanbal.fxdedektifi.forex.data.entity.SubscriptionEntity;
import java.util.List;
import io.reactivex.Observable;


public interface RestApiSubscription {

  String API_BASE_URL =
      "https://fxingsign.firebaseio.com/";
  String JSON_EXTENSION = ".json";

  /**
   * Api url for getting all signals
   */
  String API_URL_GET_SIGNAL_LIST = API_BASE_URL + "subscriptions"+".json";

  /**
   * Retrieves an {@link Observable} which will emit a List of {@link SubscriptionEntity}.
   */
  Observable<List<SubscriptionEntity>> subscriptionEntityList();

  /**
   * Get an {@link Observable} which will emit a {@link SubscriptionEntity}.
   *
   * @param authenticatedUserUid The authenticated user id used to retrieve purchased subscription
   * data by this authenticated user.
   */
  Observable<List<SubscriptionEntity>> purchasedSubscriptionsList(String authenticatedUserUid);


}
