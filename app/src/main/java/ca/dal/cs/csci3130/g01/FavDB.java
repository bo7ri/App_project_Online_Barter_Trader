package ca.dal.cs.csci3130.g01;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author Ahmed Elghamriny
 * This class uses SQL to store favorites.
 */
public class FavDB extends SQLiteOpenHelper {

    private static int DB_VERSION = 1;
    private static String DATABASE_NAME = "CoffeeDB";
    private static String TABLE_NAME = "favoriteTable";
    public static String KEY_ID = "id";
    public static String ITEM_TITLE = "itemTitle";
    public static String ITEM_IMAGE = "itemImage";
    public static String FAVORITE_STATUS = "fStatus";
    public static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " TEXT," + ITEM_TITLE
            + " TEXT, " + ITEM_IMAGE + " TEXT," + FAVORITE_STATUS + " TEXT)";

    /**
     * Constructore for Database
     * @param context
     */

    public FavDB(Context context){
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    /**
     * Automatically executes query on click
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Leave empty.
    }

    public void insertEmpty(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        for (int x = 1; x < 11; x++){
            cv.put(KEY_ID, x);
            cv.put(FAVORITE_STATUS, "0");

            db.insert(TABLE_NAME, null, cv);
        }
    }

    /**
     * Method to insert the item from providers listing into the database to showcase in the saved items page.
     * @param item_title
     * @param item_image
     * @param id
     * @param fav_status
     */
    public void insertIntoTheDatabase(String item_title, int item_image, String id, String fav_status){
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ITEM_TITLE, item_title);
        cv.put(ITEM_IMAGE, item_image);
        cv.put(KEY_ID, id);
        cv.put(FAVORITE_STATUS, fav_status);
        db.insert(TABLE_NAME, null, cv);
        Log.d("FavDB Status", item_title + ", favstatus - " + fav_status + " - . " + cv);
    }

    /**
     * Method to read data stored in the database
     * @param id
     * @return
     */
    public Cursor readAllData(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from " + TABLE_NAME + " where " + KEY_ID + " = " + id;
        return db.rawQuery(sql,null, null);
    }

    /**
     * Method to remove the favorite from the database
     * @param id
     */
    public void remove_fav(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "UPDATE " + TABLE_NAME + " SET " + FAVORITE_STATUS + "= '0' WHERE " + KEY_ID + "=" +id;
        db.execSQL(sql);
        Log.d("remove", id.toString());
    }

    /**
     * Method to select all the items in the favorite list
     * @return
     */
    public Cursor select_all_favorite_list(){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + FAVORITE_STATUS + " = '1'";
        return db.rawQuery(sql, null, null);
    }
}
