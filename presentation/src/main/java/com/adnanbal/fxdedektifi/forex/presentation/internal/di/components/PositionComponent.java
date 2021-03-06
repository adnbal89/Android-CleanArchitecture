package com.adnanbal.fxdedektifi.forex.presentation.internal.di.components;

import com.adnanbal.fxdedektifi.forex.presentation.internal.di.PerActivity;
import com.adnanbal.fxdedektifi.forex.presentation.internal.di.modules.ActivityModule;
import com.adnanbal.fxdedektifi.forex.presentation.internal.di.modules.PositionModule;
import com.adnanbal.fxdedektifi.forex.presentation.view.fragment.AccountAndSubscriptionsFragment;
import com.adnanbal.fxdedektifi.forex.presentation.view.fragment.PersonalPositionsFragment;
import com.adnanbal.fxdedektifi.forex.presentation.view.fragment.ProfitFragment;
import com.adnanbal.fxdedektifi.forex.presentation.view.fragment.SignalsFragment;
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

  void inject(ProfitFragment profitFragment);

  void inject(AccountAndSubscriptionsFragment accountAndSubscriptionsFragment);

}
