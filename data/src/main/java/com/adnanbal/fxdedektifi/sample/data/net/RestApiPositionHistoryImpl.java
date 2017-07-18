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
import com.adnanbal.fxdedektifi.sample.data.entity.PositionEntity;
import com.adnanbal.fxdedektifi.sample.data.entity.mapper.PositionEntityJsonMapper;
import com.adnanbal.fxdedektifi.sample.data.exception.NetworkConnectionException;
import io.reactivex.Observable;
import java.net.MalformedURLException;
import java.util.List;

/**
 * {@link RestApiPosition} implementation for retrieving data from the network.
 */
public class RestApiPositionHistoryImpl implements RestApiPositionHistory {

  private final Context context;
  private final PositionEntityJsonMapper positionEntityJsonMapper;

  /**
   * Constructor of the class
   *
   * @param context {@link Context}.
   * @param positionEntityJsonMapper {@link PositionEntityJsonMapper}.
   */
  public RestApiPositionHistoryImpl(Context context,
      PositionEntityJsonMapper positionEntityJsonMapper) {
    if (context == null || positionEntityJsonMapper == null) {
      throw new IllegalArgumentException("The constructor parameters cannot be null!!!");
    }
    this.context = context.getApplicationContext();
    this.positionEntityJsonMapper = positionEntityJsonMapper;
  }

  @Override
  public Observable<List<PositionEntity>> positionHistoryEntityList() {
    return Observable.create(emitter -> {
      if (isThereInternetConnection()) {
        try {
          String responsePositionEntities = getPositionEntitiesFromApi();
          if (responsePositionEntities != null) {
            emitter.onNext(positionEntityJsonMapper.transformPositionEntityCollection(
                responsePositionEntities));
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
  public Observable<PositionEntity> positionHistoryEntityById(int positionId) {
    return Observable.create(emitter -> {
      if (isThereInternetConnection()) {
        try {
          String responsePositionDetails = getPositionDetailsFromApi(positionId);
          if (responsePositionDetails != null) {
            emitter
                .onNext(positionEntityJsonMapper.transformPositionEntity(responsePositionDetails));
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

  private String getPositionEntitiesFromApi() throws MalformedURLException {
    return ApiConnection.createGET(API_URL_GET_POSITION_LIST).request_getSyncCall();
  }

  private String getPositionDetailsFromApi(int positionId) throws MalformedURLException {
    String apiUrl = API_URL_GET_POSITION_DETAILS + positionId;
    return ApiConnection.createGET(apiUrl).request_getSyncCall();
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
