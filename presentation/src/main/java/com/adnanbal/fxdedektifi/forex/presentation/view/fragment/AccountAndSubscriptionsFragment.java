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

import static org.solovyev.android.checkout.ProductTypes.SUBSCRIPTION;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.adnanbal.fxdedektifi.forex.presentation.AndroidApplication;
import com.adnanbal.fxdedektifi.forex.presentation.R;
import com.adnanbal.fxdedektifi.forex.presentation.internal.di.components.PositionComponent;
import com.adnanbal.fxdedektifi.forex.presentation.model.SubscriptionModel;
import com.adnanbal.fxdedektifi.forex.presentation.presenter.SubscriptionListPresenter;
import com.adnanbal.fxdedektifi.forex.presentation.view.SubscriptionListView;
import com.adnanbal.fxdedektifi.forex.presentation.view.adapter.SkusAdapter;
import com.adnanbal.fxdedektifi.forex.presentation.view.adapter.SubscriptionsLayoutManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import org.solovyev.android.checkout.Billing;
import org.solovyev.android.checkout.Checkout;
import org.solovyev.android.checkout.IntentStarter;
import org.solovyev.android.checkout.Inventory;
import org.solovyev.android.checkout.ProductTypes;
import org.solovyev.android.checkout.Purchase;
import org.solovyev.android.checkout.RequestListener;
import org.solovyev.android.checkout.Sku;
import org.solovyev.android.checkout.UiCheckout;

public class AccountAndSubscriptionsFragment extends BaseFragment implements SubscriptionListView {

  private AccountAndSubscriptionsFragment.PurchaseListListener purchaseListListener;

  private static final List<String> SKUS = Arrays
      .asList("monthly",
          "three_month", "six_month"
      );

  /**
   * Interface for listening signal list events.
   */
  public interface PurchaseListListener {

    void onPurchaseSuccess(Purchase purchase);
  }

  private UiCheckout mCheckout;
  /*
  * Butterknife unbinder, will be executed to clear callbacks
   */
  Unbinder unbinder;

  @Inject
  SkusAdapter skusAdapter;
  @Inject
  SubscriptionListPresenter subscriptionListPresenter;

  @BindView(com.adnanbal.fxdedektifi.forex.presentation.R.id.rl_progress)
  RelativeLayout rl_progress;
  @BindView(com.adnanbal.fxdedektifi.forex.presentation.R.id.rl_retry)
  RelativeLayout rl_retry;
  @BindView(com.adnanbal.fxdedektifi.forex.presentation.R.id.bt_retry)
  Button bt_retry;
  @BindView(R.id.rv_subscriptions)
  RecyclerView rv_subscriptions;
  @BindView(R.id.txt_expiryTime)
  TextView txt_expiryTime;


  static final String TAG = "SubscriptionsFragment";


  public AccountAndSubscriptionsFragment() {
    setRetainInstance(true);
  }


