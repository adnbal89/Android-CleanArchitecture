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

import com.adnanbal.fxdedektifi.forex.domain.model.UserLoginDetails;
import com.adnanbal.fxdedektifi.forex.presentation.internal.di.PerActivity;
import com.adnanbal.fxdedektifi.forex.presentation.model.UserLoginDetailsModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import javax.inject.Inject;


/**
 * Mapper class used to transform {@link UserLoginDetails} (in
 * the domain layer) to {@link UserLoginDetailsModel} in
 * the presentation layer.
 */
@PerActivity
public class UserLoginDetailsModelDataMapper {

  @Inject
  public UserLoginDetailsModelDataMapper() {
  }

  /**
   * Transform a {@link UserLoginDetails} into an {@link UserLoginDetailsModel}.
   *
   * @param userLoginDetails Object to be transformed.
   * @return {@link UserLoginDetailsModel}.
   */
  public UserLoginDetailsModel transform(UserLoginDetails userLoginDetails) {
    if (userLoginDetails == null) {
      throw new IllegalArgumentException("Cannot transform a null value");
    }
    final UserLoginDetailsModel userLoginDetailsModel = new UserLoginDetailsModel(
        userLoginDetails.getId());
    userLoginDetailsModel.setUserUid(userLoginDetails.getId());
    userLoginDetailsModel.setDate(userLoginDetails.getDate());
    return userLoginDetailsModel;
  }

  /**
   * Transform a Collection of {@link UserLoginDetails} into a Collection of {@link
   * UserLoginDetailsModel}.
   *
   * @param userLoginDetailssCollection Objects to be transformed.
   * @return List of {@link UserLoginDetailsModel}.
   */
  public Collection<UserLoginDetailsModel> transform(
      Collection<UserLoginDetails> userLoginDetailssCollection) {
    Collection<UserLoginDetailsModel> userLoginDetailssModelsCollection;

    if (userLoginDetailssCollection != null && !userLoginDetailssCollection.isEmpty()) {
      userLoginDetailssModelsCollection = new ArrayList<>();
      for (UserLoginDetails userLoginDetails : userLoginDetailssCollection) {
        userLoginDetailssModelsCollection.add(transform(userLoginDetails));
      }
    } else {
      userLoginDetailssModelsCollection = Collections.emptyList();
    }

    return userLoginDetailssModelsCollection;
  }
}
