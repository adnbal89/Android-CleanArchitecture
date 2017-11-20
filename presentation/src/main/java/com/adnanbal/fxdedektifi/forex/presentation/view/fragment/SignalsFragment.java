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
import android.content.Context;
import android.os.Bundle;
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
import com.adnanbal.fxdedektifi.forex.presentation.model.UserSignalDB;
import com.adnanbal.fxdedektifi.forex.presentation.presenter.SignalListPresenter;
import com.adnanbal.fxdedektifi.forex.presentation.sqlite.DatabaseHandler;
import com.adnanbal.fxdedektifi.forex.presentation.view.SignalListView;
import com.adnanbal.fxdedektifi.forex.presentation.view.adapter.PositionsLayoutManager;
import com.adnanbal.fxdedektifi.forex.presentation.view.adapter.SignalsAdapter;
import com.mobapphome.mahandroidupdater.tools.MAHUpdaterController;
import io.fabric.sdk.android.Fabric;
import java.util.Collection;
import javax.inject.Inject;

public class SignalsFragment extends BaseFragment implements SignalListView,
    ConfirmSignalDialogView {

  static final String TAG = "SignalsFragment";

  /*
  * Butterknife unbinder, will be executed to clear callbacks
   */
  Unbinder unbinder;


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
  //  @Inject
//  UserLoginDetailsPresenter userLoginDetailsPresenter;
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

    // For MAHUpdater (VersionChecker) init
    try {
      MAHUpdaterController.init(getActivity(),
          "https://fxingsign.firebaseio.com/versionChecker.json", null, false);
    } catch (Exception e) {
      Fabric.getLogger().e(TAG, e.getMessage());
    }

    try {
      this.getComponent(PositionComponent.class).inject(this);
    } catch (Exception e) {
      Fabric.getLogger().e(TAG, e.getMessage());
      getActivity().finish();
    }
//    UserLoginDetailsModel userLoginDetailsModel = new UserLoginDetailsModel();
//    userLoginDetailsModel.setUserUid(AndroidApplication.userUid);
//    userLoginDetailsPresenter.addUserLoginDetails(userLoginDetailsModel);

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


  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    if (this.signalListPresenter != null) {
      this.signalListPresenter.setView(this);

    }
    if (savedInstanceState == null) {
      this.loadUserSignalsList(AndroidApplication.userUid);
      this.loadSignalList();
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    this.signalsAdapter.notifyDataSetChanged();
    if (this.signalListPresenter != null) {
      this.signalListPresenter.resume();
    }
  }

  @Override
  public void onPause() {
    super.onPause();
    if (this.signalListPresenter != null) {
      this.signalListPresenter.pause();
    }

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
    if (this.signalListPresenter != null) {
      this.signalListPresenter.destroy();
    }

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
    if (rl_progress != null) {
      this.rl_progress.setVisibility(View.VISIBLE);
    }
    this.getActivity().setProgressBarIndeterminateVisibility(true);
  }

  @Override
  public void hideLoading() {
    if (rl_progress != null) {
      this.rl_progress.setVisibility(View.GONE);
    }
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
    this.signalsAdapter.notifyDataSetChanged();

  }

  @Override
  public void viewSignal(SignalModel signalModel) {
    if (this.signalListListener != null) {
      this.signalListListener.onSignalClicked(signalModel, this);
    }

  }

  @Override
  public void openSignalConfirmedOnline(SignalModel signalModel, Boolean openOrClose) {
    if (openOrClose) {
      this.showToastMessage(getResources().getString(R.string.text_signal_opened));
    } else {
      this.showToastMessage(getResources().getString(R.string.text_signal_closed));
    }

    DatabaseHandler db;
    db = new DatabaseHandler(context());

    UserSignalDB temp = new UserSignalDB(signalModel.getId());
    if (!openOrClose) {
      if (db.Exists(signalModel.getId())) {
        db.deleteUserSignalDB(temp);
      }
    } else {
      if (!db.Exists(signalModel.getId())) {
        db.addUserSignalDB(temp);
      }
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
