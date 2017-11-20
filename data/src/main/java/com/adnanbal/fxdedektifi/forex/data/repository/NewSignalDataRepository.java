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

import com.adnanbal.fxdedektifi.forex.data.entity.NewPositionEntity;
import com.adnanbal.fxdedektifi.forex.data.entity.mapper.NewSignalEntityDataMapper;
import com.adnanbal.fxdedektifi.forex.data.repository.datasource.Datastore.NewSignalDataStore;
import com.adnanbal.fxdedektifi.forex.data.repository.datasource.DatastoreFactory.NewSignalDataStoreFactory;
import com.adnanbal.fxdedektifi.forex.domain.repository.NewSignalRepository;
import io.reactivex.Observable;
import java.util.Map;
import javax.inject.Inject;


/**
 * {@link NewSignalRepository} for creating signal data to firabase database.
 */
public class NewSignalDataRepository implements NewSignalRepository {

  private final NewSignalDataStoreFactory newSignalDataStoreFactory;
  private final NewSignalEntityDataMapper newSignalEntityDataMapper;

  /**
   * Constructs a {@link NewSignalRepository}.
   *
   * @param newSignalDataStoreFactory A factory to construct different data source implementations.
   * @param newSignalEntityDataMapper {@link NewSignalEntityDataMapper}.
   */
  @Inject
  NewSignalDataRepository(NewSignalDataStoreFactory newSignalDataStoreFactory,
      NewSignalEntityDataMapper newSignalEntityDataMapper) {
    this.newSignalDataStoreFactory = newSignalDataStoreFactory;
    this.newSignalEntityDataMapper = newSignalEntityDataMapper;
  }


  @Override
  public Observable<Boolean> createNewSignal(String positionId, String pair, double volume,
      boolean buy_sell, double openingPrice, boolean open, String status, String comment,
      Map<String, String> date, String operationOnSignal, Double take_profit_price,
      Double stop_loss_price, double closingPrice, boolean hitStopLoss,
      boolean hitTakeProfit) {
    final NewSignalDataStore newSignalDataStore = this.newSignalDataStoreFactory.create();
    NewPositionEntity entity = this.newSignalEntityDataMapper
        .createNewPositionEntityObject(positionId, pair, volume,
            buy_sell, openingPrice, open, status, comment, date, take_profit_price,
            stop_loss_price, closingPrice, hitStopLoss, hitTakeProfit);

    return newSignalDataStore.createNewSignalEntity(entity, operationOnSignal);
  }
}
