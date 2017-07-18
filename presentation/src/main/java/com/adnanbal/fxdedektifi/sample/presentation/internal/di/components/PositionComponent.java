package com.adnanbal.fxdedektifi.sample.presentation.internal.di.components;

import com.adnanbal.fxdedektifi.sample.presentation.internal.di.PerActivity;
import com.adnanbal.fxdedektifi.sample.presentation.internal.di.modules.ActivityModule;
import com.adnanbal.fxdedektifi.sample.presentation.internal.di.modules.PositionModule;
import com.adnanbal.fxdedektifi.sample.presentation.view.fragment.PersonalHistoryFragment;
import com.adnanbal.fxdedektifi.sample.presentation.view.fragment.PersonalPositionsFragment;
import com.adnanbal.fxdedektifi.sample.presentation.view.fragment.SignalsFragment;
import dagger.Component;

/**
 * A scope {@link PerActivity} component.
 * Injects position specific Fragments.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class,
    PositionModule.class})
public interface PositionComponent extends ActivityComponent {

  void inject(PersonalPositionsFragment personalPositionsFragment);

  void inject(SignalsFragment signalsFragment);

  void inject(PersonalHistoryFragment personalHistoryFragment);
}
