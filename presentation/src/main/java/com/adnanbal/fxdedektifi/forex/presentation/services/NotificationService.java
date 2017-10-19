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

package com.adnanbal.fxdedektifi.forex.presentation.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings.System;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;
import com.adnanbal.fxdedektifi.forex.data.exception.NetworkConnectionException;
import com.adnanbal.fxdedektifi.forex.presentation.AndroidApplication;
import com.adnanbal.fxdedektifi.forex.presentation.R;
import com.adnanbal.fxdedektifi.forex.presentation.model.SignalModel;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.FirebaseException;
import com.firebase.client.ValueEventListener;

public class NotificationService extends Service {

  static final String TAG = "NotificationService";
  int countFlag = 0;

  private NotificationCompat.Builder builder;
  private NotificationManager notificationManager;
  private int notificationId;


  public NotificationService() {
  }

  @Override
  public IBinder onBind(Intent intent) {
    // TODO: Return the communication channel to the service.
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    if (intent != null && intent.hasExtra("counter")) {
      Bundle bundle = intent.getExtras();
      countFlag = bundle.getInt("counter");
    } else {
      countFlag = 0;
    }

    Firebase.setAndroidContext(getApplicationContext());

    Firebase myFirebaseRef = new Firebase("https://fxingsign.firebaseio.com/");
    if (isThereInternetConnection()) {

      myFirebaseRef.child("signal").addChildEventListener(new ChildEventListener() {


        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

          if (countFlag > 0) {
            SignalModel signalModel;

            signalModel = dataSnapshot.getValue(SignalModel.class);
            signalModel.setId(dataSnapshot.getKey());
            //TODO : Notification impl. code

            if (!AndroidApplication.listChangedSignalModel.contains(signalModel)) {
              AndroidApplication.listChangedSignalModel.add(signalModel);
            }

            showUpdatedSignalNotification(signalModel, "Open");


          } else {
            //DataInitialization
          }

        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

          SignalModel signalModel;

          signalModel = dataSnapshot.getValue(SignalModel.class);
          signalModel.setId(dataSnapshot.getKey());

            if (!AndroidApplication.listChangedSignalModel.contains(signalModel)) {
              AndroidApplication.listChangedSignalModel.add(signalModel);
            }



          //TODO : Notification impl. code
          if (signalModel.getClosingPrice() != 0) {
            showUpdatedSignalNotification(signalModel, "Closed");
          } else {
            showUpdatedSignalNotification(signalModel, "Updated");
          }

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
          SignalModel signalModel;

          signalModel = dataSnapshot.getValue(SignalModel.class);
          signalModel.setId(dataSnapshot.getKey());
          //TODO : Notification for signal closing

          showUpdatedSignalNotification(signalModel, "Closed");

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {
          Log.e(TAG, new FirebaseException(firebaseError.getMessage()).getMessage());

        }
      });

      myFirebaseRef.child("signal").addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
          countFlag++;
        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {

        }
      });

    } else {
//      Log.e(TAG, new NetworkConnectionException().getMessage());
    }
    return START_STICKY;
  }

  public void showUpdatedSignalNotification(SignalModel signalModel, String status) {
    setUpNotificationBuilder(signalModel, status);
    startForeground(0, builder.build());
    notificationManager.notify(0, builder.build());

  }

  /**
   * Checks if the device has any active internet connection.
   *
   * @return true device with internet connection, otherwise false.
   */
  private boolean isThereInternetConnection() {
    boolean isConnected;

    ConnectivityManager connectivityManager =
        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
    isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

    return isConnected;
  }

  private void setUpNotificationBuilder(SignalModel signalModel, String status) {

    notificationManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);

    builder =
        new NotificationCompat.Builder(this, "1")
            .setSmallIcon(R.drawable.ic_comment_alternative)
            .setBadgeIconType(R.drawable.ic_launcher)
            .setVibrate(new long[]{1000, 1000})
            .setSound(System.DEFAULT_NOTIFICATION_URI)
            .setAutoCancel(true);

    if (status.equals("Open")) {
      builder.setContentTitle("You have a new signal")
          .setContentText(
              signalModel.getPair() + " : " + (signalModel.isBuy_sell() ? "Buy" : "Sell") + " at "
                  + signalModel.getOpeningPrice()).setPriority(Notification.PRIORITY_MAX);
    } else if (status.equals("Updated")) {
      builder.setContentTitle("Current signal update")
          .setContentText(
              signalModel.getPair() + " : " + (signalModel.isBuy_sell() ? "Buy" : "Sell") + " at "
                  + signalModel.getOpeningPrice()).setPriority(Notification.PRIORITY_MAX);

    } else if (status.equals("Closed")) {
      builder.setContentTitle("Close position signal")
          .setContentText(
              signalModel.getPair() + " : " + "Close at "
                  + signalModel.getClosingPrice()).setPriority(Notification.PRIORITY_MAX);
    }
//    Intent notificationIntent = new Intent(getContext(), SignalsActivity.class);
//    PendingIntent contentIntent = PendingIntent.getActivity(getContext(), 0, notificationIntent,
//        PendingIntent.FLAG_UPDATE_CURRENT);
//    builder.setContentIntent();

  }
}
