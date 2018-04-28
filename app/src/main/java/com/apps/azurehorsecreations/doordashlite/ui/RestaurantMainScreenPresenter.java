package com.apps.azurehorsecreations.doordashlite.ui;

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
 * Created by pattycase on 4/27/18.
 */

public class RestaurantMainScreenPresenter implements RestaurantMainScreenContract.Presenter {
    Retrofit retrofit;
    RestaurantMainScreenContract.View mView;
    RestaurantNavigator mNavigator;

    @Inject
    public RestaurantMainScreenPresenter(Retrofit retrofit, RestaurantMainScreenContract.View mView) {
        this.retrofit = retrofit;
        this.mView = mView;
    }

    @Override
    public void loadRestaurants() {
        retrofit.create(RestaurantService.class).getRestaurantList(37.422740,-122.139956).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .unsubscribeOn(Schedulers.io())
            .subscribe(new Observer<List<Restaurant>>() {

            @Override
            public void onSubscribe (Disposable d){
            }

            @Override
            public void onComplete () {
                mView.showComplete();
            }

            @Override
            public void onError (Throwable e){
                mView.showError(e.getMessage());
            }

            @Override
            public void onNext (List<Restaurant> restaurants) {
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
                @Query("lat") double lat,
                @Query("lng") double lng
        );
    }
}
