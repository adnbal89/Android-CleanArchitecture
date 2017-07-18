package com.adnanbal.fxdedektifi.sample.presentation.view.activity;

import android.os.Bundle;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.adnanbal.fxdedektifi.sample.presentation.R;

/**
 * Main application screen. This is the app entry point.
 */
public class MainActivity extends BaseActivity {

  @BindView(R.id.btn_LoadData)
  Button btn_LoadData;
  @BindView(R.id.btn_personal_positions)
  Button btn_personal_positions;
  @BindView(R.id.btn_signals)
  Button btn_signals;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
  }


  /**
   * Goes to the Personal Position screen.
   */
  @OnClick(R.id.btn_personal_positions)
  void navigateToPersonalPositions() {
    this.navigator.navigateToPersonalPositions(this);
  }

  /**
   * Goes to the signals screen.
   */
  @OnClick(R.id.btn_signals)
  void navigateToSignals() {
    this.navigator.navigateToSignals(this);
  }


  /**
   * Goes to the user list screen.
   */
  @OnClick(R.id.btn_LoadData)
  void navigateToUserList() {
    this.navigator.navigateToUserList(this);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
  }

}
