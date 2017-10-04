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

package com.adnanbal.fxdedektifi.forex.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.adnanbal.fxdedektifi.forex.presentation.AndroidApplication;
import com.adnanbal.fxdedektifi.forex.presentation.R;
import com.adnanbal.fxdedektifi.forex.presentation.internal.di.HasComponent;
import com.adnanbal.fxdedektifi.forex.presentation.internal.di.components.DaggerPositionComponent;
import com.adnanbal.fxdedektifi.forex.presentation.internal.di.components.PositionComponent;
import com.adnanbal.fxdedektifi.forex.presentation.model.PositionModel;
import com.adnanbal.fxdedektifi.forex.presentation.model.SignalModel;
import com.adnanbal.fxdedektifi.forex.presentation.model.UserSignalModel;
import com.adnanbal.fxdedektifi.forex.presentation.view.ConfirmDialogView;
import com.adnanbal.fxdedektifi.forex.presentation.view.fragment.AccountAndSubscriptionsFragment;
import com.adnanbal.fxdedektifi.forex.presentation.view.fragment.AccountAndSubscriptionsFragment.PurchaseListListener;
import com.adnanbal.fxdedektifi.forex.presentation.view.fragment.ConfirmSignalDialogView;
import com.adnanbal.fxdedektifi.forex.presentation.view.fragment.PersonalHistoryFragment;
import com.adnanbal.fxdedektifi.forex.presentation.view.fragment.PersonalHistoryFragment.PositionHistoryListListener;
import com.adnanbal.fxdedektifi.forex.presentation.view.fragment.PersonalPositionsFragment;
import com.adnanbal.fxdedektifi.forex.presentation.view.fragment.PersonalPositionsFragment.PositionListListener;
import com.adnanbal.fxdedektifi.forex.presentation.view.fragment.SignalsFragment;
import com.adnanbal.fxdedektifi.forex.presentation.view.fragment.SignalsFragment.SignalListListener;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.Builder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import org.solovyev.android.checkout.ActivityCheckout;
import org.solovyev.android.checkout.Billing;
import org.solovyev.android.checkout.Checkout;
import org.solovyev.android.checkout.Inventory;
import org.solovyev.android.checkout.ProductTypes;
import org.solovyev.android.checkout.Purchase;

/**
 * Activity that shows a list of Signals.
 */
