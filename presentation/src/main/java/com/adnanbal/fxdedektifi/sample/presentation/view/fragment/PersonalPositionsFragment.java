/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.adnanbal.fxdedektifi.sample.presentation.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.adnanbal.fxdedektifi.sample.presentation.R;
import com.adnanbal.fxdedektifi.sample.presentation.internal.di.components.PositionComponent;
import com.adnanbal.fxdedektifi.sample.presentation.model.PositionModel;
import com.adnanbal.fxdedektifi.sample.presentation.presenter.PositionListPresenter;
import com.adnanbal.fxdedektifi.sample.presentation.view.PositionListView;
import com.adnanbal.fxdedektifi.sample.presentation.view.adapter.PositionsAdapter;
import com.adnanbal.fxdedektifi.sample.presentation.view.adapter.PositionsLayoutManager;
import java.util.Collection;
import javax.inject.Inject;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;


/**
 * Fragment that shows a list of Positions.
 */
public class PersonalPositionsFragment extends BaseFragment implements PositionListView{

  /*
  * Butterknife unbinder, will be executed to clear callbacks
   */
  Unbinder unbinder;

  /**
   * Interface for listening position list events.
   */
  public interface PositionListListener {

    void onPositionClicked(final PositionModel positionModel);
  }

  @Inject
  PositionListPresenter positionListPresenter;
  @Inject
  PositionsAdapter positionsAdapter;

  @BindView(R.id.rv_positions)
  RecyclerView rv_positions;
  @BindView(com.adnanbal.fxdedektifi.sample.presentation.R.id.rl_progress)
  RelativeLayout rl_progress;
  @BindView(com.adnanbal.fxdedektifi.sample.presentation.R.id.rl_retry)
  RelativeLayout rl_retry;
  @BindView(com.adnanbal.fxdedektifi.sample.presentation.R.id.bt_retry)
  Button bt_retry;

  private PositionListListener positionListListener;

  public PersonalPositionsFragment() {
    setRetainInstance(true);
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    if (activity instanceof PositionListListener) {
      this.positionListListener = (PositionListListener) activity;
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
        .inflate(R.layout.fragment_personal_positions, container, false);
    unbinder = ButterKnife.bind(this, fragmentView);
    setupRecyclerView();
    return fragmentView;
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    this.positionListPresenter.setView(this);
    if (savedInstanceState == null) {
      this.loadPositionList();
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    this.positionListPresenter.resume();
  }

  @Override
  public void onPause() {
    super.onPause();
    this.positionListPresenter.pause();

  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    rv_positions.setAdapter(null);
    unbinder.unbind();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    this.positionListPresenter.destroy();

  }

  @Override
  public void onDetach() {
    super.onDetach();
    this.positionListListener = null;

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
      this.positionsAdapter.setPositionsCollection(positionModelCollection);
    }
  }

  @Override
  public void viewPosition(PositionModel positionModel) {
    if (this.positionListListener != null) {
      this.positionListListener.onPositionClicked(positionModel);
    }
  }

  @Override
  public void showError(String message) {
    this.showToastMessage(message);
  }

  private void setupRecyclerView() {
    this.positionsAdapter.setOnItemClickListener(onItemClickListener);
    SlideInUpAnimator animator = new SlideInUpAnimator(new OvershootInterpolator(1f));

    this.rv_positions.setItemAnimator(animator);
    this.rv_positions.setLayoutManager(new PositionsLayoutManager(context()));
    this.rv_positions.setAdapter(positionsAdapter);
  }

  /**
   * Loads all positions.
   */
  private void loadPositionList() {
    this.positionListPresenter.initialize();
  }

  @OnClick(com.adnanbal.fxdedektifi.sample.presentation.R.id.bt_retry)
  void onButtonRetryClick() {
    PersonalPositionsFragment.this.loadPositionList();
  }

  private PositionsAdapter.OnItemClickListener onItemClickListener =
      new PositionsAdapter.OnItemClickListener() {
        @Override
        public void onPositionItemClicked(PositionModel positionModel) {
          if (PersonalPositionsFragment.this.positionListPresenter != null
              && positionModel != null) {
            PersonalPositionsFragment.this.positionListPresenter.onPositionClicked(positionModel);
          }
        }
      };
}
