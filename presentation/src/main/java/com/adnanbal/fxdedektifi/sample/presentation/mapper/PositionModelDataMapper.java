package com.adnanbal.fxdedektifi.sample.presentation.mapper;

import com.adnanbal.fxdedektifi.sample.domain.model.Position;
import com.adnanbal.fxdedektifi.sample.presentation.internal.di.PerActivity;
import com.adnanbal.fxdedektifi.sample.presentation.model.PositionModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import javax.inject.Inject;

/**
 * Mapper class used to transform {@link Position} (in
 * the domain layer) to {@link PositionModel} in
 * the presentation layer.
 */
@PerActivity
public class PositionModelDataMapper {

  @Inject
  public PositionModelDataMapper() {
  }

  /**
   * Transform a {@link Position} into an {@link PositionModel}.
   *
   * @param position Object to be transformed.
   * @return {@link PositionModel}.
   */
  public PositionModel transform(Position position) {
    if (position == null) {
      throw new IllegalArgumentException("Cannot transform a null value");
    }
    final PositionModel positionModel = new PositionModel(position.getPositionId());
    positionModel.setPair(position.getPair());
    positionModel.setBuy_sell(position.isBuy_sell());
    positionModel.setVolume(position.getVolume());
    positionModel.setProfit(position.getProfit());
    positionModel.setClosingPrice(position.getClosingPrice());
    positionModel.setOpeningPrice(position.getOpeningPrice());
    positionModel.setOpen(position.isOpen());
    positionModel.setComment(position.getComment());

    return positionModel;
  }

  /**
   * Transform a Collection of {@link Position} into a Collection of {@link PositionModel}.
   *
   * @param positionsCollection Objects to be transformed.
   * @return List of {@link PositionModel}.
   */
  public Collection<PositionModel> transform(Collection<Position> positionsCollection) {
    Collection<PositionModel> positionsModelsCollection;

    if (positionsCollection != null && !positionsCollection.isEmpty()) {
      positionsModelsCollection = new ArrayList<>();
      for (Position position : positionsCollection) {
        positionsModelsCollection.add(transform(position));
      }
    } else {
      positionsModelsCollection = Collections.emptyList();
    }

    return positionsModelsCollection;
  }
}
