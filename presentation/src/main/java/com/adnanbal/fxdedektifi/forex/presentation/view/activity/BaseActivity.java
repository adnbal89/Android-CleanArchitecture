package com.adnanbal.fxdedektifi.forex.presentation.view.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import com.adnanbal.fxdedektifi.forex.presentation.AndroidApplication;
import com.adnanbal.fxdedektifi.forex.presentation.internal.di.components.ApplicationComponent;
import com.adnanbal.fxdedektifi.forex.presentation.internal.di.modules.ActivityModule;
import com.adnanbal.fxdedektifi.forex.presentation.navigation.Navigator;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.analytics.FirebaseAnalytics.Event;
import javax.inject.Inject;

/**
 * Base {@link android.app.Activity} class for every Activity in this application.
 */
public abstract class BaseActivity extends AppCompatActivity {

  private FirebaseAnalytics mFirebaseAnalytics;

//  private String[] mFragmentTitles;
//
//  @BindView(R.id.drawer_layout)
//   DrawerLayout mDrawerLayout;
//  @BindView(R.id.left_drawer)
//   ListView mDrawerList;
//
//
//  private ActionBarDrawerToggle mDrawerToggle;
//
//  private CharSequence mTitle;
//  private CharSequence mDrawerTitle;
//
//  //Butter knife binder
//  Unbinder unbinder;


  /*
 * Butterknife unbinder, will be executed to clear callbacks
  */

  @Inject
  Navigator navigator;

  public Navigator getNavigator() {
    return navigator;
  }

  public void setNavigator(Navigator navigator) {
    this.navigator = navigator;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.getApplicationComponent().inject(this);
// Obtain the FirebaseAnalytics instance.
    mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

    mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);
//    unbinder = ButterKnife.bind(this);
//    mTitle = mDrawerTitle = getTitle();
//
//    //Fragment titles
//    mFragmentTitles = getResources().getStringArray(R.array.array_fragment_titles);
//
//    // Set the adapter for the list view
//    mDrawerList.setAdapter(new ArrayAdapter<>(this,
//        R.layout.drawer_list_item, mFragmentTitles));
//    // Set the list's click listener
//    mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

  }

  /**
   * Adds a {@link Fragment} to this activity's layout.
   *
   * @param containerViewId The container view to where add the fragment.
   * @param fragment The fragment to be added.
   */
  protected void addFragment(int containerViewId, DialogFragment fragment) {
    final FragmentTransaction fragmentTransaction = this
        .getSupportFragmentManager().beginTransaction();

    if (fragment.getClass().getSimpleName().equals("SignalsFragment")) {
      AndroidApplication.backtackFragmentList.add(0);
    } else if (fragment.getClass().getSimpleName().equals("ProfitFragment")) {
      AndroidApplication.backtackFragmentList.add(1);
    } else if (fragment.getClass().getSimpleName().equals("AccountAndSubscriptionsFragment")) {
      AndroidApplication.backtackFragmentList.add(2);
    } else if (fragment.getClass().getSimpleName().equals("TermsAndConditionsMainFragment")) {
      AndroidApplication.backtackFragmentList.add(3);
    }

    fragmentTransaction.replace(containerViewId, fragment);
    fragmentTransaction.addToBackStack(null);
    fragmentTransaction.commitAllowingStateLoss();
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
//    unbinder.unbind();
    super.onDestroy();

  }

  @Override
  protected void onResume() {
    super.onResume();

  }

  //  private class DrawerItemClickListener implements ListView.OnItemClickListener {
//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//      selectItem(position);
//    }
//  }

//  /** Swaps fragments in the main content view */
//  private void selectItem(int position) {
//    // Create a new fragment and specify the planet to show based on position
//    Fragment fragment = new SignalsFragment();
//    Bundle args = new Bundle();
//    fragment.setArguments(args);
//
//    // Insert the fragment by replacing any existing fragment
//    FragmentManager fragmentManager = getFragmentManager();
//    fragmentManager.beginTransaction()
//        .replace(R.id.content_frame, fragment)
//        .commit();
//
//
//
//    mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
//        R.string.drawer_open, R.string.drawer_close) {
//
//      /** Called when a drawer has settled in a completely closed state. */
//      public void onDrawerClosed(View view) {
//        super.onDrawerClosed(view);
//        getActionBar().setTitle(mTitle);
//        invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
//      }
//
//      /** Called when a drawer has settled in a completely open state. */
//      public void onDrawerOpened(View drawerView) {
//        super.onDrawerOpened(drawerView);
//        getActionBar().setTitle(mDrawerTitle);
//        invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
//      }
//    };
//
//    // Set the drawer toggle as the DrawerListener
//    mDrawerLayout.setDrawerListener(mDrawerToggle);
//
//
//
//    // Highlight the selected item, update the title, and close the drawer
//    mDrawerList.setItemChecked(position, true);
//    setTitle(mFragmentTitles[position]);
//    mDrawerLayout.closeDrawer(mDrawerList);
//  }

//  /* Called whenever we call invalidateOptionsMenu() */
//  @Override
//  public boolean onPrepareOptionsMenu(Menu menu) {
//    // If the nav drawer is open, hide action items related to the content view
//    boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
//    return super.onPrepareOptionsMenu(menu);
//  }

//  @Override
//  public void setTitle(CharSequence title) {
//    mTitle = title;
//    getActionBar().setTitle(mTitle);
//  }

  public void logErrorMessageToFirebaseAnalytics(String tag, Exception exception) {
    Bundle bundle = new Bundle();
    bundle.putString("TAG", tag);
    bundle.putString("Error", exception.getMessage());

    mFirebaseAnalytics.logEvent(Event.SELECT_CONTENT, bundle);
  }

  /**
   * Checks if the device has any active internet connection.
   *
   * @return true device with internet connection, otherwise false.
   */
  private boolean isThereInternetConnection() {
    boolean isConnected;

    ConnectivityManager connectivityManager =
        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
    isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

    return isConnected;
  }

}


