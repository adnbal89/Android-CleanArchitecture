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
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.adnanbal.fxdedektifi.forex.presentation.R;
import com.adnanbal.fxdedektifi.forex.presentation.model.PositionModel;
import com.adnanbal.fxdedektifi.forex.presentation.util.DateFormatter;
import com.adnanbal.fxdedektifi.forex.presentation.util.DoubleToString;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;

/**
 * ,
 * Adapter that manages a collection of {@link PositionModel`  SQA alModel}.
 */
public class PositionHistoryAdapter extends
    RecyclerView.Adapter<PositionHistoryAdapter.PositionViewHolder> {

  Context context;

  //
  DateFormatter dateFormatter;

  public interface OnItemClickListener {

    void onPositionItemClicked(PositionModel positionModel);
  }

  private List<PositionModel> positionsCollection;
  private final LayoutInflater layoutInflater;

  private OnItemClickListener onItemClickListener;

  @Inject
  PositionHistoryAdapter(Context context) {
    this.context = context;
    this.layoutInflater =
        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    this.positionsCollection = Collections.emptyList();
    this.dateFormatter = new DateFormatter();
  }

  @Override
  public int getItemCount() {
    return (this.positionsCollection != null) ? this.positionsCollection.size() : 0;
  }

  @Override
  public PositionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    final View view = this.layoutInflater.inflate(R.layout.row_personal_history, parent, false);
    return new PositionViewHolder(view);
  }


  @Override
  public void onBindViewHolder(PositionViewHolder holder, int signal) {
    final PositionModel positionModel = this.positionsCollection.get(signal);
    holder.textViewPair.setText(positionModel.getPair());
    if (positionModel.isBuy_sell()) {
      holder.textViewBuySell.setText(context.getResources().getString(R.string.signal_buy));
      holder.textViewBuySell.setTextColor(
          context.getResources().getColor(R.color.color_textview_personal_signals_buy));
    } else {
      holder.textViewBuySell.setText(context.getResources().getString(R.string.signal_sell));
      holder.textViewBuySell.setTextColor(
          context.getResources().getColor(R.color.color_textview_personal_signals_sell));
    }

//    holder.textViewStatus.setText(positionModel.getStatus());

    holder.textViewPositionVolume.setText(DoubleToString.convertFrom(positionModel.getVolume()));

    holder.textViewProfit.setText(String.valueOf(
        Double
            .valueOf(10000 * (positionModel.getClosingPrice() - positionModel.getOpeningPrice()))
            .intValue()));

    if ((positionModel.getClosingPrice() - positionModel.getOpeningPrice()) > 0) {

      holder.textViewProfit.setTextColor(
          context.getResources().getColor(R.color.color_textview_personal_signals_buy));
    } else {
      holder.textViewProfit.setTextColor(
          context.getResources().getColor(R.color.color_textview_personal_signals_sell));
    }

    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (PositionHistoryAdapter.this.onItemClickListener != null) {
          PositionHistoryAdapter.this.onItemClickListener.onPositionItemClicked(positionModel);
        }
      }
    });

    holder.textView_Close_Date.setText(dateFormatter.format(positionModel.getDate()));

    holder.textView_Open_To_Close
        .setText(String.valueOf(positionModel.getOpeningPrice()) + " -> " + String
            .valueOf(positionModel.getClosingPrice()));
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  public void setPositionsCollection(Collection<PositionModel> positionsCollection) {
    this.validatePositionsCollection(positionsCollection);
    this.positionsCollection = (List<PositionModel>) positionsCollection;
    this.notifyDataSetChanged();
  }

  public void removePositionFromCurrentCollection(final PositionModel positionModel) {
    this.validatePositionsCollection(positionsCollection);
    this.positionsCollection.remove(positionModel);
    this.notifyDataSetChanged();
  }

  public void addPositionToCurrentCollection(final PositionModel positionModel) {
    this.validatePositionsCollection(positionsCollection);
    this.positionsCollection.add(0, positionModel);
    this.notifyDataSetChanged();
  }

  public void setOnItemClickListener(
      OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }

  private void validatePositionsCollection(Collection<PositionModel> positionsCollection) {
    if (positionsCollection == null) {
      throw new IllegalArgumentException("The list cannot be null");
    }
  }

  static class PositionViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_personal_history_pair)
    TextView textViewPair;
    @BindView(R.id.tv_personal_history_buy_sell)
    TextView textViewBuySell;
    //    @BindView(R.id.tv_personal_history_status)
//    TextView textViewStatus;
    @BindView(R.id.tv_personal_history_volume)
    TextView textViewPositionVolume;
    @BindView(R.id.tv_personal_history_profit)
    TextView textViewProfit;
    @BindView(R.id.tv_open_to_close)
    TextView textView_Open_To_Close;
    @BindView(R.id.tv_personal_history_close_date)
    TextView textView_Close_Date;

    PositionViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }

}
