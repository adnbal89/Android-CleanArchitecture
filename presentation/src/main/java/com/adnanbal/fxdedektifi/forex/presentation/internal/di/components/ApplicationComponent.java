/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.adnanbal.fxdedektifi.forex.presentation.internal.di.components;

import android.content.Context;
import com.adnanbal.fxdedektifi.forex.domain.executor.PostExecutionThread;
import com.adnanbal.fxdedektifi.forex.domain.executor.ThreadExecutor;
import com.adnanbal.fxdedektifi.forex.domain.repository.PositionHistoryRepository;
import com.adnanbal.fxdedektifi.forex.domain.repository.PositionRepository;
import com.adnanbal.fxdedektifi.forex.domain.repository.SignalRepository;
import com.adnanbal.fxdedektifi.forex.domain.repository.SubscriptionRepository;
import com.adnanbal.fxdedektifi.forex.domain.repository.UserLoginDetailsRepository;
import com.adnanbal.fxdedektifi.forex.domain.repository.UserRepository;
import com.adnanbal.fxdedektifi.forex.presentation.external.AnalyticsInterface;
import com.adnanbal.fxdedektifi.forex.presentation.internal.di.modules.ApplicationModule;
import com.adnanbal.fxdedektifi.forex.presentation.view.activity.BaseActivity;
import com.google.firebase.database.FirebaseDatabase;
import dagger.Component;
import javax.inject.Singleton;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

  void inject(BaseActivity baseActivity);

  //Exposed to sub-graphs.
  Context context();

  ThreadExecutor threadExecutor();

  PostExecutionThread postExecutionThread();

  PositionRepository positionRepository();

  UserRepository userRepository();

  SignalRepository signalRepository();

  PositionHistoryRepository positionHistoryRepository();

  FirebaseDatabase firebaseDatabase();

  AnalyticsInterface analyticsHelper();

  SubscriptionRepository subscriptionRepository();

  UserLoginDetailsRepository userLoginDetailsRepository();
}
