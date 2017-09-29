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

package com.adnanbal.fxdedektifi.forex.presentation.view.fragment;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.adnanbal.fxdedektifi.forex.presentation.AndroidApplication;
import com.adnanbal.fxdedektifi.forex.presentation.R;
import com.adnanbal.fxdedektifi.forex.presentation.internal.di.components.PositionComponent;
import com.adnanbal.fxdedektifi.forex.presentation.model.SignalModel;
import com.adnanbal.fxdedektifi.forex.presentation.presenter.SignalListPresenter;
import com.adnanbal.fxdedektifi.forex.presentation.view.SignalListView;
import com.adnanbal.fxdedektifi.forex.presentation.view.activity.SignalsActivity;
import com.adnanbal.fxdedektifi.forex.presentation.view.adapter.PositionsLayoutManager;
import com.adnanbal.fxdedektifi.forex.presentation.view.adapter.SignalsAdapter;
import java.util.Collection;
import javax.inject.Inject;

public class SignalsFragment extends BaseFragment implements SignalListView,
    ConfirmSignalDialogView {

  /*
  * Butterknife unbinder, will be executed to clear callbacks
   */
  Unbinder unbinder;

  NotificationCompat.Builder builder;
  int counter = 0;
  int repeat = 0;

  /**
   * When Signal open dialog is confirmed
   */
  @Override
  public void signalDialogConfirmed(SignalModel signalModel, boolean openOrClose) {
    signalModel.setOpen(openOrClose);
    this.openOrClose = openOrClose;

//   Todo: Reserved for later use.
//    if (openOrClose && !signalModel.getUsers().keySet().contains(AndroidApplication.userUid)) {
//      signalModel.getUsers().put(AndroidApplication.userUid, true);
//    } else if (!openOrClose && signalModel.getUsers().keySet()
//        .contains(AndroidApplication.userUid)) {
//      signalModel.getUsers().remove(AndroidApplication.userUid);
//    }

    openOrCloseSignal(signalModel, openOrClose);
  }

  /**
   * Interface for listening signal list events.
   */
  public interface SignalListListener {
    void onSignalClicked(final SignalModel positionModel, ConfirmSignalDialogView view);
  }

  @Inject
  SignalListPresenter signalListPresenter;
  @Inject
  SignalsAdapter signalsAdapter;

  @BindView(R.id.rv_signals)
  RecyclerView rv_signals;
  @BindView(com.adnanbal.fxdedektifi.forex.presentation.R.id.rl_progress)
  RelativeLayout rl_progress;
  @BindView(com.adnanbal.fxdedektifi.forex.presentation.R.id.rl_retry)
  RelativeLayout rl_retry;
  @BindView(com.adnanbal.fxdedektifi.forex.presentation.R.id.bt_retry)
  Button bt_retry;

  boolean openOrClose;

  private SignalsFragment.SignalListListener signalListListener;

  public SignalsFragment() {
    setRetainInstance(true);
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    if (activity instanceof SignalListListener) {
      this.signalListListener = (SignalListListener) activity;
    }
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.getComponent(PositionComponent.class).inject(this);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    final View fragmentView = inflater
        .inflate(R.layout.fragment_signals, container, false);
    unbinder = ButterKnife.bind(this, fragmentView);
    setupRecyclerView();
    return fragmentView;
  }


  private void setUpNotificationBuilder() {
    builder =
        new NotificationCompat.Builder(getActivity())
            .setSmallIcon(R.drawable.ic_launcher)
            .setContentTitle("Fxing Sign Signal Update")
            .setContentText("You have a updated content, please check")
            .setAutoCancel(true);
    Intent notificationIntent = new Intent(getActivity(), SignalsActivity.class);
    PendingIntent contentIntent = PendingIntent.getActivity(getActivity(), 0, notificationIntent,
        PendingIntent.FLAG_UPDATE_CURRENT);
    builder.setContentIntent(contentIntent);

    // Add as notification
    NotificationManager manager = (NotificationManager) getActivity().getSystemService(
        Context.NOTIFICATION_SERVICE);

    builder.getNotification().flags |= Notification.FLAG_AUTO_CANCEL;

    manager.notify(0, builder.build());

  }


  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    this.signalListPresenter.setView(this);
    if (savedInstanceState == null) {
      this.loadUserSignalsList(AndroidApplication.userUid);
      this.loadSignalList();
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    this.signalListPresenter.resume();
  }

  @Override
  public void onPause() {
    super.onPause();
    this.signalListPresenter.pause();

  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    rv_signals.setAdapter(null);
    unbinder.unbind();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    this.signalListPresenter.destroy();

  }

  @Override
  public void onDetach() {
    super.onDetach();
    this.signalListListener = null;

  }

  @Override
  public Context context() {
    return this.getActivity().getApplicationContext();
  }

  @Override
  public void showLoading() {
    this.rl_progress.setVisibility(View.VISIBLE);
    this.getActivity().setProgressBarIndeterminateVisibility(true);
  }

  @Override
  public void hideLoading() {
    this.rl_progress.setVisibility(View.GONE);
    this.getActivity().setProgressBarIndeterminateVisibility(false);
  }

  @Override
  public void showRetry() {
    this.rl_retry.setVisibility(View.VISIBLE);
  }

  @Override
  public void hideRetry() {
    this.rl_retry.setVisibility(View.GONE);
  }

  @Override
  public void renderSignalList(Collection<SignalModel> signalModelCollection) {
    if (signalModelCollection != null) {
      this.signalsAdapter.setSignalsCollection(signalModelCollection);
    }

    if (repeat > 0) {
      setUpNotificationBuilder();
    }
    repeat++;

  }

  @Override
  public void viewSignal(SignalModel signalModel) {
    if (this.signalListListener != null) {
      this.signalListListener.onSignalClicked(signalModel, this);
    }


  }

  @Override
  public void openSignalConfirmedOnline(SignalModel signalModel) {
    if (openOrClose) {
      this.showToastMessage(getResources().getString(R.string.text_signal_opened));
    } else {
      this.showToastMessage(getResources().getString(R.string.text_signal_closed));
    }

    this.signalsAdapter.notifyDataSetChanged();

  }

  @Override
  public void showError(String message) {
    this.showToastMessage(message);
  }

  private void setupRecyclerView() {
    this.signalsAdapter.setOnItemClickListener(onItemClickListener);

//    SlideInUpAnimator animator = new SlideInUpAnimator(new OvershootInterpolator(1f));
//    this.rv_signals.setItemAnimator(animator);
    this.rv_signals.setLayoutManager(new PositionsLayoutManager(context()));
    this.rv_signals.setAdapter(signalsAdapter);
  }


  /**
   * Open a position and move it to personal positions.
   */
  private void openOrCloseSignal(SignalModel signalModel, boolean openOrClose) {
    this.signalListPresenter.open(signalModel, openOrClose);
  }

  /**
   * Loads all positions.
   */
  private void loadSignalList() {
    this.signalListPresenter.initialize();
  }

  /**
   * Loads all open userSignals.
   */
  private void loadUserSignalsList(String authenticatedUserUid) {
    this.signalListPresenter.initializeUser_Signals(authenticatedUserUid);
  }

  @OnClick(com.adnanbal.fxdedektifi.forex.presentation.R.id.bt_retry)
  void onButtonRetryClick() {
    SignalsFragment.this.loadUserSignalsList(AndroidApplication.userUid);
    SignalsFragment.this.loadSignalList();
  }

  private SignalsAdapter.OnItemClickListener onItemClickListener =
      new SignalsAdapter.OnItemClickListener() {
        @Override
        public void onSignalItemClicked(SignalModel signalModel) {

          if (SignalsFragment.this.signalListPresenter != null
              && signalModel != null) {
            SignalsFragment.this.signalListPresenter.onSignalClicked(signalModel);
          }
        }
      };

}
