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

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;

import com.adnanbal.fxdedektifi.sample.data.entity.PositionEntity;
import com.adnanbal.fxdedektifi.sample.data.entity.mapper.PositionEntityDataMapper;
import com.adnanbal.fxdedektifi.sample.data.repository.datasource.Datastore.PositionHistoryDataStore;
import com.adnanbal.fxdedektifi.sample.data.repository.datasource.DatastoreFactory.PositionHistoryDataStoreFactory;
import com.adnanbal.fxdedektifi.sample.domain.model.Position;
import io.reactivex.Observable;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PositionHistoryDataRepositoryTest {

  private static final int FAKE_POSITION_ID = 123;

  private PositionHistoryDataRepository positionHistoryDataRepository;

  @Mock
  private PositionHistoryDataStoreFactory mockPositionHistoryDataStoreFactory;
  @Mock
  private PositionEntityDataMapper mockPositionEntityDataMapper;
  @Mock
  private PositionHistoryDataStore mockPositionHistoryDataStore;
  @Mock
  private PositionEntity mockPositionEntity;
  @Mock
  private Position mockPosition;

  @Before
  public void setUp() {
    positionHistoryDataRepository = new PositionHistoryDataRepository(
        mockPositionHistoryDataStoreFactory, mockPositionEntityDataMapper);
    given(mockPositionHistoryDataStoreFactory.create(anyInt()))
        .willReturn(mockPositionHistoryDataStore);
    given(mockPositionHistoryDataStoreFactory.createCloudDataStore())
        .willReturn(mockPositionHistoryDataStore);
  }

  @Test
  public void testGetPositionsHappyCase() {
    List<PositionEntity> positionsList = new ArrayList<>();
    positionsList.add(new PositionEntity());
    given(mockPositionHistoryDataStore.positionHistoryEntityList())
        .willReturn(Observable.just(positionsList));

    positionHistoryDataRepository.getClosedPositionsList();

    verify(mockPositionHistoryDataStoreFactory).createCloudDataStore();
    verify(mockPositionHistoryDataStore).positionHistoryEntityList();
  }

  @Test
  public void testGetPositionHappyCase() {
    PositionEntity positionEntity = new PositionEntity();
    given(mockPositionHistoryDataStore.positionHistoryEntityDetails(FAKE_POSITION_ID))
        .willReturn(Observable.just(positionEntity));
    positionHistoryDataRepository.getOneCLosedPosition(FAKE_POSITION_ID);

    verify(mockPositionHistoryDataStoreFactory).create(FAKE_POSITION_ID);
    verify(mockPositionHistoryDataStore).positionHistoryEntityDetails(FAKE_POSITION_ID);
  }
}
