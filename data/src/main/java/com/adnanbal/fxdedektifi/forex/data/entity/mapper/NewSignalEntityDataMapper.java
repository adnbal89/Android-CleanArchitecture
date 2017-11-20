/*
 * *
 *  * Copyright (C) 2017 Adnan BAL Open Source Project
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.adnanbal.fxdedektifi.forex.data.entity.mapper;

import com.adnanbal.fxdedektifi.forex.data.entity.NewPositionEntity;
import com.adnanbal.fxdedektifi.forex.domain.model.NewPosition;
import java.util.Map;
import javax.inject.Inject;

/**
 * Mapper class used to transform {@link NewPositionEntity} (in the data layer) to {@link
 * NewPosition} in the domain layer.
 */
public class NewSignalEntityDataMapper {

  public static final double INITIAL_DOUBLE_VALUE = 0.0;

  @Inject
  NewSignalEntityDataMapper() {
  }

  /**
   * Transform a {@link NewPositionEntity} into an {@link NewPosition}.
   *
   * @param newPositionEntity Object to be transformed.
   * @return {@link NewPosition} if valid {@link NewPositionEntity} otherwise null.
   */
  public NewPosition transform(NewPositionEntity newPositionEntity) {
    NewPosition newPosition = null;
    if (newPositionEntity != null) {
      newPosition = new NewPosition(newPositionEntity.getId());
      newPosition.setPair(newPositionEntity.getPair());
      newPosition.setBuy_sell(newPositionEntity.isBuy_sell());
      newPosition.setVolume(newPositionEntity.getVolume());
      newPosition.setProfit(newPositionEntity.getProfit());
      newPosition.setStatus(newPositionEntity.getStatus());
      newPosition.setOpeningPrice(newPositionEntity.getOpeningPrice());
      newPosition.setClosingPrice(newPositionEntity.getClosingPrice());
      newPosition.setOpen(newPositionEntity.isOpen());
      newPosition.setComment(newPositionEntity.getComment());
      newPosition.setDate(newPositionEntity.getDate());
      newPosition.setTake_profit_price(newPositionEntity.getTake_profit_price());
      newPosition.setStop_loss_price(newPositionEntity.getStop_loss_price());
      newPosition.setHitStopLoss(newPositionEntity.isHitStopLoss());
      newPosition.setHitTakeProfit(newPositionEntity.isHitTakeProfit());

    }
    return newPosition;
  }

  public NewPositionEntity createNewPositionEntityObject(String positionId, String pair,
      double volume,
      boolean buy_sell,
      double openingPrice, boolean open, String status, String comment,
      Map<String, String> date, Double take_profit_price, Double stop_loss_price,
      double closingPrice, boolean hitStopLoss,
      boolean hitTakeProfit) {

    NewPositionEntity newPositionEntity;
    newPositionEntity = new NewPositionEntity();

    newPositionEntity.setId(positionId);
    newPositionEntity.setPair(pair);
    newPositionEntity.setVolume(volume);
    newPositionEntity.setBuy_sell(buy_sell);
    newPositionEntity.setProfit(INITIAL_DOUBLE_VALUE);
    newPositionEntity.setOpeningPrice(openingPrice);
    newPositionEntity.setClosingPrice(closingPrice);
    newPositionEntity.setOpen(open);
    newPositionEntity.setStatus(status);
    newPositionEntity.setComment(comment);
    newPositionEntity.setDate(date);
    newPositionEntity.setTake_profit_price(take_profit_price);
    newPositionEntity.setStop_loss_price(stop_loss_price);
    newPositionEntity.setHitStopLoss(hitStopLoss);
    newPositionEntity.setHitTakeProfit(hitTakeProfit);

    return newPositionEntity;
  }
}
