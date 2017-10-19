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
package com.adnanbal.fxdedektifi.forex.presentation.internal.di.modules;

import android.content.Context;
import com.adnanbal.fxdedektifi.forex.data.cache.PositionCache;
import com.adnanbal.fxdedektifi.forex.data.cache.PositionCacheImpl;
import com.adnanbal.fxdedektifi.forex.data.cache.PositionHistoryCache;
import com.adnanbal.fxdedektifi.forex.data.cache.PositionHistoryCacheImpl;
import com.adnanbal.fxdedektifi.forex.data.cache.SignalCache;
import com.adnanbal.fxdedektifi.forex.data.cache.SignalCacheImpl;
import com.adnanbal.fxdedektifi.forex.data.cache.UserCache;
import com.adnanbal.fxdedektifi.forex.data.cache.UserCacheImpl;
import com.adnanbal.fxdedektifi.forex.data.executor.JobExecutor;
import com.adnanbal.fxdedektifi.forex.data.repository.PositionDataRepository;
import com.adnanbal.fxdedektifi.forex.data.repository.PositionHistoryDataRepository;
import com.adnanbal.fxdedektifi.forex.data.repository.SignalDataRepository;
import com.adnanbal.fxdedektifi.forex.data.repository.SubscriptionDataRepository;
import com.adnanbal.fxdedektifi.forex.data.repository.UserDataRepository;
import com.adnanbal.fxdedektifi.forex.data.repository.UserLoginDetailsDataRepository;
import com.adnanbal.fxdedektifi.forex.domain.executor.PostExecutionThread;
import com.adnanbal.fxdedektifi.forex.domain.executor.ThreadExecutor;
import com.adnanbal.fxdedektifi.forex.domain.repository.PositionHistoryRepository;
import com.adnanbal.fxdedektifi.forex.domain.repository.PositionRepository;
import com.adnanbal.fxdedektifi.forex.domain.repository.SignalRepository;
import com.adnanbal.fxdedektifi.forex.domain.repository.SubscriptionRepository;
import com.adnanbal.fxdedektifi.forex.domain.repository.UserLoginDetailsRepository;
import com.adnanbal.fxdedektifi.forex.domain.repository.UserRepository;
import com.adnanbal.fxdedektifi.forex.presentation.AndroidApplication;
import com.adnanbal.fxdedektifi.forex.presentation.UIThread;
import com.adnanbal.fxdedektifi.forex.presentation.external.AnalyticsInterface;
import com.adnanbal.fxdedektifi.forex.presentation.firebase.FirebaseAnalyticsHelper;
import com.adnanbal.fxdedektifi.forex.presentation.presenter.SignalListPresenter;
import com.adnanbal.fxdedektifi.forex.presentation.presenter.UserLoginDetailsPresenter;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.FirebaseDatabase;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */

@Module
public class ApplicationModule {

  private final AndroidApplication application;

  public ApplicationModule(AndroidApplication application) {
    this.application = application;
  }

//  @Provides
//  @ApplicationScope
//  public FirebaseAuth provideFirebaseAuth() {
//    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//    return firebaseAuth;
//  }

  @Provides
  @Singleton
  public FirebaseDatabase provideFirebaseDatabase() {
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    firebaseDatabase.setPersistenceEnabled(true);
    return firebaseDatabase;
  }

  @Provides
  @Singleton
  public AnalyticsInterface provideAnalyticsHelper(Context context) {
    AnalyticsInterface AnalyticsInterface = new FirebaseAnalyticsHelper(
        FirebaseAnalytics.getInstance(context));
    return AnalyticsInterface;
  }

  @Provides
  @Singleton
  public SignalListPresenter provideSignalListPresenter(SignalListPresenter signalListPresenter) {
    return signalListPresenter;
  }

  @Provides
  @Singleton
  public UserLoginDetailsPresenter provideUserLoginDetailsPresenter(
      UserLoginDetailsPresenter userLoginDetailsPresenter) {
    return userLoginDetailsPresenter;
  }

  @Provides
  @Singleton
  Context provideApplicationContext() {
    return this.application;
  }

  @Provides
  @Singleton
  ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
    return jobExecutor;
  }

  @Provides
  @Singleton
  PostExecutionThread providePostExecutionThread(UIThread uiThread) {
    return uiThread;
  }

  //Caches
  @Provides
  @Singleton
  UserCache provideUserCache(UserCacheImpl userCache) {
    return userCache;
  }

  @Provides
  @Singleton
  PositionCache providePositionCache(PositionCacheImpl positionCache) {
    return positionCache;
  }

  @Provides
  @Singleton
  SignalCache provideSignalCache(SignalCacheImpl signalCache) {
    return signalCache;
  }

  @Provides
  @Singleton
  PositionHistoryCache providePositionHistoryCache(PositionHistoryCacheImpl positionHistoryCache) {
    return positionHistoryCache;
  }

  //Repositories
  @Provides
  @Singleton
  PositionRepository providePositionRepository(PositionDataRepository positionDataRepository) {
    return positionDataRepository;
  }

  @Provides
  @Singleton
  UserRepository provideUserRepository(UserDataRepository userDataRepository) {
    return userDataRepository;
  }

  @Provides
  @Singleton
  SignalRepository provideSignalRepository(SignalDataRepository signalDataRepository) {
    return signalDataRepository;
  }

  @Provides
  @Singleton
  SubscriptionRepository provideSubscriptionRepository(
      SubscriptionDataRepository subscriptionDataRepository) {
    return subscriptionDataRepository;
  }

  @Provides
  @Singleton
  PositionHistoryRepository providePositionHistoryRepository(
      PositionHistoryDataRepository positionHistoryDataRepository) {
    return positionHistoryDataRepository;
  }

  @Provides
  @Singleton
  UserLoginDetailsRepository provideUserLoginDetailsRepository(
      UserLoginDetailsDataRepository userLoginDetailsDataRepository) {
    return userLoginDetailsDataRepository;
  }


}
