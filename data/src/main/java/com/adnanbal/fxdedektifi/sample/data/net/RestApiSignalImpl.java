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

package com.adnanbal.fxdedektifi.sample.data.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.adnanbal.fxdedektifi.sample.data.entity.SignalEntity;
import com.adnanbal.fxdedektifi.sample.data.entity.mapper.SignalEntityJsonMapper;
import com.adnanbal.fxdedektifi.sample.data.exception.NetworkConnectionException;
import io.reactivex.Observable;
import java.net.MalformedURLException;
import java.util.List;

/**
 * {@link RestApiSignal} implementation for retrieving data from the network.
 */
public class RestApiSignalImpl implements RestApiSignal {

  private final Context context;
  private final SignalEntityJsonMapper signalEntityJsonMapper;

  /**
   * Constructor of the class
   *
   * @param context {@link Context}.
   * @param signalEntityJsonMapper {@link SignalEntityJsonMapper}.
   */
  public RestApiSignalImpl(Context context, SignalEntityJsonMapper signalEntityJsonMapper) {
    if (context == null || signalEntityJsonMapper == null) {
      throw new IllegalArgumentException("The constructor parameters cannot be null!!!");
    }
    this.context = context.getApplicationContext();
    this.signalEntityJsonMapper = signalEntityJsonMapper;
  }

  @Override
  public Observable<List<SignalEntity>> signalEntityList() {
    return Observable.create(emitter -> {
      if (isThereInternetConnection()) {
        try {
          String responseSignalEntities = getSignalEntitiesFromApi();
          if (responseSignalEntities != null) {
            emitter.onNext(signalEntityJsonMapper.transformSignalEntityCollection(
                responseSignalEntities));
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
  public Observable<SignalEntity> signalEntityById(int signalId) {
    return Observable.create(emitter -> {
      if (isThereInternetConnection()) {
        try {
          String responseSignalDetails = getSignalDetailsFromApi(signalId);
          if (responseSignalDetails != null) {
            emitter
                .onNext(signalEntityJsonMapper.transformSignalEntity(responseSignalDetails));
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
  public Observable<Boolean> addEntityToCloud(SignalEntity signalEntity) {

    return Observable.create(emitter -> {
      if (isThereInternetConnection()) {
        try {
          String responseAddEntity = postEntitytoApi(signalEntity);
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

  private String getSignalEntitiesFromApi() throws MalformedURLException {
    return ApiConnection.createGET(API_URL_GET_SIGNAL_LIST).request_getSyncCall();
  }

  private String getSignalDetailsFromApi(int signalId) throws MalformedURLException {
    String apiUrl = API_URL_GET_SIGNAL_DETAILS + signalId;
    return ApiConnection.createGET(apiUrl).request_getSyncCall();
  }

  //Todo : correct sync call
  private String postEntitytoApi(SignalEntity signalEntity) throws MalformedURLException {
    String apiUrl = API_URL_POST_SIGNAL + "/" + signalEntity.getPositionId();
    return ApiConnection.createPost(apiUrl).request_postSyncCall(signalEntity);
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
