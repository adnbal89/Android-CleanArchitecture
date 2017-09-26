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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * DateFormatter class, "dd.MM.yyyy hh:mm:ss" format with default {@link Locale}.getDefault();
 *
 *
 */
public class DateFormatter {

  Date date;

  public DateFormatter() {

  }

  public DateFormatter(Date date) {
    this.date = date;
  }

  public String format(Date date) {
    SimpleDateFormat dt = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss", Locale.getDefault());
    return dt.format(date);
  }
}
