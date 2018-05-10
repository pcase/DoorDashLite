package com.apps.azurehorsecreations.doordashlite.ui;

import com.apps.azurehorsecreations.doordashlite.di.component.NetComponent;
import com.apps.azurehorsecreations.doordashlite.di.scope.CustomScope;
import dagger.Component;

/**
 * RestaurantDetailPageComponent is the bridge between RestaurantDetailPageModule and
 * RestaurantDetailPageActivity
 */

@CustomScope
@Component(dependencies = NetComponent.class, modules = RestaurantDetailPageModule.class)
public interface RestaurantDetailPageComponent {
    void inject(RestaurantDetailPageActivity activity);
}
