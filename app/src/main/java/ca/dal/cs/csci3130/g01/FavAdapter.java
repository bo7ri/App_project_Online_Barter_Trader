package ca.dal.cs.csci3130.g01;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * @author Ahmed Elghamriny
 * This class creates a list view of the favorited items.
 */

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder> {

    private Context context;
    private List<FavItem> favItemList;
    private FavDB favDB;

    //test


    /**
     * Constructor for Favorite Adapter
     * @param context context of the current application
     * @param favItemList list of the items that are favorites.
     */
    public FavAdapter(Context context, List<FavItem> favItemList){
        this.context = context;
        this.favItemList = favItemList;
    }

    /**
     * Initializes the context of the view and sets up the database
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_items, parent, false);
        favDB = new FavDB(context);
        return new ViewHolder(view);
    }

    /**
     * Holder to bind the elements of the item
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.item_title.setText(favItemList.get(position).getItem_title());
        holder.imageView.setImageResource(favItemList.get(position).getItem_image());

    }

    /**
     *
     * @return size of the number of favorites
     */
    @Override
    public int getItemCount() {
        return favItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView item_title;
        Button favBtn;
        ImageView imageView;

        /**
         * Fetches and initializes elements of item.
         * @param itemView
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_title = itemView.findViewById(R.id.favTextView);
            favBtn = itemView.findViewById(R.id.favBtn);
            imageView = itemView.findViewById(R.id.favImageView);

            favBtn.setOnClickListener(v -> {
                int position = getAdapterPosition();
                final FavItem favItem = favItemList.get(position);
                favDB.remove_fav(favItem.getKey_id());
                removeItem(position);
            });
        }
    }

    /**
     * Method to remove the item from the favorite list.
     * @param position
     */
    private void removeItem(int position) {
        favItemList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, favItemList.size());
    }
}
