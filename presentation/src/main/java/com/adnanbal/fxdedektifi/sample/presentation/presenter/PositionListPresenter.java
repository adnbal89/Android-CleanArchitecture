package com.adnanbal.fxdedektifi.sample.presentation.presenter;

import android.support.annotation.NonNull;
import com.adnanbal.fxdedektifi.sample.domain.exception.DefaultErrorBundle;
import com.adnanbal.fxdedektifi.sample.domain.exception.ErrorBundle;
import com.adnanbal.fxdedektifi.sample.domain.interactor.CloseOpenPosition;
import com.adnanbal.fxdedektifi.sample.domain.interactor.CloseOpenPosition.Params;
import com.adnanbal.fxdedektifi.sample.domain.interactor.DefaultObserver;
import com.adnanbal.fxdedektifi.sample.domain.interactor.GetPositionList;
import com.adnanbal.fxdedektifi.sample.domain.interactor.OpenPosition;
import com.adnanbal.fxdedektifi.sample.domain.model.Position;
import com.adnanbal.fxdedektifi.sample.presentation.exception.ErrorMessageFactory;
import com.adnanbal.fxdedektifi.sample.presentation.mapper.PositionModelDataMapper;
import com.adnanbal.fxdedektifi.sample.presentation.model.PositionModel;
import com.adnanbal.fxdedektifi.sample.presentation.view.PositionListView;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
public class PositionListPresenter implements Presenter {


  private PositionModel positionModelToRemove;
  private PositionModel positionModelToAdd;

  private PositionListView positionListView;

  private final OpenPosition openPositionUseCase;
  private final CloseOpenPosition closeOpenPositionUseCase;
  private final GetPositionList getPositionListUseCase;
  private final PositionModelDataMapper positionModelDataMapper;
//  private final PositionToPositionModelDataMapper positionToPositionModelDataMapper;


  @Inject
  public PositionListPresenter(GetPositionList getPositionListUseCase,
      CloseOpenPosition closeOpenPositionUseCase,
      PositionModelDataMapper positionModelDataMapper,
      OpenPosition openPositionUseCase) {
    this.closeOpenPositionUseCase = closeOpenPositionUseCase;
    this.getPositionListUseCase = getPositionListUseCase;
    this.openPositionUseCase = openPositionUseCase;
    this.positionModelDataMapper = positionModelDataMapper;
//    this.positionToPositionModelDataMapper = positionToPositionModelDataMapper;
  }

  public void setView(@NonNull PositionListView view) {
    this.positionListView = view;
  }

  @Override
  public void resume() {
  }

  @Override
  public void pause() {
  }

  @Override
  public void destroy() {
    this.closeOpenPositionUseCase.dispose();
    this.getPositionListUseCase.dispose();
    this.positionListView = null;
  }

  /**
   * Initializes the presenter by start retrieving the position list.
   */
  public void initialize() {
    this.loadPositionList();
  }

  public void close(PositionModel positionModel) {
    this.positionModelToRemove = positionModel;
    this.closePosition(positionModel);
  }

  public void open(PositionModel positionModel) {
    this.positionModelToAdd = positionModel;
    this.openPosition(positionModel);
  }


  private void loadPositionList() {
    this.hideViewRetry();
    this.showViewLoading();
    this.getPositionList();
  }

  private void closePosition(PositionModel positionModel) {
    this.hideViewRetry();
    this.showViewLoading();
    this.closeOpenPosition(positionModel);
  }

  public void onPositionClicked(PositionModel positionModel) {
    this.positionListView.viewPosition(positionModel);
  }

  private void showViewLoading() {
    this.positionListView.showLoading();
  }

  private void hideViewLoading() {
    this.positionListView.hideLoading();
  }

  private void showViewRetry() {
    this.positionListView.showRetry();
  }

  private void hideViewRetry() {
    this.positionListView.hideRetry();
  }

  private void showErrorMessage(ErrorBundle errorBundle) {
    String errorMessage = ErrorMessageFactory.create(this.positionListView.context(),
        errorBundle.getException());
    this.positionListView.showError(errorMessage);
  }

  private void showPositionsCollectionInView(Collection<Position> positionsCollection) {
    final Collection<PositionModel> positionModelsCollection =
        this.positionModelDataMapper.transform(positionsCollection);
    this.positionListView.renderPositionList(positionModelsCollection);
  }

  private void getPositionList() {
    this.getPositionListUseCase.execute(new PositionListObserver(), null);
  }

  private void closeOpenPosition(PositionModel positionModel) {
    this.closeOpenPositionUseCase
        .execute(new PositionCloseObserver(), Params.forPosition(positionModel.getPositionId()));
  }

  private void openPosition(PositionModel positionModel) {
    this.openPositionUseCase
        .execute(new PositionOpenObserver(),
            OpenPosition.Params.forPosition(positionModel.getPositionId(), positionModel.getPair(),
                positionModel.getVolume(), positionModel.isBuy_sell(),
                positionModel.getOpeningPrice(), positionModel.isOpen(), positionModel.getStatus(),
                positionModel.getComment()
            ));
  }


  private final class PositionListObserver extends DefaultObserver<List<Position>> {

    @Override
    public void onComplete() {
      PositionListPresenter.this.hideViewLoading();
    }

    @Override
    public void onError(Throwable e) {
      PositionListPresenter.this.hideViewLoading();
      PositionListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
      PositionListPresenter.this.showViewRetry();
    }

    @Override
    public void onNext(List<Position> positions) {
      PositionListPresenter.this.showPositionsCollectionInView(positions);
    }
  }


  private final class PositionCloseObserver extends DefaultObserver<Boolean> {

    @Override
    public void onComplete() {
      PositionListPresenter.this.hideViewLoading();
    }

    @Override
    public void onError(Throwable e) {
      PositionListPresenter.this.hideViewLoading();
      PositionListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
//      PositionListPresenter.this.showViewPositionCloseFailed();
    }

    @Override
    public void onNext(Boolean result) {
      PositionListPresenter.this.confirmRemoveOperationComplete(result);
    }
  }


  private final class PositionOpenObserver extends DefaultObserver<Boolean> {

    @Override
    public void onComplete() {
      PositionListPresenter.this.hideViewLoading();
    }

    @Override
    public void onError(Throwable e) {
      PositionListPresenter.this.hideViewLoading();
      PositionListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
//      PositionListPresenter.this.showViewPositionCloseFailed();
    }

    @Override
    public void onNext(Boolean result) {
      if (result) {
        //Todo: add to collection.
        PositionListPresenter.this.confirmAddOperationComplete(result);
      }
    }
  }

  //TODO : implement according to result
  private void confirmRemoveOperationComplete(Boolean result) {

    System.out.println("RESULT : " + result.toString());
//    final PositionModel positionModel =
//        this.positionModelDataMapper.transform(position);
    this.positionListView.closePositionConfirmedOnline(positionModelToRemove);
  }

  private void confirmAddOperationComplete(Boolean result) {
//    final PositionModel positionModel =
//        this.positionModelDataMapper.transform(position);
    this.positionListView.openPositionConfirmedOnline(positionModelToAdd);
  }
}
