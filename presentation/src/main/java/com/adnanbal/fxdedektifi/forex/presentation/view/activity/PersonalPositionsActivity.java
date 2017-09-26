/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.adnanbal.fxdedektifi.forex.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import com.adnanbal.fxdedektifi.forex.presentation.R;
import com.adnanbal.fxdedektifi.forex.presentation.internal.di.HasComponent;
import com.adnanbal.fxdedektifi.forex.presentation.internal.di.components.DaggerPositionComponent;
import com.adnanbal.fxdedektifi.forex.presentation.internal.di.components.PositionComponent;
import com.adnanbal.fxdedektifi.forex.presentation.model.PositionModel;
import com.adnanbal.fxdedektifi.forex.presentation.view.ConfirmDialogView;
import com.adnanbal.fxdedektifi.forex.presentation.view.fragment.PersonalPositionsFragment;
import com.adnanbal.fxdedektifi.forex.presentation.view.fragment.PersonalPositionsFragment.PositionListListener;

/**
 * Activity that shows a list of Positions.
 */
public class PersonalPositionsActivity extends BaseActivity implements
    HasComponent<PositionComponent>, PositionListListener {

//  ActionBarDrawerToggle actionBarDrawerToggle;
//  DrawerLayout drawerLayout;

  public static Intent getCallingIntent(Context context) {
    return new Intent(context, PersonalPositionsActivity.class);
  }

  private PositionComponent positionComponent;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
    setContentView(com.adnanbal.fxdedektifi.forex.presentation.R.layout.activity_layout);
    setUpToolbar();

    this.initializeInjector();

    if (savedInstanceState == null) {
      addFragment(
          com.adnanbal.fxdedektifi.forex.presentation.R.id.fragmentContainer,
          new PersonalPositionsFragment());
    }
  }

  private void initializeInjector() {
    this.positionComponent = DaggerPositionComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .build();
  }

  @Override
  public PositionComponent getComponent() {
    return positionComponent;
  }



  // Menu icons are inflated just as they were with actionbar
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.appbar_menu, menu);
    return true;
  }


  @Override
  public void onPositionClicked(PositionModel positionModel, ConfirmDialogView view) {

  }
}
