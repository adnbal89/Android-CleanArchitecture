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
package com.adnanbal.fxdedektifi.forex.presentation;

import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatActivity;
import com.adnanbal.fxdedektifi.forex.domain.model.User;
import com.adnanbal.fxdedektifi.forex.presentation.internal.di.components.ApplicationComponent;
import com.adnanbal.fxdedektifi.forex.presentation.internal.di.components.DaggerApplicationComponent;
import com.adnanbal.fxdedektifi.forex.presentation.internal.di.modules.ApplicationModule;
import com.adnanbal.fxdedektifi.forex.presentation.model.UserSignalModel;
import com.adnanbal.fxdedektifi.forex.presentation.util.Encryption;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.squareup.leakcanary.LeakCanary;
import java.util.ArrayList;
import java.util.Collection;
import javax.annotation.Nonnull;
import org.solovyev.android.checkout.Billing;

/**
 * Android Main Application
 */
public class AndroidApplication extends MultiDexApplication {

  private ApplicationComponent applicationComponent;
  public static String userUid;
  public static Collection<UserSignalModel> listUserSignalModel;

  @Override
  public void onCreate() {
    super.onCreate();
    FacebookSdk.sdkInitialize(this);
    AppEventsLogger.activateApp(this);

    this.initializeInjector();
    this.initializeLeakDetection();
    listUserSignalModel = new ArrayList<>();
  }

  private void initializeInjector() {
    this.applicationComponent = DaggerApplicationComponent.builder()
        .applicationModule(new ApplicationModule(this))
        .build();
  }

  public ApplicationComponent getApplicationComponent() {
    return this.applicationComponent;
  }

  private void initializeLeakDetection() {
    if (BuildConfig.DEBUG) {
      LeakCanary.install(this);
    }
  }

  @Nonnull
  private final Billing mBilling = new Billing(this, new Billing.DefaultConfiguration() {
    @Nonnull
    @Override
    public String getPublicKey() {
      // encrypted public key of the app. Plain version can be found in Google Play's Developer
      // Console in Service & APIs section under "YOUR LICENSE KEY FOR THIS APPLICATION" title.
      // A naive encryption algorithm is used to "protect" the key. See more about key protection
      // here: https://developer.android.com/google/play/billing/billing_best_practices.html#key
      final String s = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAisM/KtPGjOYmQ/eVNLKW/6I/x3W9AccU/0SzeQX+gKYWoj1KbRC4rQ64u3/J2hI/vUAmqE5BleFqjmAaZpzK3V2sHc5ze8A01lA1+UUoORctSQ5mby6M7d+/C239dKxThAUCdcDtsw9sONYxGVY9KLqeXzzLiJoBEQVJWTChEG0e5rLxEM51UBEz6DVJVpl+pjhe8jlFMPIrgxrbwqbkTQfOwgAIEhSSLzS53tFWuwZdGxWTPOs8E+YTrOcQMDMzWnwIARNqevASWfAMV538G4betX0uL9uBDFCtBYqG/GzOGKe9DFFZ6jmIalq9e5uyGLefcqFf0MIREzJJBS6zowIDAQAB";
      return Encryption.decrypt(s, "fxdedektifi@gmail.com");
    }
  });

  /**
   * Returns an instance of {@link AndroidApplication} attached to the passed activity.
   */
  public static AndroidApplication get(AppCompatActivity activity) {
    return (AndroidApplication) activity.getApplication();
  }

  @Nonnull
  public Billing getBilling() {
    return mBilling;
  }


}
