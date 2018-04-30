package com.apps.azurehorsecreations.doordashlite.ui.navigation;

import android.content.Context;
import android.content.Intent;
import com.apps.azurehorsecreations.doordashlite.data.Restaurant;

/**
 * RestaurantNavigator
 */

public class RestaurantNavigator {
    private static final String RESTAURANT = "RESTAURANT";
    private final Context mActivityContext;
    private final Class<?> mClassToNavigateTo;
    private Restaurant mRestaurant;

    public RestaurantNavigator(Context activityContext, Class<?> cls, Restaurant restaurant) {
        this.mActivityContext = activityContext;
        this.mClassToNavigateTo = cls;
        this.mRestaurant = restaurant;
    }

    public void launchActivity() {
        Intent intent = new Intent(mActivityContext, mClassToNavigateTo);
        intent.putExtra(RESTAURANT, mRestaurant);
        mActivityContext.startActivity(intent);
    }
}
