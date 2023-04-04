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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ProviderPage extends AppCompatActivity {

    private Product product;
    private String lastName;
    FirebaseFirestore database;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_page);

        database = FirebaseFirestore.getInstance();

        // Get custom toolbar
        toolbar = findViewById(R.id.providerPageToolBar);

        // get last name
        lastName = getIntent().getStringExtra("lastName").trim();
        // get product
        product = getIntent().getParcelableExtra("product");
        if(product != null) {
            String username = product.getUsername().trim();
            getUserDataDB(username, lastName);
            getRequestCount(username, lastName);
//            getTotalValue(product.getUsername());
            getRating(username, lastName);
        }

    }

    private void getUserDataDB(String username, String lastName){
        // Declaration of DocumentReference variables
        database.collection("UserList")
                .whereEqualTo("Username", username)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    if (queryDocumentSnapshots.isEmpty()) return;

                    // List of documents from the database
                    List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();

                    for (DocumentSnapshot document : snapshotList){

                        String lastnameDocument = document.get("LastName").toString();
                        if(lastnameDocument.equals(lastName)){
                            TextView firstName = findViewById(R.id.providerFirstName);
                            firstName.setText(document.get("FirstName").toString());

                            TextView lastNameText =findViewById(R.id.providerLastName);
                            lastNameText.setText(lastnameDocument);
                        }

                    }
                }).addOnFailureListener(e -> Toast.makeText(getApplicationContext(),"Fail", Toast.LENGTH_LONG).show());
    }

    private void getRequestCount(String username, String lastName){

        AtomicInteger counter = new AtomicInteger();
        database.collection("RequestList").whereEqualTo("ProviderUsername", username)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    if (queryDocumentSnapshots.isEmpty()) return;

                    // List of documents from the database
                    List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();

                    for (DocumentSnapshot document : snapshotList){
                        String lastnameDocument = document.get("LastName").toString();
                        if(lastnameDocument.equals(lastName)){
                            counter.incrementAndGet();
                        }
                    }
                });

        TextView requestCounter = findViewById(R.id.totalRequest);
        requestCounter.setText(String.format("Total Requests: %d", counter.intValue()));

    }

    private void getTotalValue(String username){
//        AtomicLong counter = new AtomicLong();
//        database.collection("RequestList").whereEqualTo("ProviderUsername", username)
//                .get()
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()){
//                        for (QueryDocumentSnapshot document : task.getResult()){
//                            counter.incrementAndGet();
//                        }
//                    }
//                });
//        TextView requestCounter = findViewById(R.id.totalValue);
//        requestCounter.setText(String.format("Total Value: $%.2f", counter.doubleValue()));
    }

    private void getRating(String username, String lastName){
        database.collection("UserList")
                .whereEqualTo("UserName", username)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    if (queryDocumentSnapshots.isEmpty()) return;

                    // List of documents from the database
                    List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();

                    for (DocumentSnapshot document : snapshotList){
                        String lastNameDocument = document.get("LastName").toString();
                        if(lastNameDocument.equals(lastName)){
                            TextView userRating = findViewById(R.id.providerRating);
                            String rating = document.get("Rating").toString();
                            userRating.setText(String.format("Rating: %s", rating));
                        }
                    }
                });

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
