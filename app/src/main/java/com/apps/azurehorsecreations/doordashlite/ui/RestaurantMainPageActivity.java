package com.apps.azurehorsecreations.doordashlite.ui;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.apps.azurehorsecreations.doordashlite.DDLApp;
import com.apps.azurehorsecreations.doordashlite.R;
import com.apps.azurehorsecreations.doordashlite.data.LatLng;
import com.apps.azurehorsecreations.doordashlite.data.Restaurant;
import com.apps.azurehorsecreations.doordashlite.ui.adapters.RestaurantMainPageAdapter;
import com.apps.azurehorsecreations.doordashlite.ui.navigation.RestaurantNavigator;
import com.apps.azurehorsecreations.doordashlite.util.EspressoIdlingResource;
import com.apps.azurehorsecreations.doordashlite.util.GPSTracker;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class RestaurantMainPageActivity extends AppCompatActivity implements RestaurantMainPageContract.View, RestaurantMainPageAdapter.OnItemClickListener {
    private static final int NUMBER_OF_COLUMNS = 1;
    private TextView mEmptyVIew;
    private ListView mListView;
    private ArrayList<String> mList;
    private RecyclerView mRecyclerView;
    private ArrayAdapter<String> mAdapter;
    private RestaurantMainPageAdapter mRestaurantAdapter;
    private GPSTracker gpsTracker;
    private LatLng latLng;

    @Inject
    RestaurantMainPagePresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (isConnected()) {
            try {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            latLng = getLocation();

            mEmptyVIew = findViewById(R.id.empty_view);
            mListView = findViewById(R.id.list);
            mRecyclerView = findViewById(R.id.recycler_view);
            mList = new ArrayList<>();

            mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
            final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, NUMBER_OF_COLUMNS);
            mRecyclerView.setLayoutManager(gridLayoutManager);

            DaggerRestaurantMainPageComponent.builder()
                    .netComponent(((DDLApp) getApplicationContext()).getNetComponent())
                    .restaurantMainPageModule(new RestaurantMainPageModule(this))
                    .build().inject(this);

            //Increment the counter before making a network request
            EspressoIdlingResource.increment();

            mainPresenter.loadRestaurants(latLng);
        } else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.No_connection), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void showRestaurants(List<Restaurant> restaurants) {
        for (int i=0; i<restaurants.size(); i++) {
            mList.add(restaurants.get(i).getName());
        }

        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mList);
        mListView.setAdapter(mAdapter);
        mRestaurantAdapter = new RestaurantMainPageAdapter(this, restaurants, this);
        mRecyclerView .setAdapter(mRestaurantAdapter);

        //Decrement after loading the posts
        EspressoIdlingResource.decrement();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getApplicationContext(), getResources().getString(R.string.Error) + message, Toast.LENGTH_SHORT).show();
        mRecyclerView.setVisibility(View.INVISIBLE);
        mEmptyVIew.setVisibility(View.VISIBLE);

        // If there is no network connection we get an error and decrement the counter because the call has finished
        EspressoIdlingResource.decrement();
    }

    @Override
    public void showComplete() {
    }

    @Override
    public void onClick(Restaurant restaurant) {
        mainPresenter.setNavigator(new RestaurantNavigator(this, RestaurantDetailPageActivity.class, restaurant));
        mainPresenter.navigateToNewScreen();
    }

    private boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    private LatLng getLocation(){
        LatLng latLng = new LatLng(0, 0);
        gpsTracker = new GPSTracker(RestaurantMainPageActivity.this);
        if(gpsTracker.canGetLocation()){
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
            latLng.setLatitude(latitude);
            latLng.setLongitude(longitude);
        } else {
            gpsTracker.showSettingsAlert();
        }
        return latLng;
    }

    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }
}
