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

import com.adnanbal.fxdedektifi.forex.domain.interactor.AddUserLoginDetails;
import com.adnanbal.fxdedektifi.forex.domain.interactor.DefaultObserver;
import com.adnanbal.fxdedektifi.forex.presentation.mapper.UserSignalModelDataMapper;
import com.adnanbal.fxdedektifi.forex.presentation.model.UserLoginDetailsModel;
import javax.inject.Inject;

public class UserLoginDetailsPresenter implements Presenter {

  private final static String TAG = UserLoginDetailsPresenter.class.getName();

  private final AddUserLoginDetails addUserLoginDetailsUseCase;
  private final UserSignalModelDataMapper userSignalModelDataMapper;

  @Inject
  public UserLoginDetailsPresenter(AddUserLoginDetails addUserLoginDetailsUseCase,
      UserSignalModelDataMapper userSignalModelDataMapper
  ) {
    this.addUserLoginDetailsUseCase = addUserLoginDetailsUseCase;
    this.userSignalModelDataMapper = userSignalModelDataMapper;
  }

  @Override
  public void resume() {

  }

  @Override
  public void pause() {

  }

  @Override
  public void destroy() {
    this.addUserLoginDetailsUseCase.dispose();
  }

  public void addUserLoginDetails(UserLoginDetailsModel userLoginDetailsModel) {

    this.addUserLoginDetailsUseCase
        .execute(new UserLoginDetailsOpenObserver(), AddUserLoginDetails.Params.forUserLoginDetails(
            userLoginDetailsModel.getId(), userLoginDetailsModel.getUserUid(),
            userLoginDetailsModel.getDate())
        );
  }


  private final class UserLoginDetailsOpenObserver extends DefaultObserver<Boolean> {

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {
    }

    @Override
    public void onNext(Boolean result) {
      if (result) {
      }
    }
  }


}
