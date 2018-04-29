package com.apps.azurehorsecreations.doordashlite.ui;

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
import com.apps.azurehorsecreations.doordashlite.data.Restaurant;
import com.apps.azurehorsecreations.doordashlite.ui.adapters.RestaurantMainPageAdapter;
import com.apps.azurehorsecreations.doordashlite.ui.navigation.RestaurantNavigator;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class RestaurantMainPageActivity extends AppCompatActivity implements RestaurantMainPageContract.View, RestaurantMainPageAdapter.OnItemClickListener {
    private static final int NUMBER_OF_COLUMNS = 1;
    TextView mEmptyVIew;
    ListView mListView;
    ArrayList<String> mList;
    RecyclerView mRecyclerView;
    ArrayAdapter<String> mAdapter;
    RestaurantMainPageAdapter mRestaurantAdapter;

    @Inject
    RestaurantMainPagePresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        mainPresenter.loadRestaurants();
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
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getApplicationContext(), "Error" + message, Toast.LENGTH_SHORT).show();
        mRecyclerView.setVisibility(View.INVISIBLE);
        mEmptyVIew.setVisibility(View.VISIBLE);
    }

    @Override
    public void showComplete() {
        Toast.makeText(getApplicationContext(), "Complete", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(Restaurant restaurant) {
        Toast.makeText(getApplicationContext(), "Clicked on " + restaurant.getName(), Toast.LENGTH_SHORT).show();
        mainPresenter.setNavigator(new RestaurantNavigator(this, RestaurantDetailPageActivity.class, restaurant));
        mainPresenter.navigateToNewScreen();
    }
}
