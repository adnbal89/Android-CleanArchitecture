package com.adnanbal.fxdedektifi.sample.presentation.presenter;

import android.support.annotation.NonNull;
import com.adnanbal.fxdedektifi.sample.domain.exception.DefaultErrorBundle;
import com.adnanbal.fxdedektifi.sample.domain.exception.ErrorBundle;
import com.adnanbal.fxdedektifi.sample.domain.interactor.DefaultObserver;
import com.adnanbal.fxdedektifi.sample.domain.interactor.GetPositionHistoryList;
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
public class PositionHistoryListPresenter implements Presenter {

  private PositionListView positionHistoryListView;

  private final GetPositionHistoryList getPositionHistoryListUseCase;
  private final PositionModelDataMapper positionModelDataMapper;

  @Inject
  public PositionHistoryListPresenter(OpenPosition openPositionUseCase,
      GetPositionHistoryList getPositionHistoryListUseCase,
      PositionModelDataMapper positionModelDataMapper) {
    this.getPositionHistoryListUseCase = getPositionHistoryListUseCase;
    this.positionModelDataMapper = positionModelDataMapper;

  }

  public void setView(@NonNull PositionListView view) {
    this.positionHistoryListView = view;
  }

  @Override
  public void resume() {
  }

  @Override
  public void pause() {
  }

  @Override
  public void destroy() {
    this.getPositionHistoryListUseCase.dispose();
    this.positionHistoryListView = null;
  }

  /**
   * Initializes the presenter by start retrieving the position list.
   */
  public void initialize() {
    this.loadPositionHistoryList();
  }

  private void loadPositionHistoryList() {
    this.hideViewRetry();
    this.showViewLoading();
    this.getPositionHistoryList();
  }


  public void onPositionClicked(PositionModel positionModel) {
    this.positionHistoryListView.viewPosition(positionModel);
  }

  private void showViewLoading() {
    this.positionHistoryListView.showLoading();
  }

  private void hideViewLoading() {
    this.positionHistoryListView.hideLoading();
  }

  private void showViewRetry() {
    this.positionHistoryListView.showRetry();
  }

  private void hideViewRetry() {
    this.positionHistoryListView.hideRetry();
  }

  private void showErrorMessage(ErrorBundle errorBundle) {
    String errorMessage = ErrorMessageFactory.create(this.positionHistoryListView.context(),
        errorBundle.getException());
    this.positionHistoryListView.showError(errorMessage);
  }

  private void showPositionsCollectionInView(Collection<Position> positionsCollection) {
    final Collection<PositionModel> positionModelsCollection =
        this.positionModelDataMapper.transform(positionsCollection);
    this.positionHistoryListView.renderPositionList(positionModelsCollection);
  }

  private void getPositionHistoryList() {
    this.getPositionHistoryListUseCase.execute(new PositionHistoryListObserver(), null);
  }

  private final class PositionHistoryListObserver extends DefaultObserver<List<Position>> {

    @Override
    public void onComplete() {
      PositionHistoryListPresenter.this.hideViewLoading();
    }

    @Override
    public void onError(Throwable e) {
      PositionHistoryListPresenter.this.hideViewLoading();
      PositionHistoryListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
      PositionHistoryListPresenter.this.showViewRetry();
    }

    @Override
    public void onNext(List<Position> positions) {
      PositionHistoryListPresenter.this.showPositionsCollectionInView(positions);
    }
  }
}
