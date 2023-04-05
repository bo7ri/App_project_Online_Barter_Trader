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
                                    String documentProductDescription = document.get("ProductDescription").toString();

                                    // Error checking methods to see if clicked request is same as retrieved request.
                                    if (documentProductTitle.equals(ProductTitle) && documentReceiverUsername.equals(ReceiverUsername)
                                            && documentProviderUsername.equals(ProviderUsername) && documentRequestMessage.equals(RequestMessage)) {

                                        // Getting request ID.
                                        String requestID = document.getId();

                                        // Deleting the request from firebase.
                                        cloudDatabase.collection("RequestList").document(requestID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                // Send a toast message if request is accepted successfully.
                                                Toast.makeText(RequestDetailsPage.this, "Accepted the request successfully!", Toast.LENGTH_LONG).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // Send a toast message if request is not accepted successfully.
                                                Toast.makeText(RequestDetailsPage.this, "Did not accept the request successfully!", Toast.LENGTH_SHORT).show();
                                            }
                                        });



                                        // Finding product.
                                        cloudDatabase.collection("ProductList").whereEqualTo("title", documentProductTitle)
                                                .whereEqualTo("username", documentProviderUsername).whereEqualTo("description", documentProductDescription)
                                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<QuerySnapshot> task1) {

                                                        // Checks if task1 is successful.
                                                        if (task1.isSuccessful()) {

                                                            // Loops through the documents found in ProductList.
                                                            for (QueryDocumentSnapshot document1 : task1.getResult()) {

                                                                // Getting information about the product.
                                                                String document1Title = document1.get("title").toString();
                                                                String document1Description = document1.get("description").toString();
                                                                String document1ProviderUsername = document1.get("username").toString();
                                                                String document1Price = document1.get("price").toString();

                                                                // Error checking to see if request product matches retrieved product.
                                                                if ((document1Title.equals(documentProductTitle)) && (document1Description.equals(documentProductDescription))
                                                                        && (document1ProviderUsername.equals(documentProviderUsername))) {

                                                                    // Getting product ID.
                                                                    String productID = document1.getId();






                                                                }

                                                            }
                                                        }
                                                    }
                                                });



                                    } else {
                                        // Sending error message if request cannot be found.
                                        Toast.makeText(RequestDetailsPage.this, "Did not find request!", Toast.LENGTH_LONG).show();
                                    }

                                }

                            }

                        }
                    });

            // Sending the user back to the request list page.
            Intent moveBackToProfilePage = new Intent(RequestDetailsPage.this, Profile.class);
            moveBackToProfilePage.putExtra("ReceiverUsername", ReceiverUsername);
            moveBackToProfilePage.putExtra("username", ProviderUsername);
            moveBackToProfilePage.putExtra("ProductTitle", ProductTitle);
            moveBackToProfilePage.putExtra("ProductDescription", ProductDescription);
            moveBackToProfilePage.putExtra("RequestMessage", RequestMessage);
            moveBackToProfilePage.putExtra("usertype", "Provider");
            startActivity(moveBackToProfilePage);
        });


        // Setting decline request button.
        Button requestDeclineButton = findViewById(R.id.requestDetailsPageDeclineBtn);
        requestDeclineButton.setOnClickListener(view -> {

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

                                        // Getting request ID.
                                        String requestID = document.getId();

                                        // Deleting the request from firebase.
                                        cloudDatabase.collection("RequestList").document(requestID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                // Send a toast message if request is accepted successfully.
                                                Toast.makeText(RequestDetailsPage.this, "Deleted the request successfully!", Toast.LENGTH_LONG).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // Send a toast message if request is not accepted successfully.
                                                Toast.makeText(RequestDetailsPage.this, "Did not delete the request successfully!", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                    } else {
                                        // Sending error message if request cannot be found.
                                        Toast.makeText(RequestDetailsPage.this, "Did not find request!", Toast.LENGTH_LONG).show();
                                    }

                                }

                            }

                        }
                    });

            // Sending the user back to the profile page.
            Intent moveBackToProfilePage = new Intent(RequestDetailsPage.this, Profile.class);
            moveBackToProfilePage.putExtra("ReceiverUsername", ReceiverUsername);
            moveBackToProfilePage.putExtra("username", ProviderUsername);
            moveBackToProfilePage.putExtra("ProductTitle", ProductTitle);
            moveBackToProfilePage.putExtra("ProductDescription", ProductDescription);
            moveBackToProfilePage.putExtra("RequestMessage", RequestMessage);
            moveBackToProfilePage.putExtra("usertype", "Provider");
            startActivity(moveBackToProfilePage);
        });

    }

}
