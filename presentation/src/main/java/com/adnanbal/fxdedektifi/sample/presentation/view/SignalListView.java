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

package com.adnanbal.fxdedektifi.sample.presentation.view;

import com.adnanbal.fxdedektifi.sample.presentation.model.PositionModel;
import com.adnanbal.fxdedektifi.sample.presentation.model.SignalModel;
import java.util.Collection;

/**
 * Interface representing a View in a model view presenter (MVP) pattern. In this case is used as a
 * view representing a list of {@link SignalModel}.
 */
public interface SignalListView extends LoadDataView {

  /**
   * Render a signal list in the UI.
   *
   * @param signalModelCollection The collection of {@link SignalModel} that will be shown.
   */
  void renderSignalList(Collection<SignalModel> signalModelCollection);

  /**
   * View a {@link SignalModel} profile/details.
   *
   * @param signalModel The signal that will be shown.
   */
  void viewSignal(SignalModel signalModel);


  /**
   * Confirmed to open a {@link PositionModel}.
   *
   * @param signalModel The position that will be opened.
   */
  void openSignalConfirmedOnline(SignalModel signalModel);

}