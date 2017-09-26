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

package com.adnanbal.fxdedektifi.forex.data.cache;

import com.adnanbal.fxdedektifi.forex.data.entity.PositionEntity;
import io.reactivex.Observable;

/**
 * An interface representing a position Cache.
 */
public interface PositionHistoryCache {

  /**
   * Gets an {@link Observable} which will emit a {@link PositionEntity}.
   *
   * @param positionId The position id to retrieve data.
   */
  Observable<PositionEntity> get(final String positionId);

  /**
   * Puts and element into the cache.
   *
   * @param positionEntity Element to insert in the cache.
   */
  void put(PositionEntity positionEntity);

  /**
   * Checks if an element (Position) exists in the cache.
   *
   * @param positionId The id used to look for inside the cache.
   * @return true if the element is cached, otherwise false.
   */
  boolean isCached(final String positionId);

  /**
   * Checks if the cache is expired.
   *
   * @return true, the cache is expired, otherwise false.
   */
  boolean isExpired();

  /**
   * Evict all elements of the cache.
   */
  void evictAll();
}
