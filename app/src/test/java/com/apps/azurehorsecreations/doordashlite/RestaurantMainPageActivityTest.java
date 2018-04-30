package com.apps.azurehorsecreations.doordashlite;

import android.os.Build;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import static android.support.test.espresso.Espresso.onView;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.apps.azurehorsecreations.doordashlite.data.LatLng;
import com.apps.azurehorsecreations.doordashlite.data.Restaurant;
import com.apps.azurehorsecreations.doordashlite.ui.RestaurantMainPageActivity;
import com.apps.azurehorsecreations.doordashlite.ui.RestaurantMainPagePresenter;
import com.apps.azurehorsecreations.doordashlite.ui.adapters.RestaurantMainPageAdapter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.subscribers.TestSubscriber;

import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by pattycase on 4/29/18.
 */

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricTestRunner.class)
public class RestaurantMainPageActivityTest {
    private RestaurantMainPageActivity activity;
    private List<Restaurant> restaurantList;
    private RestaurantMainPageAdapter adapter;
    private RestaurantMainPageAdapter.OnItemClickListener listener;
    private RestaurantMainPagePresenter presenter;
    private RecyclerView rvView;

    @Rule
    public ActivityTestRule<RestaurantMainPageActivity> mainActivityActivityTestRule = new ActivityTestRule<>(RestaurantMainPageActivity.class);

    @Before
    public void setup() {
        activity = Robolectric.setupActivity(RestaurantMainPageActivity.class);
        initializeRecyclerView();
    }

    @Test
    public void validateRecyclerViewExists() {
        rvView = (RecyclerView) activity.findViewById(R.id.recycler_view);
        assertNotNull("RecyclerView could not be found", rvView);
    }

    @Test
    public void validateShowRestaurantsCcalled() {
    }

    @Test
    public void validateShowDetailPageWhenClicked() {
        Espresso.registerIdlingResources(mainActivityActivityTestRule.getActivity().getCountingIdlingResource());
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view)).perform(ViewActions.click());
    }

    private void initializeRecyclerView() {
        RecyclerView rvView = (RecyclerView) activity.findViewById(R.id.recycler_view);
        restaurantList = createRestaurantList();
        adapter = new RestaurantMainPageAdapter(activity.getApplicationContext(), restaurantList, listener );
        rvView.setAdapter(adapter);
    }

    private List<Restaurant> createRestaurantList() {
        List<Restaurant> restaurantList = new ArrayList<>();
        for (int i=0; i<10; i++) {
            Restaurant restaurant = new Restaurant();
            restaurant.setName("Restaurant" + i);
            restaurant.setDescription("This is a restaurant");
            restaurant.setId(i);
            restaurant.setAverage_rating(3.5);
            restaurant.setDelivery_fee(0);
            restaurant.setCover_img_url("http://something");
            restaurantList.add(restaurant);
        }
        return restaurantList;
    }
}
