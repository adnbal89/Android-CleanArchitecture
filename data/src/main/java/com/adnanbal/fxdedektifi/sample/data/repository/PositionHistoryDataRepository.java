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

import com.adnanbal.fxdedektifi.sample.data.entity.mapper.PositionEntityDataMapper;
import com.adnanbal.fxdedektifi.sample.data.repository.datasource.Datastore.PositionHistoryDataStore;
import com.adnanbal.fxdedektifi.sample.data.repository.datasource.DatastoreFactory.PositionHistoryDataStoreFactory;
import com.adnanbal.fxdedektifi.sample.domain.model.Position;
import com.adnanbal.fxdedektifi.sample.domain.repository.PositionHistoryRepository;
import io.reactivex.Observable;
import java.util.List;
import javax.inject.Inject;

/**
 * {@link PositionHistoryRepository} for retrieving position data.
 */
public class PositionHistoryDataRepository implements PositionHistoryRepository {

  private final PositionHistoryDataStoreFactory positionHistoryDataStoreFactory;
  private final PositionEntityDataMapper positionEntityDataMapper;

  /**
   * Constructs a {@link PositionHistoryRepository}.
   *
   * @param dataStoreFactory A factory to construct different data source implementations.
   * @param positionEntityDataMapper {@link PositionEntityDataMapper}.
   */
  @Inject
  PositionHistoryDataRepository(PositionHistoryDataStoreFactory dataStoreFactory,
      PositionEntityDataMapper positionEntityDataMapper) {
    this.positionHistoryDataStoreFactory = dataStoreFactory;
    this.positionEntityDataMapper = positionEntityDataMapper;
  }


  @Override
  public Observable<List<Position>> getClosedPositionsList() {
    //we always get all positions from the cloud
    final PositionHistoryDataStore positionDataStore = this.positionHistoryDataStoreFactory
        .createCloudDataStore();
    return positionDataStore.positionHistoryEntityList()
        .map(this.positionEntityDataMapper::transform);
  }

  @Override
  public Observable<Position> getOneCLosedPosition(int positionId) {
    final PositionHistoryDataStore positionDataStore = this.positionHistoryDataStoreFactory
        .create(positionId);
    return positionDataStore.positionHistoryEntityDetails(positionId)
        .map(this.positionEntityDataMapper::transform);
  }
}
