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
import android.widget.EditText;
import android.widget.TextView;
import butterknife.ButterKnife;
import com.adnanbal.fxdedektifi.forex.presentation.R;

public class EmailAuthActivity extends BaseActivity {

  private static final String TAG = "EmailAuthActivity";



  private TextView mStatusTextView;
  private TextView mDetailTextView;
  private EditText mEmailField;
  private EditText mPasswordField;

  public static Intent createIntent(Context context) {
    return new Intent(context, EmailAuthActivity.class);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_email_auth);
    ButterKnife.bind(this);

//    FirebaseAuth auth = FirebaseAuth.getInstance();
//    if (auth.getCurrentUser() != null) {
//      startSignalsActivity(null);
//      finish();
//      return;
//    }


  }


}
