package com.apps.azurehorsecreations.doordashlite.ui;

import com.apps.azurehorsecreations.doordashlite.di.scope.CustomScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by pattycase on 4/27/18.
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
