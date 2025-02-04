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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.ArrayList;
import java.util.List;



/**
 * @author Mohamed Al-Maimani
 * This is main list page for providers products
 */
public class ProvidersListings extends AppCompatActivity implements RecyclerAdapter.OnClickRecyclerView {


    Toolbar toolbar;
    List<Product> productList;
    FirebaseFirestore database;
    RecyclerView recyclerView;
    TextView emptyList;
    List<Product> filteredList;
    RecyclerAdapter adapter;
    List<Product> databaseListProduct;
    private String username;
    private String usertype;
    private String lastName;

    private boolean sortAscending = true;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_providers_listings);

        // Data base set up
        database = FirebaseFirestore.getInstance();

        productList = new ArrayList<>();
        filteredList = new ArrayList<>();
        databaseListProduct = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        emptyList = findViewById(R.id.listIsEmpty);
        toolbar = findViewById(R.id.toolBar);

        // Set data from DB
        setupDataFromDB();


        // Set up recycler view
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // adapter set up
        adapter = new RecyclerAdapter(databaseListProduct, getApplicationContext());
        adapter.setOnClickRecyclerView(this);
        adapter.setFilteredList(databaseListProduct);
        recyclerView.setAdapter(adapter);

        // Get Extra username
        username = getIntent().getStringExtra("username");
        usertype = getIntent().getStringExtra("usertype");
        lastName = getIntent().getStringExtra("lastName");

        // Add product btn
        ImageButton productAddPageButton = findViewById(R.id.productAddBtn);
        productAddPageButton.setOnClickListener(view -> {

            if(usertype.equals("Provider")){
                Intent movingToListPageIntent = new Intent(getApplicationContext(), AddProduct.class);
                movingToListPageIntent.putExtra("username", username);
                movingToListPageIntent.putExtra("usertype", usertype);
                startActivity(movingToListPageIntent);
            } else {
                Toast.makeText(getApplicationContext(), "Only providers can add products", Toast.LENGTH_LONG).show();
            }


        });

    }

    /**
     * Sets data from database
     */
    protected void setupDataFromDB() {

        database.collection("ProductList").get().addOnSuccessListener(queryDocumentSnapshots -> {

            if (queryDocumentSnapshots.isEmpty()){
                return;
            }

            // List of documents from the database
            List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();

            // iterate through the documents and convert them into object named Product
            for (DocumentSnapshot document: snapshotList) {
                Product product = document.toObject(Product.class);

                if (product != null) databaseListProduct.add(product);
            }
            adapter.notifyDataSetChanged();

        });

    }

    /**
     * Hard coded data
     */
    protected void setData() {
        productList.add(new Product("Wooden Table", "Sample text.", "0", "0", R.drawable.table, username, 100, "Nova Scotia", "Halifax"));
        productList.add(new Product("Couch", "Sample text.", "1", "0", R.drawable.couch, username, 100, "Nova Scotia", "Halifax"));
        productList.add(new Product("Painting", "Sample text.", "2", "0", R.drawable.painting, username, 100, "Nova Scotia", "Halifax"));
        productList.add(new Product("Bed", "Sample text.", "2", "0", R.drawable.bed, username, 100, "Nova Scotia", "Halifax"));
        productList.add(new Product("Wooden Chair", "Sample text.", "4", "0", R.drawable.chair, username, 100, "Nova Scotia", "Halifax"));
    }

    /**
     * Hard coded database data
     */
    protected void uploadData(){

        setData();
        for (int i = 0; i < productList.size(); i++) {
            database.collection("ProductList").add(productList.get(i));
        }
    }

    /**
     * This is the click interface on RecyclerAdapter.class
     * @param product the position of the desired content
     */
    @Override
    public void onClick(Product product) {

        viewItemDetails(product);
    }

    /**
     * This method transfers to ItemDetails.class when button clicked and sends Product details
     * @param product product object that has product's details
     */
    public void viewItemDetails(Product product){
        Intent itemDetails = new Intent(getApplicationContext(), ItemDetails.class);
        itemDetails.putExtra("product", product);
        itemDetails.putExtra("username", username);
        itemDetails.putExtra("usertype", usertype);
        itemDetails.putExtra("lastName", lastName);

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
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.profile){
            // transfer to profile activity
            Intent profilePage = new Intent(getApplicationContext(), Profile.class);
            if(username != null) {
                profilePage.putExtra("username", username);
            }
            profilePage.putExtra("usertype", usertype);
            startActivity(profilePage);
        }

        if(item.getItemId() == R.id.sortBtn){
            if(sortAscending){
                adapter.getFilter().filter("ascending");
                sortAscending = false;
            } else {
                adapter.getFilter().filter("descending");
                sortAscending = true;
            }
        }
        else if(item.getItemId() == R.id.savedItems){
            // transfer to saved items page
            Intent savedPage = new Intent(getApplicationContext(), SavedItems.class);
            if(username != null) savedPage.putExtra("username", username);

            startActivity(savedPage);
        }
        else if(item.getItemId() == R.id.logout){
            // transfer to login page
            Intent logout = new Intent(getApplicationContext(), LoginPage.class);
            startActivity(logout);
        }

        return super.onOptionsItemSelected(item);
    }
}
