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
package com.adnanbal.fxdedektifi.sample.presentation.internal.di.modules;

import android.content.Context;
import com.adnanbal.fxdedektifi.sample.data.cache.PositionCache;
import com.adnanbal.fxdedektifi.sample.data.cache.PositionCacheImpl;
import com.adnanbal.fxdedektifi.sample.data.cache.UserCache;
import com.adnanbal.fxdedektifi.sample.data.cache.UserCacheImpl;
import com.adnanbal.fxdedektifi.sample.data.executor.JobExecutor;
import com.adnanbal.fxdedektifi.sample.data.repository.PositionDataRepository;
import com.adnanbal.fxdedektifi.sample.data.repository.UserDataRepository;
import com.adnanbal.fxdedektifi.sample.domain.executor.PostExecutionThread;
import com.adnanbal.fxdedektifi.sample.domain.executor.ThreadExecutor;
import com.adnanbal.fxdedektifi.sample.domain.repository.PositionRepository;
import com.adnanbal.fxdedektifi.sample.domain.repository.UserRepository;
import com.adnanbal.fxdedektifi.sample.presentation.AndroidApplication;
import com.adnanbal.fxdedektifi.sample.presentation.UIThread;
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
  PositionRepository providePositionRepository(PositionDataRepository positionDataRepository) {
    return positionDataRepository;
  }

  @Provides
  @Singleton
  UserRepository provideUserRepository(UserDataRepository userDataRepository) {
    return userDataRepository;
  }
}
