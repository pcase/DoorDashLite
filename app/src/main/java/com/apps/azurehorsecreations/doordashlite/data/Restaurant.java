package com.apps.azurehorsecreations.doordashlite.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

/**
 * Created by pattycase on 4/27/18.
 */

public class Restaurant implements Parcelable {
    @SerializedName("id")
    public Integer id;

    @SerializedName("name")
    public String name;

    @SerializedName("description")
    public String description;

    @SerializedName("cover_img_url")
    public String cover_img_url;

    @SerializedName("status")
    public String status;

    @SerializedName("delivery_fee")
    public Integer delivery_fee;

    @SerializedName("average_rating")
    public Double average_rating;

    @SerializedName("yelp_review_count")
    public Integer yelp_review_count;

    private Restaurant(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        cover_img_url = in.readString();
        status = in.readString();
        delivery_fee = in.readInt();
        average_rating = in.readDouble();
        yelp_review_count = in.readInt();
    }

    public Restaurant() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCover_img_url() {
        return cover_img_url;
    }

    public void setCover_img_url(String cover_img_url) {
        this.cover_img_url = cover_img_url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getDelivery_fee() {
        return delivery_fee;
    }

    public void setDelivery_fee(Integer delivery_fee) {
        this.delivery_fee = delivery_fee;
    }

    public Double getAverage_rating() {
        return average_rating;
    }

    public void setAverage_rating(Double average_rating) {
        this.average_rating = average_rating;
    }

    public Integer getYelp_review_count() {
        return yelp_review_count;
    }

    public void setYelp_review_count(Integer yelp_review_count) {
        this.yelp_review_count = yelp_review_count;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(cover_img_url);
        dest.writeString(status);
        dest.writeInt(delivery_fee);
        dest.writeDouble(average_rating);
        dest.writeInt(yelp_review_count);
    }

    public static final Creator<Restaurant> CREATOR = new Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };
}
