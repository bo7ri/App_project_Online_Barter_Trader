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

public class RequestDetailsPage extends AppCompatActivity {

    private String username;
    private String usertype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Loading in the layout.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requestdetails_page);

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
            Intent moveBackToRequestListPage = new Intent(RequestDetailsPage.this, RequestListPage.class);
            moveBackToRequestListPage.putExtra("ReceiverUsername", ReceiverUsername);
            moveBackToRequestListPage.putExtra("username", ProviderUsername);
            moveBackToRequestListPage.putExtra("ProductTitle", ProductTitle);
            moveBackToRequestListPage.putExtra("ProductDescription", ProductDescription);
            moveBackToRequestListPage.putExtra("RequestMessage", RequestMessage);
            Toast.makeText(RequestDetailsPage.this, "You have accepted this request!", Toast.LENGTH_LONG).show();
            startActivity(moveBackToRequestListPage);
        });

        // Setting decline request button.
        Button requestDeclineButton = findViewById(R.id.requestDetailsPageDeclineBtn);
        requestDeclineButton.setOnClickListener(view -> {
            Intent moveBackToRequestListPage = new Intent(RequestDetailsPage.this, RequestListPage.class);
            moveBackToRequestListPage.putExtra("ReceiverUsername", ReceiverUsername);
            moveBackToRequestListPage.putExtra("username", ProviderUsername);
            moveBackToRequestListPage.putExtra("ProductTitle", ProductTitle);
            moveBackToRequestListPage.putExtra("ProductDescription", ProductDescription);
            moveBackToRequestListPage.putExtra("RequestMessage", RequestMessage);
            Toast.makeText(RequestDetailsPage.this, "You have declined this request!", Toast.LENGTH_LONG).show();
            startActivity(moveBackToRequestListPage);
        });

    }

}
