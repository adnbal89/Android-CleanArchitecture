package com.adnanbal.fxdedektifi.forex.data.repository;

import com.adnanbal.fxdedektifi.forex.data.entity.PositionEntity;
import com.adnanbal.fxdedektifi.forex.data.entity.mapper.PositionEntityDataMapper;
import com.adnanbal.fxdedektifi.forex.data.repository.datasource.Datastore.PositionDataStore;
import com.adnanbal.fxdedektifi.forex.data.repository.datasource.DatastoreFactory.PositionDataStoreFactory;
import com.adnanbal.fxdedektifi.forex.domain.model.Position;
import com.adnanbal.fxdedektifi.forex.domain.repository.PositionRepository;
import io.reactivex.Observable;
import java.util.Date;
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
  public Observable<Position> getOnePosition(String positionId) {
    final PositionDataStore positionDataStore = this.positionDataStoreFactory.create(positionId);
    return positionDataStore.positionEntityDetails(positionId)
        .map(this.positionEntityDataMapper::transform);
  }

  @Override
  public Observable<Boolean> closeOpenPosition(String positionId) {
    final PositionDataStore positionDataStore = this.positionDataStoreFactory.create(positionId);
    return positionDataStore.removePositionEntity(positionId);
  }


  @Override
  public Observable<Boolean> openPosition(String positionId, String pair, double volume,
      boolean buy_sell,
      double openingPrice, boolean open, String status, String comment, Date date,
      Double take_profit_price, Double stop_loss_price, boolean hitStopLoss,
      boolean hitTakeProfit) {
    final PositionDataStore positionDataStore = this.positionDataStoreFactory.create(positionId);
    PositionEntity entity = this.positionEntityDataMapper
        .createPositionEntityObject(positionId, pair, volume, buy_sell, openingPrice, open, status,
            comment, date, take_profit_price, stop_loss_price, hitStopLoss, hitTakeProfit);

    //.map(this.positionEntityDataMapper::transform);
    return positionDataStore
        .addPositionEntity(entity);
  }
}
