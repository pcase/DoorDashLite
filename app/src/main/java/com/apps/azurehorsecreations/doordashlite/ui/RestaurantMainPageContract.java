package com.apps.azurehorsecreations.doordashlite.ui;

import com.apps.azurehorsecreations.doordashlite.data.LatLng;
import com.apps.azurehorsecreations.doordashlite.data.Restaurant;
import java.util.List;

/**
 * Created by pattycase on 4/27/18.
 */

public class RestaurantMainPageContract {
    public interface View {
        void showRestaurants(List<Restaurant> restaurants);

        void showError(String message);

        void showComplete();
    }

    public interface Presenter {
        void loadRestaurants(LatLng latLng);
    }
}
