package com.adnanbal.fxdedektifi.sample.data.repository.datasource;

import com.adnanbal.fxdedektifi.sample.data.entity.PositionEntity;
import io.reactivex.Observable;
import java.util.List;

/**
 * Interface that represents a data store from where data is retrieved.
 */
public interface PositionDataStore {

  /**
   * Get an {@link Observable} which will emit a List of {@link PositionEntity}.
   */
  Observable<List<PositionEntity>> positionEntityList();

  /**
   * Get an {@link Observable} which will emit a {@link PositionEntity} by its id.
   *
   * @param positionId The id to retrieve position data.
   */
  Observable<PositionEntity> positionEntityDetails(final int positionId);
}
