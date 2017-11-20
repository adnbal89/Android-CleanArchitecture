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
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import com.adnanbal.fxdedektifi.forex.presentation.R;
import com.adnanbal.fxdedektifi.forex.presentation.view.fragment.TermsAndConditionsFragment;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.github.paolorotolo.appintro.model.SliderPage;

public class MainIntroActivity extends AppIntro {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setDepthAnimation();
    showSkipButton(false);
    setDoneText("Accept");
    setIndicatorColor(getResources().getColor(R.color.indicator_color1),
        getResources().getColor(R.color.indicator_color2));

    SliderPage sliderPage1 = new SliderPage();
    sliderPage1.setTitle(getResources().getString(R.string.welcome));
    sliderPage1.setImageDrawable(R.drawable.welcome);
    sliderPage1.setTitleColor(getResources().getColor(R.color.color_welcome_text));
    sliderPage1.setDescColor(getResources().getColor(R.color.color_welcome_text));
    sliderPage1.setBgColor(getResources().getColor(R.color.color_canteen));

    SliderPage sliderPage2 = new SliderPage();
    sliderPage2.setTitle(getResources().getString(R.string.title_intro_solid_signals));
    sliderPage2.setDescription(getResources().getString(R.string.description_intro_solid_signals));
    sliderPage2.setImageDrawable(R.drawable.signal);
    sliderPage2.setBgColor(getResources().getColor(R.color.color_material_metaphor));

    SliderPage sliderPage3 = new SliderPage();
    sliderPage3.setTitle(getResources().getString(R.string.title_profit_tracking));
    sliderPage3.setDescription(getResources().getString(R.string.description_profit_tracking));
    sliderPage3.setImageDrawable(R.drawable.profit_tracking);
    sliderPage3.setBgColor(getResources().getColor(R.color.color_primary_custom));

    SliderPage sliderPage4 = new SliderPage();
    sliderPage4.setTitle(getResources().getString(R.string.title_no_robot));
    sliderPage4.setDescription(getResources().getString(R.string.description_no_robot));
    sliderPage4.setImageDrawable(R.drawable.analysis);
    sliderPage4.setBgColor(getResources().getColor(R.color.color_material_motion));

    SliderPage sliderPage5 = new SliderPage();
    sliderPage5.setTitle(getResources().getString(R.string.title_instant_notification));
    sliderPage5.setDescription(getResources().getString(R.string.description_instant_notification));
    sliderPage5.setImageDrawable(R.drawable.notif);
    sliderPage5.setBgColor(getResources().getColor(R.color.color_custom_fragment_1));

    SliderPage sliderPage6 = new SliderPage();
    sliderPage6.setTitle(getResources().getString(R.string.title_unique_live_signals));
    sliderPage6.setDescription(getResources().getString(R.string.description_unique_live_signals));
    sliderPage6.setImageDrawable(R.drawable.live_signals);
    sliderPage6.setBgColor(getResources().getColor(R.color.color_canteen));
    sliderPage6.setTitleColor(getResources().getColor(R.color.color_cloud));
    sliderPage6.setDescColor(getResources().getColor(R.color.color_cloud));

    SliderPage sliderPage7 = new SliderPage();
    sliderPage7.setTitle(getResources().getString(R.string.title_user_friendly_interface));
    sliderPage7
        .setDescription(getResources().getString(R.string.description_user_friendly_interface));
    sliderPage7.setImageDrawable(R.drawable.user_friendly);
    sliderPage7.setBgColor(getResources().getColor(R.color.color_material_bold));

//    SliderPage sliderPage8 = new SliderPage();
//    sliderPage8.setTitle(getResources().getString(R.string.terms_and_conditions));
//    sliderPage8.setDescription(getResources().getString(R.string.termsandConditionsDoc));
//    sliderPage8.setImageDrawable(R.drawable.terms_and_conditions);
//    sliderPage8.setBgColor(getResources().getColor(R.color.color_terms));

    addSlide(AppIntroFragment.newInstance(sliderPage1));
    addSlide(AppIntroFragment.newInstance(sliderPage2));
    addSlide(AppIntroFragment.newInstance(sliderPage3));
    addSlide(AppIntroFragment.newInstance(sliderPage4));
    addSlide(AppIntroFragment.newInstance(sliderPage5));
    addSlide(AppIntroFragment.newInstance(sliderPage6));
    addSlide(AppIntroFragment.newInstance(sliderPage7));
    addSlide(new TermsAndConditionsFragment());

    setCustomTransformer(new ZoomOutPageTransformer());


  }


  public static Intent getCallingIntent(Context context) {
    return new Intent(context, MainIntroActivity.class);
  }

  @Override
  public void onDonePressed(Fragment currentFragment) {
    super.onDonePressed(currentFragment);
    finish();
  }

  @Override
  public void onBackPressed() {
    if (pager.getCurrentItem() == 0 || pager.getCurrentItem() > 4) {
      // Do not allow the user to leave the intro after this slide
      setGoBackLock(true);
    } else {
      setGoBackLock(false);
    }

    super.onBackPressed();
  }

  public class ZoomOutPageTransformer implements ViewPager.PageTransformer {

    private static final float MIN_SCALE = 0.85f;
    private static final float MIN_ALPHA = 0.5f;

    public void transformPage(View view, float position) {
      int pageWidth = view.getWidth();
      int pageHeight = view.getHeight();

      if (position < -1) { // [-Infinity,-1)
        // This page is way off-screen to the left.
        view.setAlpha(0);

      } else if (position <= 1) { // [-1,1]
        // Modify the default slide transition to shrink the page as well
        float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
        float vertMargin = pageHeight * (1 - scaleFactor) / 2;
        float horzMargin = pageWidth * (1 - scaleFactor) / 2;
        if (position < 0) {
          view.setTranslationX(horzMargin - vertMargin / 2);
        } else {
          view.setTranslationX(-horzMargin + vertMargin / 2);
        }

        // Scale the page down (between MIN_SCALE and 1)
        view.setScaleX(scaleFactor);
        view.setScaleY(scaleFactor);

        // Fade the page relative to its size.
        view.setAlpha(MIN_ALPHA +
            (scaleFactor - MIN_SCALE) /
                (1 - MIN_SCALE) * (1 - MIN_ALPHA));

      } else { // (1,+Infinity]
        // This page is way off-screen to the right.
        view.setAlpha(0);
      }
    }
  }
}