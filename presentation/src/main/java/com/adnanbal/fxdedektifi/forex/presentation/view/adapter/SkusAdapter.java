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

package com.adnanbal.fxdedektifi.forex.presentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.adnanbal.fxdedektifi.forex.presentation.AndroidApplication;
import com.adnanbal.fxdedektifi.forex.presentation.R;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import org.solovyev.android.checkout.Purchase;
import org.solovyev.android.checkout.Purchase.State;
import org.solovyev.android.checkout.Sku;

public class SkusAdapter extends
    RecyclerView.Adapter<SkusAdapter.SkuViewHolder> {

  Context context;
  private List<Sku> skusCollection;
  private final LayoutInflater layoutInflater;
  private SkusAdapter.OnItemClickListener onItemClickListener;
  private int counter = 0;

  @Inject
  public SkusAdapter(Context context) {
    this.context = context;
    this.layoutInflater =
        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    this.skusCollection = Collections.emptyList();
  }

  public interface OnItemClickListener {

    void onSkuItemClicked(Sku sku);
  }

  public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }

  public void setSkusCollection(
      Collection<Sku> skusCollection) {
    this.validateSkusCollection(skusCollection);
    this.skusCollection = (List<Sku>) skusCollection;
    this.notifyDataSetChanged();
  }

  private void validateSkusCollection(
      Collection<Sku> skusCollection) {
    if (skusCollection == null) {
      throw new IllegalArgumentException("The list cannot be null");
    }
  }

  @Override
  public SkuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    final View view = this.layoutInflater
        .inflate(R.layout.row_available_subscriptions, parent, false);
    return new SkuViewHolder(view);
  }

  @Override
  public void onBindViewHolder(SkuViewHolder holder, int subscription) {
    final Sku sku = this.skusCollection.get(subscription);
    holder.textViewSubscription_name.setText(sku.getDisplayTitle());
    holder.textViewSubscription_price.setText(sku.price);

    for (Purchase purchase : AndroidApplication.purchasedList) {
      if (purchase.sku.equals(sku.id.code) && purchase.state == State.PURCHASED) {
        holder.itemView.setEnabled(false);
        holder.iv_paid.setVisibility(View.VISIBLE);
      }

    }

    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (SkusAdapter.this.onItemClickListener != null) {
          SkusAdapter.this.onItemClickListener
              .onSkuItemClicked(sku);
        }
      }
    });


  }

  @Override
  public long getItemId(int signal) {
    return signal;
  }


  @Override
  public int getItemCount() {
    return (this.skusCollection != null) ? this.skusCollection.size()
        : 0;
  }

  static class SkuViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.iv_paid)
    ImageView iv_paid;
    @BindView(R.id.textViewSubscription_name)
    TextView textViewSubscription_name;
    @BindView(R.id.textViewSubscription_price)
    TextView textViewSubscription_price;


    SkuViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
