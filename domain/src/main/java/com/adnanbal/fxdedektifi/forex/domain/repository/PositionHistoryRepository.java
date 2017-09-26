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

package com.adnanbal.fxdedektifi.forex.domain.repository;

import com.adnanbal.fxdedektifi.forex.domain.model.Position;
import io.reactivex.Observable;
import java.util.List;

public interface PositionHistoryRepository {

  /**
   * Get an {@link Observable} which will emit a List of closed {@link Position}.
   */
  Observable<List<Position>> getClosedPositionsList();


  /**
   * Get an {@link Observable} which will emit a closed {@link Position}.
   *
   * @param positionId The position id used to retrieve position data.
   */
  Observable<Position> getOneCLosedPosition(final String positionId);
}
