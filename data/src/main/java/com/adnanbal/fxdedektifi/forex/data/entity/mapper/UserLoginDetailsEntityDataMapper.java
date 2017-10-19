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

import com.adnanbal.fxdedektifi.forex.data.entity.UserLoginDetailsEntity;
import com.adnanbal.fxdedektifi.forex.domain.model.UserLoginDetails;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

/**
 * Mapper class used to transform {@link UserLoginDetailsEntity} (in the data layer) to {@link
 * UserLoginDetails} in the domain layer.
 */
public class UserLoginDetailsEntityDataMapper {

  @Inject
  UserLoginDetailsEntityDataMapper() {
  }

  /**
   * Transform a {@link UserLoginDetailsEntity} into an {@link UserLoginDetails}.
   *
   * @param userLoginDetailsEntity Object to be transformed.
   * @return {@link UserLoginDetails} if valid {@link UserLoginDetailsEntity} otherwise null.
   */
  public UserLoginDetails transform(UserLoginDetailsEntity userLoginDetailsEntity) {
    UserLoginDetails userLoginDetails = null;

    if (userLoginDetailsEntity != null) {
      userLoginDetails = new UserLoginDetails();
      userLoginDetails.setDate(userLoginDetailsEntity.getDate());
      userLoginDetails.setUserUid(userLoginDetailsEntity.getUserUid());
    }
    return userLoginDetails;
  }

  /**
   * Transform a List of {@link UserLoginDetailsEntity} into a Collection of {@link
   * UserLoginDetails}.
   *
   * @param userLoginDetailsEntityCollection Object Collection to be transformed.
   * @return {@link UserLoginDetails} if valid {@link UserLoginDetailsEntity} otherwise null.
   */
  public List<UserLoginDetails> transform(
      Collection<UserLoginDetailsEntity> userLoginDetailsEntityCollection) {
    final List<UserLoginDetails> userLoginDetailsList = new ArrayList<>();
    for (UserLoginDetailsEntity userLoginDetailsEntity : userLoginDetailsEntityCollection) {
      final UserLoginDetails userLoginDetails = transform(userLoginDetailsEntity);
      if (userLoginDetails != null) {
        userLoginDetailsList.add(userLoginDetails);
      }
    }
    return userLoginDetailsList;
  }


  public UserLoginDetailsEntity createUserLoginDetailsEntityObject(String id, String userUid,
      Map<String, String> date) {
    UserLoginDetailsEntity userLoginDetailsEntity;
    userLoginDetailsEntity = new UserLoginDetailsEntity();
    userLoginDetailsEntity.setId(id);
    userLoginDetailsEntity.setUserUid(userUid);
    userLoginDetailsEntity.setDate(date);

    return userLoginDetailsEntity;
  }


}
