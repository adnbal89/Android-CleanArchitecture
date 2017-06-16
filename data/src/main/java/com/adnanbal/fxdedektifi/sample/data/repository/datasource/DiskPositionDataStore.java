package com.adnanbal.fxdedektifi.sample.data.repository.datasource;

import com.adnanbal.fxdedektifi.sample.data.cache.PositionCache;
import com.adnanbal.fxdedektifi.sample.data.entity.PositionEntity;
import io.reactivex.Observable;
import java.util.List;

/**
 * {@link PositionDataStore} implementation based on file system data store.
 */
public class DiskPositionDataStore implements PositionDataStore {

  private final PositionCache positionCache;

  /**
   * Construct a {@link PositionDataStore} based file system data store.
   *
   * @param positionCache A {@link PositionCache} to cache data retrieved from the api.
   */
  DiskPositionDataStore(PositionCache positionCache) {
    this.positionCache = positionCache;
  }

  @Override
  public Observable<List<PositionEntity>> positionEntityList() {
    //TODO: implement simple cache for storing/retrieving collections of positions.
    throw new UnsupportedOperationException("Operation is not available!!!");
  }

  @Override
  public Observable<PositionEntity> positionEntityDetails(final int positionId) {
    return this.positionCache.get(positionId);
  }
}
