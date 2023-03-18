package ca.dal.cs.csci3130.g01;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mohamed Al-maimani
 * This class create a scrollable items which can be clicked to view its contents
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> implements Filterable {

    // Create a list of product
    private List<Product> productList;
    private List<Product> filteredProduct;

    // DO NOT TOUCH THIS
    // Create a on click listener
    private onClickRecyclerView onClickRecyclerView;

    private FavDB favDB;

    private Context context;


    /**
     * A constructor of recycler view
     * @param productList a list of product specified
     */
    public RecyclerAdapter(List<Product> productList, Context context){
        this.productList = productList;
        this.context = context;
    }

    /**
     * A method for setting filtered data
     * @param filteredProduct A new list of items
     */
    public void setFilteredList(List<Product> filteredProduct) {
        this.filteredProduct = filteredProduct;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults results = new FilterResults();
                if(charSequence == null || charSequence.length() == 0){
                    results.values = filteredProduct;
                    results.count = filteredProduct.size();

                } else {
                    String searchResult = charSequence.toString().toLowerCase();
                    List<Product> newProductList = new ArrayList<>();

                    for (Product product: filteredProduct) {
                        String titleLowerCase = product.getTitle().toLowerCase();
                        if(titleLowerCase.contains(searchResult.toLowerCase())){
                            newProductList.add(product);
                        }
                    }

                    results.values = newProductList;
                    results.count = newProductList.size();
                }
                return results;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                productList = (ArrayList<Product>) filterResults.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }

    // DO NOT TOUCH THIS
    /**
     * An interface for onCLick listener for recycler view
     */
    public interface onClickRecyclerView{
        void onClick(Product product);
    }

    // DO NOT TOUCH THIS
    /**
     * A method for setting onClick listener
     * @param onClickRecyclerView A listener
     */
    public void setOnClickRecyclerView(RecyclerAdapter.onClickRecyclerView onClickRecyclerView) {
        this.onClickRecyclerView = onClickRecyclerView;
    }

    /**
     * A nested public class for holding the layout
     */
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView itemName;
        TextView itemDescription;
        ImageView itemImage;
        Button favBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_name);
            itemImage = itemView.findViewById(R.id.item_image);
            favBtn = itemView.findViewById(R.id.favBtn);

            favBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Product product = productList.get(position);

                    if(product.getFavStatus().equals("0")){
                        product.setFavStatus("1");
                        favDB.insertIntoTheDatabase(product.getTitle(), product.getImageResource(), product.getKey_id(), product.getFavStatus());
                        favBtn.setBackgroundResource(R.drawable.baseline_bookmark_active_24);
                    }
                    else {
                        product.setFavStatus("0");
                        favDB.remove_fav(product.getKey_id());
                        favBtn.setBackgroundResource(R.drawable.baseline_bookmark_inactive_24);
                    }
                }
            });
        }
    }


    /**
     * Creates the recycler view
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return View that holds all the populated items
     */
    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        favDB = new FavDB(context);
        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);
        if(firstStart){
            createTableOnFirstStart();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Binds each contents of layout
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        Product product = productList.get(position);

        readCursorData(product, holder);
        holder.itemImage.setImageResource(R.drawable.no_image_found_default);
        holder.itemName.setText(product.getTitle());

        holder.favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(product.getFavStatus().equals("0")){
                    product.setFavStatus("1");
                    favDB.insertIntoTheDatabase(product.getTitle(), product.getImageResource(), product.getKey_id(), product.getFavStatus());
                    holder.favBtn.setBackgroundResource(R.drawable.baseline_bookmark_active_24);
                }
                else {
                    product.setFavStatus("0");
                    favDB.remove_fav(product.getKey_id());
                    holder.favBtn.setBackgroundResource(R.drawable.baseline_bookmark_inactive_24);
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRecyclerView.onClick(product);
            }
        });

    }

    /**
     * Returns the size of the adapter
     */
    @Override
    public int getItemCount() {
        return productList.size();
    }

    private void createTableOnFirstStart() {
        favDB.insertEmpty();

        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }

    private void readCursorData(Product product, ViewHolder viewHolder) {
        Cursor cursor = favDB.readAllData(product.getKey_id());
        SQLiteDatabase db = favDB.getReadableDatabase();
        try{
            while(cursor.moveToNext()){
                @SuppressLint("Range") String item_fav_status = cursor.getString(cursor.getColumnIndex(FavDB.FAVORITE_STATUS));
                product.setFavStatus(item_fav_status);

                if(item_fav_status != null && item_fav_status.equals("1")){
                    viewHolder.favBtn.setBackgroundResource(R.drawable.baseline_bookmark_active_24);
                }
                else if (item_fav_status != null && item_fav_status.equals("0")) {
                    viewHolder.favBtn.setBackgroundResource(R.drawable.baseline_bookmark_inactive_24);
                }
            }
        } finally {
            if(cursor != null && cursor.isClosed()){
                cursor.close();
                db.close();
            }
        }
    }
}
