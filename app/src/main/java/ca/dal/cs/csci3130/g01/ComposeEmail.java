package ca.dal.cs.csci3130.g01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

        String senderEmail = getIntent().getStringExtra("senderEmail");

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
}

