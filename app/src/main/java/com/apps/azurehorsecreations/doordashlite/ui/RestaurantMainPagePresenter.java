package com.apps.azurehorsecreations.doordashlite.ui;

import android.location.Location;
import com.apps.azurehorsecreations.doordashlite.data.Restaurant;
import com.apps.azurehorsecreations.doordashlite.ui.navigation.RestaurantNavigator;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * RestaurantMainPagePresenter handles the network calls to get the restaurant information
 * and calls the view's methods with the results.
 */

public class RestaurantMainPagePresenter implements RestaurantMainPageContract.Presenter {
    private static final int OFFSET = 0;
    private static final int LIMIT = 50;
    public Retrofit retrofit;
    RestaurantMainPageContract.View mView;
    RestaurantNavigator mNavigator;

    @Inject
    public RestaurantMainPagePresenter(Retrofit retrofit, RestaurantMainPageContract.View mView) {
        this.retrofit = retrofit;
        this.mView = mView;
    }

    /*
     * loadRestaurants
     * Request the restaurant list and send it back to the view
     */
    @Override
    public void loadRestaurants(Location location) {

        mView.showProgress();

        retrofit.create(RestaurantService.class).getRestaurantList(OFFSET, LIMIT, location.getLatitude(), location.getLongitude()).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .unsubscribeOn(Schedulers.io())
            .subscribe(new Observer<List<Restaurant>>() {

            @Override
            public void onSubscribe (Disposable d){
            }

            @Override
            public void onComplete () {
                mView.hideProgress();
                mView.showComplete();
            }

            @Override
            public void onError (Throwable e){
                mView.hideProgress();
                mView.showError(e.getMessage());
            }

            @Override
            public void onNext (List<Restaurant> restaurants) {
                mView.hideProgress();
                mView.showRestaurants(restaurants);
            }
        });
    }

    public void setNavigator(RestaurantNavigator navigator) {
        this.mNavigator = navigator;
    }

    public void navigateToNewScreen() {
        this.mNavigator.launchActivity();
    }

    public interface RestaurantService {
        @GET("/v2/restaurant/")
        Observable<List<Restaurant>> getRestaurantList(
                @Query("offset") int offset,
                @Query("limit") int limit,
                @Query("lat") double lat,
                @Query("lng") double lng
        );
    }
}
