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
import com.adnanbal.fxdedektifi.forex.data.entity.UserLoginDetailsEntity;
import com.adnanbal.fxdedektifi.forex.data.entity.mapper.UserLoginDetailsEntityJsonMapper;
import com.adnanbal.fxdedektifi.forex.data.exception.NetworkConnectionException;
import com.google.firebase.database.ServerValue;
import io.reactivex.Observable;
import java.net.MalformedURLException;

/**
 * {@link RestApiUserLoginDetails} implementation for adding/retrieving data from the network.
 */
public class RestApiUserLoginDetailsImpl implements RestApiUserLoginDetails {

  private final Context context;
  private final UserLoginDetailsEntityJsonMapper userLoginDetailsEntityJsonMapper;

  /**
   * Constructor of the class
   *
   * @param context {@link android.content.Context}.
   * @param userLoginDetailsEntityJsonMapper {@link UserLoginDetailsEntityJsonMapper}.
   */
  public RestApiUserLoginDetailsImpl(Context context,
      UserLoginDetailsEntityJsonMapper userLoginDetailsEntityJsonMapper) {
    if (context == null || userLoginDetailsEntityJsonMapper == null) {
      throw new IllegalArgumentException("The constructor parameters cannot be null!!!");
    }
    this.context = context.getApplicationContext();
    this.userLoginDetailsEntityJsonMapper = userLoginDetailsEntityJsonMapper;
  }

  //TODO : implement
  @Override
  public Observable<Boolean> addEntityToCloud(UserLoginDetailsEntity userLoginDetailsEntity) {
    return Observable.create(emitter -> {
      if (isThereInternetConnection()) {
        try {
          String responseAddEntity = postEntitytoApi(userLoginDetailsEntity);
          if (responseAddEntity != null) {
            //TODO : correct here
            //userLoginDetailsEntityJsonMapper.transformUserLoginDetailsEntity(responseRemoveEntity)
            emitter
                .onNext(true);
            emitter.onComplete();
          } else {
            emitter.onError(new NetworkConnectionException());
          }
        } catch (Exception e) {
          emitter.onError(new NetworkConnectionException(e.getCause()));
        }
      } else {
        //TODO : implement offline Data access;
        emitter.onError(new NetworkConnectionException());
      }
    });
  }

  //Todo : correct sync call
  private String postEntitytoApi(UserLoginDetailsEntity userLoginDetailsEntity)
      throws MalformedURLException {
    String apiUrl = API_URL_POST_USER_LOGIN_DETAILS + "/" + userLoginDetailsEntity.getUserUid() + JSON_EXTENSION;


    userLoginDetailsEntity.setDate(ServerValue.TIMESTAMP);
    return ApiConnection.createPost(apiUrl)
        .request_postUserLoginDetailEntitySyncCall(userLoginDetailsEntity);
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
