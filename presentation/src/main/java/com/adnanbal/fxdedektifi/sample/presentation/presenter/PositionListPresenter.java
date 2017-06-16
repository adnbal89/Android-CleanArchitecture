package com.adnanbal.fxdedektifi.sample.presentation.presenter;

import android.support.annotation.NonNull;
import com.adnanbal.fxdedektifi.sample.domain.exception.DefaultErrorBundle;
import com.adnanbal.fxdedektifi.sample.domain.exception.ErrorBundle;
import com.adnanbal.fxdedektifi.sample.domain.interactor.DefaultObserver;
import com.adnanbal.fxdedektifi.sample.domain.interactor.GetPositionList;
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

  private PositionListView positionListView;

  private final GetPositionList getPositionListUseCase;
  private final PositionModelDataMapper positionModelDataMapper;

  @Inject
  public PositionListPresenter(GetPositionList getPositionListUseCase,
      PositionModelDataMapper positionModelDataMapper) {
    this.getPositionListUseCase = getPositionListUseCase;
    this.positionModelDataMapper = positionModelDataMapper;
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
    this.getPositionListUseCase.dispose();
    this.positionListView = null;
  }

  /**
   * Initializes the presenter by start retrieving the position list.
   */
  public void initialize() {
    this.loadPositionList();
  }

  private void loadPositionList() {
    this.hideViewRetry();
    this.showViewLoading();
    this.getPositionList();
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
}
