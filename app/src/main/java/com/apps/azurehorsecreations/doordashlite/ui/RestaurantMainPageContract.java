package com.apps.azurehorsecreations.doordashlite.ui;

import android.location.Location;

import com.apps.azurehorsecreations.doordashlite.data.Restaurant;
import java.util.List;

/**
 * RestaurantMainPageContract
 */

public class RestaurantMainPageContract {
    public interface View {
        void showRestaurants(List<Restaurant> restaurants);

        void showError(String message);

        void showComplete();

        void showProgress();

        void hideProgress();
    }

    public interface Presenter {
        void loadRestaurants(Location location);
    }
}
