package com.adnanbal.fxdedektifi.sample.data.repository;

import com.adnanbal.fxdedektifi.sample.data.entity.mapper.PositionEntityDataMapper;
import com.adnanbal.fxdedektifi.sample.data.repository.datasource.PositionDataStore;
import com.adnanbal.fxdedektifi.sample.data.repository.datasource.PositionDataStoreFactory;
import com.adnanbal.fxdedektifi.sample.domain.model.Position;
import com.adnanbal.fxdedektifi.sample.domain.repository.PositionRepository;
import io.reactivex.Observable;
import java.util.List;
import javax.inject.Inject;

/**
 * {@link PositionRepository} for retrieving position data.
 */
public class PositionDataRepository implements PositionRepository {

  private final PositionDataStoreFactory positionDataStoreFactory;
  private final PositionEntityDataMapper positionEntityDataMapper;

  /**
   * Constructs a {@link PositionRepository}.
   *
   * @param dataStoreFactory A factory to construct different data source implementations.
   * @param positionEntityDataMapper {@link PositionEntityDataMapper}.
   */
  @Inject
  PositionDataRepository(PositionDataStoreFactory dataStoreFactory,
      PositionEntityDataMapper positionEntityDataMapper) {
    this.positionDataStoreFactory = dataStoreFactory;
    this.positionEntityDataMapper = positionEntityDataMapper;
  }

  @Override
  public Observable<List<Position>> getPositionsList() {
    //we always get all positions from the cloud
    final PositionDataStore positionDataStore = this.positionDataStoreFactory.createCloudDataStore();
    return positionDataStore.positionEntityList().map(this.positionEntityDataMapper::transform);

  }

  @Override
  public Observable<Position> getOnePosition(int positionId) {
    final PositionDataStore positionDataStore = this.positionDataStoreFactory.create(positionId);
    return positionDataStore.positionEntityDetails(positionId).map(this.positionEntityDataMapper::transform);

  }
}
