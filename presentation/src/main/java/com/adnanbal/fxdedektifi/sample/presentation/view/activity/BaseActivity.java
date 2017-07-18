package com.adnanbal.fxdedektifi.sample.presentation.view.activity;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.adnanbal.fxdedektifi.sample.presentation.AndroidApplication;
import com.adnanbal.fxdedektifi.sample.presentation.internal.di.components.ApplicationComponent;
import com.adnanbal.fxdedektifi.sample.presentation.internal.di.modules.ActivityModule;
import com.adnanbal.fxdedektifi.sample.presentation.navigation.Navigator;
import javax.inject.Inject;

/**
 * Base {@link android.app.Activity} class for every Activity in this application.
 */
public abstract class BaseActivity extends AppCompatActivity {

  /*
 * Butterknife unbinder, will be executed to clear callbacks
  */

  @Inject
  Navigator navigator;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.getApplicationComponent().inject(this);

  }

  /**
   * Adds a {@link Fragment} to this activity's layout.
   *
   * @param containerViewId The container view to where add the fragment.
   * @param fragment The fragment to be added.
   */
  protected void addFragment(int containerViewId, DialogFragment fragment) {
    final FragmentTransaction fragmentTransaction = this
        .getFragmentManager().beginTransaction();

    fragmentTransaction.replace(containerViewId, fragment);
    fragmentTransaction.commit();
  }

  /**
   * Get the Main Application component for dependency injection.
   *
   * @return {@link ApplicationComponent}
   */
  protected ApplicationComponent getApplicationComponent() {
    return ((AndroidApplication) getApplication()).getApplicationComponent();
  }

  /**
   * Get an Activity module for dependency injection.
   *
   * @return {@link ActivityModule}
   */
  protected ActivityModule getActivityModule() {
    return new ActivityModule(this);
  }

  protected void setUpToolbar() {
    // Find the toolbar view inside the activity layout

  }


  @Override
  protected void onDestroy() {
    super.onDestroy();
  }


}

//else if (itemId == R.id.action_profit) {
//    startActivity(new Intent(this, NotificationsActivity.class));
//    }else if (itemId == R.id.action_history) {
//    startActivity(new Intent(this, NotificationsActivity.class));
//    }

