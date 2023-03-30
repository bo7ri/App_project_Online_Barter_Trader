package ca.dal.cs.csci3130.g01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class RequestDetailsPage extends AppCompatActivity {

    private String username;
    private String usertype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Loading in the layout.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requestdetails_page);

        // Getting instance of database.
        FirebaseFirestore cloudDatabase = FirebaseFirestore.getInstance();

        // Getting intents.
        String ReceiverUsername = getIntent().getStringExtra("ReceiverUsername");
        String ProviderUsername = getIntent().getStringExtra("ProviderUsername");
        String ProductTitle = getIntent().getStringExtra("ProductTitle");
        String ProductDescription = getIntent().getStringExtra("ProductDescription");
        String RequestMessage = getIntent().getStringExtra("RequestMessage");

        // Getting text views.
        TextView productTitleTextView = findViewById(R.id.requestDetailsProductTitleInput);
        TextView productDescriptionTextView = findViewById(R.id.requestDetailsProductDescriptionInput);
        TextView receiverUsernameTextView = findViewById(R.id.requestDetailsReceiverNameInput);
        TextView requestMessageTextView = findViewById(R.id.requestDetailsRequestMsgInput);

        // Setting the text views.
        productTitleTextView.setText(ProductTitle);
        productDescriptionTextView.setText(ProductDescription);
        receiverUsernameTextView.setText(ReceiverUsername);
        requestMessageTextView.setText(RequestMessage);

        // Setting accept request button.
        Button requestAcceptButton = findViewById(R.id.requestDetailsPageAcceptBtn);
        requestAcceptButton.setOnClickListener(view -> {

            // To do: find product price, add value to provider, delete request from database.

            // Finding request from the database.
            cloudDatabase.collection("RequestList").whereEqualTo("ProductTitle", ProductTitle)
                    .whereEqualTo("ProviderUsername", ProviderUsername).whereEqualTo("ReceiverUsername", ReceiverUsername)
                    .whereEqualTo("RequestMessage", RequestMessage).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            // Checks if task is successful.
                            if (task.isSuccessful()) {

                                // Loops through the documents found by the previous query.
                                for (QueryDocumentSnapshot document : task.getResult()) {

                                    // Getting and storing the appropriate information.
                                    String documentProductTitle = document.get("ProductTitle").toString();
                                    String documentReceiverUsername = document.get("ReceiverUsername").toString();
                                    String documentProviderUsername = document.get("ProviderUsername").toString();
                                    String documentRequestMessage = document.get("RequestMessage").toString();

                                    // Error checking methods to see if clicked request is same as retrieved request.
                                    if (documentProductTitle.equals(ProductTitle) && documentReceiverUsername.equals(ReceiverUsername)
                                            && documentProviderUsername.equals(ProviderUsername) && documentRequestMessage.equals(RequestMessage)) {
                                        Toast.makeText(RequestDetailsPage.this, "Found request!", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(RequestDetailsPage.this, "Did not find request!", Toast.LENGTH_LONG).show();
                                    }

                                }

                            }

                        }
                    });

            // Sending the user back to the request list page.
            Intent moveBackToRequestListPage = new Intent(RequestDetailsPage.this, Profile.class);
            moveBackToRequestListPage.putExtra("ReceiverUsername", ReceiverUsername);
            moveBackToRequestListPage.putExtra("username", ProviderUsername);
            moveBackToRequestListPage.putExtra("ProductTitle", ProductTitle);
            moveBackToRequestListPage.putExtra("ProductDescription", ProductDescription);
            moveBackToRequestListPage.putExtra("RequestMessage", RequestMessage);
            moveBackToRequestListPage.putExtra("usertype", "Provider");
            Toast.makeText(RequestDetailsPage.this, "You have accepted this request!", Toast.LENGTH_LONG).show();
            startActivity(moveBackToRequestListPage);
        });

        // Setting decline request button.
        Button requestDeclineButton = findViewById(R.id.requestDetailsPageDeclineBtn);
        requestDeclineButton.setOnClickListener(view -> {

            // To do: find and delete request from database.

            // Finding request from the database.
            cloudDatabase.collection("RequestList").whereEqualTo("ProductTitle", ProductTitle)
                    .whereEqualTo("ProviderUsername", ProviderUsername).whereEqualTo("ReceiverUsername", ReceiverUsername)
                    .whereEqualTo("RequestMessage", RequestMessage).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            // Checks if task is successful.
                            if (task.isSuccessful()) {

                                // Loops through the documents found by the previous query.
                                for (QueryDocumentSnapshot document : task.getResult()) {

                                    // Getting and storing the appropriate information.
                                    String documentProductTitle = document.get("ProductTitle").toString();
                                    String documentReceiverUsername = document.get("ReceiverUsername").toString();
                                    String documentProviderUsername = document.get("ProviderUsername").toString();
                                    String documentRequestMessage = document.get("RequestMessage").toString();

                                    // Error checking methods to see if clicked request is same as retrieved request.
                                    if (documentProductTitle.equals(ProductTitle) && documentReceiverUsername.equals(ReceiverUsername)
                                            && documentProviderUsername.equals(ProviderUsername) && documentRequestMessage.equals(RequestMessage)) {

                                        // Deleting request.
                                        String testString = document.getId();
                                        cloudDatabase.collection("RequestList").document(testString).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(RequestDetailsPage.this, "WooYeah", Toast.LENGTH_LONG).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(RequestDetailsPage.this, "NooYeah", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                        Toast.makeText(RequestDetailsPage.this, testString, Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(RequestDetailsPage.this, "Did not find request!", Toast.LENGTH_LONG).show();
                                    }

                                }

                            }

                        }
                    });

            // Sending the user back to the request list page.
            Intent moveBackToRequestListPage = new Intent(RequestDetailsPage.this, Profile.class);
            moveBackToRequestListPage.putExtra("ReceiverUsername", ReceiverUsername);
            moveBackToRequestListPage.putExtra("username", ProviderUsername);
            moveBackToRequestListPage.putExtra("ProductTitle", ProductTitle);
            moveBackToRequestListPage.putExtra("ProductDescription", ProductDescription);
            moveBackToRequestListPage.putExtra("RequestMessage", RequestMessage);
            moveBackToRequestListPage.putExtra("usertype", "Provider");
            Toast.makeText(RequestDetailsPage.this, "You have declined this request!", Toast.LENGTH_LONG).show();
            startActivity(moveBackToRequestListPage);
        });

    }

}
