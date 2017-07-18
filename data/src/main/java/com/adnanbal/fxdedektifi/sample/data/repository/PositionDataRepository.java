package com.adnanbal.fxdedektifi.sample.data.repository;

import com.adnanbal.fxdedektifi.sample.data.entity.PositionEntity;
import com.adnanbal.fxdedektifi.sample.data.entity.mapper.PositionEntityDataMapper;
import com.adnanbal.fxdedektifi.sample.data.repository.datasource.Datastore.PositionDataStore;
import com.adnanbal.fxdedektifi.sample.data.repository.datasource.DatastoreFactory.PositionDataStoreFactory;
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
    final PositionDataStore positionDataStore = this.positionDataStoreFactory
        .createCloudDataStore();
    return positionDataStore.positionEntityList().map(this.positionEntityDataMapper::transform);

  }

  @Override
  public Observable<Position> getOnePosition(int positionId) {
    final PositionDataStore positionDataStore = this.positionDataStoreFactory.create(positionId);
    return positionDataStore.positionEntityDetails(positionId)
        .map(this.positionEntityDataMapper::transform);
  }

  @Override
  public Observable<Boolean> closeOpenPosition(int positionId) {
    final PositionDataStore positionDataStore = this.positionDataStoreFactory.create(positionId);
    return positionDataStore.removePositionEntity(positionId);
  }


  @Override
  public Observable<Boolean> openPosition(int positionId, String pair, double volume,
      boolean buy_sell,
      double openingPrice, boolean open, String status, String comment) {
    final PositionDataStore positionDataStore = this.positionDataStoreFactory.create(positionId);
    PositionEntity entity = this.positionEntityDataMapper
        .createPositionEntityObject(positionId, pair, volume, buy_sell, openingPrice, open, status,
            comment);

    //.map(this.positionEntityDataMapper::transform);
    return positionDataStore
        .addPositionEntity(entity);
  }
}
