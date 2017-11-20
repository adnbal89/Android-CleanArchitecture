package com.adnanbal.fxdedektifi.forex.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.adnanbal.fxdedektifi.forex.presentation.AndroidApplication;
import com.adnanbal.fxdedektifi.forex.presentation.R;
import com.adnanbal.fxdedektifi.forex.presentation.sqlite.DatabaseHandler;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mobapphome.mahandroidupdater.tools.MAHUpdaterController;
import java.util.Arrays;

public class LoginActivity extends BaseActivity {

  @BindView(R.id.button_sign_out)
  public Button button_sign_out;
  @BindView(R.id.button_return)
  public Button button_return;


  private TextView text;
  private FirebaseAuth auth;
  static final String TAG = "LoginActivity";
  private static final int RC_SIGN_IN = 123;

  DatabaseHandler db;

  /*
  * Butterknife unbinder, will be executed to clear callbacks
   */
  Unbinder unbinder;


  public static Intent getCallingIntent(Context context) {
    return new Intent(context, LoginActivity.class);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    db = new DatabaseHandler(this);
    unbinder = ButterKnife.bind(this);
    auth = FirebaseAuth.getInstance();

    Thread timerThread = new Thread() {
      public void run() {
        try {
          sleep(3000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    };
    timerThread.start();

    button_sign_out.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        auth.signOut();
        AuthUI.getInstance()
            .signOut(LoginActivity.this)
            .addOnCompleteListener(new OnCompleteListener<Void>() {
              public void onComplete(@NonNull Task<Void> task) {
                // User is now signed out
                showMessage(R.string.signed_out);
                updateViews();
              }
            });

        android.os.Process.killProcess(android.os.Process.myPid());

        button_sign_out.setVisibility(View.INVISIBLE);
        button_return.setVisibility(View.INVISIBLE);
      }
    });

    button_return.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        updateViews();
      }
    });

    updateViews();

//    processLogin();

//    // Obtain the views
//    button = findViewById(R.id.button);
//    text = findViewById(R.id.text);
//
//    button.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View view) {
//
//      }
//    });
  }

  private void processLogin() {

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
      auth.signOut();

      AuthUI.getInstance()
          .signOut(LoginActivity.this)
          .addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(@NonNull Task<Void> task) {
              // User is now signed out
              db.deleteAllUserSignalDB();

              showMessage(R.string.signed_out);
              updateViews();
            }
          });

    }
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
//      button.setText(R.string.sign_out);
      FirebaseUser user = auth.getCurrentUser();
//      this.navigator.navigateToNewSignalActivity(this);
//      this.navigator.navigateToSignals(this);

      this.navigator.navigateToSignals(this);

      AndroidApplication.userUid = user.getUid();
      AndroidApplication.userEmail = user.getEmail();
      button_sign_out.setVisibility(View.VISIBLE);
      button_return.setVisibility(View.VISIBLE);

//      // Show user info
//      text.setText("UID: " + user.getUid() + "\n" +
//          "Name: " + user.getDisplayName() + "\n" +
//          "Email: " + user.getEmail());

    } else {
      processLogin();
//      button.setText(R.string.sign_in);
//      text.setText("");
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
    MAHUpdaterController.end();
    unbinder.unbind();
    super.onDestroy();
  }


}