package com.apps.azurehorsecreations.doordashlite.ui;

import com.apps.azurehorsecreations.doordashlite.data.Restaurant;
import java.util.List;

/**
 * RestaurantDetailPageContract Interface specifies contract between View and Presenter
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
