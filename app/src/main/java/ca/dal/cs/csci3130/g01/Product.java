package ca.dal.cs.csci3130.g01;



import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Spinner;


import androidx.annotation.NonNull;


/**
 * This is a classic product contents it implements Parcelable interface
 * @author Mohamed Al-Maimani
 */


public class Product implements Parcelable {

    private int imageResource;
    private String title;
    private String key_id;
    private String favStatus;
    private String description;
    private String username;
    private double price;

    private String province;
    private String city;
    /**
     * A basic product constructor
     * @param title The title of the product
     * @param description The description of the product
     */
    public Product(@NonNull String title, @NonNull String description, @NonNull String username) {
        this.title = title;
        this.description = description;
        this.username = username;
    }

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
    public Product(String title, String description, String key_id, String favStatus, int imageResource, String username, double price, String province, String city) {
        this.title = title;
        this.description = description;
        this.key_id = key_id;
        this.favStatus = favStatus;
        this.imageResource = imageResource;
        this.username = username;
        this.price = price;
        this.province = province;
        this.city = city;
    }

    // This is part of Parcelable interface
    protected Product(Parcel in) {
        description = in.readString();
        username = in.readString();
        favStatus = in.readString();
        imageResource = in.readInt();
        key_id = in.readString();
        title = in.readString();
        price = in.readDouble();
        province = in.readString();
        city = in.readString();
    }

    // This is part of Parcelable interface
    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(description);
        parcel.writeString(username);
        parcel.writeString(favStatus);
        parcel.writeInt(imageResource);
        parcel.writeString(key_id);
        parcel.writeString(title);
        parcel.writeDouble(price);
        parcel.writeString(province);
        parcel.writeString(city);
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

    public String getUsername() { return username; }

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

    public void setUsername(String username) {
        this.username = username;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }



}
