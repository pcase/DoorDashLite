package com.apps.azurehorsecreations.doordashlite.ui;

import com.apps.azurehorsecreations.doordashlite.di.scope.CustomScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by pattycase on 4/27/18.
 */

@Module
public class RestaurantMainScreenModule {
    private final RestaurantMainScreenContract.View mView;

    public RestaurantMainScreenModule(RestaurantMainScreenContract.View mView) {
        this.mView = mView;
    }

    @Provides
    @CustomScope
    RestaurantMainScreenContract.View providesRestaurantMainScreenContractView() {
        return mView;
    }
}
