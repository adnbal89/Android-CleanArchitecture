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

import static org.solovyev.android.checkout.ProductTypes.SUBSCRIPTION;

import android.os.Bundle;
import android.util.Log;
import com.adnanbal.fxdedektifi.forex.presentation.AndroidApplication;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nonnull;
import org.solovyev.android.checkout.ActivityCheckout;
import org.solovyev.android.checkout.Billing;
import org.solovyev.android.checkout.Checkout;
import org.solovyev.android.checkout.Inventory;
import org.solovyev.android.checkout.ProductTypes;
import org.solovyev.android.checkout.Purchase;
import org.solovyev.android.checkout.RequestListener;
import org.solovyev.android.checkout.Sku;

public class BillingActivity extends BaseActivity {

  static final String TAG = "BillingActivity";

  private static final List<String> SKUS = Arrays
      .asList("test_purchase", "test_subscription", "weekly_subscription");
  private final List<Inventory.Callback> mInventoryCallbacks = new ArrayList<>();

  private ActivityCheckout mCheckout;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //BILLING START
    final Billing billing = AndroidApplication.get(this).getBilling();
    mCheckout = Checkout.forActivity(this, billing);
    mCheckout.start();

    final Inventory.Request request = Inventory.Request.create();
    request.loadPurchases(SUBSCRIPTION);
    request.loadSkus(SUBSCRIPTION, SKUS);
    request.loadSkus(ProductTypes.IN_APP, SKUS);

    mCheckout.loadInventory(request, new Inventory.Callback()

    {
      @Override
      public void onLoaded(@Nonnull Inventory.Products products) {
        List<Sku> skuList = products.get("subs").getSkus();
        purchase(skuList.get(0));

      }
    });

    //BILLING END
  }

  private void purchase(Sku sku) {
    mCheckout.startPurchaseFlow(sku, null, new PurchaseListener());
  }

  private class PurchaseListener implements RequestListener<Purchase> {

    @Override
    public void onSuccess(@Nonnull Purchase result) {
      Log.d(TAG, "PURCHASE RESULT : " + result.toString());
    }

    @Override
    public void onError(int response, @Nonnull Exception e) {
      Log.d(TAG, "PURCHASE RESULT : " + e.toString());

    }
  }

  @Override
  public void onDestroy() {
    mCheckout.stop();
    super.onDestroy();
  }
}
