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

import static com.adnanbal.fxdedektifi.forex.data.net.BaseFirebaseDataSource.FIREBASE_CHILD_KEY_POSITION_HISTORY;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.adnanbal.fxdedektifi.forex.data.entity.PositionEntity;
import com.adnanbal.fxdedektifi.forex.data.entity.SignalEntity;
import com.adnanbal.fxdedektifi.forex.data.entity.mapper.PositionEntityJsonMapper;
import com.adnanbal.fxdedektifi.forex.data.exception.NetworkConnectionException;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.FirebaseException;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import io.reactivex.Observable;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link RestApiPosition} implementation for retrieving data from the network.
 */
public class RestApiPositionHistoryImpl implements RestApiPositionHistory {

  private final Context context;
  private final PositionEntityJsonMapper positionEntityJsonMapper;
  /**
   * The target node for a given service
   */
  private DatabaseReference childReference = null;
  private FirebaseDatabase firebaseDatabase;


  /**
   * Constructor of the class
   *
   * @param context {@link Context}.
   * @param positionEntityJsonMapper {@link PositionEntityJsonMapper}.
   */
  public RestApiPositionHistoryImpl(FirebaseDatabase firebaseDatabase, Context context,
      PositionEntityJsonMapper positionEntityJsonMapper) {
    if (context == null || positionEntityJsonMapper == null) {
      throw new IllegalArgumentException("The constructor parameters cannot be null!!!");
    }
    Firebase.setAndroidContext(context);
    this.firebaseDatabase = firebaseDatabase;
    this.context = context.getApplicationContext();
    this.positionEntityJsonMapper = positionEntityJsonMapper;
  }

  public DatabaseReference getChildReference() {
    if (childReference == null) {
      this.childReference = this.firebaseDatabase.
          getReference()
          .child(FIREBASE_CHILD_KEY_POSITION_HISTORY);
    }

    return childReference;
  }

  @Override
  public Observable<List<PositionEntity>> positionHistoryEntityList() {
    return Observable.create(emitter -> {
      Firebase myFirebaseRef = new Firebase("https://fxingsign.firebaseio.com/");

      if (isThereInternetConnection()) {

        myFirebaseRef.child("positionHistory").addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot snapshot) {
            List<PositionEntity> list = new ArrayList<>();
            for (DataSnapshot child : snapshot.getChildren()) {
//              child.getValue(SignalEntity.class).setId(child.getKey());
              list.add(0, child.getValue(SignalEntity.class));
            }
            emitter.onNext(list);
          }

          @Override
          public void onCancelled(FirebaseError error) {
            emitter.onError(new FirebaseException(error.getMessage()));
          }
        });
      } else {
        emitter.onError(new NetworkConnectionException());
      }
    });
  }

  @Override
  public Observable<PositionEntity> positionHistoryEntityById(String positionId) {
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

  private String getPositionDetailsFromApi(String positionId) throws MalformedURLException {
    String apiUrl = API_URL_GET_POSITION_DETAILS + positionId + JSON_EXTENSION;
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
