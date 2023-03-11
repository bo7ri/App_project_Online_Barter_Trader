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

    private String title;
    private String description;

    /**
     * A basic product constructor
     * @param title The title of the product
     * @param description The description of the product
     */
    public Product(@NonNull String title, @NonNull String description) {
        this.title = title;
        this.description = description;

    }

    // This is part of Parcelable interface
    public Product() {
    }

    // This is part of Parcelable interface
    protected Product(Parcel in) {
        title = in.readString();
        description = in.readString();
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

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(description);
    }

    // setters and getters
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
