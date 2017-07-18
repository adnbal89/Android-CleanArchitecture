package com.adnanbal.fxdedektifi.sample.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.auth0.android.Auth0;
import com.auth0.android.lock.AuthenticationCallback;
import com.auth0.android.lock.Lock;
import com.auth0.android.lock.LockCallback;
import com.auth0.android.lock.utils.LockException;
import com.auth0.android.result.Credentials;

public class LoginActivity extends BaseActivity {

  private Lock mLock;

  public static Intent getCallingIntent(Context context) {
    return new Intent(context, LoginActivity.class);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
//    startActivity(new Intent(getApplicationContext(), PersonalPositionsActivity.class));
//
    //Auth0 credentials
    Auth0 auth0 = new Auth0(
        getString(com.adnanbal.fxdedektifi.sample.presentation.R.string.auth0_client_id), getString(
        com.adnanbal.fxdedektifi.sample.presentation.R.string.auth0_domain));
    auth0.setOIDCConformant(true);

    mLock = Lock.newBuilder(auth0, mCallback)
        //Add parameters to the builder
        .build(this);

    //start Authentication Activity
    startActivity(mLock.newIntent(this));
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    // Your own Activity code

//    unregister/Destroy the lock
    mLock.onDestroy(this);
    mLock = null;
  }


  private final LockCallback mCallback = new AuthenticationCallback() {

    /**
     * User authenticated successfully.
     *
     * @param credentials A Context needed to open the destiny activity.
     */
    @Override
    public void onAuthentication(Credentials credentials) {
      Toast.makeText(getApplicationContext(), "Log In - Success", Toast.LENGTH_SHORT).show();
      startActivity(new Intent(getApplicationContext(), PersonalPositionsActivity.class));
      finish();
    }

    @Override
    public void onCanceled() {
      Toast.makeText(getApplicationContext(), "Log In - Cancelled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(LockException error) {
      Toast.makeText(getApplicationContext(), "Log In - Error Occurred", Toast.LENGTH_SHORT).show();
    }
  };


  @Override
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
  }
}