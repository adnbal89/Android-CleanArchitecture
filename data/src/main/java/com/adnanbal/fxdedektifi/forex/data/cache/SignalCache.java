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

import com.adnanbal.fxdedektifi.forex.data.entity.SignalEntity;
import io.reactivex.Observable;


/**
 * An interface representing a signal Cache.
 */
public interface SignalCache {

  /**
   * Gets an {@link Observable} which will emit a {@link SignalEntity}.
   *
   * @param signalId The signal id to retrieve data.
   */
  Observable<SignalEntity> get(final String signalId);

  /**
   * Puts and element into the cache.
   *
   * @param signalEntity Element to insert in the cache.
   */
  Observable<Boolean> put(SignalEntity signalEntity);

  /**
   * Checks if an element (Signal) exists in the cache.
   *
   * @param signalId The id used to look for inside the cache.
   * @return true if the element is cached, otherwise false.
   */
  boolean isCached(final String signalId);

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


  /**
   * deletes  element into the cache.
   *
   * @param positionId Element to remove from the cache.
   */
  Observable<Boolean> delete(final String positionId);
}
