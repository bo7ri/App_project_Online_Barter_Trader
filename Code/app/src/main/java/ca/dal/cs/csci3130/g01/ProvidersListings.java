package ca.dal.cs.csci3130.g01;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.ArrayList;
import java.util.List;



/**
 * @author Mohamed Al-Maimani
 * This is main list page for providers products
 */
public class ProvidersListings extends AppCompatActivity implements RecyclerAdapter.onClickRecyclerView{


    Toolbar toolbar;
    List<Product> productList;
    FirebaseFirestore database;
    RecyclerView recyclerView;
    TextView emptyList;
    List<Product> filteredList;

    RecyclerAdapter adapter;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_providers_listings);

        productList = new ArrayList<>();
        filteredList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        emptyList = findViewById(R.id.listIsEmpty);
        toolbar = findViewById(R.id.toolBar);


        // Data base set up
        database = FirebaseFirestore.getInstance();

        // set data Locally
        setData();

        // upload data to firebase
//        uploadData();

        // adapter set up
        adapter = new RecyclerAdapter(productList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.setOnClickRecyclerView(this);

        // Set data from DB
//        setupDataDB(adapter);
        //adapter.notifyDataSetChanged();


    }

    /**
     * Sets data from database
     * @param adapter takes the recycler view adapter
     */
    protected void setupDataDB(RecyclerAdapter adapter) {
        database.collection("ProductList").get().addOnSuccessListener(queryDocumentSnapshots -> {

            if (queryDocumentSnapshots.isEmpty()){
                return;
            }

            // List of documents from the database
            List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();

            // iterate through the documents and convert them into object named Product
            for (DocumentSnapshot document: snapshotList) {
                Product product = document.toObject(Product.class);

                if (product != null) productList.add(product);
            }

            for (int i = 0; i < productList.size(); i++) {
                adapter.notifyItemChanged(i);
            }

            if (adapter.getItemCount() == 0) emptyList.append("No products found");
        });
    }

    /**
     * Hard coded data
     */
    protected void setData() {


        productList.add(new Product("Chair","desc" + 1));
        productList.add(new Product("Table","desc" + 1));
        productList.add(new Product("TV","desc" + 1));
        productList.add(new Product("Phone","desc" + 1));
        productList.add(new Product("Wooden chair","desc" + 1));
        productList.add(new Product("laptop","desc" + 1));
        productList.add(new Product("lamp","desc" + 1));

    }

    /**
     * Hard coded database data
     */
    protected void uploadData(){

        for (int i = 0; i < productList.size(); i++) {
            database.collection("ProductList").add(productList.get(i));
        }
    }

    /**
     * This is the click interface on RecyclerAdapter.class
     * @param position the position of the desired content
     */
    @Override
    public void onClick(int position) {
        viewItemDetails(productList.get(position));
    }

    /**
     * This method transfers to ItemDetails.class when button clicked and sends Product details
     * @param product product object that has product's details
     */
    public void viewItemDetails(Product product){
        Intent itemDetails = new Intent(getApplicationContext(), ItemDetails.class);
        itemDetails.putExtra("product", product);
        startActivity(itemDetails);
    }

    /**
     * Inflates the toolbar with search functionality
     * @param menu the menu that has the items
     * @return super onCreateOptionsMenu result
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.general_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type here to search..");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                filteredList.clear();
                emptyList.setText(R.string.EMPTY_STRING);

                for (Product product : productList) {
                    String titleLowerCase = product.getTitle().toLowerCase();
                    if(titleLowerCase.contains(newText.toLowerCase())){
                        filteredList.add(product);
                    }
                }

                if(filteredList.size() > 0){
                    adapter.setProductList(filteredList);
                    adapter.notifyDataSetChanged();
                    return true;

                } else{
                    emptyList.setText(R.string.EMPTY_LIST);
                    return false;
                }
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.profile){
            // TODO
            // transfer to profile activity
        }
        if(item.getItemId() == R.id.logout){
            // TODO
            // transfer to login page
        }

        return super.onOptionsItemSelected(item);
    }
}