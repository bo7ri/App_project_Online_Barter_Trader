package ca.dal.cs.csci3130.g01;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class RequestDetailsPage extends AppCompatActivity {

    private String username;
    private String usertype;
    private String tempProviderUsernameID;

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

        // Setting up the text views on the page.
        setRequestDetailsTextView(ProductTitle, ProductDescription, ReceiverUsername, RequestMessage);

        // Setting accept request button.
        Button requestAcceptButton = findViewById(R.id.requestDetailsPageAcceptBtn);
        requestAcceptButton.setOnClickListener(view -> {

            // Finding request from the database.
            cloudDatabase.collection("RequestList").whereEqualTo("ProductTitle", ProductTitle)
                    .whereEqualTo("ProviderUsername", ProviderUsername).whereEqualTo("ReceiverUsername", ReceiverUsername)
                    .whereEqualTo("RequestMessage", RequestMessage).get().addOnCompleteListener(task -> {

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

                                    // Deleting the request from Firebase.
                                    deleteRequestFromFirebase(cloudDatabase, requestID, "Accept");

                                    // Finding product.
                                    cloudDatabase.collection("ProductList").whereEqualTo("title", documentProductTitle)
                                            .whereEqualTo("username", documentProviderUsername).whereEqualTo("description", documentProductDescription)
                                            .get().addOnCompleteListener(task1 -> {
                                                // Finding product to update the total value traded of the provider.
                                                findProductToUpdateValue(cloudDatabase, task1, documentProductTitle, documentProductDescription, documentProviderUsername);
                                            });

                                } else {
                                    // Sending error message if request cannot be found.
                                    Toast.makeText(RequestDetailsPage.this, "Did not find request!", Toast.LENGTH_LONG).show();
                                }

                            }
                        }
                    });

            // Sending the user back to the request list page.
            moveRequestPageToProfilePage(ReceiverUsername, ProviderUsername, ProductTitle, ProductDescription, RequestMessage);

        });


        // Setting decline request button.
        Button requestDeclineButton = findViewById(R.id.requestDetailsPageDeclineBtn);
        requestDeclineButton.setOnClickListener(view -> {

            // Finding request from the database.
            cloudDatabase.collection("RequestList").whereEqualTo("ProductTitle", ProductTitle)
                    .whereEqualTo("ProviderUsername", ProviderUsername).whereEqualTo("ReceiverUsername", ReceiverUsername)
                    .whereEqualTo("RequestMessage", RequestMessage).get().addOnCompleteListener(task -> {
                        // Checks if task is successful.
                        if (task.isSuccessful()) {
                            // Loops through the documents found by the previous query.
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Calling declineButtonPressed method.
                                declineButtonPressed(document, cloudDatabase, ProductTitle, ReceiverUsername, ProviderUsername, RequestMessage);
                            }
                        }
                    });

            // Sending the user back to the profile page.
            moveRequestPageToProfilePage(ReceiverUsername, ProviderUsername, ProductTitle, ProductDescription, RequestMessage);

        });

    }

    // Method to set text views on the page.
    protected void setRequestDetailsTextView(String productTitle, String productDescription, String receiverUsername, String requestMessage) {

        // Getting text views.
        TextView productTitleTextView = findViewById(R.id.requestDetailsProductTitleInput);
        TextView productDescriptionTextView = findViewById(R.id.requestDetailsProductDescriptionInput);
        TextView receiverUsernameTextView = findViewById(R.id.requestDetailsReceiverNameInput);
        TextView requestMessageTextView = findViewById(R.id.requestDetailsRequestMsgInput);

        // Setting the text views.
        productTitleTextView.setText(productTitle);
        productDescriptionTextView.setText(productDescription);
        receiverUsernameTextView.setText(receiverUsername);
        requestMessageTextView.setText(requestMessage);

    }

    // Method to move user back to profile page.
    protected void moveRequestPageToProfilePage(String receiverUsername, String providerUsername, String productTitle,
                                                String productDescription, String requestMessage) {
        // Sending the user back to the profile page.
        Intent moveBackToProfilePage = new Intent(RequestDetailsPage.this, Profile.class);
        moveBackToProfilePage.putExtra("ReceiverUsername", receiverUsername);
        moveBackToProfilePage.putExtra("username", providerUsername);
        moveBackToProfilePage.putExtra("ProductTitle", productTitle);
        moveBackToProfilePage.putExtra("ProductDescription", productDescription);
        moveBackToProfilePage.putExtra("RequestMessage", requestMessage);
        moveBackToProfilePage.putExtra("usertype", "Provider");
        startActivity(moveBackToProfilePage);
    }

    // Method to delete request from Firebase.
    protected void deleteRequestFromFirebase(FirebaseFirestore ffInstance, String requestID, String typeOfRequest) {

        // Deleting the request from firebase.
        ffInstance.collection("RequestList").document(requestID).delete().addOnSuccessListener(unused -> {
            if (typeOfRequest.equals("Accept")) {
                Toast.makeText(RequestDetailsPage.this, "Accepted the request successfully!", Toast.LENGTH_LONG).show();
            } else if (typeOfRequest.equals("Decline")) {
                Toast.makeText(RequestDetailsPage.this, "Declined the request successfully!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> {
            if (typeOfRequest.equals("Accept")) {
                Toast.makeText(RequestDetailsPage.this, "Did not accept the request successfully!", Toast.LENGTH_SHORT).show();
            } else if (typeOfRequest.equals("Decline")) {
                Toast.makeText(RequestDetailsPage.this, "Did not decline the request successfully!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    // Deleting the product from firebase.
    protected void deleteProductFromFirebase(FirebaseFirestore ffInstance, String productID) {

        // Delete product from the firebase.
        ffInstance.collection("ProductList").document(productID).delete().addOnSuccessListener(unused -> {
            // Send a toast message if product is found.
            Toast.makeText(RequestDetailsPage.this, "Product was deleted successfully", Toast.LENGTH_SHORT);
        }).addOnFailureListener(e -> {
            // Send a toast message if product is not found.
            Toast.makeText(RequestDetailsPage.this, "Product was not found in app!", Toast.LENGTH_SHORT).show();
        });

    }

    // Performing and calling methods when decline button is pressed.
    protected void declineButtonPressed(QueryDocumentSnapshot declinedDocument, FirebaseFirestore ffInstance, String productTitle,
                                        String receiverUsername, String providerUsername, String requestMessage) {

        // Getting and storing the appropriate information.
        String documentProductTitle = declinedDocument.get("ProductTitle").toString();
        String documentReceiverUsername = declinedDocument.get("ReceiverUsername").toString();
        String documentProviderUsername = declinedDocument.get("ProviderUsername").toString();
        String documentRequestMessage = declinedDocument.get("RequestMessage").toString();

        // Error checking methods to see if clicked request is same as retrieved request.
        if (documentProductTitle.equals(productTitle) && documentReceiverUsername.equals(receiverUsername)
                && documentProviderUsername.equals(providerUsername) && documentRequestMessage.equals(requestMessage)) {

            // Getting request ID.
            String requestID = declinedDocument.getId();

            // Deleting the request from Firebase.
            deleteRequestFromFirebase(ffInstance, requestID, "Decline");

        } else {
            // Sending error message if request cannot be found.
            Toast.makeText(RequestDetailsPage.this, "Did not find request!", Toast.LENGTH_LONG).show();
        }

    }

    // Method to get provider user id from Firebase.
    protected void setProviderUserIDFromFirebase(FirebaseFirestore ffInstance, String providerUsername, double productPrice) {

        // Finding the user id.
        ffInstance.collection("UserList").whereEqualTo("Username", providerUsername).get().addOnCompleteListener(task -> {

            // Checks if task is successful.
            if (task.isSuccessful()) {
                // Loops through the documents found by the previous query.
                for (QueryDocumentSnapshot document : task.getResult()) {
                    // Getting username of provider.
                    String documentProviderUsername = document.get("Username").toString();
                    // Error checking to see if given username is same as retrieved username.
                    if (documentProviderUsername.equals(providerUsername)) {
                        tempProviderUsernameID = document.getId();
                        setTotalValueTraded(ffInstance, providerUsername, document.getId(), productPrice);
                    }
                }
            }

        });

    }

    // Method to find product.
    protected void findProductToUpdateValue(FirebaseFirestore ffInstance, Task<QuerySnapshot> task, String productTitle, String productDescription, String providerUsername) {

        // Checks if task1 is successful.
        if (task.isSuccessful()) {

            // Loops through the documents found in ProductList.
            for (QueryDocumentSnapshot document1 : task.getResult()) {

                // Getting information about the product.
                String document1Title = document1.get("title").toString();
                String document1Description = document1.get("description").toString();
                String document1ProviderUsername = document1.get("username").toString();
                double document1Price = document1.getDouble("price");

                // Error checking to see if request product matches retrieved product.
                if ((document1Title.equals(productTitle)) && (document1Description.equals(productDescription))
                        && (document1ProviderUsername.equals(providerUsername))) {

                    // Getting product ID.
                    String productID = document1.getId();

                    // Delete product from the firebase.
                    deleteProductFromFirebase(ffInstance, productID);

                    // Setting temp user ID in tempProviderUsernameID.
                    setProviderUserIDFromFirebase(ffInstance, document1ProviderUsername, document1Price);

                }

            }
        }

    }

    // Method to get total value traded from firebase.
    protected void setTotalValueTraded(FirebaseFirestore ffInstance, String providerUsername, String providerID, double productPrice) {

        // Getting total value from TotalValue collection.
        DocumentReference documentRef = ffInstance.collection("TotalValue").document(providerID);
        documentRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {

                    double currTotalValue = document.getDouble("ProviderTotalValue");

                    // Creating a map for updated value.
                    Map<String, Object> newTotalValueMap = new HashMap<>();
                    newTotalValueMap.put("ProviderUsername", providerUsername);
                    newTotalValueMap.put("ProviderUsernameID", providerID);
                    newTotalValueMap.put("ProviderTotalValue", productPrice + currTotalValue);

                    ffInstance.collection("TotalValue").document(providerID).set(newTotalValueMap).addOnSuccessListener(unused ->
                            Toast.makeText(RequestDetailsPage.this, "Price successfully added!", Toast.LENGTH_LONG))
                            .addOnFailureListener(e -> Toast.makeText(RequestDetailsPage.this, "Price was not added!", Toast.LENGTH_LONG));

                } else {

                    // Creating a map for updated value.
                    Map<String, Object> newTotalValueMap = new HashMap<>();
                    newTotalValueMap.put("ProviderUsername", providerUsername);
                    newTotalValueMap.put("ProviderUsernameID", providerID);
                    newTotalValueMap.put("ProviderTotalValue", productPrice);

                    ffInstance.collection("TotalValue").document(providerID).set(newTotalValueMap).addOnSuccessListener(unused ->
                            Toast.makeText(RequestDetailsPage.this, "Price successfully added!", Toast.LENGTH_LONG))
                            .addOnFailureListener(e -> Toast.makeText(RequestDetailsPage.this, "Price was not added!", Toast.LENGTH_LONG).show());

                }
            }
        });

    }

}
