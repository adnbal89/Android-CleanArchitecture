package com.adnanbal.fxdedektifi.sample.data.cache;

import com.adnanbal.fxdedektifi.sample.data.entity.PositionEntity;
import io.reactivex.Observable;

/**
 * An interface representing a position Cache.
 */
public interface PositionCache {

  /**
   * Gets an {@link Observable} which will emit a {@link PositionEntity}.
   *
   * @param positionId The position id to retrieve data.
   */
  Observable<PositionEntity> get(final int positionId);

  /**
   * Puts and element into the cache.
   *
   * @param positionEntity Element to insert in the cache.
   */
  Observable<Boolean> put(PositionEntity positionEntity);

  /**
   * Puts and element into the cache.
   *
   * @param positionId Element to remove from the cache.
   */
  Observable<Boolean> delete(final int positionId);

  /**
   * Checks if an element (Position) exists in the cache.
   *
   * @param positionId The id used to look for inside the cache.
   * @return true if the element is cached, otherwise false.
   */
  boolean isCached(final int positionId);

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
