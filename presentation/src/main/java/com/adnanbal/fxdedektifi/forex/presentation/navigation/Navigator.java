/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.adnanbal.fxdedektifi.forex.presentation.navigation;

import android.content.Context;
import android.content.Intent;
import com.adnanbal.fxdedektifi.forex.presentation.model.SignalModel;
import com.adnanbal.fxdedektifi.forex.presentation.view.activity.BillingActivity;
import com.adnanbal.fxdedektifi.forex.presentation.view.activity.LoginActivity;
import com.adnanbal.fxdedektifi.forex.presentation.view.activity.MainIntroActivity;
import com.adnanbal.fxdedektifi.forex.presentation.view.activity.NewSignalActivity;
import com.adnanbal.fxdedektifi.forex.presentation.view.activity.PersonalPositionsActivity;
import com.adnanbal.fxdedektifi.forex.presentation.view.activity.SignalsActivity;
import com.adnanbal.fxdedektifi.forex.presentation.view.activity.UserDetailsActivity;
import com.adnanbal.fxdedektifi.forex.presentation.view.activity.UserListActivity;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Class used to navigate through the application.
 */
@Singleton
public class Navigator {

  @Inject
  public Navigator() {
    //empty
  }

  /**
   * Goes to the user list screen.
   *
   * @param context A Context needed to open the destiny activity.
   */
  public void navigateToUserList(Context context) {
    if (context != null) {
      Intent intentToLaunch = UserListActivity.getCallingIntent(context);
      context.startActivity(intentToLaunch);
    }
  }

  /**
   * Goes to the main tutorial intro screen.
   *
   * @param context A Context needed to open the destiny activity.
   */
  public void navigateToMainIntroActivity(Context context) {
    if (context != null) {
      Intent intentToLaunch = MainIntroActivity.getCallingIntent(context);
      context.startActivity(intentToLaunch);
    }
  }

  /**
   * Goes to the user details screen.
   *
   * @param context A Context needed to open the destiny activity.
   */
  public void navigateToUserDetails(Context context, int userId) {
    if (context != null) {
      Intent intentToLaunch = UserDetailsActivity.getCallingIntent(context, userId);
      context.startActivity(intentToLaunch);
    }
  }

//  /**
//   * Goes to the login screen.
//   *
//   * @param context A Context needed to open the destiny activity.
//   */
//  public void navigateToLogin(Context context) {
//    if (context != null) {
//      Intent intentToLaunch = LoginActivity.getCallingIntent(context);
//      context.startActivity(intentToLaunch);
//    }
//  }


  /**
   * Goes to the {@link PersonalPositionsActivity} screen.
   *
   * @param context A Context needed to open the destiny activity.
   */
  public void navigateToPersonalPositions(Context context) {
    if (context != null) {
      Intent intentToLaunch = PersonalPositionsActivity.getCallingIntent(context);
      context.startActivity(intentToLaunch);
    }
  }

//  /**
//   * Goes to the {@link PersonalPositionsActivity} screen.
//   *
//   * @param context A Context needed to open the destiny activity.
//   */
//  public void navigateToPositionsHistory(Context context) {
//    if (context != null) {
//      Intent intentToLaunch = PersonalPositionsActivity.getCallingIntent(context);
//      context.startActivity(intentToLaunch);
//    }
//  }


  /**
   * Goes to the given signals screen.
   *
   * @param context A Context needed to open the destiny activity.
   */
  public void navigateToSignals(Context context) {
    if (context != null) {
      Intent intentToLaunch = SignalsActivity.getCallingIntent(context);
      context.startActivity(intentToLaunch);
    }
  }

  /**
   * Goes to the given Login screen.
   *
   * @param context A Context needed to open the destiny activity.
   */
  public void navigateToLogin(Context context) {
    if (context != null) {
      Intent intentToLaunch = LoginActivity.getCallingIntent(context);
      context.startActivity(intentToLaunch);
    }
  }

  /**
   * Goes to the given signals screen.
   *
   * @param context A Context needed to open the destiny activity.
   */
  public void navigateToMyAccountAndSubscriptionsActivity(Context context) {
    if (context != null) {
      Intent intentToLaunch = BillingActivity.getCallingIntent(context);
      context.startActivity(intentToLaunch);
    }
  }

  /**
   * Goes to the given signals screen.
   *
   * @param context A Context needed to open the destiny activity.
   */
  public void navigateToNewSignalActivity(Context context, SignalModel signalModel) {
    if (context != null) {
      Intent intentToLaunch = NewSignalActivity.getCallingIntent(context);
      intentToLaunch.putExtra("signalModelToEdit", signalModel);
      context.startActivity(intentToLaunch);
    }
  }
}
