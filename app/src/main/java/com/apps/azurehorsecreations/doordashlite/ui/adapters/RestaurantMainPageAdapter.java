package com.apps.azurehorsecreations.doordashlite.ui.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.apps.azurehorsecreations.doordashlite.data.Restaurant;
import com.apps.azurehorsecreations.doordashlite.R;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * RestaurantMainPageAdapter binds the restaurant data to a ViewHolder for a RecyclerView
 */

public class RestaurantMainPageAdapter extends RecyclerView.Adapter<RestaurantMainPageAdapter.ViewHolder> {
    private List<Restaurant> mRestaurantList;
    private Handler mHandler;
    private Context mContext;
    private final OnItemClickListener mListener;
    private  SharedPreferences pref;
    SharedPreferences.Editor editor;

    public RestaurantMainPageAdapter(Context context, List<Restaurant> restaurants, OnItemClickListener listener) {
        this.mContext = context;
        this.mRestaurantList = restaurants;
        this.mListener = listener;

        pref = mContext.getSharedPreferences("MyPref", 0);
        editor = pref.edit();
    }

    public Restaurant getItem(int id) {
        return mRestaurantList.get(id);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_row_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String name = mRestaurantList.get(position).getName();
        holder.nameTextView.setText(name);
        String description = mRestaurantList.get(position).getDescription();
        holder.descriptionTextView.setText(description);
        String coverImgUrl = mRestaurantList.get(position).getCover_img_url();
        String status = mRestaurantList.get(position).getStatus();
        holder.statusTextView.setText(status);
        Integer deliveryFee = mRestaurantList.get(position).getDelivery_fee();
        holder.deliveryFeeTextView.setText(deliveryFee.toString());
        holder.click(mRestaurantList.get(position), mListener);
        Glide.with(mContext).load(mRestaurantList.get(position).getCover_img_url()).into(holder.coverImageView);
        holder.favoriteButton.setText(getButtonText(mRestaurantList.get(position).getId()));
        holder.favoriteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int id = mRestaurantList.get(position).getId();
                if (isFavorite(id)) {
                    holder.favoriteButton.setText("make unfavorite");
                    saveFavoriteInPrefs(id, "unfavorite");
                } else {
                    holder.favoriteButton.setText("make favorite");
                    saveFavoriteInPrefs(id, "favorite");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRestaurantList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView nameTextView;
        protected TextView descriptionTextView;
        protected TextView statusTextView;
        protected TextView deliveryFeeTextView;
        protected ImageView coverImageView;
        protected Button favoriteButton;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name);
            descriptionTextView = itemView.findViewById(R.id.description);
            statusTextView = itemView.findViewById(R.id.status);
            deliveryFeeTextView = itemView.findViewById(R.id.delivery_fee);
            coverImageView = itemView.findViewById(R.id.cover_image);
            favoriteButton = itemView.findViewById(R.id.favorite_button);
        }

        public void click(final Restaurant restaurant, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(restaurant);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onClick(Restaurant restaurant);
    }

    private void saveFavoriteInPrefs(int id, String favorite) {
        editor.putString(String.valueOf(id), favorite);
        editor.commit();
    }

    private String getButtonText(int id) {
        String buttonText = "make favorite";
        String favorite = pref.getString(String.valueOf(id), "");
        if (favorite != "") {
            if (favorite.equals("favorite")) {
                buttonText =  "make unfavorite";
            } else {
                buttonText = "make favorite";
            }
        }
        return buttonText;
    }

    private boolean isFavorite(int id) {
        boolean isFavorite = false;
        String favorite = pref.getString(String.valueOf(id), "");
        if (favorite != "") {
            if (favorite.equals("favorite")) {
                isFavorite = true;
            } else {
                isFavorite = false;
            }
        }
        return isFavorite;
    }
}
