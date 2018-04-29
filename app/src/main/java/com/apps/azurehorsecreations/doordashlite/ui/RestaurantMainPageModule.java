package com.apps.azurehorsecreations.doordashlite.ui;

import com.apps.azurehorsecreations.doordashlite.di.scope.CustomScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by pattycase on 4/27/18.
 */

@Module
public class RestaurantMainPageModule {
    private final RestaurantMainPageContract.View mView;

    public RestaurantMainPageModule(RestaurantMainPageContract.View mView) {
        this.mView = mView;
    }

    @Provides
    @CustomScope
    RestaurantMainPageContract.View providesRestaurantMainScreenContractView() {
        return mView;
    }
}
