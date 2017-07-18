package com.adnanbal.fxdedektifi.sample.presentation.presenter;

import android.support.annotation.NonNull;
import com.adnanbal.fxdedektifi.sample.domain.exception.DefaultErrorBundle;
import com.adnanbal.fxdedektifi.sample.domain.exception.ErrorBundle;
import com.adnanbal.fxdedektifi.sample.domain.interactor.DefaultObserver;
import com.adnanbal.fxdedektifi.sample.domain.interactor.GetSignalList;
import com.adnanbal.fxdedektifi.sample.domain.interactor.OpenSignal;
import com.adnanbal.fxdedektifi.sample.domain.model.Signal;
import com.adnanbal.fxdedektifi.sample.presentation.exception.ErrorMessageFactory;
import com.adnanbal.fxdedektifi.sample.presentation.mapper.SignalModelDataMapper;
import com.adnanbal.fxdedektifi.sample.presentation.model.SignalModel;
import com.adnanbal.fxdedektifi.sample.presentation.view.SignalListView;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
public class SignalListPresenter implements Presenter {

  private SignalModel signalToAdd;

  private SignalListView signalListView;

  private final OpenSignal openSignalUseCase;
  private final GetSignalList getSignalListUseCase;
  private final SignalModelDataMapper signalModelDataMapper;

  @Inject
  public SignalListPresenter(OpenSignal openSignalUseCase, GetSignalList getSignalListUseCase,
      SignalModelDataMapper signalModelDataMapper) {
    this.getSignalListUseCase = getSignalListUseCase;
    this.signalModelDataMapper = signalModelDataMapper;
    this.openSignalUseCase = openSignalUseCase;
  }

  public void setView(@NonNull SignalListView view) {
    this.signalListView = view;
  }


  @Override
  public void resume() {

  }

  @Override
  public void pause() {

  }

  @Override
  public void destroy() {
    this.openSignalUseCase.dispose();
    this.getSignalListUseCase.dispose();
    this.signalListView = null;
  }

  /**
   * Initializes the presenter by start retrieving the signal list.
   */
  public void initialize() {
    this.loadSignalList();
  }

  public void open(SignalModel signalModel) {
    this.signalToAdd = signalModel;
    this.openSignal(signalModel);
  }

  private void loadSignalList() {
    this.hideViewRetry();
    this.showViewLoading();
    this.getSignalList();
  }

  public void onSignalClicked(SignalModel signalModel) {
    this.signalListView.viewSignal(signalModel);
  }


  private void showViewLoading() {
    this.signalListView.showLoading();
  }

  private void hideViewLoading() {
    this.signalListView.hideLoading();
  }

  private void showViewRetry() {
    this.signalListView.showRetry();
  }

  private void hideViewRetry() {
    this.signalListView.hideRetry();
  }

  private void showErrorMessage(ErrorBundle errorBundle) {
    String errorMessage = ErrorMessageFactory.create(this.signalListView.context(),
        errorBundle.getException());
    this.signalListView.showError(errorMessage);
  }

  private void showSignalsCollectionInView(Collection<Signal> signalsCollection) {
    final Collection<SignalModel> signalModelsCollection =
        this.signalModelDataMapper.transform(signalsCollection);
    this.signalListView.renderSignalList(signalModelsCollection);
  }

  private void getSignalList() {
    this.getSignalListUseCase.execute(new SignalListObserver(), null);
  }

  private void openSignal(SignalModel signalModel) {
    this.openSignalUseCase
        .execute(new SignalOpenObserver(),
            OpenSignal.Params.forSignal(signalModel.getPositionId(), signalModel.getPair(),
                signalModel.getVolume(), signalModel.isBuy_sell(),
                signalModel.getOpeningPrice(), signalModel.isOpen(), signalModel.getStatus(),
                signalModel.getComment(), signalModel.getStatusChangePrice(), signalModel.getTerm()
            ));
  }

  private final class SignalListObserver extends DefaultObserver<List<Signal>> {

    @Override
    public void onComplete() {
      SignalListPresenter.this.hideViewLoading();
    }

    @Override
    public void onError(Throwable e) {
      SignalListPresenter.this.hideViewLoading();
      SignalListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
      SignalListPresenter.this.showViewRetry();
    }

    @Override
    public void onNext(List<Signal> signals) {
      SignalListPresenter.this.showSignalsCollectionInView(signals);
    }
  }


  private final class SignalOpenObserver extends DefaultObserver<Boolean> {

    @Override
    public void onComplete() {
      SignalListPresenter.this.hideViewLoading();
    }

    @Override
    public void onError(Throwable e) {
      SignalListPresenter.this.hideViewLoading();
      SignalListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
//      SignalListPresenter.this.showViewSignalCloseFailed();
    }

    @Override
    public void onNext(Boolean result) {
      if (result) {
        //Todo: add to collection.
        SignalListPresenter.this.confirmAddOperationComplete(result);
      }
    }
  }

  private void confirmAddOperationComplete(Boolean result) {
//    final SignalModel positionModel =
//        this.positionModelDataMapper.transform(position);
    this.signalListView.openSignalConfirmedOnline(signalToAdd);
  }

}

