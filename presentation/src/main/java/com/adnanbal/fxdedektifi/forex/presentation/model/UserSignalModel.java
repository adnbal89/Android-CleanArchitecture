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

package com.adnanbal.fxdedektifi.forex.presentation.model;

import java.io.Serializable;
import java.util.Map;

public class UserSignalModel implements Serializable {


  public Map<String, Boolean> signals;

  public UserSignalModel() {
  }

  public UserSignalModel(Map<String, Boolean> signals) {
    this.signals = signals;
  }

  public Map<String, Boolean> getSignals() {
    return signals;
  }

  public void setSignals(Map<String, Boolean> signals) {
    this.signals = signals;
  }

}
