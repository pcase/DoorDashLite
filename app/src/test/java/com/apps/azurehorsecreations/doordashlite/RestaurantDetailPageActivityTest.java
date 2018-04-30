package com.apps.azurehorsecreations.doordashlite;

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

import java.util.ArrayList;
import java.util.List;

import static android.app.PendingIntent.getActivity;
import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.v4.content.ContextCompat.startActivity;
import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
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
//
//    @Mock
//    RestaurantDetailPageContract.Presenter presenter;
//
//
//    @MediumTest
//    @RunWith(AndroidJUnit4.class)
//    public class SecondActivityTest {
//
//        @Rule
//        public ActivityTestRule<RestaurantDetailPageActivity> rule  = new ActivityTestRule<RestaurantDetailPageActivity>(RestaurantDetailPageActivity.class)
//        {
//            @Override
//            protected Intent getActivityIntent() {
//                Restaurant restaurant = new Restaurant();
//                restaurant.setId(1);
//                InstrumentationRegistry.getTargetContext();
//                Intent intent = new Intent(Intent.ACTION_MAIN);
//                intent.putExtra("RESTAURANT", restaurant);
//                return intent;
//            }
//        };
//
//        @Test
//        public void ensureIntentDataIsDisplayed() throws Exception {
//            RestaurantDetailPageActivity activity = rule.getActivity();
//            View viewById = activity.findViewById(R.id.name);
//            assertThat(viewById, instanceOf(TextView.class));
//        }
    //    }

    @Before
    public void setup() {
//        activity = Robolectric.setupActivity(RestaurantDetailPageActivity.class);
    }

    @Test
    public void validateActivityUI() {
    }

    @Test
    public void validateshowRestaurantDetailCalled() {

    }
}
