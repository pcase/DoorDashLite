package com.apps.azurehorsecreations.doordashlite.ui;

import com.apps.azurehorsecreations.doordashlite.di.scope.CustomScope;
import dagger.Module;
import dagger.Provides;

/**
 * RestaurantDetailPageModule
 */

@Module
public class RestaurantDetailPageModule {
    private final RestaurantDetailPageContract.View mView;

    public RestaurantDetailPageModule(RestaurantDetailPageContract.View mView) {
        this.mView = mView;
    }

    @Provides
    @CustomScope
    RestaurantDetailPageContract.View providesRestaurantDetailcreenContractView() {
        return mView;
    }
}
