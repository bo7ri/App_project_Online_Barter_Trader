package ca.dal.cs.csci3130.g01;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class ProductProviderProfile extends AppCompatActivity {
    Toolbar toolbar;
    FirebaseFirestore database;
    String username;
    TextView providerUsername;
    TextView providerRating;
    String providerRatingString;
    TextView providerRatingTotal;
    RatingBar ratingBar;
    float ratingSubmitted;

    int totalRatings;
    String totalRatingsString;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_provider);

        toolbar = findViewById(R.id.toolBar);
        // Get the instance of the Firebase
        database = FirebaseFirestore.getInstance();

        // Setting the providers username through intent from ItemDetails class.
        username = getIntent().getStringExtra("username").trim();
        providerUsername = findViewById(R.id.provider_username);
        providerUsername.setText(username);

        // Setting the rating and number of ratings by calling the getRating and get totalRatings methods
        // which searches through firebase for the fields
        providerRating = findViewById(R.id.provider_rating);
        getRating();
        providerRatingTotal = findViewById(R.id.provider_rating_number);
        getTotalRatings();

        // Setting rating bar listener and capturing the rating set by the user
        ratingBar = findViewById(R.id.rating_bar);
        ratingBar.setOnRatingBarChangeListener((ratingBar, v, b) -> {
            String message = null;
            int ratingHeldInBar = (int) ratingBar.getRating();
            switch (ratingHeldInBar) {
                case 1:
                    message = "Extremely Unpleasant";
                    ratingSubmitted = 1;
                    break;
                case 2:
                    message = "Unpleasant";
                    ratingSubmitted = 2;
                    break;
                case 3:
                    message = "Neutral";
                    ratingSubmitted = 3;
                    break;
                case 4:
                    message = "Pleasant";
                    ratingSubmitted = 4;
                    break;
                case 5:
                    message = "Extremely Pleasant";
                    ratingSubmitted = 5;
                    break;
            }
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });

        // Setting the rating submit button which updates the rating based on the users input
        // from the rating bar
        Button ratingSubmitButton = findViewById(R.id.rate_button);
        ratingSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTotalRatings();
                updateRating();
            }
        });
    }

    /**
     * Gets the rating set from firebase and sets the text view on the app layout.
     */
    public void getRating(){
        CollectionReference users = database.collection("UserList");
        Query query = users.whereEqualTo("Username", username);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                database.collection("UserList").document(document.getId())
                                        .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                               providerRatingString = documentSnapshot.getString("Rating");
                                                providerRating.setText(providerRatingString);
                                         }
                                        });
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    /**
     * Calculates new average rating and updates it on firebase
     */
    public void updateRating(){
        float averageOld = Float.parseFloat(providerRatingString);
        float averageNew = averageOld + (ratingSubmitted - averageOld)/totalRatings;
        String newRatingString = String.format("%.1f", averageNew);
        Map<String, Object> ratingUpdate = new HashMap<>();
        ratingUpdate.put("Rating", newRatingString);

        CollectionReference users = database.collection("UserList");
        Query query = users.whereEqualTo("Username", username);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                        database.collection("UserList").whereEqualTo("Rating", newRatingString).get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        database.collection("UserList").document(document.getId()).update(ratingUpdate);
                                    }
                                });
                    }
                }
            }});
    }

    /**
     *  Gets the total rating set from firebase and sets the text view on the app layout.
     */
    public void getTotalRatings(){
        CollectionReference users = database.collection("UserList");
        Query query = users.whereEqualTo("Username", username);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                        database.collection("UserList").document(document.getId())
                                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        totalRatingsString = "(" + documentSnapshot.getString("numberOfRatings") + ")";
                                        providerRatingTotal.setText(totalRatingsString);
                                    }
                                });
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }

    /**
     * Updates the total ratings in firebase database after a user submits a rating on a provider.
     */
    public void updateTotalRatings(){
        totalRatings = Integer.parseInt(totalRatingsString.substring(1, totalRatingsString.length() - 1));
        totalRatings++;
        totalRatingsString = Integer.toString(totalRatings);
        Map<String, Object> ratingUpdate = new HashMap<>();
        ratingUpdate.put("numberOfRatings", totalRatingsString);

        CollectionReference users = database.collection("UserList");
        Query query = users.whereEqualTo("Username", username);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                        database.collection("UserList").whereEqualTo("numberOfRatings", totalRatingsString).get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        database.collection("UserList").document(document.getId()).update(ratingUpdate);
                                    }
                                });
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }});

    }

    /**
     * Inflates the toolbar with items
     * @param menu the menu that has the items
     * @return super onCreateOptionsMenu result
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile_menu, menu);


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
            // transfer to home page
            Intent home = new Intent(getApplicationContext(), ProvidersListings.class);
            startActivity(home);
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
