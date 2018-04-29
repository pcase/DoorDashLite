package com.apps.azurehorsecreations.doordashlite.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import java.util.ArrayList;

import javax.inject.Inject;

public class RestaurantDetailPageActivity extends AppCompatActivity implements RestaurantDetailPageContract.View {
    private static final int NUMBER_OF_COLUMNS = 1;
    TextView mEmptyVIew;
    TextView name;
    TextView description;
    TextView averageRating;
    TextView yelpReviewCount;
    Restaurant mRestaurant;

    ListView mListView;
    ArrayList<String> mList;
    RecyclerView mRecyclerView;
    ArrayAdapter<String> mAdapter;
    RestaurantMainPageAdapter mRestaurantAdapter;

    @Inject
    RestaurantDetailPagePresenter detailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_restaurant_detail);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mRestaurant = extras.getParcelable(getResources().getString(R.string.RESTAURANT));
        }

        mEmptyVIew = findViewById(R.id.empty_view);
        name = findViewById(R.id.name);
        description = findViewById(R.id.description);
        averageRating = findViewById(R.id.average_rating);
        yelpReviewCount =findViewById(R.id.yelp_review_count);

        DaggerRestaurantDetailPageComponent.builder()
                .netComponent(((DDLApp) getApplicationContext()).getNetComponent())
                .restaurantDetailPageModule(new RestaurantDetailPageModule(this))
                .build().inject(this);

        detailPresenter.loadRestaurantDetail(mRestaurant.getId());
    }

    @Override
    public void showRestaurantDetail(Restaurant restaurant) {
        if (restaurant.getName() != null) {
            name.setText(restaurant.getName());
        }

        if (restaurant.getDescription() != null) {
            description.setText(restaurant.getDescription());
        }

        if (restaurant.getAverage_rating() != null) {
            averageRating.setText(restaurant.getAverage_rating().toString());
        }

        if (restaurant.getYelp_review_count() != null) {
            yelpReviewCount.setText(restaurant.getYelp_review_count().toString());
        }
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getApplicationContext(), getResources().getString(R.string.Error) + message, Toast.LENGTH_SHORT).show();
        mEmptyVIew.setVisibility(View.VISIBLE);
    }

    @Override
    public void showComplete() {
        Toast.makeText(getApplicationContext(), getResources().getString(R.string.Complete), Toast.LENGTH_SHORT).show();
    }
}
