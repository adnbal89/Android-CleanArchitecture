package com.adnanbal.fxdedektifi.forex.presentation.presenter;

import android.support.annotation.NonNull;
import com.adnanbal.fxdedektifi.forex.domain.exception.DefaultErrorBundle;
import com.adnanbal.fxdedektifi.forex.domain.exception.ErrorBundle;
import com.adnanbal.fxdedektifi.forex.domain.interactor.DefaultObserver;
import com.adnanbal.fxdedektifi.forex.domain.interactor.GetSignalList;
import com.adnanbal.fxdedektifi.forex.domain.interactor.GetUser_SignalList;
import com.adnanbal.fxdedektifi.forex.domain.interactor.GetUser_SignalList.Params;
import com.adnanbal.fxdedektifi.forex.domain.interactor.OpenSignal;
import com.adnanbal.fxdedektifi.forex.domain.model.Signal;
import com.adnanbal.fxdedektifi.forex.domain.model.UserSignal;
import com.adnanbal.fxdedektifi.forex.presentation.AndroidApplication;
import com.adnanbal.fxdedektifi.forex.presentation.exception.ErrorMessageFactory;
import com.adnanbal.fxdedektifi.forex.presentation.external.AnalyticsInterface;
import com.adnanbal.fxdedektifi.forex.presentation.mapper.SignalModelDataMapper;
import com.adnanbal.fxdedektifi.forex.presentation.mapper.UserSignalModelDataMapper;
import com.adnanbal.fxdedektifi.forex.presentation.model.SignalModel;
import com.adnanbal.fxdedektifi.forex.presentation.model.UserSignalModel;
import com.adnanbal.fxdedektifi.forex.presentation.view.SignalListView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
public class SignalListPresenter implements Presenter {

  private SignalModel signalToAdd;
  private SignalListView signalListView;

  private final static String TAG = SignalListPresenter.class.getName();
  private final AnalyticsInterface analyticsInterface;

  private final OpenSignal openSignalUseCase;
  private final GetSignalList getSignalListUseCase;
  private final GetUser_SignalList getUser_SignalListUseCase;

  private final SignalModelDataMapper signalModelDataMapper;
  private final UserSignalModelDataMapper userSignalModelDataMapper;

  @Inject
  public SignalListPresenter(AnalyticsInterface analyticsInterface, OpenSignal openSignalUseCase,
      GetSignalList getSignalListUseCase,
      SignalModelDataMapper signalModelDataMapper, GetUser_SignalList getUser_SignalListUseCase,
      UserSignalModelDataMapper userSignalModelDataMapper) {

    this.analyticsInterface = analyticsInterface;
    this.getSignalListUseCase = getSignalListUseCase;
    this.getUser_SignalListUseCase = getUser_SignalListUseCase;
    this.signalModelDataMapper = signalModelDataMapper;
    this.userSignalModelDataMapper = userSignalModelDataMapper;
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

  public void initializeUser_Signals(String authenticatedUserUid) {
    this.loadUser_SignalList(authenticatedUserUid);
  }


  public void open(SignalModel signalModel, boolean openOrClose) {
    this.signalToAdd = signalModel;

    this.openSignal(signalModel.getPositionId(), openOrClose);
  }

  private void loadSignalList() {
    this.hideViewRetry();
    this.showViewLoading();
    this.getSignalList();
  }

  private void loadUser_SignalList(String authenticatedUserUid) {
    this.getUser_SignalList(authenticatedUserUid);
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

    System.out.println("Date Month: " + signalsCollection.iterator().next().getDate().getMonth());

    this.signalListView.renderSignalList(signalModelsCollection);
  }

  private Collection<UserSignalModel> transformUserSignalToUserSignalModel(
      Collection<UserSignal> signalsCollection) {
    final Collection<UserSignalModel> signalModelsCollection =
        this.userSignalModelDataMapper.transform(signalsCollection);
    return signalModelsCollection;
  }

  private void getSignalList() {
    this.getSignalListUseCase.execute(new SignalListObserver(), null);
  }

  private void getUser_SignalList(String userUid) {
    this.getUser_SignalListUseCase.execute(new User_SignalListObserver(), Params.forUser(userUid));

  }

  private void openSignal(String signalId, boolean openOrClose) {
//    this.openSignalUseCase
//        .execute(new SignalOpenObserver(),
//            OpenSignal.Params.forSignal(signalModel.getPositionId(), signalModel.getPair(),
//                signalModel.getVolume(), signalModel.isBuy_sell(),
//                signalModel.getOpeningPrice(), signalModel.isOpen(), signalModel.getStatus(),
//                signalModel.getComment(), signalModel.getStatusChangePrice(), signalModel.getTerm(),
//                signalModel.getDate(), signalModel.getUsers(), signalModel.getChangedFields()
//

    Map<String, Boolean> userSignalMap = new HashMap<>();

    userSignalMap.put(signalId, true);

    UserSignalModel uSignalModel = new UserSignalModel(userSignalMap);
    //if operation is close
    if (!openOrClose) {
      AndroidApplication.listUserSignalModel.iterator().next().getSignals().remove(signalId);
    } else {
      if (AndroidApplication.listUserSignalModel.size() != 0) {

        AndroidApplication.listUserSignalModel.iterator().next().getSignals().put(signalId, true);
      } else {
        AndroidApplication.listUserSignalModel = new ArrayList<>();
        AndroidApplication.listUserSignalModel.add(uSignalModel);
      }

    }

    String authenticatedUserUid = AndroidApplication.userUid;

    this.openSignalUseCase
        .execute(new SignalOpenObserver(),
            OpenSignal.Params.forSignal(userSignalMap, authenticatedUserUid, openOrClose));
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
      SignalListPresenter.this.hideViewLoading();
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


  //User_Signal observer for getting current open signals
  private final class User_SignalListObserver extends DefaultObserver<List<UserSignal>> {

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
    public void onNext(List<UserSignal> userSignals) {
//      SignalListPresenter.this.showSignalsCollectionInView(userSignals);
      AndroidApplication.listUserSignalModel = transformUserSignalToUserSignalModel(userSignals);
//      confirmAddOperationComplete(true);
//        SignalListPresenter.this.hideViewLoading();
    }
  }

  private void confirmAddOperationComplete(Boolean result) {
//    final SignalModel positionModel =
//        this.positionModelDataMapper.transform(position);
    this.signalListView.openSignalConfirmedOnline(signalToAdd);
  }

}

