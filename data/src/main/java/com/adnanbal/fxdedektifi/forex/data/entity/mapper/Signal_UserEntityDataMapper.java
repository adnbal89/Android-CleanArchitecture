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

package com.adnanbal.fxdedektifi.forex.data.entity.mapper;

import com.adnanbal.fxdedektifi.forex.data.entity.UserSignalEntity;
import com.adnanbal.fxdedektifi.forex.domain.model.UserSignal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;

public class Signal_UserEntityDataMapper {

  @Inject
  Signal_UserEntityDataMapper() {
  }

  /**
   * Transform a {@link UserSignalEntity} into an {@link UserSignal }.
   *
   * @param userSignalEntity Object to be transformed.
   * @return {@link UserSignal} if valid {@link UserSignalEntity} otherwise null.
   */
  public UserSignal transform(UserSignalEntity userSignalEntity) {
    UserSignal userSignal = null;
    if (userSignalEntity != null) {
      userSignal = new UserSignal(userSignalEntity.getSignals());
    }
    return userSignal;
  }

  /**
   * Transform a List of {@link UserSignalEntity} into a Collection of {@link UserSignal}.
   *
   * @param userSignalEntityCollection Object Collection to be transformed.
   * @return {@link UserSignal} if valid {@link UserSignalEntity} otherwise null.
   */
  public List<UserSignal> transform(Collection<UserSignalEntity> userSignalEntityCollection) {
    final List<UserSignal> userSignalList = new ArrayList<>(20);
    for (UserSignalEntity userSignalEntity : userSignalEntityCollection) {
      final UserSignal userSignal = transform(userSignalEntity);
      if (userSignal != null) {
        userSignalList.add(userSignal);
      }
    }
    return userSignalList;
  }



}
