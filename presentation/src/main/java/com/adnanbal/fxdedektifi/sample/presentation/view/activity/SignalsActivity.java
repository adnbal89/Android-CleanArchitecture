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

package com.adnanbal.fxdedektifi.sample.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.adnanbal.fxdedektifi.sample.presentation.R;
import com.adnanbal.fxdedektifi.sample.presentation.internal.di.HasComponent;
import com.adnanbal.fxdedektifi.sample.presentation.internal.di.components.DaggerPositionComponent;
import com.adnanbal.fxdedektifi.sample.presentation.internal.di.components.PositionComponent;
import com.adnanbal.fxdedektifi.sample.presentation.model.PositionModel;
import com.adnanbal.fxdedektifi.sample.presentation.model.SignalModel;
import com.adnanbal.fxdedektifi.sample.presentation.view.ConfirmDialogView;
import com.adnanbal.fxdedektifi.sample.presentation.view.fragment.AccountFragment;
import com.adnanbal.fxdedektifi.sample.presentation.view.fragment.ConfirmSignalDialogView;
import com.adnanbal.fxdedektifi.sample.presentation.view.fragment.PersonalHistoryFragment;
import com.adnanbal.fxdedektifi.sample.presentation.view.fragment.PersonalHistoryFragment.PositionHistoryListListener;
import com.adnanbal.fxdedektifi.sample.presentation.view.fragment.PersonalPositionsFragment;
import com.adnanbal.fxdedektifi.sample.presentation.view.fragment.PersonalPositionsFragment.PositionListListener;
import com.adnanbal.fxdedektifi.sample.presentation.view.fragment.SignalsFragment;
import com.adnanbal.fxdedektifi.sample.presentation.view.fragment.SignalsFragment.SignalListListener;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.Builder;

/**
 * Activity that shows a list of Signals.
 */
