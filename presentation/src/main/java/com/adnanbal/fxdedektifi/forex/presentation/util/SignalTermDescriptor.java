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

package com.adnanbal.fxdedektifi.forex.presentation.util;

import android.support.annotation.StringDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Signal Term descriptor class(enum) for using signal's current Term.
 */
public class SignalTermDescriptor {

  public static final String SHORT = "short";
  public static final String MID = "mid";
  public static final String LONG = "long";


  public final String signalTerm;


  // Describes when the annotation will be discarded
  @Retention(RetentionPolicy.SOURCE)
  // Enumerate valid values for this interface
  @StringDef({SHORT, MID, LONG})
  // Create an interface for validating int types
  public @interface SignalTermDef {

  }

  // Mark the argument as restricted to these enumerated types
  public SignalTermDescriptor(@SignalTermDef String signalTerm) {
    this.signalTerm = signalTerm;
  }
}