  @Override
  public void onAttach(Context context) {
    super.onAttach(context);

    //TODO : fix
    //    if (activity instanceof SubscriptionListListener) {
//      this.subscriptionListListener = (SubscriptionListListener) activity;
//    }
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    final AppCompatActivity activity = (AppCompatActivity) getActivity();
    final Billing billing = AndroidApplication.get(activity).getBilling();
    mCheckout = Checkout.forUi(new MyIntentStarter(this), this, billing);

    this.purchaseListListener = (PurchaseListListener) activity;
    this.getComponent(PositionComponent.class).inject(this);
    mCheckout.start();


  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    final View fragmentView = inflater
        .inflate(R.layout.fragment_account_and_subscriptions, container, false);
    unbinder = ButterKnife.bind(this, fragmentView);
    setupRecyclerView();

    if (AndroidApplication.purchasedList.size() != 0) {
      txt_expiryTime
          .setText(context().getResources().getString(R.string.seeActiveSubcriptions));
    } else {
      txt_expiryTime.setText(context().getResources().getString(R.string.expiryTimeComment) + " "
          + AndroidApplication.accountExpiryTime + " " + getResources().getString(R.string.days));
    }

    //Show loading circle while waiting subs. lists to be loaded.
    if (isThereInternetConnection()) {
      showLoading();
    } else {
      showError(getResources().getString(R.string.no_internet_connection));
    }

    return fragmentView;
  }


  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    mCheckout.onActivityResult(requestCode, resultCode, data);
    super.onActivityResult(requestCode, resultCode, data);
  }

  private void setupRecyclerView() {
    this.skusAdapter.setOnItemClickListener(onItemClickListener);

//    SlideInUpAnimator animator = new SlideInUpAnimator(new OvershootInterpolator(1f));
//    this.rv_signals.setItemAnimator(animator);
    this.rv_subscriptions.setLayoutManager(new SubscriptionsLayoutManager(context()));
    this.rv_subscriptions.setAdapter(skusAdapter);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    final Inventory.Request request = Inventory.Request.create();
    request.loadPurchases(ProductTypes.IN_APP);
    request.loadPurchases(ProductTypes.SUBSCRIPTION);

    request.loadSkus(SUBSCRIPTION, SKUS);
    request.loadSkus(ProductTypes.IN_APP, SKUS);
    request.loadAllPurchases();

    mCheckout.loadInventory(request, new Inventory.Callback() {
      @Override
      public void onLoaded(@Nonnull Inventory.Products products) {

        List<Purchase> purchaseList = products.get(ProductTypes.SUBSCRIPTION).getPurchases();
        List<Purchase> sortedList = new ArrayList<>();

        List<Sku> skuList = new ArrayList<>();
        skuList.addAll(products.get(ProductTypes.SUBSCRIPTION).getSkus());

        // Now sort by address instead of name (default).
        Collections.sort(skuList, new Comparator<Sku>() {
          public int compare(Sku one, Sku other) {
            return one.price.compareTo(other.price);
          }
        });

        List<Sku> skuInAppList = products.get(ProductTypes.IN_APP).getSkus();
        List<Purchase> purchaseInapList = products.get(ProductTypes.IN_APP).getPurchases();

        //Hİde loading after subscriptionsList are ready.
        hideLoading();

        skusAdapter.setSkusCollection(skuList);
        rv_subscriptions.setAdapter(skusAdapter);
      }
    });


  }


  private void purchase(Sku sku) {
    mCheckout.startPurchaseFlow(sku, null, new PurchaseListener());

  }

  @Override
  public void onResume() {
    super.onResume();
  }

  @Override
  public void onPause() {
    super.onPause();
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    rv_subscriptions.setAdapter(null);
    unbinder.unbind();
  }

  @Override
  public void onDestroy() {
    mCheckout.stop();
    super.onDestroy();
  }

  @Override
  public void onDetach() {
    super.onDetach();
    this.purchaseListListener = null;

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
    if (rl_retry != null)

    {
      this.rl_retry.setVisibility(View.VISIBLE);
    }
  }

  @Override
  public void hideRetry() {
    if (rl_retry != null)

    {
      this.rl_retry.setVisibility(View.GONE);
    }
  }

  @Override
  public void showError(String message) {
    this.showToastMessage(message);
  }

  @Override
  public Context context() {
    return this.getActivity().getApplicationContext();
  }

  @Override
  public void renderSubscriptionList(Collection<SubscriptionModel> subscriptionModelCollection) {
    if (subscriptionModelCollection == null) {
      subscriptionModelCollection = new ArrayList<>();
    }

    if (subscriptionModelCollection != null) {
//      this.skusAdapter.setSubscriptionsCollection(subscriptionModelCollection);
    }
  }

  @Override
  public void viewSubscription(SubscriptionModel subscriptionModel) {
    //Todo : fix addingListener & presenter
    //    if (this.signalListListener != null) {
//      this.signalListListener.onSignalClicked(signalModel, this);
//    }
  }

  private SkusAdapter.OnItemClickListener onItemClickListener =
      new SkusAdapter.OnItemClickListener() {
        @Override
        public void onSkuItemClicked(Sku sku) {

//          if (AndroidApplication.purchasedList.size() > 0) {
//            showToastMessage(getResources().getString(R.string.alreadyownpurchase));
//          } else {
//            purchase(sku);
//          }

          purchase(sku);

          //TODO : fix
//          if (AccountAndSubscriptionsFragment.this.signalListPresenter != null
//              && signalModel != null) {
//            SignalsFragment.this.signalListPresenter.onSignalClicked(signalModel);
//          }
        }
      };


  public class PurchaseListener implements RequestListener<Purchase> {

    @Override
    public void onSuccess(@Nonnull Purchase result) {
      Log.d(TAG, "PURCHASE RESULT : " + result.toString());
      List<Purchase> list = new ArrayList();
      list.addAll(AndroidApplication.purchasedList);
      list.add(result);
      AndroidApplication.purchasedList = list;
      skusAdapter.notifyDataSetChanged();
      if (purchaseListListener != null) {
        purchaseListListener.onPurchaseSuccess(result);
      }

    }

    @Override
    public void onError(int response, @Nonnull Exception e) {
      Log.d(TAG, "PURCHASE RESULT : " + e.toString());
    }
  }

  /**
   * Trivial implementation of {@link IntentStarter} for the support lib's {@link DialogFragment}.
   */
  private static class MyIntentStarter implements IntentStarter {

    @Nonnull
    private final DialogFragment mFragment;

    public MyIntentStarter(@Nonnull DialogFragment fragment) {
      mFragment = fragment;
    }

    @Override
    public void startForResult(@Nonnull IntentSender intentSender, int requestCode,
        @Nonnull Intent intent) throws IntentSender.SendIntentException {
      mFragment.startIntentSenderForResult(intentSender, requestCode, intent, 0, 0, 0, null);
    }
  }

  /**
   * Checks if the device has any active internet connection.
   *
   * @return true device with internet connection, otherwise false.
   */
  private boolean isThereInternetConnection() {
    boolean isConnected;

    ConnectivityManager connectivityManager =
        (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
    isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

    return isConnected;
  }

}
