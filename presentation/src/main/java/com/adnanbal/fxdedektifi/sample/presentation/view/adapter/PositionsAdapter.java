package com.adnanbal.fxdedektifi.sample.presentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.adnanbal.fxdedektifi.sample.presentation.R;
import com.adnanbal.fxdedektifi.sample.presentation.model.PositionModel;
import com.adnanbal.fxdedektifi.sample.presentation.util.DoubleToString;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by adnbal89 on 12.06.2017.
 */

/**
 * Adapter that manages a collection of {@link PositionModel}.
 */
public class PositionsAdapter extends RecyclerView.Adapter<PositionsAdapter.PositionViewHolder> {

  Context context;

  public interface OnItemClickListener {

    void onPositionItemClicked(PositionModel positionModel);
  }

  private List<PositionModel> positionsCollection;
  private final LayoutInflater layoutInflater;

  private OnItemClickListener onItemClickListener;

  @Inject
  PositionsAdapter(Context context) {
    this.context = context;
    this.layoutInflater =
        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    this.positionsCollection = Collections.emptyList();
  }


  @Override
  public int getItemCount() {
    return (this.positionsCollection != null) ? this.positionsCollection.size() : 0;
  }

  @Override
  public PositionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    final View view = this.layoutInflater.inflate(R.layout.row_personal_positions, parent, false);
    return new PositionViewHolder(view);
  }

  @Override
  public void onBindViewHolder(PositionViewHolder holder, int position) {
    final PositionModel positionModel = this.positionsCollection.get(position);
    holder.textViewPair.setText(positionModel.getPair());
    if (positionModel.isBuy_sell()) {
      holder.textViewBuySell.setText(context.getResources().getString(R.string.position_buy));
      holder.textViewBuySell.setTextColor(
          context.getResources().getColor(R.color.color_textview_personal_positions_buy));
    } else {
      holder.textViewBuySell.setText(context.getResources().getString(R.string.position_sell));
      holder.textViewBuySell.setTextColor(
          context.getResources().getColor(R.color.color_textview_personal_positions_sell));

    }
    holder.textViewPositionVolume.setText(DoubleToString.convertFrom(positionModel.getVolume()));
    holder.textViewProfit.setText(DoubleToString.convertFrom(positionModel.getProfit()));
    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (PositionsAdapter.this.onItemClickListener != null) {
          PositionsAdapter.this.onItemClickListener.onPositionItemClicked(positionModel);
        }
      }
    });

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

  public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }


  private void validatePositionsCollection(Collection<PositionModel> positionsCollection) {
    if (positionsCollection == null) {
      throw new IllegalArgumentException("The list cannot be null");
    }
  }

  static class PositionViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_pair)
    TextView textViewPair;
    @BindView(R.id.tv_buy_sell)
    TextView textViewBuySell;
    @BindView(R.id.tv_position_volume)
    TextView textViewPositionVolume;
    @BindView(R.id.tv_profit)
    TextView textViewProfit;

    PositionViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