public class SignalsActivity extends BaseActivity implements HasComponent<PositionComponent>,
    SignalListListener, PositionListListener, PositionHistoryListListener, PurchaseListListener {

  private static final Boolean OPEN = true;
  private static final Boolean CLOSED = false;

  private ActivityCheckout mCheckout;


  @BindView(R.id.appbar)
  Toolbar toolbar;
  @BindView(R.id.drawer_layout)
  DrawerLayout mDrawer;
  @BindView(R.id.navigationView)
  NavigationView navigationViewDrawer;
  @BindView(R.id.idBottomBar)
  BottomNavigationView bottomNavigationView;


  Builder mMaterialDialog;

  ConfirmDialogView confirmDialogView;
  ConfirmSignalDialogView confirmSignalDialogView;

  Unbinder unbinder;

  PositionModel positionModel;
  private Toast toast;

  static final String TAG = "SignalsActivity";

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

//    final Inventory.Request request = Inventory.Request.create();
//    request.loadPurchases(ProductTypes.IN_APP);
//    request.loadPurchases(ProductTypes.SUBSCRIPTION);
//
//    request.loadSkus(SUBSCRIPTION, SKUS);
//    request.loadSkus(ProductTypes.IN_APP, SKUS);

    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
    setContentView(com.adnanbal.fxdedektifi.forex.presentation.R.layout.activity_layout);
    unbinder = ButterKnife.bind(this);

    setUpToolbar_();
    setUpMaterialDialog();
    setupDrawerContent(navigationViewDrawer);

    this.initializeInjector();
    initializeApp(savedInstanceState);

  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    mCheckout.onActivityResult(requestCode, resultCode, data);
    super.onActivityResult(requestCode, resultCode, data);
  }

  private void normalStart() {
    bottomNavigationView.setVisibility(View.VISIBLE);
    navigationViewDrawer.getMenu().findItem(R.id.nav_item_signals_fragment).setVisible(true);

    addFragment(
        com.adnanbal.fxdedektifi.forex.presentation.R.id.fragmentContainer,
        new SignalsFragment());
    navigationViewDrawer.setCheckedItem(R.id.nav_item_signals_fragment);

    bottomNavigationView.setOnNavigationItemSelectedListener(
        new BottomNavigationView.OnNavigationItemSelectedListener() {
          @Override
          public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

              case R.id.action_signals:
                getSupportActionBar().setTitle(R.string.signals);
                navigationViewDrawer.setCheckedItem(R.id.nav_item_signals_fragment);
                addFragment(
                    com.adnanbal.fxdedektifi.forex.presentation.R.id.fragmentContainer,
                    new SignalsFragment());
                break;

              case R.id.action_profit:
                getSupportActionBar().setTitle(R.string.profit);
                navigationViewDrawer.setCheckedItem(R.id.nav_item_profit_fragment);

                addFragment(
                    com.adnanbal.fxdedektifi.forex.presentation.R.id.fragmentContainer,
                    new PersonalHistoryFragment());
                break;

//              case R.id.action_personal_positions:
//                getSupportActionBar().setTitle(R.string.personal_positions);
//                addFragment(
//                    com.adnanbal.fxdedektifi.forex.presentation.R.id.fragmentContainer,
//                    new PersonalPositionsFragment());
//                break;

//              case R.id.action_history:
//                getSupportActionBar().setTitle(R.string.signal_history);
//                addFragment(
//                    com.adnanbal.fxdedektifi.forex.presentation.R.id.fragmentContainer,
//                    new SignalHistoryFragment());
//                break;
            }
            return true;
          }
        });
  }

  private void initializeApp(Bundle savedInstanceState) {

    final Inventory.Request request = Inventory.Request.create();

    //BILLING START
    final Billing billing = AndroidApplication.get(this).getBilling();
    mCheckout = Checkout.forActivity(this, billing);
    mCheckout.start();

    request.loadAllPurchases();

    mCheckout.loadInventory(request, new Inventory.Callback() {

      @Override
      public void onLoaded(@Nonnull Inventory.Products products) {
        List<Purchase> purchaseList = products.get(ProductTypes.SUBSCRIPTION).getPurchases();
        List<Purchase> purchaseInapList = products.get(ProductTypes.IN_APP).getPurchases();

        AndroidApplication.purchasedList = purchaseList;

        if (purchaseList.size() == 0) {
          limitedStart();
        } else {
          normalStart();
        }
        mCheckout.stop();
      }
    });

  }

  private void limitedStart() {

    bottomNavigationView.setVisibility(View.GONE);
    navigationViewDrawer.getMenu().findItem(R.id.nav_item_signals_fragment).setVisible(false);
    getSupportActionBar().setTitle(R.string.my_account);
    addFragment(
        com.adnanbal.fxdedektifi.forex.presentation.R.id.fragmentContainer,
        new AccountAndSubscriptionsFragment());
  }

