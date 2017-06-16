package com.adnanbal.fxdedektifi.sample.data.entity.mapper;

import com.adnanbal.fxdedektifi.sample.data.entity.PositionEntity;
import com.adnanbal.fxdedektifi.sample.domain.model.Position;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Mapper class used to transform {@link PositionEntity} (in the data layer) to {@link Position} in
 * the domain layer.
 */
@Singleton
public class PositionEntityDataMapper {

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
      position = new Position(positionEntity.getPositionId());
      position.setPair(positionEntity.getPair());
      position.setBuy_sell(positionEntity.isBuy_sell());
      position.setVolume(positionEntity.getVolume());
      position.setProfit(positionEntity.getProfit());
    }
    return position;
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
