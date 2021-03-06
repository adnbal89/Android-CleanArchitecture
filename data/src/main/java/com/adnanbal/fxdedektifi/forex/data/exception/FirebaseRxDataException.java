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

package com.adnanbal.fxdedektifi.forex.data.exception;

import android.support.annotation.NonNull;
import com.google.firebase.database.DatabaseError;

public class FirebaseRxDataException extends Exception {

  protected DatabaseError error;

  public FirebaseRxDataException(@NonNull DatabaseError error) {
    this.error = error;
  }

  public DatabaseError getError() {
    return error;
  }

  @Override
  public String toString() {
    return "RxFirebaseDataException{" +
        "error=" + error +
        '}';
  }
}