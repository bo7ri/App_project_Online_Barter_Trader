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
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ProviderPage extends AppCompatActivity {

    private Product product;
//    private String lastName;
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
            String username = product.getUsername().trim();
            getUserDataDBAndRating(username);
            getRequestCount(username);
            getTotalValue(username);

        }

    }

    private void getUserDataDBAndRating(String username){
        // Declaration of DocumentReference variables
        database.collection("UserList")
                .whereEqualTo("Username", username)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    if (queryDocumentSnapshots.isEmpty()) return;

                    // List of documents from the database
                    List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();

                    DocumentSnapshot document = snapshotList.get(0);

                    TextView firstName = findViewById(R.id.providerFirstName);
                    firstName.setText(document.get("FirstName").toString());

                    TextView lastNameText =findViewById(R.id.providerLastName);
                    lastNameText.setText(document.get("LastName").toString());

                    TextView userRating = findViewById(R.id.providerRating);
                    String rating = document.get("Rating").toString();
                    userRating.setText(String.format("Rating: %s", rating));


                }).addOnFailureListener(e -> Toast.makeText(getApplicationContext(),"Fail", Toast.LENGTH_LONG).show());
    }

    private void getRequestCount(String username){


        database.collection("RequestList")
                .whereEqualTo("ProviderUsername", username)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    if (queryDocumentSnapshots.isEmpty()) return;

                    // List of documents from the database
                    List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();

                    int counter = 0;

                    for (int i = 0; i < snapshotList.size(); i++) {
                        counter++;
                    }

                    TextView requestCounter = findViewById(R.id.totalRequest);
                    String text = "Total Requests: " + counter;
                    requestCounter.setText(text);

                }).addOnFailureListener(e -> Toast.makeText(getApplicationContext(),"Fail", Toast.LENGTH_LONG).show());



    }

    private void getTotalValue(String username){

        database.collection("TotalValue").whereEqualTo("ProviderUsername", username)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    if (queryDocumentSnapshots.isEmpty()) return;

                    // List of documents from the database
                    List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();

                    double totalValue = 0;
                    for (DocumentSnapshot document : snapshotList){
                        double value = Double.parseDouble(document.get("ProviderTotalValue").toString());
                        totalValue += value;
                    }

                    TextView requestCounter = findViewById(R.id.totalValue);
                    String text = "Total Value: $" + totalValue;
                    requestCounter.setText(text);

                }).addOnFailureListener(e -> Toast.makeText(getApplicationContext(),"Fail", Toast.LENGTH_LONG).show());

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
