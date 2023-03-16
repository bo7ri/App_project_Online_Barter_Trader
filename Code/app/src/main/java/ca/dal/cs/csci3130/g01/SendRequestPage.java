package ca.dal.cs.csci3130.g01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SendRequestPage extends AppCompatActivity {

    private String username;
    private String usertype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Loading in the layout.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendrequest_page);

        // Getting the username from intent.
        username = getIntent().getStringExtra("username");
        Product product = getIntent().getParcelableExtra("product");
        usertype = getIntent().getStringExtra("usertype");

        // Setting the fields in the page.
        TextView productTitleField = findViewById(R.id.sendRequestProductTitleInput);
        productTitleField.setText(product.getTitle().trim());
        TextView productDescriptionField = findViewById(R.id.sendRequestProductDescriptionInput);
        productDescriptionField.setText(product.getDescription().trim());
        if (product.getUsername() != null) {
            TextView productOwnerField = findViewById(R.id.sendRequestProductOwnerInput);
            productOwnerField.setText(product.getUsername().trim());
        }

        // Getting cancel request button.
        Button cancelRequestButton = findViewById(R.id.sendRequestCancelBtn);
        cancelRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveBackToItemListPage = new Intent(SendRequestPage.this, ProvidersListings.class);
                moveBackToItemListPage.putExtra("username", username);
                moveBackToItemListPage.putExtra("product", product);
                moveBackToItemListPage.putExtra("usertype", usertype);
                SendRequestPage.this.startActivity(moveBackToItemListPage);
            }
        });

        // Getting submit request button.
        Button submitRequestButton = findViewById(R.id.sendRequestSubmitBtn);
        submitRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Initializing the variables.
                String currentProductTitle = product.getTitle();
                String currentProductDescription = product.getDescription();
                String currentProductOwner = product.getUsername();
                String currentRequestMessage = getRequestMessage();

                // Checking if an empty or null values exist in the variables.
                boolean isRequestVariablesEmpty = false;
                if (username == null || username.length() < 1) {
                    isRequestVariablesEmpty = true;
                    Toast.makeText(SendRequestPage.this, "Invalid! Empty username!", Toast.LENGTH_LONG).show();
                } else if (currentProductTitle == null || currentProductTitle.length() < 1) {
                    isRequestVariablesEmpty = true;
                    Toast.makeText(SendRequestPage.this, "Invalid! Empty title!", Toast.LENGTH_LONG).show();
                } else if (currentProductDescription == null || currentProductDescription.length() < 1) {
                    isRequestVariablesEmpty = true;
                    Toast.makeText(SendRequestPage.this, "Invalid! Empty description!", Toast.LENGTH_LONG).show();
                } else if (currentProductOwner == null || currentProductOwner.length() < 1) {
                    isRequestVariablesEmpty = true;
                    Toast.makeText(SendRequestPage.this, "Invalid! Empty owner!", Toast.LENGTH_LONG).show();
                } else if (currentRequestMessage == null || currentRequestMessage.length() < 1) {
                    isRequestVariablesEmpty = true;
                    Toast.makeText(SendRequestPage.this, "Invalid! Empty message!", Toast.LENGTH_LONG).show();
                }

                // If none of the variable are empty.
                if (!isRequestVariablesEmpty && usertype.equals("Receiver")) {

                    // Sending data to Firebase.
                    FirebaseFirestore databaseInstance = FirebaseFirestore.getInstance();
                    Map<String, Object> tradeRequest = new HashMap<>();
                    tradeRequest.put("ReceiverUsername", username);
                    tradeRequest.put("ProviderUsername", currentProductOwner);
                    tradeRequest.put("ProductTitle", currentProductTitle);
                    tradeRequest.put("ProductDescription", currentProductDescription);
                    tradeRequest.put("RequestMessage", currentRequestMessage);
                    databaseInstance.collection("RequestList").add(tradeRequest);

                    // Sending a toast if submitted successfully.
                    Toast.makeText(SendRequestPage.this, "Submitted successfully!", Toast.LENGTH_LONG).show();

                    // Moving back to list page.
                    Intent moveBackToItemListPage = new Intent(SendRequestPage.this, ProvidersListings.class);
                    moveBackToItemListPage.putExtra("username", username);
                    moveBackToItemListPage.putExtra("product", product);
                    moveBackToItemListPage.putExtra("usertype", usertype);
                    SendRequestPage.this.startActivity(moveBackToItemListPage);

                }


            }
        });

    }

    // Getting the username from field.
    protected String getRequestMessage() {
        EditText requestMessage = findViewById(R.id.sendRequestMessageInput);
        return requestMessage.getText().toString().trim();
    }

}

