package ca.dal.cs.csci3130.g01;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;


/**
 * This is a classic product contents it implements Parcelable interface
 * @author Mohamed Al-Maimani
 */


public class Product implements Parcelable {
//test

    private int imageResource;
    private String title;
    private String key_id;
    private String favStatus;
    private String description;


    public Product(){

    }

    /**
     * Constructor for Product class.
     * @param title
     * @param description
     * @param key_id
     * @param favStatus
     * @param imageResource
     */
    public Product(String title, String description, String key_id, String favStatus, int imageResource) {
        this.title = title;
        this.description = description;
        this.key_id = key_id;
        this.favStatus = favStatus;
        this.imageResource = imageResource;
    }

    // This is part of Parcelable interface
    protected Product(Parcel in) {
        description = in.readString();
        favStatus = in.readString();
        imageResource = in.readInt();
        key_id = in.readString();
        title = in.readString();
    }

// This is part of Parcelable interface
    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    // This is part of Parcelable interface
    @Override
    public int describeContents() {
        return 0;
    }

    // This is part of Parcelable interface
    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {;
        parcel.writeString(description);
        parcel.writeString(favStatus);
        parcel.writeInt(imageResource);
        parcel.writeString(key_id);
        parcel.writeString(title);
    }

    // setters and getters

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKey_id() {
        return key_id;
    }

    public void setKey_id(String key_id) {
        this.key_id = key_id;
    }

    public String getFavStatus() {
        return favStatus;
    }

    public void setFavStatus(String favStatus) {
        this.favStatus = favStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
