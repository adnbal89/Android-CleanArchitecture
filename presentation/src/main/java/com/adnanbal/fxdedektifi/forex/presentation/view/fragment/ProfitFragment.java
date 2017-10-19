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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import com.adnanbal.fxdedektifi.forex.presentation.presenter.ProfitListPresenter;
import com.adnanbal.fxdedektifi.forex.presentation.view.ConfirmDialogView;
import com.adnanbal.fxdedektifi.forex.presentation.view.PositionListView;
import com.adnanbal.fxdedektifi.forex.presentation.view.adapter.PositionsLayoutManager;
import com.adnanbal.fxdedektifi.forex.presentation.view.adapter.ProfitAdapter;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendHorizontalAlignment;
import com.github.mikephil.charting.components.Legend.LegendOrientation;
import com.github.mikephil.charting.components.Legend.LegendVerticalAlignment;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import javax.inject.Inject;

public class ProfitFragment extends BaseFragment implements PositionListView,
    OnChartGestureListener,
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
  int current_year = Calendar.getInstance().get(Calendar.YEAR);

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
  ProfitListPresenter profitListPresenter;
  @Inject
  ProfitAdapter profitAdapter;

  Context context;

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
  @BindView(R.id.barChartProfit)
  BarChart barChartProfit;

  private Typeface tf;


  public ProfitFragment() {
    setRetainInstance(true);
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    context = activity;
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
    int year;

    cal.setTime(positionModel.getDate());
    month = cal.get(Calendar.MONTH);
    year = cal.get(Calendar.YEAR);

    if (year == current_year) {
      if (positionModel.getPair().contains("JPY")) {

        if (month < 3) {
          first_quarter_profit +=
              (positionModel.getClosingPrice() - positionModel.getOpeningPrice()) / 100;
        } else if (month >= 3 && month < 6) {
          second_quarter_profit += (positionModel.getClosingPrice() - positionModel
              .getOpeningPrice()) / 100;
        } else if (month >= 6 && month < 9) {
          third_quarter_profit +=
              (positionModel.getClosingPrice() - positionModel.getOpeningPrice()) / 100;
        } else if (month >= 9 && month < 12) {
          fourth_quarter_profit +=
              (positionModel.getClosingPrice() - positionModel.getOpeningPrice()) / 100;
        }

      } else {

        if (month < 3) {
          first_quarter_profit += (positionModel.getClosingPrice() - positionModel
              .getOpeningPrice());
        } else if (month >= 3 && month < 6) {
          second_quarter_profit += (positionModel.getClosingPrice() - positionModel
              .getOpeningPrice());
        } else if (month >= 6 && month < 9) {
          third_quarter_profit += (positionModel.getClosingPrice() - positionModel
              .getOpeningPrice());
        } else if (month >= 9 && month < 12) {
          fourth_quarter_profit +=
              (positionModel.getClosingPrice() - positionModel.getOpeningPrice());
        }
      }

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
    pieChart.setHoleRadius(32f);
    pieChart.setTransparentCircleRadius(26f);

    Legend l = pieChart.getLegend();
    l.setTextSize(5f);
    l.setVerticalAlignment(LegendVerticalAlignment.TOP);
    l.setHorizontalAlignment(LegendHorizontalAlignment.RIGHT);
    l.setOrientation(LegendOrientation.VERTICAL);
    l.setDrawInside(false);

    pieChart.setData(generatePieData());

    pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

    // barChartProfit.spin(2000, 0, 360);}
  }


  ////////////// BAR CHART START ///////////////////////
  private void setUpBarChart() {
    // create a new chart object
    barChartProfit.getDescription().setEnabled(false);
    barChartProfit.setOnChartGestureListener(this);

    barChartProfit.setDrawGridBackground(false);
    barChartProfit.setDrawBarShadow(false);

    Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf");

    barChartProfit.setData(generateBarData());

    Legend l = barChartProfit.getLegend();
    l.setEnabled(false);


    YAxis leftAxis = barChartProfit.getAxisLeft();
    leftAxis.setTypeface(tf);
    leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

    barChartProfit.getAxisRight().setEnabled(false);

    XAxis xAxis = barChartProfit.getXAxis();
    xAxis.setEnabled(false);
    barChartProfit.animateY(1400, Easing.EasingOption.EaseInOutQuad);

//    // programatically add the chart
//    barChartFrameLayout.addView(barChartProfit);
  }


  private String[] mLabels = new String[]{""};
//    private String[] mXVals = new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dec" };

  private String getLabel(int i) {
    return mLabels[i];
  }

  ////////////// BAR CHART END ///////////////////////


  private SpannableString generateCenterText() {
    SpannableString s = new SpannableString(
        getResources().getString(R.string.revenuesQuarters) + " " + current_year);
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

    PieDataSet ds1 = new PieDataSet(entries1, getResources().getString(R.string.in_pips));

    ds1.setColors(ColorTemplate.MATERIAL_COLORS);
    ds1.setSliceSpace(1f);
    ds1.setValueTextColor(Color.BLACK);
    ds1.setValueTextSize(8f);

    PieData d = new PieData(ds1);
    d.setValueTypeface(tf);

    return d;
  }

  protected BarData generateBarData() {

    int count = 4;
    ArrayList<IBarDataSet> sets = new ArrayList<IBarDataSet>();
    ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

//    for (int i = 0; i < count; i++) {
//      entries1.add(new PieEntry((float) (totalProfit / (Math.random() * 5)), "Quarter " + (i + 1)));
//    }
    entries.add(new BarEntry(1, (float) (10000 * first_quarter_profit)));
    entries.add(new BarEntry(2, (float) (10000 * second_quarter_profit)));
    entries.add(new BarEntry(3, (float) (10000 * third_quarter_profit)));
    entries.add(new BarEntry(4, (float) (10000 * fourth_quarter_profit)));

//    entries1.add(new PieEntry((float) totalProfit, "Total Profit"));
    BarDataSet ds = new BarDataSet(entries, null);

    ds.setColors(ColorTemplate.MATERIAL_COLORS);
    ds.setValueTextColor(Color.BLACK);
    ds.setValueTextSize(8f);
    sets.add(ds);

    BarData d = new BarData(sets);
    d.setValueTypeface(tf);

    return d;
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    this.profitListPresenter.setView(this);

    if (savedInstanceState == null) {
      this.loadPositionList();
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    this.profitListPresenter.resume();
  }

  @Override
  public void onPause() {
    super.onPause();
    this.profitListPresenter.pause();

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
    this.profitListPresenter.destroy();

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
      this.profitAdapter.setPositionsCollection(positionModelCollection);
    }

    double tempTotalProfit = 0;

    for (Iterator iterator = positionModelCollection.iterator(); iterator.hasNext(); ) {
      PositionModel positionModel = (PositionModel) iterator.next();
      tempTotalProfit = tempTotalProfit + positionModel.getProfit();

      calculate_quarterly_profit(positionModel);
    }
    this.totalProfit = tempTotalProfit;
    setUpPieChart();
    setUpBarChart();
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
    //    profitAdapter.removePositionFromCurrentCollection(positionModel);
  }

  @Override
  public void openPositionConfirmedOnline(PositionModel positionModel) {
    //    profitAdapter.addPositionToCurrentCollection(positionModel);
  }

  private void setupRecyclerView() {
    this.profitAdapter.setOnItemClickListener(onItemClickListener);

    //    SlideInUpAnimator animator = new SlideInUpAnimator(new OvershootInterpolator(1f));
    //    this.rv_personal_history.setItemAnimator(animator);

    this.rv_personal_history.setLayoutManager(new PositionsLayoutManager(context()));
    this.rv_personal_history.setAdapter(profitAdapter);
  }

  @Override
  public void showError(String message) {
    this.showToastMessage(message);
  }


  /**
   * Loads all positions.
   */
  private void loadPositionList() {
    this.profitListPresenter.initialize();
  }

  @OnClick(com.adnanbal.fxdedektifi.forex.presentation.R.id.bt_retry)
  void onButtonRetryClick() {
    ProfitFragment.this.loadPositionList();
  }

  private ProfitAdapter.OnItemClickListener onItemClickListener =
      new ProfitAdapter.OnItemClickListener() {
        @Override
        public void onPositionItemClicked(PositionModel positionModel) {
          if (ProfitFragment.this.profitListPresenter != null
              && positionModel != null) {
            ProfitFragment.this.profitListPresenter
                .onPositionClicked(positionModel);
          }
        }
      };


  @Override
  public void onChartGestureStart(MotionEvent me,
      ChartTouchListener.ChartGesture lastPerformedGesture) {
    Log.i("Gesture", "START");
  }

  @Override
  public void onChartGestureEnd(MotionEvent me,
      ChartTouchListener.ChartGesture lastPerformedGesture) {
    Log.i("Gesture", "END");
    barChartProfit.highlightValues(null);
  }

  @Override
  public void onChartLongPressed(MotionEvent me) {
    Log.i("LongPress", "Chart longpressed.");
  }

  @Override
  public void onChartDoubleTapped(MotionEvent me) {
    Log.i("DoubleTap", "Chart double-tapped.");
  }

  @Override
  public void onChartSingleTapped(MotionEvent me) {
    Log.i("SingleTap", "Chart single-tapped.");
  }

  @Override
  public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
    Log.i("Fling", "Chart flinged. VeloX: " + velocityX + ", VeloY: " + velocityY);
  }

  @Override
  public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
    Log.i("Scale / Zoom", "ScaleX: " + scaleX + ", ScaleY: " + scaleY);
  }

  @Override
  public void onChartTranslate(MotionEvent me, float dX, float dY) {
    Log.i("Translate / Move", "dX: " + dX + ", dY: " + dY);
  }

}