//  @Override
//  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//    mCheckout.onActivityResult(requestCode, resultCode, data);
//    super.onActivityResult(requestCode, resultCode, data);
//  }

  private void setUpMaterialDialog() {

  }

  private void setupDrawerContent(NavigationView navigationView) {
    navigationView.setNavigationItemSelectedListener(
        new NavigationView.OnNavigationItemSelectedListener() {
          @Override
          public boolean onNavigationItemSelected(MenuItem menuItem) {
            selectDrawerItem(menuItem);
            return true;
          }
        });
  }


  public void selectDrawerItem(MenuItem menuItem) {
    // Create a new fragment and specify the fragment to show based on nav item clicked

    switch (menuItem.getItemId()) {
      case R.id.nav_item_signals_fragment:
        bottomNavigationView.setVisibility(View.VISIBLE);
        getSupportActionBar().setTitle(R.string.signals);
        addFragment(
            com.adnanbal.fxdedektifi.forex.presentation.R.id.fragmentContainer,
            new SignalsFragment());
        break;
      case R.id.nav_item_profit_fragment:
//        bottomNavigationView.setVisibility(View.VISIBLE);
        getSupportActionBar().setTitle(R.string.profit);
        addFragment(
            com.adnanbal.fxdedektifi.forex.presentation.R.id.fragmentContainer,
            new PersonalHistoryFragment());
        break;

      case R.id.nav_item_my_account_fragment:
        bottomNavigationView.setVisibility(View.GONE);
        getSupportActionBar().setTitle(R.string.my_account);
        addFragment(
            com.adnanbal.fxdedektifi.forex.presentation.R.id.fragmentContainer,
            new AccountAndSubscriptionsFragment());
        break;

      default:
        getSupportActionBar().setTitle(R.string.signals);
        addFragment(
            com.adnanbal.fxdedektifi.forex.presentation.R.id.fragmentContainer,
            new SignalsFragment());
    }

    // Highlight the selected item has been done by NavigationView
    menuItem.setChecked(true);

    // Close the navigation drawer
    mDrawer.closeDrawers();
  }


  private void setUpToolbar_() {

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
                com.adnanbal.fxdedektifi.forex.presentation.R.id.fragmentContainer,
                new AccountAndSubscriptionsFragment());
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
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        System.out.println("Drawer Clicked");
        mDrawer.openDrawer((GravityCompat.START));
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle mActionBarDrawerToggle = new ActionBarDrawerToggle(
//            this, drawer, toolbar, R.string.navigation_drawer_open,
//            R.string.navigation_drawer_close);
//        drawer.setDrawerListener(mActionBarDrawerToggle);
//        mActionBarDrawerToggle.syncState();
//        drawer.openDrawer(Gravity.LEFT);
////        drawer.setVisibility(View.VISIBLE);
        return true;
    }
    return super.onOptionsItemSelected(item);
  }


  @Override
  public PositionComponent getComponent() {
    return positionComponent;
  }


  @Override
  protected void onDestroy() {
    unbinder.unbind();
    mCheckout.stop();
    super.onDestroy();
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

    final List<String> signalIdList = new ArrayList<>();

    for (UserSignalModel userSignalModel : AndroidApplication.listUserSignalModel) {
      for (String signalIdTemp : userSignalModel.getSignals().keySet()) {
        signalIdList.add(signalIdTemp);
      }
    }

    //if position is already closed/not open/ No tick is present
    if (!signalIdList.contains(signalModel.getPositionId())) {
      //and comment is empty or null
      if (signalModel.getComment() == null || signalModel.getComment().isEmpty()) {
        dialog.items(R.array.array_signal_click_menu_1);
      } else {
        dialog.items(R.array.array_signal_click_menu_2);
      }

    } else {
      //if position is already open/ tick is present
      if (signalModel.getComment() != null && !signalModel.getComment().isEmpty()) {
        dialog.items(R.array.array_signal_click_menu_4);
      } else if ((signalModel.getComment() != null && signalModel.getComment().isEmpty())
          || signalModel.getComment() == null) {
        dialog.items(R.array.array_signal_click_menu_3);
      }

    }

    /**
     * Material Dialog callback, 0 : show comment , 1 : open  signal , 2: close signal, Show comment is only available when exists
     */
    List<String> finalSignalIdList = signalIdList;
    dialog.itemsCallback(new MaterialDialog.ListCallback() {
      @Override
      public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
        if (confirmSignalDialogView instanceof SignalsFragment) {
          if (text.toString().contains(getString(R.string.open_pos))) {
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
          } else if (text.toString().contains(getString(R.string.close_pos))) {
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

          } else if (!signalIdList.contains(signalModel.getPositionId()) && which == 1) {
            mMaterialDialog.title(R.string.text_dialog_view_signal_title)
                .content(signalModel.getComment())
                .negativeText(R.string.text_dialog_view_OK)
                .cancelable(true);
            mMaterialDialog.show();
          } else if (text.toString().contains(getString(R.string.comment_pos))) {
            mMaterialDialog.title(R.string.text_dialog_view_signal_title)
                .content(signalModel.getComment())
                .negativeText(R.string.text_dialog_view_OK)
                .cancelable(true);
            mMaterialDialog.show();
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

  @Override
  public void onPurchaseSuccess(Purchase purchase) {

    navigationViewDrawer.setCheckedItem(R.id.nav_item_signals_fragment);
    normalStart();
  }

//  private void purchase(Sku sku) {
//    mCheckout.startPurchaseFlow(sku, null, new SignalsActivity.PurchaseListener());
//
//  }
//
//  public class PurchaseListener implements RequestListener<Purchase> {
//
//    @Override
//    public void onSuccess(@Nonnull Purchase result) {
//      Log.d(TAG, "PURCHASE RESULT : " + result.toString());
//
//
//    }
//
//    @Override
//    public void onError(int response, @Nonnull Exception e) {
//      Log.d(TAG, "PURCHASE RESULT : " + e.toString());
//      showToast(e.toString() + "response code : " + ResponseCodes.toString(response) );
//    }
//  }


}
