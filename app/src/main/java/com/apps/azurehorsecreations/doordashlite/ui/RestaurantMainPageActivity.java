package com.apps.azurehorsecreations.doordashlite.ui;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.apps.azurehorsecreations.doordashlite.DDLApp;
import com.apps.azurehorsecreations.doordashlite.R;
import com.apps.azurehorsecreations.doordashlite.data.Restaurant;
import com.apps.azurehorsecreations.doordashlite.ui.adapters.RestaurantMainPageAdapter;
import com.apps.azurehorsecreations.doordashlite.ui.navigation.RestaurantNavigator;
import com.apps.azurehorsecreations.doordashlite.util.EspressoIdlingResource;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * RestaurantMainPageActivity
 * Displays a list of nearby restaurants
 *
 * Note: some of the location code is from https://en.proft.me/2017/04/17/how-get-location-latitude-longitude-gps-android/
 */

public class RestaurantMainPageActivity extends AppCompatActivity implements RestaurantMainPageContract.View, RestaurantMainPageAdapter.OnItemClickListener, LocationListener {
    private final static String TAG = "RestaurantMainPageAct";
    private final static double DOORDASH_LATITUDE = 37.422740;
    private final static double DOORDASH_LONGITUDE = -122.139956;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;
    private final static int ALL_PERMISSIONS_RESULT = 101;
    private static final int NUMBER_OF_COLUMNS = 1;
    private TextView mEmptyVIew;
    private ListView mListView;
    private ArrayList<String> mList;
    private RecyclerView mRecyclerView;
    private ArrayAdapter<String> mAdapter;
    private RestaurantMainPageAdapter mRestaurantAdapter;
    protected ProgressBar mProgressBar;

    LocationManager locationManager;
    Location loc;
    ArrayList<String> permissions = new ArrayList<>();
    ArrayList<String> permissionsToRequest;
    ArrayList<String> permissionsRejected = new ArrayList<>();
    boolean isGPS = false;
    boolean isNetwork = false;
    boolean canGetLocation = true;

    @Inject
    RestaurantMainPagePresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (isConnected()) {
            requestAndGetLocation();
        } else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.No_connection), Toast.LENGTH_LONG).show();
        }
    }

    /* update UI
     * After the location is obtained, get the restaurant list and display it
     */
    private void updateUI(Location loc) {
        mProgressBar = findViewById(R.id.progressbar);
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

        // Increment the counter before making a network request
        EspressoIdlingResource.increment();

        mainPresenter.loadRestaurants(loc);
    }

    /*
     * showRestaurants
     * Display the restaurants provided by the presenter
     */
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

    /*
     * showError
     * Display an error message in a Toast, and show the empty list view
     */
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
    public void showProgress() {
        mProgressBar.setVisibility(android.view.View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(android.view.View.INVISIBLE);
    }

    @Override
    public void onClick(Restaurant restaurant) {
        mainPresenter.setNavigator(new RestaurantNavigator(this, RestaurantDetailPageActivity.class, restaurant));
        mainPresenter.navigateToNewScreen();
    }

    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }

    /*
     * requestAndGetLocation
     * Check and request permissions then call getLocation()
     */
    private void requestAndGetLocation() {
        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);
        isGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        permissionsToRequest = findUnAskedPermissions(permissions);

        if (!isGPS && !isNetwork) {
            Log.d(TAG, "Connection off");
            showSettingsAlert();
            getLastLocation();
        } else {
            Log.d(TAG, "Connection on");
            // Check permissions
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (permissionsToRequest.size() > 0) {
                    requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]),
                            ALL_PERMISSIONS_RESULT);
                    Log.d(TAG, "Permission requests");
                    canGetLocation = false;
                }
            }

            // Get location
            getLocation();
        }
    }

    /*
     * isConnected
     * Checks network connectivity
     */
    private boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    /*
     * getLocation
     * Gets current location and calls updateUI
     */
    private void getLocation() {
        try {
            if (canGetLocation) {
                Log.d(TAG, "Can get location");
                if (isGPS) {
                    // from GPS
                    Log.d(TAG, "GPS on");
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    if (locationManager != null) {
                        loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (loc != null)
                            updateUI(loc);
                    }
                } else if (isNetwork) {
                    // from Network Provider
                    Log.d(TAG, "NETWORK_PROVIDER on");
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    if (locationManager != null) {
                        loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (loc != null)
                            updateUI(loc);
                    }
                } else {
                    loc = new Location("");
                    loc.setLatitude(DOORDASH_LATITUDE);
                    loc.setLongitude(DOORDASH_LONGITUDE);
                    updateUI(loc);
                }
            } else {
                Log.d(TAG, "Can't get location");
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    /*
     * getLastLocation
     * Gets last known location
     */
    private void getLastLocation() {
        try {
            Criteria criteria = new Criteria();
            String provider = locationManager.getBestProvider(criteria, false);
            Location location = locationManager.getLastKnownLocation(provider);
            Log.d(TAG, provider);
            Log.d(TAG, location == null ? "NO LastLocation" : location.toString());
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    /*
     * findUnaskedPermssions
     * Returns list of unasked permissions
     */
    private ArrayList findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList result = new ArrayList();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    /*
     * hasPermissions
     * Checks permissions
     */
    private boolean hasPermission(String permission) {
        if (canAskPermission()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    /*
     * canAskPermission
     * On Android 6.0 and higher, apps need to ask for needed permissions at runtime
     */
    private boolean canAskPermission() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    /*
     * onRequestPermissionsResult
     * Called back from requestionPermissions. Checks result of permissions request.
     * If granted, calls getLocation.
     */
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (permissions != null && permissionsToRequest != null) {
            permissionsRejected.clear();
            switch (requestCode) {
                case ALL_PERMISSIONS_RESULT:
                    Log.d(TAG, "onRequestPermissionsResult");
                    for (String perms : permissionsToRequest) {
                        if (!hasPermission(perms)) {
                            permissionsRejected.add(perms);
                        }
                    }

                    if (permissionsRejected.size() > 0) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                                showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(permissionsRejected.toArray(
                                                            new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    } else {
                        Log.d(TAG, "No rejected permissions.");
                        canGetLocation = true;
                        getLocation();
                    }
                    break;
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        updateUI(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        getLocation();
    }

    @Override
    public void onProviderDisabled(String provider) {
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }

    /*
     * showSettingsAlert
     * Displays alert dialog if GPS is not enabled
     */
    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("GPS is not Enabled!");
        alertDialog.setMessage("Do you want to turn on GPS?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }

    /*
     * showMessageOKCancel
     * Displays OK-Cancel dialog
     */
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
}
