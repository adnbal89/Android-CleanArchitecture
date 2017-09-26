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

package com.adnanbal.fxdedektifi.forex.presentation.firebase;

import android.os.Bundle;
import android.support.annotation.NonNull;
import com.adnanbal.fxdedektifi.forex.presentation.external.AnalyticsInterface;
import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * Created by remychantenay on 28/03/2017.
 */

public class FirebaseAnalyticsHelper implements AnalyticsInterface {

    private FirebaseAnalytics instance;

    public FirebaseAnalyticsHelper(FirebaseAnalytics instance) {
        this.instance = instance;
    }

    @Override
    public void trackLoginSuccess(@NonNull Bundle bundle) {
        instance.logEvent("login", bundle);
    }

    @Override
    public void trackLoginFailure(@NonNull Bundle bundle) {
        instance.logEvent("login_fail", bundle);
    }

    @Override
    public void trackRegisterSuccess(@NonNull Bundle bundle) {
        instance.logEvent("register", bundle);
    }

    @Override
    public void trackRegisterFailure(@NonNull Bundle bundle) {
        instance.logEvent("register_fail", bundle);
    }

    @Override
    public void trackGetTagListSuccess(@NonNull Bundle bundle) {
        instance.logEvent("get_taglist", bundle);
    }

    @Override
    public void trackGetTagListFailure(@NonNull Bundle bundle) {
        instance.logEvent("get_taglist_fail", bundle);
    }

    @Override
    public void trackCreateTaskSuccess(@NonNull Bundle bundle) {
        instance.logEvent("task_create", bundle);
    }

    @Override
    public void trackCreateTaskFailure(@NonNull Bundle bundle) {
        instance.logEvent("task_create_fail", bundle);
    }

    @Override
    public void trackGetBucketSuccess(@NonNull Bundle bundle) {
        instance.logEvent("get_bucket", bundle);
    }

    @Override
    public void trackGetBucketFailure(@NonNull Bundle bundle) {
        instance.logEvent("get_bucket_fail", bundle);
    }

    @Override
    public void trackDeleteTaskSuccess(@NonNull Bundle bundle) {
        instance.logEvent("task_delete", bundle);
    }

    @Override
    public void trackDeleteFailure(@NonNull Bundle bundle) {
        instance.logEvent("task_delete_fail", bundle);
    }

    @Override
    public void trackPageView(@NonNull String view) {
        instance.logEvent(view, null);
    }
}
