package ca.dal.cs.csci3130.g01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

/**
 * An activity for displaying a list of emails in an inbox.
 */
public class Inbox extends AppCompatActivity {

    private FirebaseFirestore db;
    private RecyclerView recyclerView;
    private EmailAdapter emailAdapter;
    private List<Email> emails;
    private String username;
    private String usertype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);

        // configure RecyclerView in the layout
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create a new list of Email objects
        emails = new ArrayList<>();
        // Create a new EmailAdapter to display them
        emailAdapter = new EmailAdapter(this, emails);
        recyclerView.setAdapter(emailAdapter);

        // Get the Firestore database instance
        db = FirebaseFirestore.getInstance();

        // Get the email address of the recipient from the profile page
        Intent intent = getIntent();
        String recipientEmail = intent.getStringExtra("recipientEmail");
        username = getIntent().getStringExtra("username");
        usertype = getIntent().getStringExtra("usertype");

        // Retrieve the emails from Firestore
        if (recipientEmail != null) {
            db.collection("Emails")
                    .whereEqualTo("recipientEmail", recipientEmail)
                    .addSnapshotListener((value, error) -> {
                        if (error != null) {
                            // Display an error message if there was an error retrieving the emails
                            Toast.makeText(Inbox.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // For each email document retrieved from Firestore, add the corresponding Email object to the list and notify the adapter of the change
                        for (DocumentChange documentChange : value.getDocumentChanges()) {
                            if (documentChange.getType() == DocumentChange.Type.ADDED) {
                                Email email = documentChange.getDocument().toObject(Email.class);
                                emails.add(email);
                                emailAdapter.notifyDataSetChanged();
                            }
                        }
                    });
        } else {
            // Display an error message if the recipient email address was not found in the intent
            Toast.makeText(Inbox.this, "Error: Recipient email not found.", Toast.LENGTH_SHORT).show();
        }
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
            home.putExtra("username", username);
            home.putExtra("usertype", usertype);
            startActivity(home);
        }
        else if(item.getItemId() == R.id.savedItems){
            // transfer to saved items page
            Intent savedPage = new Intent(getApplicationContext(), SavedItems.class);
            savedPage.putExtra("username", username);
            savedPage.putExtra("usertype", usertype);
            startActivity(savedPage);
        }
        else if(item.getItemId() == R.id.logout){
            // transfer to login page
            Intent logout = new Intent(getApplicationContext(), LoginPage.class);
            logout.putExtra("username", username);
            logout.putExtra("usertype", usertype);
            startActivity(logout);
        }

        return super.onOptionsItemSelected(item);
    }
    
}
