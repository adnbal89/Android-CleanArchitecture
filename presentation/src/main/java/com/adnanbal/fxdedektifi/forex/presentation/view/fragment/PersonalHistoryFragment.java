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

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.adnanbal.fxdedektifi.forex.presentation.R;
import com.adnanbal.fxdedektifi.forex.presentation.internal.di.components.PositionComponent;
import com.adnanbal.fxdedektifi.forex.presentation.model.PositionModel;
import com.adnanbal.fxdedektifi.forex.presentation.presenter.PositionHistoryListPresenter;
import com.adnanbal.fxdedektifi.forex.presentation.view.ConfirmDialogView;
import com.adnanbal.fxdedektifi.forex.presentation.view.PositionListView;
import com.adnanbal.fxdedektifi.forex.presentation.view.adapter.PositionHistoryAdapter;
import com.adnanbal.fxdedektifi.forex.presentation.view.adapter.PositionsLayoutManager;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import javax.inject.Inject;

public class PersonalHistoryFragment extends BaseFragment implements PositionListView,
    ConfirmDialogView {

  /*
  * Butterknife unbinder, will be executed to clear callbacks
   */
  Unbinder unbinder;

  Calendar cal = Calendar.getInstance();

  double totalProfit;

  double first_quarter_profit = 0;
  double second_quarter_profit = 0;
  double third_quarter_profit = 0;
  double fourth_quarter_profit = 0;

  private PositionHistoryListListener positionHistoryListListener;

  @Override
  public void dialogConfirmed(PositionModel positionModel) {

  }


  /**
   * Interface for listening position list events.
   */
  public interface PositionHistoryListListener {

    void onPositionClicked(final PositionModel positionModel, ConfirmDialogView view);
  }

  @Inject
  PositionHistoryListPresenter positionHistoryListPresenter;
  @Inject
  PositionHistoryAdapter positionHistoryAdapter;

  @BindView(R.id.rv_personal_history)
  RecyclerView rv_personal_history;
  @BindView(R.id.rl_progress)
  RelativeLayout rl_progress;
  @BindView(R.id.rl_retry)
  RelativeLayout rl_retry;
  @BindView(R.id.bt_retry)
  Button bt_retry;
  @BindView(R.id.pieChart_position_history)
  PieChart pieChart;

  private Typeface tf;


  public PersonalHistoryFragment() {
    setRetainInstance(true);
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    if (activity instanceof PositionHistoryListListener) {
      this.positionHistoryListListener = (PositionHistoryListListener) activity;
    }
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.getComponent(PositionComponent.class).inject(this);
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    final View fragmentView = inflater
        .inflate(R.layout.fragment_personal_history, container, false);
    unbinder = ButterKnife.bind(this, fragmentView);
    setupRecyclerView();
    return fragmentView;
  }

  private void calculate_quarterly_profit(PositionModel positionModel) {
    double calculated_profit_model = 0;

    int month;

    cal.setTime(positionModel.getDate());
    month = cal.get(Calendar.MONTH);

    if (month < 3) {
      first_quarter_profit += (positionModel.getClosingPrice() - positionModel.getOpeningPrice());
    } else if (month >= 3 && month < 6) {
      second_quarter_profit += (positionModel.getClosingPrice() - positionModel.getOpeningPrice());
    } else if (month >= 6 && month < 9) {
      third_quarter_profit += (positionModel.getClosingPrice() - positionModel.getOpeningPrice());
    } else if (month >= 9 && month < 12) {
      fourth_quarter_profit +=
          (positionModel.getClosingPrice() - positionModel.getOpeningPrice());
    }


  }

  private void setUpPieChart() {

    pieChart.getDescription().setEnabled(false);

    Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf");

    pieChart.setCenterTextTypeface(tf);
    pieChart.setCenterText(generateCenterText());
    pieChart.setCenterTextSize(5f);
    pieChart.setCenterTextTypeface(tf);

    // radius of the center hole in percent of maximum radius
    pieChart.setHoleRadius(30f);
    pieChart.setTransparentCircleRadius(25f);

    Legend l = pieChart.getLegend();
    l.setTextSize(6f);
    l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
    l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
    l.setOrientation(Legend.LegendOrientation.VERTICAL);
    l.setDrawInside(false);

    pieChart.setData(generatePieData());

    pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

    // mChart.spin(2000, 0, 360);}
  }

  private SpannableString generateCenterText() {
    SpannableString s = new SpannableString("Revenues\nQuarters 2015");
    s.setSpan(new RelativeSizeSpan(2f), 0, 8, 0);
    s.setSpan(new ForegroundColorSpan(Color.BLACK), 8, s.length(), 0);
    return s;
  }

  protected PieData generatePieData() {

    int count = 4;

    ArrayList<PieEntry> entries1 = new ArrayList<>();

//    for (int i = 0; i < count; i++) {
//      entries1.add(new PieEntry((float) (totalProfit / (Math.random() * 5)), "Quarter " + (i + 1)));
//    }
    entries1.add(new PieEntry((float) (10000 * first_quarter_profit), "Quarter 1"));
    entries1.add(new PieEntry((float) (10000 * second_quarter_profit), "Quarter 2"));
    entries1.add(new PieEntry((float) (10000 * third_quarter_profit), "Quarter 3"));
    entries1.add(new PieEntry((float) (10000 * fourth_quarter_profit), "Quarter 4"));

//    entries1.add(new PieEntry((float) totalProfit, "Total Profit"));

    PieDataSet ds1 = new PieDataSet(entries1, "Quarterly Revenues 2015");

    ds1.setColors(ColorTemplate.MATERIAL_COLORS);
    ds1.setSliceSpace(1f);
    ds1.setValueTextColor(Color.BLACK);
    ds1.setValueTextSize(8f);

    PieData d = new PieData(ds1);
    d.setValueTypeface(tf);

    return d;
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    this.positionHistoryListPresenter.setView(this);

    if (savedInstanceState == null) {
      this.loadPositionList();
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    this.positionHistoryListPresenter.resume();
  }

  @Override
  public void onPause() {
    super.onPause();
    this.positionHistoryListPresenter.pause();

  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    rv_personal_history.setAdapter(null);
    unbinder.unbind();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    this.positionHistoryListPresenter.destroy();

  }

  @Override
  public void onDetach() {
    super.onDetach();
    this.positionHistoryListListener = null;
  }

  @Override
  public Context context() {
    return this.getActivity().getApplicationContext();
  }

  @Override
  public void showLoading() {
    this.rl_progress.setVisibility(View.VISIBLE);
    this.getActivity().setProgressBarIndeterminateVisibility(true);
  }

  @Override
  public void hideLoading() {
    this.rl_progress.setVisibility(View.GONE);
    this.getActivity().setProgressBarIndeterminateVisibility(false);
  }

  @Override
  public void showRetry() {
    this.rl_retry.setVisibility(View.VISIBLE);
  }

  @Override
  public void hideRetry() {
    this.rl_retry.setVisibility(View.GONE);
  }

  @Override
  public void renderPositionList(Collection<PositionModel> positionModelCollection) {
    if (positionModelCollection != null) {
      this.positionHistoryAdapter.setPositionsCollection(positionModelCollection);
    }

    double tempTotalProfit = 0;

    for (Iterator iterator = positionModelCollection.iterator(); iterator.hasNext(); ) {
      PositionModel positionModel = (PositionModel) iterator.next();
      tempTotalProfit = tempTotalProfit + positionModel.getProfit();

      calculate_quarterly_profit(positionModel);
    }
    this.totalProfit = tempTotalProfit;
    setUpPieChart();
  }

  @Override
  public void viewPosition(PositionModel positionModel) {
    if (this.positionHistoryListListener != null) {
      this.positionHistoryListListener.onPositionClicked(positionModel, this);
    }
  }

  //TODO : implement method
  @Override
  public void closePositionConfirmedOnline(PositionModel positionModel) {
    //    positionHistoryAdapter.removePositionFromCurrentCollection(positionModel);
  }

  @Override
  public void openPositionConfirmedOnline(PositionModel positionModel) {
    //    positionHistoryAdapter.addPositionToCurrentCollection(positionModel);
  }

  private void setupRecyclerView() {
    this.positionHistoryAdapter.setOnItemClickListener(onItemClickListener);

    //    SlideInUpAnimator animator = new SlideInUpAnimator(new OvershootInterpolator(1f));
    //    this.rv_personal_history.setItemAnimator(animator);

    this.rv_personal_history.setLayoutManager(new PositionsLayoutManager(context()));
    this.rv_personal_history.setAdapter(positionHistoryAdapter);
  }

  @Override
  public void showError(String message) {
    this.showToastMessage(message);
  }


  /**
   * Loads all positions.
   */
  private void loadPositionList() {
    this.positionHistoryListPresenter.initialize();
  }

  @OnClick(com.adnanbal.fxdedektifi.forex.presentation.R.id.bt_retry)
  void onButtonRetryClick() {
    PersonalHistoryFragment.this.loadPositionList();
  }

  private PositionHistoryAdapter.OnItemClickListener onItemClickListener =
      new PositionHistoryAdapter.OnItemClickListener() {
        @Override
        public void onPositionItemClicked(PositionModel positionModel) {
          if (PersonalHistoryFragment.this.positionHistoryListPresenter != null
              && positionModel != null) {
            PersonalHistoryFragment.this.positionHistoryListPresenter
                .onPositionClicked(positionModel);
          }
        }
      };
}
