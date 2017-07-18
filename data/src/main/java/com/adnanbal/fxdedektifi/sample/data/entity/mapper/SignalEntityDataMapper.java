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

package com.adnanbal.fxdedektifi.sample.data.entity.mapper;

import com.adnanbal.fxdedektifi.sample.data.entity.SignalEntity;
import com.adnanbal.fxdedektifi.sample.domain.model.Signal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;

/**
 * Mapper class used to transform {@link SignalEntity} (in the data layer) to {@link Signal} in
 * the domain layer.
 */
public class SignalEntityDataMapper {
  public static final double INITIAL_DOUBLE_VALUE = 0.0;

  @Inject
  SignalEntityDataMapper() {
  }

  /**
   * Transform a {@link SignalEntity} into an {@link Signal}.
   *
   * @param signalEntity Object to be transformed.
   * @return {@link Signal} if valid {@link SignalEntity} otherwise null.
   */
  public Signal transform(SignalEntity signalEntity) {
    Signal signal = null;
    if (signalEntity != null) {
      signal = new Signal(signalEntity.getPositionId());
      signal.setPair(signalEntity.getPair());
      signal.setBuy_sell(signalEntity.isBuy_sell());
      signal.setVolume(signalEntity.getVolume());
      signal.setProfit(signalEntity.getProfit());
      signal.setStatusChangePrice(signalEntity.getStatusChangePrice());
      signal.setStatus(signalEntity.getStatus());
      signal.setOpeningPrice(signalEntity.getOpeningPrice());
      signal.setTerm(signalEntity.getTerm());
      signal.setClosingPrice(signalEntity.getClosingPrice());
      signal.setOpen(signalEntity.isOpen());
      signal.setComment(signalEntity.getComment());
    }
    return signal;
  }

  /**
   * Transform a List of {@link SignalEntity} into a Collection of {@link Signal}.
   *
   * @param signalEntityCollection Object Collection to be transformed.
   * @return {@link Signal} if valid {@link SignalEntity} otherwise null.
   */
  public List<Signal> transform(Collection<SignalEntity> signalEntityCollection) {
    final List<Signal> signalList = new ArrayList<>(20);
    for (SignalEntity signalEntity : signalEntityCollection) {
      final Signal signal = transform(signalEntity);
      if (signal != null) {
        signalList.add(signal);
      }
    }
    return signalList;
  }


  public SignalEntity createSignalEntityObject(int positionId, String pair, double volume,
      boolean buy_sell,
      double openingPrice, boolean open, String status, String comment, double statusChangePrice,String term ) {

    SignalEntity signalEntity;
    signalEntity = new SignalEntity();

    signalEntity.setPositionId(positionId);
    signalEntity.setPair(pair);
    signalEntity.setVolume(volume);
    signalEntity.setBuy_sell(buy_sell);
    signalEntity.setProfit(INITIAL_DOUBLE_VALUE);
    signalEntity.setOpeningPrice(openingPrice);
    signalEntity.setClosingPrice(INITIAL_DOUBLE_VALUE);
    signalEntity.setOpen(open);
    signalEntity.setStatus(status);
    signalEntity.setComment(comment);
    signalEntity.setStatusChangePrice(statusChangePrice);
    signalEntity.setTerm(term);

    return signalEntity;
  }

}
