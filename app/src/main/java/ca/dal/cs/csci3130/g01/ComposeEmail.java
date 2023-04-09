package ca.dal.cs.csci3130.g01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

/**
 * This activity for writing and sending a new email
 */
public class ComposeEmail extends AppCompatActivity {
    // Declare variables
    private EditText recipientEditText;
    private EditText subjectEditText;
    private EditText bodyEditText;

    private FirebaseFirestore firestore;
    private String username;
    private String usertype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_email);

        // Initialize the fields
        recipientEditText = findViewById(R.id.recipient_edit_text);
        subjectEditText = findViewById(R.id.subject_edit_text);
        bodyEditText = findViewById(R.id.body_edit_text);
        Button sendButton = findViewById(R.id.send_button);

        firestore = FirebaseFirestore.getInstance();

        // Getting intents.
        String senderEmail = getIntent().getStringExtra("senderEmail");
        username = getIntent().getStringExtra("username");
        usertype = getIntent().getStringExtra("usertype");

        sendButton.setOnClickListener(view -> {
            String recipientEmail = recipientEditText.getText().toString();
            String subject = subjectEditText.getText().toString();
            String body = bodyEditText.getText().toString();

            // Create an Email object
            Email email = new Email(body, recipientEmail, senderEmail, subject);

            // Store the email in Firestore
            firestore.collection("Emails").add(email)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(ComposeEmail.this, "Email sent successfully!",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e -> Toast.makeText(ComposeEmail.this,
                            "Failed to send email.", Toast.LENGTH_SHORT).show());
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

