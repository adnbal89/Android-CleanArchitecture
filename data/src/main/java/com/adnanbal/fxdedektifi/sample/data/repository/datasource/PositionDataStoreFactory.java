package com.adnanbal.fxdedektifi.sample.data.repository.datasource;

import android.content.Context;
import android.support.annotation.NonNull;
import com.adnanbal.fxdedektifi.sample.data.cache.PositionCache;
import com.adnanbal.fxdedektifi.sample.data.entity.mapper.PositionEntityJsonMapper;
import com.adnanbal.fxdedektifi.sample.data.net.RestApiPosition;
import com.adnanbal.fxdedektifi.sample.data.net.RestApiPositionImpl;
import javax.inject.Inject;

/**
 * Factory that creates different implementations of {@link PositionDataStore}.
 */
public class PositionDataStoreFactory {

  private final Context context;
  private final PositionCache positionCache;

  @Inject
  PositionDataStoreFactory(@NonNull Context context, @NonNull PositionCache positionCache) {
    this.context = context.getApplicationContext();
    this.positionCache = positionCache;
  }

  /**
   * Create {@link PositionDataStore} from a positionid.
   */
  public PositionDataStore create(int positionId) {
    PositionDataStore PositionDataStore;

    if (!this.positionCache.isExpired() && this.positionCache.isCached(positionId)) {
      PositionDataStore = new DiskPositionDataStore(this.positionCache);
    } else {
      PositionDataStore = createCloudDataStore();
    }

    return PositionDataStore;
  }

  /**
   * Create {@link PositionDataStore} to retrieve data from the Cloud.
   */
  public PositionDataStore createCloudDataStore() {
    final PositionEntityJsonMapper positionEntityJsonMapper = new PositionEntityJsonMapper();
    final RestApiPosition restApi = new RestApiPositionImpl(this.context, positionEntityJsonMapper);

    return new CloudPositionDataStore(restApi, this.positionCache);
  }
}
