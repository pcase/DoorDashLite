package com.apps.azurehorsecreations.doordashlite;


import android.os.Build;

import com.apps.azurehorsecreations.doordashlite.data.Restaurant;
import com.apps.azurehorsecreations.doordashlite.ui.RestaurantMainPageActivity;
import com.apps.azurehorsecreations.doordashlite.ui.RestaurantMainPageContract;
import com.apps.azurehorsecreations.doordashlite.ui.RestaurantMainPageModule;
import com.apps.azurehorsecreations.doordashlite.ui.RestaurantMainPagePresenter;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subscribers.TestSubscriber;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pattycase on 4/29/18.
 */

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricTestRunner.class)
public class RestaurantMainPagePresenterTest {
    private RestaurantMainPagePresenter mPresenter;
    private RestaurantMainPageModule mockModel;
    private RestaurantMainPageContract.View mockView;
    private RestaurantMainPageActivity activity;
    Retrofit retrofit;
    LatLng latLng;
    List<Restaurant> restaurantList;
    Gson gson;

    @Before
    public void setup() {
        gson = createGson();
        retrofit = createRetrofit();
        latLng = new LatLng(37.422740, -122.139956);
        restaurantList = createRestaurantList();

        // Creating the mocks
        mockView = Mockito.mock(RestaurantMainPageContract.View.class );
//        mockModel = Mockito.mock( RestaurantMainPageModule.class, RETURNS_DEEP_STUBS );

        // Pass the mocks to a Presenter instance
        mPresenter = new RestaurantMainPagePresenter(retrofit, mockView);
        activity = Robolectric.setupActivity(RestaurantMainPageActivity.class);

        final Observable<List<Restaurant>> observable = Observable.just(restaurantList);
    }

    @Test
    public void verifyPresenterCalledView() {
        mPresenter.loadRestaurants(latLng);
//        Mockito.verify(mockView, Mockito.only()).showRestaurants(restaurantList);
        TestSubscriber<Restaurant> testSubscriber = new TestSubscriber<>();

    }

    @Test
    public void verifyPresenterNavigatesToDetailPage() {

    }

    private List<Restaurant> createRestaurantList() {
        List<Restaurant> restaurantList = new ArrayList<>();
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Dennys");
        restaurant.setCover_img_url("");
        restaurant.setDelivery_fee(0);
        restaurant.setId(1);
        restaurant.setDescription("Fast food");
        restaurant.setAverage_rating(3.5);
        restaurant.setNumber_of_ratings(100);
        restaurant.setStatus("22 minutes");
        restaurantList.add(restaurant);
        return restaurantList;
    }

    private Retrofit createRetrofit() {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("https://api.doordash.com/")
                .build();
    }

    private Gson createGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }

}
