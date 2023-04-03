package ca.dal.cs.csci3130.g01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ProviderPage extends AppCompatActivity {

    private Product product;

    FirebaseFirestore database;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_page);

        database = FirebaseFirestore.getInstance();

        // Get custom toolbar
        toolbar = findViewById(R.id.providerPageToolBar);

        // get product
        product = getIntent().getParcelableExtra("product");
        if(product != null) {
            getUserDataDB(product.getUsername());
            getRequestCount(product.getUsername());
            getTotalValue(product.getUsername());
        }
    }

    private void getUserDataDB(String username){
        // Declaration of DocumentReference variables
        database.collection("UserList").whereEqualTo("Username", username)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        for (QueryDocumentSnapshot document : task.getResult()){
                            TextView firstName = findViewById(R.id.providerFirstName);
                            firstName.setText(document.get("FirstName").toString());

                            TextView lastName =findViewById(R.id.providerLastName);
                            lastName.setText(document.get("LastName").toString());
                        }

                    }
                });
    }

    private void getRequestCount(String username){

        AtomicInteger counter = new AtomicInteger();
        database.collection("RequestList").whereEqualTo("ProviderUsername", username)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        for (QueryDocumentSnapshot document : task.getResult()){
                            counter.incrementAndGet();
                        }
                    }
                });
        TextView requestCounter = findViewById(R.id.totalRequest);
        requestCounter.setText(String.format("Total Requests: %d", counter.intValue()));

    }

    private void getTotalValue(String username){
        AtomicLong counter = new AtomicLong();
        database.collection("RequestList").whereEqualTo("ProviderUsername", username)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        for (QueryDocumentSnapshot document : task.getResult()){
                            counter.incrementAndGet();
                        }
                    }
                });
        TextView requestCounter = findViewById(R.id.totalValue);
        requestCounter.setText(String.format("Total Value: $%.2f", counter.doubleValue()));
    }

    /**
     * Inflates the toolbar with items
     * @param menu the menu that has the items
     * @return super onCreateOptionsMenu result
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.item_details_menu, menu);


        return super.onCreateOptionsMenu(menu);
    }

    /**
     * When clicked on item in the toolbar go to desired activity
     * @param item item in the toolbar
     * @return super onCreateOptionsMenu result
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.homeButton){
            // transfer to home pahe
            Intent home = new Intent(getApplicationContext(), ProvidersListings.class);
            startActivity(home);
        }
        else if(item.getItemId() == R.id.profile){
            // transfer to profile activity
            Intent profilePage = new Intent(getApplicationContext(), Profile.class);
            if(product != null) profilePage.putExtra("product", product);
            startActivity(profilePage);
        }
        else if(item.getItemId() == R.id.savedItems){
            // transfer to saved items page
            Intent savedPage = new Intent(getApplicationContext(), SavedItems.class);
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
