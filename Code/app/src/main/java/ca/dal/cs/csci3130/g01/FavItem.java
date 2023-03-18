package ca.dal.cs.csci3130.g01;

/**
 * @author Ahmed Elghamriny
 * This class creates a favorite item with its elements.
 */

public class FavItem {
    private String item_title;
    private String key_id;
    private int item_image;

    public FavItem() {

    }

    /**
     * Constructor for favorite item
     * @param item_title
     * @param key_id
     * @param item_image
     */
    public FavItem(String item_title, String key_id, int item_image){
        this.item_title = item_title;
        this.key_id = key_id;
        this.item_image = item_image;
    }

    //setters and getters

    public String getItem_title() {
        return item_title;
    }

    public void setItem_title(String item_title) {
        this.item_title = item_title;
    }

    public String getKey_id() {
        return key_id;
    }

    public void setKey_id(String key_id) {
        this.key_id = key_id;
    }

    public int getItem_image() {
        return item_image;
    }

    public void setItem_image(int item_image) {
        this.item_image = item_image;
    }
}
