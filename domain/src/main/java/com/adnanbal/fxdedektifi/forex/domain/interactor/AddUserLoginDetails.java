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

package com.adnanbal.fxdedektifi.forex.domain.interactor;

import com.adnanbal.fxdedektifi.forex.domain.executor.PostExecutionThread;
import com.adnanbal.fxdedektifi.forex.domain.executor.ThreadExecutor;
import com.adnanbal.fxdedektifi.forex.domain.model.UserLoginDetails;
import com.adnanbal.fxdedektifi.forex.domain.repository.UserLoginDetailsRepository;
import com.fernandocejas.arrow.checks.Preconditions;
import io.reactivex.Observable;
import java.util.Map;
import javax.inject.Inject;


/**
 * This class is an implementation of {@link UseCase} that represents a use case for
 * retrieving a collection of all {@link UserLoginDetails}.
 */
public class AddUserLoginDetails extends UseCase<Boolean, AddUserLoginDetails.Params> {


  private final UserLoginDetailsRepository userLoginDetailsRepository;

  @Inject
  AddUserLoginDetails(UserLoginDetailsRepository userLoginDetailsRepository,
      ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.userLoginDetailsRepository = userLoginDetailsRepository;
  }

  @Override
  Observable<Boolean> buildUseCaseObservable(Params params) {
    Preconditions.checkNotNull(params);
    return this.userLoginDetailsRepository
        .addUserLoginDetails(params.id, params.userUid, params.date);
  }

  public static final class Params {

    String id;
    String userUid;
    Map<String, String> date;

    private Params(String id, String userUid, Map<String, String> date) {
      this.id = id;
      this.userUid = userUid;
      this.date = date;
    }

    public static AddUserLoginDetails.Params forUserLoginDetails(String id, String userUid,
        Map<String, String> date) {
      return new AddUserLoginDetails.Params(id, userUid, date);
    }
  }


}
