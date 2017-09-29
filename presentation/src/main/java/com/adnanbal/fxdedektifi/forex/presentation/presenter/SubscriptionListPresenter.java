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

package com.adnanbal.fxdedektifi.forex.presentation.presenter;

import android.support.annotation.NonNull;
import com.adnanbal.fxdedektifi.forex.domain.exception.DefaultErrorBundle;
import com.adnanbal.fxdedektifi.forex.domain.exception.ErrorBundle;
import com.adnanbal.fxdedektifi.forex.domain.interactor.DefaultObserver;
import com.adnanbal.fxdedektifi.forex.domain.interactor.GetPurchasedSubscriptionList;
import com.adnanbal.fxdedektifi.forex.domain.interactor.GetPurchasedSubscriptionList.Params;
import com.adnanbal.fxdedektifi.forex.domain.interactor.GetSubscriptionList;
import com.adnanbal.fxdedektifi.forex.domain.model.Subscription;
import com.adnanbal.fxdedektifi.forex.presentation.exception.ErrorMessageFactory;
import com.adnanbal.fxdedektifi.forex.presentation.external.AnalyticsInterface;
import com.adnanbal.fxdedektifi.forex.presentation.mapper.SubscriptionModelDataMapper;
import com.adnanbal.fxdedektifi.forex.presentation.model.SubscriptionModel;
import com.adnanbal.fxdedektifi.forex.presentation.view.SubscriptionListView;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;

public class SubscriptionListPresenter implements Presenter {

  private SubscriptionListView subscriptionListView;

  private final static String TAG = SubscriptionListPresenter.class.getName();

  private GetSubscriptionList getSubscriptionListUseCase;
  private GetPurchasedSubscriptionList getPurchasedSubscriptionListUseCase;

  private SubscriptionModelDataMapper subscriptionModelDataMapper;
  private AnalyticsInterface analyticsInterface;


  @Inject
  public SubscriptionListPresenter(AnalyticsInterface analyticsInterface,
      GetSubscriptionList getSubscriptionListUseCase,
      GetPurchasedSubscriptionList getPurchasedSubscriptionListUseCase,
      SubscriptionModelDataMapper subscriptionModelDataMapper) {

    this.analyticsInterface = analyticsInterface;
    this.getSubscriptionListUseCase = getSubscriptionListUseCase;
    this.subscriptionModelDataMapper = subscriptionModelDataMapper;
    this.getPurchasedSubscriptionListUseCase = getPurchasedSubscriptionListUseCase;
  }

  public void setView(@NonNull SubscriptionListView view) {
    this.subscriptionListView = view;
  }


  @Override
  public void resume() {

  }

  @Override
  public void pause() {

  }

  @Override
  public void destroy() {
    this.getSubscriptionListUseCase.dispose();
    this.getPurchasedSubscriptionListUseCase.dispose();
    this.subscriptionListView = null;
  }

  /**
   * Initializes the presenter by start retrieving the subscription list.
   */
  public void initialize() {
    this.loadSubscriptionList();
  }

  private void loadSubscriptionList() {
    this.hideViewRetry();
    this.showViewLoading();
    this.getSubscriptionList();
  }

  private void loadPurchasedSubscriptionsList(String authenticatedUserUid) {
    this.getPurchasedSubscriptionList(authenticatedUserUid);
  }

  private void getSubscriptionList() {
    this.getSubscriptionListUseCase.execute(new SubscriptionListObserver(), null);
  }

  private void getPurchasedSubscriptionList(String authenticatedUserUid) {
    this.getPurchasedSubscriptionListUseCase
        .execute(new PurchasedSubscriptionListObserver(),
            Params.forSubscription(authenticatedUserUid));
  }

  private void showViewLoading() {
    this.subscriptionListView.showLoading();
  }

  private void hideViewLoading() {
    this.subscriptionListView.hideLoading();
  }

  private void showViewRetry() {
    this.subscriptionListView.showRetry();
  }

  private void hideViewRetry() {
    this.subscriptionListView.hideRetry();
  }

  private void showErrorMessage(ErrorBundle errorBundle) {
    String errorMessage = ErrorMessageFactory.create(this.subscriptionListView.context(),
        errorBundle.getException());
    this.subscriptionListView.showError(errorMessage);
  }

  private void showSubscriptionCollectionInView(Collection<Subscription> subscriptionsCollection) {
    final Collection<SubscriptionModel> signalModelsCollection =
        this.subscriptionModelDataMapper.transform(subscriptionsCollection);

    this.subscriptionListView.renderSubscriptionList(signalModelsCollection);
  }

  private class SubscriptionListObserver extends DefaultObserver<List<Subscription>> {

    @Override
    public void onComplete() {
      SubscriptionListPresenter.this.hideViewLoading();
    }

    @Override
    public void onError(Throwable e) {
      SubscriptionListPresenter.this.hideViewLoading();
      SubscriptionListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
      SubscriptionListPresenter.this.showViewRetry();
    }

    @Override
    public void onNext(List<Subscription> signals) {
      SubscriptionListPresenter.this.showSubscriptionCollectionInView(signals);
      SubscriptionListPresenter.this.hideViewLoading();
    }

  }

  private class PurchasedSubscriptionListObserver extends DefaultObserver<List<Subscription>> {

    @Override
    public void onComplete() {
      SubscriptionListPresenter.this.hideViewLoading();
    }

    @Override
    public void onError(Throwable e) {
      SubscriptionListPresenter.this.hideViewLoading();
      SubscriptionListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
      SubscriptionListPresenter.this.showViewRetry();
    }

    @Override
    public void onNext(List<Subscription> subscriptions) {
      SubscriptionListPresenter.this.showSubscriptionCollectionInView(subscriptions);
      SubscriptionListPresenter.this.hideViewLoading();
    }

  }


}
