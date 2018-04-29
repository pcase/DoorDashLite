package com.apps.azurehorsecreations.doordashlite.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.apps.azurehorsecreations.doordashlite.DDLApp;
import com.apps.azurehorsecreations.doordashlite.R;
import com.apps.azurehorsecreations.doordashlite.data.Restaurant;
import com.apps.azurehorsecreations.doordashlite.ui.adapters.RestaurantMainPageAdapter;
import com.apps.azurehorsecreations.doordashlite.util.Utilities;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import javax.inject.Inject;

public class RestaurantDetailPageActivity extends AppCompatActivity implements RestaurantDetailPageContract.View {
    private static final int NUMBER_OF_COLUMNS = 1;
    TextView mEmptyVIew;
    ImageView coverImage;
    TextView name;
    TextView description;
    TextView averageRating;
    TextView deliveryFee;
    TextView status;
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
        coverImage = findViewById(R.id.cover_image);
        name = findViewById(R.id.name);
        description = findViewById(R.id.description);
        averageRating = findViewById(R.id.average_rating);
        deliveryFee = findViewById(R.id.delivery_fee);
        status = findViewById(R.id.status);

        DaggerRestaurantDetailPageComponent.builder()
                .netComponent(((DDLApp) getApplicationContext()).getNetComponent())
                .restaurantDetailPageModule(new RestaurantDetailPageModule(this))
                .build().inject(this);

        detailPresenter.loadRestaurantDetail(mRestaurant.getId());
    }

    @Override
    public void showRestaurantDetail(Restaurant restaurant) {
        if (restaurant.getCover_img_url() != null) {
            Glide.with(this).load(restaurant.getCover_img_url()).into(coverImage);
        }

        if (restaurant.getName() != null) {
            name.setText(restaurant.getName());
        }

        if (restaurant.getDescription() != null) {
            description.setText(restaurant.getDescription());
        }

        if (restaurant.getAverage_rating() != null && restaurant.getNumber_of_ratings() != null ) {
            averageRating.setText(getRatingText(restaurant.getAverage_rating().toString(), restaurant.getNumber_of_ratings().toString()));
        }

        if (restaurant.getDelivery_fee() != null) {
            deliveryFee.setText(getResources().getString(R.string.delivery) + " " + Utilities.getFormattedAmount(restaurant.getDelivery_fee()));
        }

        if (restaurant.getStatus() != null) {
            status.setText(getResources().getString(R.string.status) + " " + restaurant.getStatus());
        }
    }

    private String getRatingText(String rating, String reviewCount) {
        StringBuilder ratingStringBuilder = new StringBuilder();
        ratingStringBuilder.append(getResources().getString(R.string.average_rating));
        ratingStringBuilder.append(" " + rating + " (" + reviewCount + " ");
        ratingStringBuilder.append(getResources().getString(R.string.reviews) + ")");
        return ratingStringBuilder.toString();
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
