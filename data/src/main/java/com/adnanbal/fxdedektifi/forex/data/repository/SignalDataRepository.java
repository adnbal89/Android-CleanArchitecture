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

import com.adnanbal.fxdedektifi.forex.data.entity.SignalEntity;
import com.adnanbal.fxdedektifi.forex.data.entity.mapper.SignalEntityDataMapper;
import com.adnanbal.fxdedektifi.forex.data.entity.mapper.Signal_UserEntityDataMapper;
import com.adnanbal.fxdedektifi.forex.data.repository.datasource.Datastore.SignalDataStore;
import com.adnanbal.fxdedektifi.forex.data.repository.datasource.DatastoreFactory.SignalDataStoreFactory;
import com.adnanbal.fxdedektifi.forex.domain.model.Signal;
import com.adnanbal.fxdedektifi.forex.domain.model.UserSignal;
import com.adnanbal.fxdedektifi.forex.domain.repository.SignalRepository;
import io.reactivex.Observable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

/**
 * {@link SignalRepository} for retrieving signal data.
 */
public class SignalDataRepository implements SignalRepository {

  private final SignalDataStoreFactory signalDataStoreFactory;
  private final SignalEntityDataMapper signalEntityDataMapper;
  private final Signal_UserEntityDataMapper signal_userEntityDataMapper;

  /**
   * Constructs a {@link SignalRepository}.
   *
   * @param dataStoreFactory A factory to construct different data source implementations.
   * @param signalEntityDataMapper {@link SignalEntityDataMapper}.
   */
  @Inject
  SignalDataRepository(SignalDataStoreFactory dataStoreFactory,
      SignalEntityDataMapper signalEntityDataMapper,
      Signal_UserEntityDataMapper signal_userEntityDataMapper) {
    this.signalDataStoreFactory = dataStoreFactory;
    this.signalEntityDataMapper = signalEntityDataMapper;
    this.signal_userEntityDataMapper = signal_userEntityDataMapper;
  }

  @Override
  public Observable<List<Signal>> getSignalsList() {
    //we always get all signals from the cloud
    final SignalDataStore signalDataStore = this.signalDataStoreFactory
        .createCloudDataStore();
    return signalDataStore.signalEntityList().map(this.signalEntityDataMapper::transform);
  }

  @Override
  public Observable<Signal> getOneSignal(String signalId) {
    final SignalDataStore signalDataStore = this.signalDataStoreFactory.create(signalId);
    return signalDataStore.signalEntityDetails(signalId)
        .map(this.signalEntityDataMapper::transform);
  }

  @Override
  public Observable<Boolean> openSignal(String positionId, String pair, double volume,
      boolean buy_sell, double openingPrice, boolean open, String status, String comment,
      double statusChangePrice, String term, Date date, Map<String, Boolean> users,
      Map<String, List<String>> changedFields, Double take_profit_price, Double stop_loss_price,
      boolean hitStopLoss,
      boolean hitTakeProfit) {
    final SignalDataStore signalDataStore = this.signalDataStoreFactory.create(positionId);
    SignalEntity entity = this.signalEntityDataMapper
        .createSignalEntityObject(positionId, pair, volume, buy_sell, openingPrice, open, status,
            comment, statusChangePrice, term, date, users, changedFields, take_profit_price,
            stop_loss_price, hitStopLoss, hitTakeProfit);

    return signalDataStore.addSignalEntity(entity);
  }

  @Override
  public Observable<List<UserSignal>> getUser_SignalList(String authenticatedUserUid) {
    //we always get all signals from the cloud
    final SignalDataStore signalDataStore = this.signalDataStoreFactory
        .createCloudDataStore();
    return signalDataStore.userSignalEntityList(authenticatedUserUid)
        .map(this.signal_userEntityDataMapper::transform);

  }


  @Override
  public Observable<Boolean> patchUserSignal(Map<String, Boolean> userSignalMap,
      String authenticatedUserUid, boolean openOrClose) {
    //we always get all signals from the cloud
    final SignalDataStore signalDataStore = this.signalDataStoreFactory
        .createCloudDataStore();
    return signalDataStore.patchUserSignalEntity(userSignalMap, authenticatedUserUid, openOrClose);

  }


  @Override
  public Observable<Signal> getUpdatedSignal() {
    //we always get all signals from the cloud
    final SignalDataStore signalDataStore = this.signalDataStoreFactory
        .createCloudDataStore();
    return signalDataStore.getUpdatedSignal().map(this.signalEntityDataMapper::transform);
  }


}
