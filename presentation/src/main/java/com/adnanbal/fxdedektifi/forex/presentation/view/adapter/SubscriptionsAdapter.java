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
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.adnanbal.fxdedektifi.forex.presentation.R;
import com.adnanbal.fxdedektifi.forex.presentation.model.SubscriptionModel;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;

public class SubscriptionsAdapter extends
    RecyclerView.Adapter<SubscriptionsAdapter.SubscriptionViewHolder> {

  Context context;
  private List<SubscriptionModel> subscriptionModelsCollection;
  private final LayoutInflater layoutInflater;
  private SubscriptionsAdapter.OnItemClickListener onItemClickListener;

  @Inject
  SubscriptionsAdapter(Context context) {
    this.context = context;
    this.layoutInflater =
        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    this.subscriptionModelsCollection = Collections.emptyList();
  }

  public interface OnItemClickListener {

    void onSubscriptionItemClicked(SubscriptionModel subscriptionModel);
  }

  public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }

  public void setSubscriptionsCollection(
      Collection<SubscriptionModel> subscriptionModelsCollection) {
    this.validateSubscriptionsCollection(subscriptionModelsCollection);
    this.subscriptionModelsCollection = (List<SubscriptionModel>) subscriptionModelsCollection;
    this.notifyDataSetChanged();
  }

  private void validateSubscriptionsCollection(
      Collection<SubscriptionModel> subscriptionModelsCollection) {
    if (subscriptionModelsCollection == null) {
      throw new IllegalArgumentException("The list cannot be null");
    }
  }

  @Override
  public SubscriptionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    final View view = this.layoutInflater
        .inflate(R.layout.row_available_subscriptions, parent, false);
    return new SubscriptionViewHolder(view);
  }

  @Override
  public void onBindViewHolder(SubscriptionViewHolder holder, int subscription) {
    final SubscriptionModel subscriptionModel = this.subscriptionModelsCollection.get(subscription);
    holder.textViewSubscription_name.setText(subscriptionModel.getName());
    holder.textViewSubscription_price.setText(subscriptionModel.getPrice());

    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (SubscriptionsAdapter.this.onItemClickListener != null) {
          SubscriptionsAdapter.this.onItemClickListener
              .onSubscriptionItemClicked(subscriptionModel);
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
    return (this.subscriptionModelsCollection != null) ? this.subscriptionModelsCollection.size()
        : 0;
  }

  static class SubscriptionViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.textViewSubscription_name)
    TextView textViewSubscription_name;
    @BindView(R.id.textViewSubscription_price)
    TextView textViewSubscription_price;
    @BindView(R.id.button_subscription_buy)
    Button button_subscription_buy;


    SubscriptionViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
