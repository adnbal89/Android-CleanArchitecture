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
import com.adnanbal.fxdedektifi.forex.presentation.internal.di.components.ApplicationComponent;
import com.adnanbal.fxdedektifi.forex.presentation.internal.di.components.DaggerApplicationComponent;
import com.adnanbal.fxdedektifi.forex.presentation.internal.di.modules.ApplicationModule;
import com.adnanbal.fxdedektifi.forex.presentation.model.SignalModel;
import com.adnanbal.fxdedektifi.forex.presentation.model.UserSignalModel;
import com.crashlytics.android.Crashlytics;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.analytics.FirebaseAnalytics;
import io.fabric.sdk.android.Fabric;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.Nonnull;
import org.solovyev.android.checkout.Billing;
import org.solovyev.android.checkout.Purchase;


/**
 * Android Main Application
 */
public class AndroidApplication extends MultiDexApplication {

  private FirebaseAnalytics mFirebaseAnalytics;


  private ApplicationComponent applicationComponent;
  public static String userUid;
  public static Collection<UserSignalModel> listUserSignalModel;
  public static List<Purchase> purchasedList;
  public static List<SignalModel> listChangedSignalModel;
  public static String accountExpiryTime;
  public static String userEmail;
  public static List<Integer> backtackFragmentList;

  @Override
  public void onCreate() {
    super.onCreate();

    final Fabric fabric = new Fabric.Builder(this)
        .kits(new Crashlytics())
        .debuggable(true)
        .build();
    Fabric.with(this, new Crashlytics());

    FacebookSdk.sdkInitialize(this);
    AppEventsLogger.activateApp(this);

    this.initializeInjector();
    this.initializeLeakDetection();
    listUserSignalModel = new ArrayList<>();
    purchasedList = new ArrayList<>();
    listChangedSignalModel = new ArrayList<>();
    backtackFragmentList = new ArrayList<>();

    // Obtain the FirebaseAnalytics instance.
    mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


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
//    if (BuildConfig.DEBUG) {
//      LeakCanary.install(this);
//    }
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
      final String s = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqZLbZB+g/R1grHdeIj5fZMAlp9qUweCOapEDyBisoiGYQz2ZHyhmFcEns9UAcgBBhI7h8KZ8nCTB+lyNUf3pPC9+VbmE9Dko2zvwq6nHI0wonj78BmK8b793sB+PdnKXDl+LcsSCG8zGydtUBQOHgwFu/SEGyabHwqtRNER4DeDg4X3zLI5TCVlFhnRAF+QprgKy6Eo3P8EZ7J27OHOUbIT+ny10OZ75stjdgyCCwVz7PUy2cCZu0FAVP4nMNinPr89vXvGOda47ywbG5ugmV6KBXXqyWYhY7nxq8IVv6xlvDGsSR7cDtCP3eSxtfuIpg4P7ddh+RbZps7/ioetxRQIDAQAB";
      return s;
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