public class SignalsActivity extends BaseActivity implements HasComponent<PositionComponent>,
    SignalListListener, PositionListListener, PositionHistoryListListener {

  private static final Boolean OPEN = true;
  private static final Boolean CLOSED = false;

  @BindView(R.id.appbar)
  Toolbar toolbar;

  Builder mMaterialDialog;

  ConfirmDialogView confirmDialogView;
  ConfirmSignalDialogView confirmSignalDialogView;

  Unbinder unbinder;

  PositionModel positionModel;
  private Toast toast;


  public static Intent getCallingIntent(Context context) {
    return new Intent(context, SignalsActivity.class);
  }

  private void showToast(String message) {
    if (toast != null) {
      toast.cancel();
      toast = null;
    }
    toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
    toast.show();
  }

  private PositionComponent positionComponent;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
    setContentView(com.adnanbal.fxdedektifi.sample.presentation.R.layout.activity_layout);
    BottomNavigationView bottomNavigationView = findViewById(R.id.idBottomBar);

    setUpToolbar_();
    setUpMaterialDialog();

    this.initializeInjector();

    if (savedInstanceState == null) {
      addFragment(
          com.adnanbal.fxdedektifi.sample.presentation.R.id.fragmentContainer,
          new SignalsFragment());
    }
    bottomNavigationView.setOnNavigationItemSelectedListener(
        new BottomNavigationView.OnNavigationItemSelectedListener() {
          @Override
          public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

              case R.id.action_signals:
                getSupportActionBar().setTitle(R.string.signals);
                addFragment(
                    com.adnanbal.fxdedektifi.sample.presentation.R.id.fragmentContainer,
                    new SignalsFragment());
                break;

              case R.id.action_profit:
                getSupportActionBar().setTitle(R.string.profit);
                addFragment(
                    com.adnanbal.fxdedektifi.sample.presentation.R.id.fragmentContainer,
                    new PersonalHistoryFragment());
                break;

//              case R.id.action_personal_positions:
//                getSupportActionBar().setTitle(R.string.personal_positions);
//                addFragment(
//                    com.adnanbal.fxdedektifi.sample.presentation.R.id.fragmentContainer,
//                    new PersonalPositionsFragment());
//                break;

//              case R.id.action_history:
//                getSupportActionBar().setTitle(R.string.signal_history);
//                addFragment(
//                    com.adnanbal.fxdedektifi.sample.presentation.R.id.fragmentContainer,
//                    new SignalHistoryFragment());
//                break;
            }
            return true;
          }
        });

  }


  private void setUpMaterialDialog() {

  }

  private void setUpToolbar_() {

    unbinder = ButterKnife.bind(this);
    // Sets the Toolbar to act as the ActionBar for this Activity window.
    // Make sure the toolbar exists in the activity and is not null
    //Inflate the Toolbar with a menu

    setSupportActionBar(toolbar);
    getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
      @Override
      public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
          //Change the ImageView image source depends on menu item click
          case R.id.action_account:

            getSupportActionBar().setTitle(R.string.my_account);
            addFragment(
                com.adnanbal.fxdedektifi.sample.presentation.R.id.fragmentContainer,
                new AccountFragment());
            return true;

        }
        //If above criteria does not meet then default is false;
        return false;
      }
    });

  }

  private void initializeInjector() {
    this.positionComponent = DaggerPositionComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .build();
  }

  @Override
  public PositionComponent getComponent() {
    return positionComponent;
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    unbinder.unbind();
  }

  // Menu icons are inflated just as they were with actionbar
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.appbar_menu, menu);
    return true;
  }

  @Override
  public void onPositionClicked(final PositionModel positionModel, ConfirmDialogView view) {
    //  this.navigator.navigateToUserDetails(this, userModel.getUserId());
    //    Toast.makeText(this, "Position Clicked!!!", Toast.LENGTH_SHORT).show();

    mMaterialDialog = new MaterialDialog.Builder(this);

    mMaterialDialog
        .onPositive(new MaterialDialog.SingleButtonCallback() {
          @Override
          public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
            confirmDialogView.dialogConfirmed(positionModel);
            Toast.makeText(SignalsActivity.this, R.string.toast_dialog_confirmed,
                Toast.LENGTH_SHORT).show();
          }
        }).onNegative(new MaterialDialog.SingleButtonCallback() {
      @Override
      public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
        dialog.dismiss();
      }
    });

    this.confirmDialogView = view;
    this.positionModel = positionModel;

    if (view instanceof PersonalPositionsFragment) {
      mMaterialDialog.title(R.string.text_dialog_view_title)
          .content(R.string.text_dialog_view_content)
          .positiveText(R.string.text_dialog_view_OK)
          .negativeText(R.string.text_dialog_view_Cancel)
          .cancelable(true);
    } else if (view instanceof PersonalHistoryFragment) {

      if (positionModel.getComment() != null && !positionModel.getComment().isEmpty()) {

        mMaterialDialog.title(R.string.text_dialog_view_comment_content)
            .content(positionModel.getComment())
            .negativeText(R.string.text_dialog_view_OK)
            .cancelable(true);

      } else {
        mMaterialDialog.title(R.string.text_dialog_no_content_title)
            .negativeText(R.string.text_dialog_view_OK)
            .cancelable(true);
      }
    }
    mMaterialDialog.show();

  }

  public void showList(final SignalModel signalModel) {
    Builder dialog = new MaterialDialog.Builder(this)
        .title(signalModel.getPair())
        .titleColor(getResources().getColor(R.color.color_material_dialog_signal_fragment));

    if (signalModel.getComment() == null || signalModel.getComment().isEmpty()) {
      dialog.items(R.array.array_signal_click_menu_1);
    } else {
      dialog.items(R.array.array_signal_click_menu_2);
    }

    /**
     * Material Dialog callback, 0 : show comment , 1 : open  signal , 2: close signal, Show comment is only available when exists
     */
    dialog.itemsCallback(new MaterialDialog.ListCallback() {
      @Override
      public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
        if (confirmSignalDialogView instanceof SignalsFragment) {
          if (which == 2) {
            if (signalModel.getComment() != null && !signalModel.getComment().isEmpty()) {
              mMaterialDialog.title(R.string.text_dialog_view_signal_title)
                  .content(signalModel.getComment())
                  .negativeText(R.string.text_dialog_view_OK)
                  .cancelable(true);
              mMaterialDialog.show();
            }
          } else if (which == 0) {
            mMaterialDialog
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                  @Override
                  public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    confirmSignalDialogView.signalDialogConfirmed(signalModel, true);
                  }
                }).onNegative(new MaterialDialog.SingleButtonCallback() {
              @Override
              public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
              }
            });

            mMaterialDialog.title(R.string.text_dialog_open_signal_view_title)
                .content(R.string.text_dialog_open_signal_view_content)
                .positiveText(R.string.text_dialog_view_OK)
                .negativeText(R.string.text_dialog_view_Cancel)
                .cancelable(true).show();

          } else if (which == 1) {
            mMaterialDialog
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                  @Override
                  public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    confirmSignalDialogView.signalDialogConfirmed(signalModel, false);
                  }
                }).onNegative(new MaterialDialog.SingleButtonCallback() {
              @Override
              public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
              }
            });

            mMaterialDialog.title(R.string.text_dialog_close_signal_view_title)
                .content(R.string.text_dialog_close_signal_view_content)
                .positiveText(R.string.text_dialog_view_OK)
                .negativeText(R.string.text_dialog_view_Cancel)
                .cancelable(true).show();
          }
        }
      }
    })
        .show();
  }

  @Override
  public void onSignalClicked(SignalModel signalModel, ConfirmSignalDialogView view) {

    mMaterialDialog = new MaterialDialog.Builder(this);

    this.confirmSignalDialogView = view;
    showList(signalModel);

//    if (view instanceof SignalsFragment) {
//      if (signalModel.getComment() != null && !signalModel.getComment().isEmpty()) {
//        mMaterialDialog.title(R.string.text_dialog_view_signal_title)
//            .content(signalModel.getComment())
//            .negativeText(R.string.text_dialog_view_OK)
//            .cancelable(true);
//        mMaterialDialog.show();
//      }
//    }

  }
}
