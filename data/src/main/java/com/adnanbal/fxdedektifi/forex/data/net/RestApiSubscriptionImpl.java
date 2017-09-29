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

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.adnanbal.fxdedektifi.forex.data.entity.SubscriptionEntity;
import com.adnanbal.fxdedektifi.forex.data.entity.mapper.SubscriptionEntityJsonMapper;
import com.adnanbal.fxdedektifi.forex.data.exception.NetworkConnectionException;
import io.reactivex.Observable;
import java.net.MalformedURLException;
import java.util.List;

public class RestApiSubscriptionImpl implements RestApiSubscription {

  private final Context context;
  private final SubscriptionEntityJsonMapper subscriptionEntityJsonMapper;


  /**
   * Constructor of the class
   *
   * @param context {@link Context}.
   * @param subscriptionEntityJsonMapper {@link SubscriptionEntityJsonMapper}.
   */
  public RestApiSubscriptionImpl(Context context,
      SubscriptionEntityJsonMapper subscriptionEntityJsonMapper) {

    if (context == null || subscriptionEntityJsonMapper == null) {
      throw new IllegalArgumentException("The constructor parameters cannot be null!!!");
    }

    this.context = context.getApplicationContext();
    this.subscriptionEntityJsonMapper = subscriptionEntityJsonMapper;
  }

  @Override
  public Observable<List<SubscriptionEntity>> subscriptionEntityList() {
    return Observable.create(emitter -> {
      if (isThereInternetConnection()) {
        try {
          String responseSubscriptionList = getSubscriptionListFromApi();
          if (responseSubscriptionList != null) {
            emitter
                .onNext(subscriptionEntityJsonMapper
                    .transformSubscriptionEntityCollection(responseSubscriptionList));
            emitter.onComplete();
          } else {
            emitter.onError(new NetworkConnectionException());
          }
        } catch (Exception e) {
          emitter.onError(new NetworkConnectionException(e.getCause()));
        }
      } else {
        emitter.onError(new NetworkConnectionException());
      }
    });
  }

  @Override
  public Observable<List<SubscriptionEntity>> purchasedSubscriptionsList(
      String authenticatedUserUid) {
    return Observable.create(emitter -> {
      if (isThereInternetConnection()) {
        try {
          String responsePurchasedSubscriptionList = getPurchasedSubscriptionListFromApi();
          if (responsePurchasedSubscriptionList != null) {
            emitter
                .onNext(subscriptionEntityJsonMapper
                    .transformSubscriptionEntityCollection(responsePurchasedSubscriptionList));
            emitter.onComplete();
          } else {
            emitter.onError(new NetworkConnectionException());
          }
        } catch (Exception e) {
          emitter.onError(new NetworkConnectionException(e.getCause()));
        }
      } else {
        emitter.onError(new NetworkConnectionException());
      }
    });
  }

  private String getPurchasedSubscriptionListFromApi() {
    //Todo : fix
    return null;
  }

  private String getSubscriptionListFromApi() throws MalformedURLException {

    //Todo : fix
    return null;
  }

  /**
   * Checks if the device has any active internet connection.
   *
   * @return true device with internet connection, otherwise false.
   */
  private boolean isThereInternetConnection() {
    boolean isConnected;

    ConnectivityManager connectivityManager =
        (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
    isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

    return isConnected;
  }
}
