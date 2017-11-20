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

import com.adnanbal.fxdedektifi.forex.domain.interactor.DefaultObserver;
import com.adnanbal.fxdedektifi.forex.domain.interactor.NewSignal;
import com.adnanbal.fxdedektifi.forex.presentation.external.AnalyticsInterface;
import com.adnanbal.fxdedektifi.forex.presentation.model.NewPositionModel;
import javax.inject.Inject;

public class NewSignalPresenter implements Presenter {

  private final static String TAG = NewSignalPresenter.class.getName();

  private final AnalyticsInterface analyticsInterface;
  private final NewSignal newSignalUseCase;

  @Inject
  public NewSignalPresenter(AnalyticsInterface analyticsInterface, NewSignal newSignalUseCase) {
    this.newSignalUseCase = newSignalUseCase;
    this.analyticsInterface = analyticsInterface;
  }

  @Override
  public void resume() {

  }

  @Override
  public void pause() {

  }

  @Override
  public void destroy() {
    newSignalUseCase.dispose();
  }

  public void createNewSignaltoDatabase(NewPositionModel positionModel, String operationOnSignal) {
    createNewSignal(positionModel, operationOnSignal);
  }

  private void createNewSignal(NewPositionModel positionModel, String operationOnSignal) {
    this.newSignalUseCase.execute(new NewSignalObserver(),
        NewSignal.Params.forPosition(positionModel.getId(), positionModel.getPair(),
            positionModel.getVolume(), positionModel.isBuy_sell(),
            positionModel.getOpeningPrice(), positionModel.isOpen(), positionModel.getStatus(),
            positionModel.getComment(), positionModel.getDate(), operationOnSignal,
            positionModel.getTake_profit_price(), positionModel.getStop_loss_price(),
            positionModel.getClosingPrice(), positionModel.isHitStopLoss(),
            positionModel.isHitTakeProfit()));

  }

  private final class NewSignalObserver extends DefaultObserver<Boolean> {

    @Override
    public void onComplete() {
      //todo : COMPLETE.
//      NewSignalPresenter.this.hideViewLoading();
    }

    @Override
    public void onError(Throwable e) {
      //TODO : cOMPLETE
//      NewSignalPresenter.this.hideViewLoading();
//      NewSignalPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
//      NewSignalPresenter.this.showViewRetry();
    }

    @Override
    public void onNext(Boolean result) {
//      NewSignalPresenter.this.hideViewLoading();
    }
  }


}
