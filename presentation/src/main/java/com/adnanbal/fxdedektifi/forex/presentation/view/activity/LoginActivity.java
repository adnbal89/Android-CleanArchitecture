package com.adnanbal.fxdedektifi.forex.presentation.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.adnanbal.fxdedektifi.forex.presentation.AndroidApplication;
import com.adnanbal.fxdedektifi.forex.presentation.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.Arrays;

public class LoginActivity extends BaseActivity {

  private Button button;
  private TextView text;
  private FirebaseAuth auth;
  static final String TAG = "LoginActivity";
  private static final int RC_SIGN_IN = 123;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    auth = FirebaseAuth.getInstance();

    // Obtain the views
    button = findViewById(R.id.button);
    text = findViewById(R.id.text);

    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (!isLoggedIn()) {
          // Fire the sign in with FireBase
          startActivityForResult(AuthUI.getInstance().
                  createSignInIntentBuilder().
                  setProviders(
                      Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                          new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
                          new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build())).build(),
              RC_SIGN_IN);


        } else {
          // Perform sign out
          AuthUI.getInstance()
              .signOut(LoginActivity.this)
              .addOnCompleteListener(new OnCompleteListener<Void>() {
                public void onComplete(@NonNull Task<Void> task) {
                  // User is now signed out
                  showMessage(R.string.signed_out);
                  updateViews();
                }
              });
        }
      }
    });
    updateViews();
  }



  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == RC_SIGN_IN) {
      // Get identity provider response from intent
      IdpResponse response = IdpResponse.fromResultIntent(data);
      if (resultCode == ResultCodes.OK) {
        //Navigate to Signals Activity
        // Successfully signed in
        updateViews();
        return;
      } else {
        // Sign in failed
        updateViews();
        if (response == null) {
          // User pressed back button
          showMessage(R.string.sign_in_cancelled);
          return;
        }
        if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
          showMessage(R.string.no_internet_connection);
          return;
        }
        if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
          showMessage(R.string.unknown_error);
          return;
        }
      }
      showMessage(R.string.unknown_response);
    }
  }

  private void updateViews() {

    if (isLoggedIn()) {
      showMessage(R.string.signed_in);
      button.setText(R.string.sign_out);
      FirebaseUser user = auth.getCurrentUser();

      this.navigator.navigateToSignals(this);

      AndroidApplication.userUid = user.getUid();


      // Show user info
      text.setText("UID: " + user.getUid() + "\n" +
          "Name: " + user.getDisplayName() + "\n" +
          "Email: " + user.getEmail());

    } else {
      button.setText(R.string.sign_in);
      text.setText("");
    }
  }

  private boolean isLoggedIn() {
    return auth.getCurrentUser() != null;
  }

  private void showMessage(int message) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show();
  }


  @Override
  public void onDestroy() {
    super.onDestroy();
  }

}