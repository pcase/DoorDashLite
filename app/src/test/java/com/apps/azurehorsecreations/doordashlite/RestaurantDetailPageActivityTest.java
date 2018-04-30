package com.apps.azurehorsecreations.doordashlite;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.MediumTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.apps.azurehorsecreations.doordashlite.data.Restaurant;
import com.apps.azurehorsecreations.doordashlite.ui.RestaurantDetailPageActivity;
import com.apps.azurehorsecreations.doordashlite.ui.RestaurantDetailPageContract;
import com.apps.azurehorsecreations.doordashlite.ui.RestaurantDetailPagePresenter;
import com.apps.azurehorsecreations.doordashlite.ui.RestaurantMainPageActivity;
import com.apps.azurehorsecreations.doordashlite.ui.RestaurantMainPagePresenter;
import com.apps.azurehorsecreations.doordashlite.ui.adapters.RestaurantMainPageAdapter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowApplication;

import java.util.ArrayList;
import java.util.List;

import static android.app.PendingIntent.getActivity;
import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.v4.content.ContextCompat.startActivity;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by pattycase on 4/29/18.
 */

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricTestRunner.class)
public class RestaurantDetailPageActivityTest {
    private RestaurantDetailPageActivity activity;

    @Mock
    RestaurantDetailPageContract.Presenter presenter;

    @Test
    public void RestaurantDetailPageActivityShouldGetIntent() {
        Intent expectedIntent = createIntent(1);
        Activity activity = Robolectric.buildActivity(RestaurantDetailPageActivity.class, expectedIntent).create().get();
        assertEquals(expectedIntent, activity.getIntent());
    }

    @Test
    public void validateActivityUI() {
        Activity activity = createActivityWithIntent();
        TextView name = activity.findViewById(R.id.name);
        TextView description = activity.findViewById(R.id.description);
        TextView averageRating = activity.findViewById(R.id.average_rating);
        TextView deliveryFee = activity.findViewById(R.id.delivery_fee);
        TextView status = activity.findViewById(R.id.status);
        assertNotNull(name.getText());
        assertNotNull(description.getText());
        assertNotNull(averageRating.getText());
        assertNotNull(deliveryFee.getText());
        assertNotNull(status.getText());
    }

    @Test
    public void validateshowRestaurantDetailCalled() {
    }

    private Intent createIntent(int id) {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(id);
        restaurant.setName("John's");
        restaurant.setStatus("10 minutes");
        restaurant.setNumber_of_ratings(100);
        restaurant.setAverage_rating(3.5);
        restaurant.setDescription("Casual");
        restaurant.setDelivery_fee(0);
        restaurant.setCover_img_url("https://github.com/");
        InstrumentationRegistry.getTargetContext();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.putExtra("RESTAURANT", restaurant);
        return intent;
    }

    private Activity createActivityWithIntent() {
        InstrumentationRegistry.getTargetContext();
        Intent expectedIntent = createIntent(1);
        return Robolectric.buildActivity(RestaurantDetailPageActivity.class, expectedIntent).create().get();
    }
}
