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

package com.adnanbal.fxdedektifi.forex.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.adnanbal.fxdedektifi.forex.presentation.R;
import com.adnanbal.fxdedektifi.forex.presentation.internal.di.HasComponent;
import com.adnanbal.fxdedektifi.forex.presentation.internal.di.components.DaggerNewPositionComponent;
import com.adnanbal.fxdedektifi.forex.presentation.internal.di.components.NewPositionComponent;
import com.adnanbal.fxdedektifi.forex.presentation.model.SignalModel;
import com.adnanbal.fxdedektifi.forex.presentation.view.fragment.NewSignalFragment;

public class NewSignalActivity extends BaseActivity implements HasComponent<NewPositionComponent> {

  SignalModel signalModelToEdit;

  static final String TAG = "NewSignalActivity";
  Unbinder unbinder;

  private NewPositionComponent newPositionComponent;

  public static Intent getCallingIntent(Context context) {
    return new Intent(context, NewSignalActivity.class);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_layout_alternative);
    this.initializeInjector();
    unbinder = ButterKnife.bind(this);

    signalModelToEdit = (SignalModel) getIntent().getSerializableExtra("signalModelToEdit");

    Bundle bundle = new Bundle();
    bundle.putSerializable("signalModelToEdit", signalModelToEdit);

    NewSignalFragment newSignalFragment = new NewSignalFragment();
    newSignalFragment.setArguments(bundle);

    addFragment(
        com.adnanbal.fxdedektifi.forex.presentation.R.id.fragmentContainer,
        newSignalFragment);
  }

  private void initializeInjector() {
    this.newPositionComponent = DaggerNewPositionComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .build();
  }

  @Override
  public NewPositionComponent getComponent() {
    return newPositionComponent;
  }
}
