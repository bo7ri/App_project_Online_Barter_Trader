package ca.dal.cs.csci3130.g01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
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
}
