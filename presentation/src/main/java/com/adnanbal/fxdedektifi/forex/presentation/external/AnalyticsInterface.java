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

package com.adnanbal.fxdedektifi.forex.presentation.external;

import android.os.Bundle;
import android.support.annotation.NonNull;

/**
 * Created by remychantenay on 28/03/2017.
 */

public interface AnalyticsInterface {

  String PARAM_USER_UID = "user_uid";
  String PARAM_MESSAGE = "message";

  String VIEW_ONBOARDING = "view_onboarding";
  String VIEW_LOGIN = "view_login";
  String VIEW_REGISTRATION = "view_register";
  String VIEW_BUCKET = "view_bucket";
  String VIEW_CREATE_TASK = "view_create_task";

  void trackLoginSuccess(@NonNull Bundle bundle);

  void trackLoginFailure(@NonNull Bundle bundle);

  void trackRegisterSuccess(@NonNull Bundle bundle);

  void trackRegisterFailure(@NonNull Bundle bundle);

  void trackGetTagListSuccess(@NonNull Bundle bundle);

  void trackGetTagListFailure(@NonNull Bundle bundle);

  void trackCreateTaskSuccess(@NonNull Bundle bundle);

  void trackCreateTaskFailure(@NonNull Bundle bundle);

  void trackGetBucketSuccess(@NonNull Bundle bundle);

  void trackGetBucketFailure(@NonNull Bundle bundle);

  void trackDeleteTaskSuccess(@NonNull Bundle bundle);

  void trackDeleteFailure(@NonNull Bundle bundle);

  void trackPageView(@NonNull String view);
}
