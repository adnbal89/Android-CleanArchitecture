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
import com.adnanbal.fxdedektifi.forex.data.entity.SignalEntity;
import com.adnanbal.fxdedektifi.forex.data.entity.UserSignalEntity;
import com.adnanbal.fxdedektifi.forex.data.entity.mapper.SignalEntityJsonMapper;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@link RestApiSignal} implementation for retrieving data from the network.
 */
public class RestApiSignalImpl extends BaseFirebaseDataSource implements RestApiSignal {

  /**
   * The target node for a given service
   */
  private DatabaseReference childReference = null;
  private FirebaseDatabase firebaseDatabase;
  private Map<String, List<String>> listChangedField = new HashMap<>();
  List<String> changedFieldsList;

  private final Context context;
  private final SignalEntityJsonMapper signalEntityJsonMapper;
  Firebase myFirebaseRef;


  /**
   * Constructor of the class
   *
   * @param context {@link Context}.
   * @param signalEntityJsonMapper {@link SignalEntityJsonMapper}.
   */
  public RestApiSignalImpl(FirebaseDatabase firebaseDatabase, Context context,
      SignalEntityJsonMapper signalEntityJsonMapper) {

    if (context == null || signalEntityJsonMapper == null) {
      throw new IllegalArgumentException("The constructor parameters cannot be null!!!");
    }

    Firebase.setAndroidContext(context);
    this.firebaseDatabase = firebaseDatabase;
    this.context = context.getApplicationContext();
    this.signalEntityJsonMapper = signalEntityJsonMapper;
  }

  public DatabaseReference getChildReference() {
    if (childReference == null) {
      this.childReference = this.firebaseDatabase.
          getReference()
          .child(FIREBASE_CHILD_KEY_SIGNAL);
    }

    return childReference;
  }

//  /**
//   * Allows to get the {@link List<SignalEntity>}
//   */
//  public Observable<SignalEntity> getSignalList() {
//    return RxFirebase.getObservable(getChildReference(), SignalEntity.class);
//  }

  @Override
  public Observable<List<SignalEntity>> signalEntityList() {

    return Observable.create(emitter -> {

      Firebase myFirebaseRef = new Firebase("https://fxingsign.firebaseio.com/");
      if (isThereInternetConnection()) {

        myFirebaseRef.child("signal").addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot snapshot) {
            List<SignalEntity> list = new ArrayList<>();

            for (DataSnapshot child : snapshot.getChildren()) {
//              child.getValue(SignalEntity.class).setId(child.getKey());
              SignalEntity signalEntity = child.getValue(SignalEntity.class);
              if (listChangedField != null && listChangedField.size() != 0) {
                signalEntity.setChangedFields(listChangedField);
              }

              list.add(0, signalEntity);
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
  public Observable<SignalEntity> signalEntityById(String signalId) {
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


  @Override
  public Observable<Boolean> patchUserSignalMapToCloud(Map<String, Boolean> userSignalMap,
      String authenticatedUserUid, boolean openOrClose) {

    return Observable.create(emitter -> {
      if (isThereInternetConnection()) {
        try {
          String responeUserSignalFromApi;

          //if position open clicked
          if (openOrClose) {
            responeUserSignalFromApi = patchEntitytoApi(userSignalMap, authenticatedUserUid);
          } else {
            responeUserSignalFromApi = patchDeleteEntityFromApi(userSignalMap,
                authenticatedUserUid);
          }

          if (responeUserSignalFromApi == "200" || responeUserSignalFromApi != null) {

            //TODO : correct here
            //positionEntityJsonMapper.transformPositionEntity(responseRemoveEntity)
            emitter
                .onNext(true);
//            emitter.onComplete();
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


  @Override
  public Observable<List<UserSignalEntity>> userSignalEntityList(String authenticatedUserUid) {

    return Observable.create(emitter -> {
      Firebase myFirebaseRef = new Firebase("https://fxingsign.firebaseio.com/");

      if (isThereInternetConnection()) {

        myFirebaseRef.child("user_signal/" + authenticatedUserUid)
            .addValueEventListener(new ValueEventListener() {
              @Override
              public void onDataChange(DataSnapshot snapshot) {
                List<UserSignalEntity> list = new ArrayList<>();

                list.add(snapshot.getValue(UserSignalEntity.class));

                emitter.onNext(list);

              }

              @Override
              public void onCancelled(FirebaseError error) {
              }
            });

      } else {
        emitter.onError(new NetworkConnectionException());
      }
    });
  }

  private String getSignalEntitiesFromApi() throws MalformedURLException {
    return ApiConnection.createGET(API_URL_GET_SIGNAL_LIST).request_getSyncCall();
  }

  private String getSignalDetailsFromApi(String signalId) throws MalformedURLException {
    String apiUrl = API_URL_GET_SIGNAL_DETAILS + signalId + JSON_EXTENSION;
    return ApiConnection.createGET(apiUrl).request_getSyncCall();
  }

  //Todo : correct sync call
  private String postEntitytoApi(SignalEntity signalEntity) throws MalformedURLException {
    String apiUrl = API_URL_POST_SIGNAL + "/" + signalEntity.getId() + JSON_EXTENSION;
    return ApiConnection.createPost(apiUrl).request_postSyncCall(signalEntity);
  }

  private String patchEntitytoApi(Map<String, Boolean> userSignalMap, String authenticatedUserUid)
      throws MalformedURLException {
    String apiUrl =
        API_URL_PATCH_USER_SIGNAL + "/" + authenticatedUserUid + "/" + "signals" + JSON_EXTENSION;
    return ApiConnection.createPatch(apiUrl).request_patchSyncCall(userSignalMap);
  }

  private String patchDeleteEntityFromApi(Map<String, Boolean> userSignalMap,
      String authenticatedUserUid)
      throws MalformedURLException {
    String signalToDelete = null;

    for (String signalToDeleteId : userSignalMap.keySet()) {
      signalToDelete = signalToDeleteId;
    }

    String apiUrl =
        API_URL_PATCH_USER_SIGNAL + "/" + authenticatedUserUid + "/" + "signals" + "/"
            + signalToDelete + JSON_EXTENSION;
    return ApiConnection.createPatch(apiUrl).request_deleteSyncCall();
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
