package com.adnanbal.fxdedektifi.forex.data.entity.mapper;

import com.adnanbal.fxdedektifi.forex.data.entity.PositionEntity;
import com.adnanbal.fxdedektifi.forex.domain.model.Position;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Mapper class used to transform {@link PositionEntity} (in the data layer) to {@link Position} in
 * the domain layer.
 */
@Singleton
public class PositionEntityDataMapper {

  /**
   * initial price value
   */
  public static final double INITIAL_DOUBLE_VALUE = 0.0;

  @Inject
  PositionEntityDataMapper() {
  }

  /**
   * Transform a {@link PositionEntity} into an {@link Position}.
   *
   * @param positionEntity Object to be transformed.
   * @return {@link Position} if valid {@link PositionEntity} otherwise null.
   */

  public Position transform(PositionEntity positionEntity) {
    Position position = null;

    if (positionEntity != null) {
      position = new Position(positionEntity.getId());
      position.setPair(positionEntity.getPair());
      position.setBuy_sell(positionEntity.isBuy_sell());
      position.setVolume(positionEntity.getVolume());
      position.setProfit(positionEntity.getProfit());
      position.setOpeningPrice(positionEntity.getOpeningPrice());
      position.setClosingPrice(positionEntity.getClosingPrice());
      position.setOpen(positionEntity.isOpen());
      position.setStatus(positionEntity.getStatus());
      position.setComment(positionEntity.getComment());
      position.setDate(positionEntity.getDate());
    }
    return position;
  }




  public PositionEntity createPositionEntityObject(String positionId, String pair, double volume,
      boolean buy_sell,
      double openingPrice, boolean open, String status, String comment, Date date) {

    PositionEntity positionEntity;
    positionEntity = new PositionEntity();

    positionEntity.setId(positionId);
    positionEntity.setPair(pair);
    positionEntity.setVolume(volume);
    positionEntity.setBuy_sell(buy_sell);
    positionEntity.setProfit(INITIAL_DOUBLE_VALUE);
    positionEntity.setOpeningPrice(openingPrice);
    positionEntity.setClosingPrice(INITIAL_DOUBLE_VALUE);
    positionEntity.setOpen(open);
    positionEntity.setStatus(status);
    positionEntity.setComment(comment);
    positionEntity.setDate(date);



    return positionEntity;
  }

  public Boolean forResult(String positionId) {
    return true;
  }

  /**
   * Transform a List of {@link PositionEntity} into a Collection of {@link Position}.
   *
   * @param positionEntityCollection Object Collection to be transformed.
   * @return {@link Position} if valid {@link PositionEntity} otherwise null.
   */
  public List<Position> transform(Collection<PositionEntity> positionEntityCollection) {
    final List<Position> positionList = new ArrayList<>(20);
    for (PositionEntity positionEntity : positionEntityCollection) {
      final Position position = transform(positionEntity);
      if (position != null) {
        positionList.add(position);
      }
    }
    return positionList;
  }

}
