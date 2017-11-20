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
import com.adnanbal.fxdedektifi.forex.data.entity.NewPositionEntity;
import com.adnanbal.fxdedektifi.forex.data.entity.mapper.NewSignalEntityJsonMapper;
import com.adnanbal.fxdedektifi.forex.data.exception.NetworkConnectionException;
import com.firebase.client.Firebase;
import com.google.firebase.database.FirebaseDatabase;
import io.reactivex.Observable;
import java.net.MalformedURLException;

/**
 * {@link RestApiNewSignal} implementation for push data to the network.
 */
public class RestApiNewSignalImpl implements RestApiNewSignal {

  private static final String SIGNAL_OPERATION_NEW = "new";
  private static final String SIGNAL_OPERATION_EDIT = "edit";
  private static final String SIGNAL_OPERATION_CLOSE = "close";


  private FirebaseDatabase firebaseDatabase;
  private final Context context;
  private final NewSignalEntityJsonMapper newSignalEntityJsonMapper;

  /**
   * Constructor of the class
   *
   * @param context {@link Context}.
   * @param newSignalEntityJsonMapper {@link NewSignalEntityJsonMapper}.
   */
  public RestApiNewSignalImpl(FirebaseDatabase firebaseDatabase, Context context,
      NewSignalEntityJsonMapper newSignalEntityJsonMapper) {

    if (context == null || newSignalEntityJsonMapper == null) {
      throw new IllegalArgumentException("The constructor parameters cannot be null!!!");
    }

    Firebase.setAndroidContext(context);
    this.firebaseDatabase = firebaseDatabase;
    this.context = context.getApplicationContext();
    this.newSignalEntityJsonMapper = newSignalEntityJsonMapper;
  }

  @Override
  public Observable<Boolean> createNewSignal(NewPositionEntity newPositionEntity,
      String operationOnSignal) {

    return Observable.create(emitter -> {
      if (isThereInternetConnection()) {
        try {
          String responseAddEntity = "";

          if (operationOnSignal.equals(SIGNAL_OPERATION_NEW)) {
            responseAddEntity = postEntitytoApi(newPositionEntity);
          } else if (operationOnSignal.equals(SIGNAL_OPERATION_EDIT)) {
            responseAddEntity = putEntitytoApi(newPositionEntity);
          } else if (operationOnSignal.equals(SIGNAL_OPERATION_CLOSE)) {
            responseAddEntity = putEntitytoApi(newPositionEntity);
          }

          if (responseAddEntity != null) {

            //TODO : correct here
            //positionEntityJsonMapper.transformPositionEntity(responseRemoveEntity)
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

  private String postEntitytoApi(NewPositionEntity newPositionEntity)
      throws MalformedURLException {
    String apiUrl =
        API_URL_POST_SIGNAL + JSON_EXTENSION;
    return ApiConnection.createPost(apiUrl).request_postNewSignalSyncCall(newPositionEntity);
  }

  private String putEntitytoApi(NewPositionEntity newPositionEntity)
      throws MalformedURLException {
    String apiUrl =
        API_URL_POST_SIGNAL + "/" + newPositionEntity.getId() + JSON_EXTENSION;
    return ApiConnection.createPut(apiUrl).request_putNewSignalSyncCall(newPositionEntity);
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

