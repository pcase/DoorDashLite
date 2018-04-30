package com.apps.azurehorsecreations.doordashlite.ui;

import com.apps.azurehorsecreations.doordashlite.data.Restaurant;

import java.util.List;

/**
 * Created by pattycase on 4/27/18.
 */

public class RestaurantDetailPageContract {
    public interface View {
        void showRestaurantDetail(Restaurant restaurant);

        void showError(String message);

        void showComplete();
    }

    public interface Presenter {
        void loadRestaurantDetail(int id);
    }
}
