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

package com.adnanbal.fxdedektifi.forex.data.repository;

import com.adnanbal.fxdedektifi.forex.data.entity.UserLoginDetailsEntity;
import com.adnanbal.fxdedektifi.forex.data.entity.mapper.UserLoginDetailsEntityDataMapper;
import com.adnanbal.fxdedektifi.forex.data.repository.datasource.Datastore.UserLoginDetailsDataStore;
import com.adnanbal.fxdedektifi.forex.data.repository.datasource.DatastoreFactory.UserLoginDetailsDataStoreFactory;
import com.adnanbal.fxdedektifi.forex.domain.repository.UserLoginDetailsRepository;
import io.reactivex.Observable;
import java.util.Map;
import javax.inject.Inject;


/**
 * {@link UserLoginDetailsDataRepository} for retrieving userLoginDetails data.
 */
public class UserLoginDetailsDataRepository implements UserLoginDetailsRepository {

  private final UserLoginDetailsDataStoreFactory userLoginDetailsDataStoreFactory;
  private final UserLoginDetailsEntityDataMapper userLoginDetailsEntityDataMapper;

  /**
   * Constructs a {@link UserLoginDetailsRepository}.
   *
   * @param dataStoreFactory A factory to construct different data source implementations.
   * @param userLoginDetailsEntityDataMapper {@link UserLoginDetailsEntityDataMapper}.
   */
  @Inject
  UserLoginDetailsDataRepository(UserLoginDetailsDataStoreFactory dataStoreFactory,
      UserLoginDetailsEntityDataMapper userLoginDetailsEntityDataMapper) {
    this.userLoginDetailsDataStoreFactory = dataStoreFactory;
    this.userLoginDetailsEntityDataMapper = userLoginDetailsEntityDataMapper;
  }


  @Override
  public Observable<Boolean> addUserLoginDetails(String id, String userUid,
      Map<String, String> date) {
    final UserLoginDetailsDataStore userLoginDetailsDataStore = this.userLoginDetailsDataStoreFactory
        .create(id);
    UserLoginDetailsEntity entity = this.userLoginDetailsEntityDataMapper
        .createUserLoginDetailsEntityObject(id, userUid, date);

    return userLoginDetailsDataStore.addUserLoginDetailsEntity(entity);
  }
}
