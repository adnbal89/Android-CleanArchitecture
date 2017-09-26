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

package com.adnanbal.fxdedektifi.forex.data.repository.datasource.Datastore;

import com.adnanbal.fxdedektifi.forex.data.entity.PositionEntity;
import io.reactivex.Observable;
import java.util.List;

/**
 * Interface that represents a data store from where data is retrieved.
 */
public interface PositionHistoryDataStore {

  /**
   * Get an {@link Observable} which will emit a List of {@link PositionEntity}.
   */
  Observable<List<PositionEntity>> positionHistoryEntityList();

  /**
   * Get an {@link Observable} which will emit a {@link PositionEntity} by its id.
   *
   * @param positionId The id to retrieve position data.
   */
  Observable<PositionEntity> positionHistoryEntityDetails(final String positionId);
}
