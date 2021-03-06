package com.adnanbal.fxdedektifi.forex.presentation.presenter;

import android.support.annotation.NonNull;
import com.adnanbal.fxdedektifi.forex.domain.exception.DefaultErrorBundle;
import com.adnanbal.fxdedektifi.forex.domain.exception.ErrorBundle;
import com.adnanbal.fxdedektifi.forex.domain.interactor.DefaultObserver;
import com.adnanbal.fxdedektifi.forex.domain.interactor.GetPositionHistoryList;
import com.adnanbal.fxdedektifi.forex.domain.interactor.OpenPosition;
import com.adnanbal.fxdedektifi.forex.domain.model.Position;
import com.adnanbal.fxdedektifi.forex.presentation.exception.ErrorMessageFactory;
import com.adnanbal.fxdedektifi.forex.presentation.mapper.PositionModelDataMapper;
import com.adnanbal.fxdedektifi.forex.presentation.model.PositionModel;
import com.adnanbal.fxdedektifi.forex.presentation.view.PositionListView;
import io.fabric.sdk.android.Fabric;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
public class ProfitListPresenter implements Presenter {

  static final String TAG = "ProfitListPresenter";

  private PositionListView positionHistoryListView;

  private final GetPositionHistoryList getPositionHistoryListUseCase;
  private final PositionModelDataMapper positionModelDataMapper;

  @Inject
  public ProfitListPresenter(OpenPosition openPositionUseCase,
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
    if (positionHistoryListView != null)

    {
      this.positionHistoryListView.viewPosition(positionModel);
    }
  }

  private void showViewLoading() {
    this.positionHistoryListView.showLoading();
  }

  private void hideViewLoading() {
    if (positionHistoryListView != null) {
      this.positionHistoryListView.hideLoading();
    }
  }

  private void showViewRetry() {
    if (positionHistoryListView != null) {
      this.positionHistoryListView.showRetry();
    }
  }

  private void hideViewRetry() {
    if (positionHistoryListView != null) {
      this.positionHistoryListView.hideRetry();
    }
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
      ProfitListPresenter.this.hideViewLoading();
    }

    @Override
    public void onError(Throwable e) {
      ProfitListPresenter.this.hideViewLoading();
      ProfitListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
      ProfitListPresenter.this.showViewRetry();
    }

    @Override
    public void onNext(List<Position> positions) {
      try {
        ProfitListPresenter.this.showPositionsCollectionInView(positions);
        ProfitListPresenter.this.hideViewLoading();
      } catch (Exception e) {
        Fabric.getLogger().e(TAG, e.getMessage());
      }
    }
  }
}
