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

package com.adnanbal.fxdedektifi.forex.presentation.mapper;

import com.adnanbal.fxdedektifi.forex.domain.model.UserSignal;
import com.adnanbal.fxdedektifi.forex.presentation.internal.di.PerActivity;
import com.adnanbal.fxdedektifi.forex.presentation.model.UserSignalModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import javax.inject.Inject;

/**
 * Mapper class used to transform {@link UserSignal} (in the domain layer) to {@link
 * UserSignalModel} in the presentation layer.
 */
@PerActivity
public class UserSignalModelDataMapper {

  @Inject
  public UserSignalModelDataMapper() {
  }


  /**
   * Transform a {@link UserSignal} into an {@link UserSignalModel}.
   *
   * @param userSignal Object to be transformed.
   * @return {@link UserSignalModel}.
   */
  public UserSignalModel transform(UserSignal userSignal) {
    if (userSignal == null) {
      throw new IllegalArgumentException("Cannot transform a null value");
    }
    final UserSignalModel userSignalModel = new UserSignalModel();
    userSignalModel.setSignals(userSignal.getSignals());
    return userSignalModel;
  }

  /**
   * Transform a Collection of {@link UserSignal} into a Collection of {@link UserSignalModel}.
   *
   * @param userSignalsCollection Objects to be transformed.
   * @return List of {@link UserSignalModel}.
   */
  public Collection<UserSignalModel> transform(Collection<UserSignal> userSignalsCollection) {
    Collection<UserSignalModel> userSignalsModelsCollection;

    if (userSignalsCollection != null && !userSignalsCollection.isEmpty()) {
      userSignalsModelsCollection = new ArrayList<>();
      for (UserSignal userSignal : userSignalsCollection) {
        userSignalsModelsCollection.add(transform(userSignal));
      }
    } else {
      userSignalsModelsCollection = Collections.emptyList();
    }

    return userSignalsModelsCollection;
  }

}
