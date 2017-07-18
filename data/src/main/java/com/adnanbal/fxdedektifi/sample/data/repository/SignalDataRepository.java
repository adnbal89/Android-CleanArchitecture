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

package com.adnanbal.fxdedektifi.sample.data.repository;

import com.adnanbal.fxdedektifi.sample.data.entity.SignalEntity;
import com.adnanbal.fxdedektifi.sample.data.entity.mapper.SignalEntityDataMapper;
import com.adnanbal.fxdedektifi.sample.data.repository.datasource.Datastore.SignalDataStore;
import com.adnanbal.fxdedektifi.sample.data.repository.datasource.DatastoreFactory.SignalDataStoreFactory;
import com.adnanbal.fxdedektifi.sample.domain.model.Signal;
import com.adnanbal.fxdedektifi.sample.domain.repository.SignalRepository;
import io.reactivex.Observable;
import java.util.List;
import javax.inject.Inject;

/**
 * {@link SignalRepository} for retrieving signal data.
 */
public class SignalDataRepository implements SignalRepository {

  private final SignalDataStoreFactory signalDataStoreFactory;
  private final SignalEntityDataMapper signalEntityDataMapper;

  /**
   * Constructs a {@link SignalRepository}.
   *
   * @param dataStoreFactory A factory to construct different data source implementations.
   * @param signalEntityDataMapper {@link SignalEntityDataMapper}.
   */
  @Inject
  SignalDataRepository(SignalDataStoreFactory dataStoreFactory,
      SignalEntityDataMapper signalEntityDataMapper) {
    this.signalDataStoreFactory = dataStoreFactory;
    this.signalEntityDataMapper = signalEntityDataMapper;
  }

  @Override
  public Observable<List<Signal>> getSignalsList() {
    //we always get all signals from the cloud
    final SignalDataStore signalDataStore = this.signalDataStoreFactory
        .createCloudDataStore();
    return signalDataStore.signalEntityList().map(this.signalEntityDataMapper::transform);
  }

  @Override
  public Observable<Signal> getOneSignal(int signalId) {
    final SignalDataStore signalDataStore = this.signalDataStoreFactory.create(signalId);
    return signalDataStore.signalEntityDetails(signalId)
        .map(this.signalEntityDataMapper::transform);
  }

  @Override
  public Observable<Boolean> openSignal(int positionId, String pair, double volume,
      boolean buy_sell, double openingPrice, boolean open, String status, String comment,
      double statusChangePrice, String term) {
    final SignalDataStore signalDataStore = this.signalDataStoreFactory.create(positionId);
    SignalEntity entity = this.signalEntityDataMapper
        .createSignalEntityObject(positionId, pair, volume, buy_sell, openingPrice, open, status,
            comment, statusChangePrice, term);

        return signalDataStore.addSignalEntity(entity);
  }
}
