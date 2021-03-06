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

package com.adnanbal.fxdedektifi.forex.presentation.view.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.adnanbal.fxdedektifi.forex.presentation.R;
import com.bluejamesbond.text.DocumentView;
import com.bluejamesbond.text.style.TextAlignment;

public class TermsAndConditionsMainFragment extends DialogFragment {

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_terms_and_conditions_main, container, false);

    DocumentView documentView = v.findViewById(R.id.termsDoc);
    documentView.getDocumentLayoutParams().setTextAlignment(TextAlignment.JUSTIFIED);
    documentView.setVerticalScrollBarEnabled(true);
    return v;
  }

}
