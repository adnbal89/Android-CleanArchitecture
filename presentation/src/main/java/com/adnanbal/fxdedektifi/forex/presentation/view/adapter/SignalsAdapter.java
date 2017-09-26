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
import com.adnanbal.fxdedektifi.forex.presentation.model.SignalModel;
import com.adnanbal.fxdedektifi.forex.presentation.model.UserSignalModel;
import com.adnanbal.fxdedektifi.forex.presentation.util.DateFormatter;
import com.adnanbal.fxdedektifi.forex.presentation.util.DoubleToString;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

/**
 * Adapter that manages a collection of {@link SignalModel}.
 */
public class SignalsAdapter extends RecyclerView.Adapter<SignalsAdapter.SignalViewHolder> {

  Context context;
  DateFormatter dateFormatter;
  String userName;

  public interface OnItemClickListener {

    void onSignalItemClicked(SignalModel signalModel);
  }

  private List<SignalModel> signalsCollection;
  private final LayoutInflater layoutInflater;

  private OnItemClickListener onItemClickListener;

  @Inject
  SignalsAdapter(Context context) {
    this.context = context;
    this.layoutInflater =
        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    this.signalsCollection = Collections.emptyList();
    this.dateFormatter = new DateFormatter();
  }


  @Override
  public int getItemCount() {
    return (this.signalsCollection != null) ? this.signalsCollection.size() : 0;
  }

  @Override
  public SignalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    final View view = this.layoutInflater.inflate(R.layout.row_signals, parent, false);
    return new SignalViewHolder(view);
  }

  @Override
  public void onBindViewHolder(SignalViewHolder holder, int signal) {
    final SignalModel signalModel = this.signalsCollection.get(signal);
    holder.textViewPair.setText(signalModel.getPair());
    if (signalModel.isBuy_sell()) {
      holder.textViewBuySell.setText(context.getResources().getString(R.string.signal_buy));
      holder.textViewBuySell.setTextColor(
          context.getResources().getColor(R.color.color_textview_personal_signals_buy));
    } else {
      holder.textViewBuySell.setText(context.getResources().getString(R.string.signal_sell));
      holder.textViewBuySell.setTextColor(
          context.getResources().getColor(R.color.color_textview_personal_signals_sell));
    }

    if (signalModel.getComment() != null && !signalModel.getComment().isEmpty()) {
      holder.imageViewSignalComment.setImageResource(R.drawable.ic_comment_alternative);
    } else {
      holder.imageViewSignalComment.setImageResource(android.R.color.transparent);
    }

    holder.date.setText(dateFormatter.format(signalModel.getDate()));

    holder.textViewOpeningPrice.setText(DoubleToString.convertFrom(signalModel.getOpeningPrice()));
    holder.imageViewSignalStatus.setImageResource(R.drawable.green_circle);

    holder.textViewStatus.setText(signalModel.getStatus());

    if (userHasOpenedSignal(signalModel)) {
      holder.iv_tick.setVisibility(View.VISIBLE);


    } else {
      holder.iv_tick.setVisibility(View.INVISIBLE);
    }

    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (SignalsAdapter.this.onItemClickListener != null) {
          SignalsAdapter.this.onItemClickListener.onSignalItemClicked(signalModel);
        }
      }
    });

  }


  private boolean userHasOpenedSignal(SignalModel signalModel) {

    for (UserSignalModel userSignalModel: AndroidApplication.listUserSignalModel) {

      for (String signalId : userSignalModel.getSignals().keySet()) {
        System.out.println(signalId + " : " + signalId);
        if (signalId.contains(signalModel.getPositionId())) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public long getItemId(int signal) {
    return signal;
  }

  public void setSignalsCollection(Collection<SignalModel> signalsCollection) {
    this.validateSignalsCollection(signalsCollection);
    this.signalsCollection = (List<SignalModel>) signalsCollection;
    this.notifyDataSetChanged();
  }


  public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }


  private void validateSignalsCollection(Collection<SignalModel> signalsCollection) {
    if (signalsCollection == null) {
      throw new IllegalArgumentException("The list cannot be null");
    }
  }

  static class SignalViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.iv_tick)
    ImageView iv_tick;
    @BindView(R.id.tv_signal_pair)
    TextView textViewPair;
    @BindView(R.id.tv_signal_buy_sell)
    TextView textViewBuySell;
    @BindView(R.id.tv_signal_opening_price)
    TextView textViewOpeningPrice;
    @BindView(R.id.tv_signal_status)
    TextView textViewStatus;
    @BindView(R.id.signal_status)
    ImageView imageViewSignalStatus;
    @BindView(R.id.signal_comment)
    ImageView imageViewSignalComment;
    @BindView(R.id.tv_date)
    TextView date;

    SignalViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
