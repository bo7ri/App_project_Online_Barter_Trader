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

public class Inbox extends AppCompatActivity {

    private FirebaseFirestore db;
    private RecyclerView recyclerView;
    private EmailAdapter emailAdapter;
    private List<Email> emails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        emails = new ArrayList<>();
        emailAdapter = new EmailAdapter(this, emails);
        recyclerView.setAdapter(emailAdapter);

        db = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        String recipientEmail = intent.getStringExtra("recipientEmail");

        if (recipientEmail != null) {
            db.collection("Emails")
                    .whereEqualTo("recipientEmail", recipientEmail)
                    .addSnapshotListener((value, error) -> {
                        if (error != null) {
                            Toast.makeText(Inbox.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        for (DocumentChange documentChange : value.getDocumentChanges()) {
                            if (documentChange.getType() == DocumentChange.Type.ADDED) {
                                Email email = documentChange.getDocument().toObject(Email.class);
                                emails.add(email);
                                emailAdapter.notifyDataSetChanged();
                            }
                        }
                    });
        } else {
            Toast.makeText(Inbox.this, "Error: Recipient email not found.", Toast.LENGTH_SHORT).show();
        }
    }
}
