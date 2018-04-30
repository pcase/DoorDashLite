package com.apps.azurehorsecreations.doordashlite.ui;

import com.apps.azurehorsecreations.doordashlite.di.component.NetComponent;
import com.apps.azurehorsecreations.doordashlite.di.scope.CustomScope;
import dagger.Component;

/**
 * RestaurantMainPageComponent
 */

@CustomScope
@Component(dependencies = NetComponent.class, modules = RestaurantMainPageModule.class)
public interface RestaurantMainPageComponent {
    void inject(RestaurantMainPageActivity activity);
}
