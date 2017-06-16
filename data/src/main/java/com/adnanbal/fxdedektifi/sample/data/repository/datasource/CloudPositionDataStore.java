package com.adnanbal.fxdedektifi.sample.data.repository.datasource;


import com.adnanbal.fxdedektifi.sample.data.cache.PositionCache;
import com.adnanbal.fxdedektifi.sample.data.entity.PositionEntity;
import com.adnanbal.fxdedektifi.sample.data.net.RestApiPosition;
import io.reactivex.Observable;
import java.util.List;

/**
 * {@link PositionDataStore} implementation based on connections to the api (Cloud).
 */
public class CloudPositionDataStore implements PositionDataStore {

  private final RestApiPosition restApiPosition;
  private final PositionCache positionCache;


  /**
   * Construct a {@link PositionDataStore} based on connections to the api (Cloud).
   *
   * @param restApiPosition The {@link RestApiPosition} implementation to use.
   * @param positionCache A {@link PositionCache} to cache data retrieved from the api.
   */
  public CloudPositionDataStore(RestApiPosition restApiPosition,
      PositionCache positionCache) {
    this.restApiPosition = restApiPosition;
    this.positionCache = positionCache;
  }

  @Override
  public Observable<List<PositionEntity>> positionEntityList() {
    return this.restApiPosition.positionEntityList();
  }

  @Override
  public Observable<PositionEntity> positionEntityDetails(final int positionId) {
    return this.restApiPosition.positionEntityById(positionId)
        .doOnNext(CloudPositionDataStore.this.positionCache::put);
  }
}
