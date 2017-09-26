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

package com.adnanbal.fxdedektifi.forex.presentation.mapper;

import com.adnanbal.fxdedektifi.forex.domain.model.Signal;
import com.adnanbal.fxdedektifi.forex.presentation.internal.di.PerActivity;
import com.adnanbal.fxdedektifi.forex.presentation.model.SignalModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import javax.inject.Inject;

/**
 * Mapper class used to transform {@link Signal} (in the domain layer) to {@link
 * SignalModel} in the presentation layer.
 */
@PerActivity
public class SignalModelDataMapper {

  @Inject
  public SignalModelDataMapper() {
  }

  /**
   * Transform a {@link Signal} into an {@link SignalModel}.
   *
   * @param signal Object to be transformed.
   * @return {@link SignalModel}.
   */
  public SignalModel transform(Signal signal) {
    if (signal == null) {
      throw new IllegalArgumentException("Cannot transform a null value");
    }
    final SignalModel signalModel = new SignalModel(signal.getPositionId());
    signalModel.setPair(signal.getPair());
    signalModel.setBuy_sell(signal.isBuy_sell());
    signalModel.setVolume(signal.getVolume());
    signalModel.setProfit(signal.getProfit());
    signalModel.setStatus(signal.getStatus());
    signalModel.setOpeningPrice(signal.getOpeningPrice());
    signalModel.setTerm(signal.getTerm());
    signalModel.setClosingPrice(signal.getClosingPrice());
    signalModel.setComment(signal.getComment());
    signalModel.setOpen(signal.isOpen());
    signalModel.setStatusChangePrice(signal.getStatusChangePrice());
    signalModel.setDate(signal.getDate());
    signalModel.setUsers(signal.getUsers());
    signalModel.setChangedFields(signal.getChangedFields());
    return signalModel;
  }

  /**
   * Transform a Collection of {@link Signal} into a Collection of {@link SignalModel}.
   *
   * @param signalsCollection Objects to be transformed.
   * @return List of {@link SignalModel}.
   */
  public Collection<SignalModel> transform(Collection<Signal> signalsCollection) {
    Collection<SignalModel> signalsModelsCollection;

    if (signalsCollection != null && !signalsCollection.isEmpty()) {
      signalsModelsCollection = new ArrayList<>();
      for (Signal signal : signalsCollection) {
        signalsModelsCollection.add(transform(signal));
      }
    } else {
      signalsModelsCollection = Collections.emptyList();
    }

    return signalsModelsCollection;
  }

}
