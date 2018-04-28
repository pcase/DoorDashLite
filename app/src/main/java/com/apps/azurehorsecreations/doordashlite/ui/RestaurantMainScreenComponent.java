package com.apps.azurehorsecreations.doordashlite.ui;

import com.apps.azurehorsecreations.doordashlite.di.component.NetComponent;
import com.apps.azurehorsecreations.doordashlite.di.scope.CustomScope;

import dagger.Component;

/**
 * Created by pattycase on 4/27/18.
 */

@CustomScope
@Component(dependencies = NetComponent.class, modules = RestaurantMainScreenModule.class)
public interface RestaurantMainScreenComponent {
    void inject(MainActivity activity);
}
