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

package com.adnanbal.fxdedektifi.forex.domain.interactor;

import com.adnanbal.fxdedektifi.forex.domain.executor.PostExecutionThread;
import com.adnanbal.fxdedektifi.forex.domain.executor.ThreadExecutor;
import com.adnanbal.fxdedektifi.forex.domain.model.Subscription;
import com.adnanbal.fxdedektifi.forex.domain.repository.SubscriptionRepository;
import io.reactivex.Observable;
import java.util.List;
import javax.inject.Inject;

public class GetPurchasedSubscriptionList extends
    UseCase<List<Subscription>, GetPurchasedSubscriptionList.Params> {

  /**
   * This class is an implementation of {@link UseCase} that represents a use case for
   * retrieving a collection of all {@link Subscription}.
   */
  private SubscriptionRepository subscriptionRepository;

  @Inject
  GetPurchasedSubscriptionList(SubscriptionRepository subscriptionRepository,
      ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.subscriptionRepository = subscriptionRepository;
  }

  @Override
  Observable<List<Subscription>> buildUseCaseObservable(Params params) {
    return this.subscriptionRepository.getPurchasedSubscriptionsList(params.authenticatedUserUid);

  }

  public static final class Params {

    String authenticatedUserUid;

    private Params(String authenticatedUserUid) {
      this.authenticatedUserUid = authenticatedUserUid;
    }

    public static GetPurchasedSubscriptionList.Params forSubscription(String authenticatedUserUid) {
      return new GetPurchasedSubscriptionList.Params(authenticatedUserUid);
    }
  }
}
