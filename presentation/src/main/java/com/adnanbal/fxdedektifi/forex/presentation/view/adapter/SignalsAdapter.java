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
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
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
import java.util.List;
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
  Animation anim;
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

    anim = new AlphaAnimation(0.0f, 1.0f);
    anim.setDuration(250); //You can manage the blinking time with this parameter
    anim.setStartOffset(20);
    anim.setBackgroundColor(context.getResources().getColor(R.color.update_light_yellow));
    anim.setRepeatMode(Animation.REVERSE);
    anim.setRepeatCount(10);

    return new SignalViewHolder(view);
  }


  @Override
  public void onBindViewHolder(final SignalViewHolder holder, int signal) {
    final SignalModel signalModel = this.signalsCollection.get(signal);
    holder.textViewPair.setText(signalModel.getPair());

    if (AndroidApplication.listChangedSignalModel.contains(signalModel)) {

      anim.setAnimationListener(new AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
          holder.itemView
              .setBackgroundColor(context.getResources().getColor(R.color.update_light_yellow));

        }

        @Override
        public void onAnimationEnd(Animation animation) {
          holder.itemView
              .setBackgroundColor(0x00000000);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
      });

      holder.itemView.startAnimation(anim);
    }

    if (signalModel.getComment() != null && !signalModel.getComment().isEmpty()) {
      holder.imageViewSignalComment.setImageResource(R.drawable.ic_comment_alternative);
    } else {
      holder.imageViewSignalComment.setImageResource(android.R.color.transparent);
    }

    holder.date.setText(dateFormatter.format(signalModel.getDate()));

    holder.textViewOpeningPrice.setText(DoubleToString.convertFrom(signalModel.getOpeningPrice()));

    if (signalModel.getStatus().equals("working")) {

      if (signalModel.isBuy_sell()) {
        holder.textViewBuySell.setText(context.getResources().getString(R.string.signal_buy));
        holder.textViewBuySell.setTextColor(
            context.getResources().getColor(R.color.color_textview_personal_signals_buy));
      } else {
        holder.textViewBuySell.setText(context.getResources().getString(R.string.signal_sell));
        holder.textViewBuySell.setTextColor(
            context.getResources().getColor(R.color.color_textview_personal_signals_sell));
      }

      holder.imageViewSignalStatus.setImageResource(R.drawable.ic_working128);
//      holder.textViewStatus.setText(signalModel.getStatus());

    } else if (signalModel.getStatus().equals("closed")) {
      holder.imageViewSignalStatus.setImageResource(R.drawable.ic_closed128);
//      holder.textViewStatus.setText(signalModel.getStatus());

      holder.textViewOpeningPrice
          .setText(
              DoubleToString.convertFrom(signalModel.getOpeningPrice()) + " -> " + DoubleToString
                  .convertFrom(signalModel.getClosingPrice()));

      if (signalModel.isHitStopLoss()) {
        holder.textViewBuySell.setText(context.getResources().getString(R.string.hit_stop_loss));
        holder.textViewBuySell
            .setTextColor(
                context.getResources().getColor(R.color.color_textview_personal_signals_sell));
      } else if (signalModel.isHitTakeProfit()) {
        holder.textViewBuySell.setText(context.getResources().getString(R.string.hit_take_profit));
        holder.textViewBuySell.setTextColor(
            context.getResources().getColor(R.color.hit_take_profit_color));

      } else {
        holder.textViewBuySell.setText(context.getResources().getString(R.string.signal_closed));
        holder.textViewBuySell.setTextColor(
            context.getResources().getColor(R.color.textView_closed_color));
      }
      holder.textViewBuySell
          .setTypeface(holder.textViewBuySell.getTypeface(), Typeface.BOLD_ITALIC);


    } else if (signalModel.getStatus().equals("updated")) {

      if (signalModel.isBuy_sell()) {
        holder.textViewBuySell.setText(context.getResources().getString(R.string.signal_buy));
        holder.textViewBuySell.setTextColor(
            context.getResources().getColor(R.color.color_textview_personal_signals_buy));
      } else {
        holder.textViewBuySell.setText(context.getResources().getString(R.string.signal_sell));
        holder.textViewBuySell.setTextColor(
            context.getResources().getColor(R.color.color_textview_personal_signals_sell));
      }

      holder.imageViewSignalStatus.setImageResource(R.drawable.ic_updated128);
//      holder.textViewStatus.setText(signalModel.getStatus());
    }

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

    if (signalModel.getStop_loss_price() != null) {
      holder.tv_signal_sl.setText(DoubleToString.convertFrom(signalModel.getStop_loss_price()));
    }
    if (signalModel.getTake_profit_price() != null) {
      holder.tv_signal_tp.setText(DoubleToString.convertFrom(signalModel.getTake_profit_price()));

    }

  }

  private boolean userHasOpenedSignal(SignalModel signalModel) {

    for (UserSignalModel userSignalModel : AndroidApplication.listUserSignalModel) {

      for (String signalId : userSignalModel.getSignals().keySet()) {

        if (signalId.contains(signalModel.getId())) {

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
    //    @BindView(R.id.tv_signal_status)
//    TextView textViewStatus;
    @BindView(R.id.signal_status)
    ImageView imageViewSignalStatus;
    @BindView(R.id.signal_comment)
    ImageView imageViewSignalComment;
    @BindView(R.id.tv_date)
    TextView date;
    @BindView(R.id.tv_signal_tp)
    TextView tv_signal_tp;
    @BindView(R.id.tv_signal_sl)
    TextView tv_signal_sl;

    SignalViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }


}
