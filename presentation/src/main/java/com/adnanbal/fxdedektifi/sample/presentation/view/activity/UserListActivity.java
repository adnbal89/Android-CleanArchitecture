/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.adnanbal.fxdedektifi.sample.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import com.adnanbal.fxdedektifi.sample.presentation.R;
import com.adnanbal.fxdedektifi.sample.presentation.internal.di.HasComponent;
import com.adnanbal.fxdedektifi.sample.presentation.internal.di.components.DaggerUserComponent;
import com.adnanbal.fxdedektifi.sample.presentation.internal.di.components.UserComponent;
import com.adnanbal.fxdedektifi.sample.presentation.model.UserModel;
import com.adnanbal.fxdedektifi.sample.presentation.view.fragment.UserListFragment;
import com.adnanbal.fxdedektifi.sample.presentation.view.fragment.UserListFragment.UserListListener;

/**
 * Activity that shows a list of Users.
 */
public class UserListActivity extends BaseActivity implements HasComponent<UserComponent>,
    UserListListener {

  public static Intent getCallingIntent(Context context) {
    return new Intent(context, UserListActivity.class);
  }

  private UserComponent userComponent;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
    setContentView(com.adnanbal.fxdedektifi.sample.presentation.R.layout.activity_layout);
    setUpToolbar();

    this.initializeInjector();

    if (savedInstanceState == null) {
      addFragment(
          com.adnanbal.fxdedektifi.sample.presentation.R.id.fragmentContainer,
          new UserListFragment());
    }


  }


  private void initializeInjector() {
    this.userComponent = DaggerUserComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .build();
  }

  @Override
  public UserComponent getComponent() {
    return userComponent;
  }

  @Override
  public void onUserClicked(UserModel userModel) {
    this.navigator.navigateToUserDetails(this, userModel.getUserId());
  }

  // Menu icons are inflated just as they were with actionbar
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.appbar_menu, menu);
    return true;
  }

  @Override
  protected void onResume() {
    super.onResume();

  }


}
