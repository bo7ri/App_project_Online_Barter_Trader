package ca.dal.cs.csci3130.g01;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Mohamed Al-maimani
 * This class create a scrollable items which can be clicked to view its contents
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>
        implements Filterable{

    // Create a list of product
    private List<Product> productList;
    private List<Product> filteredProduct;

    // DO NOT TOUCH THIS
    // Create a on click listener
    private onClickRecyclerView onClickRecyclerView;


    /**
     * A constructor of recycler view
     * @param productList a list of product specified
     */
    public RecyclerAdapter(List<Product> productList){
        this.productList = productList;
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

                    if(searchResult.equals("ascending")){
                        newProductList = productList;
                        newProductList.sort(new ProductSort());

                    } else if(searchResult.equals("descending")) {
                        newProductList = productList;
                        newProductList.sort(new ProductSort().reversed());

                    } else {
                        filteredList(searchResult, newProductList);
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

    /**
     *
     * @param searchResult
     * @param newProductList
     */
    private void filteredList(String searchResult, List<Product> newProductList) {
        for (Product product: filteredProduct) {
            String titleLowerCase = product.getTitle().toLowerCase();
            if(titleLowerCase.contains(searchResult.toLowerCase())){
                newProductList.add(product);
            }
        }
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
        private TextView item;
        private ImageView itemImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.listItemID);
            itemImage = itemView.findViewById(R.id.productImage);
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
        Product itemName = productList.get(position);
        holder.item.setText(itemName.getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRecyclerView.onClick(itemName);
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
}
