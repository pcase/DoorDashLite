package com.apps.azurehorsecreations.doordashlite.ui;

import com.apps.azurehorsecreations.doordashlite.di.component.NetComponent;
import com.apps.azurehorsecreations.doordashlite.di.scope.CustomScope;
import dagger.Component;

/**
 * RestaurantDetailPageComponent
 */

@CustomScope
@Component(dependencies = NetComponent.class, modules = RestaurantDetailPageModule.class)
public interface RestaurantDetailPageComponent {
    void inject(RestaurantDetailPageActivity activity);
}
