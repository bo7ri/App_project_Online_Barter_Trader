package ca.dal.cs.csci3130.g01;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {

    private EditText FirstN, LastN, Password, EmailN;
    private Button SubmitButton;

    private String UserID;

    private String username, usertype;

    FirebaseFirestore cloudDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Declaring edit text variables.
        FirstN = findViewById(R.id.FirstName);
        LastN = findViewById(R.id.LastName);
        Password = findViewById(R.id.PasswordChange);
        EmailN = findViewById(R.id.Email);
        SubmitButton = findViewById(R.id.Submit);

        // Getting firebase instance.
        cloudDatabase = FirebaseFirestore.getInstance();

        // Get parcel from ProvidersList
        username = getIntent().getStringExtra("username");
        usertype = getIntent().getStringExtra("usertype");

        // Finding the information about user from firebase.
        cloudDatabase.collection("UserList").whereEqualTo("Username",username).get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()){
                                for (QueryDocumentSnapshot document: task.getResult()) {
                                    String documentUsername = document.get("Username").toString();
                                    if (documentUsername.equals(username)){
                                        UserID = document.getId();
                                        String documentFN = document.get("FirstName").toString();
                                        String documentLN = document.get("LastName").toString();
                                        String documentE = document.get("EmailAddress").toString();
                                        String documentP = document.get("Password").toString();
                                        FirstN.setText(documentFN);
                                        LastN.setText(documentLN);
                                        Password.setText(documentP);
                                        EmailN.setText(documentE);
                                    }
                                }
                            }
                        });


        // Setting the submit button after editing information.
        SubmitButton.setOnClickListener(view -> cloudDatabase.collection("UserList").whereEqualTo("Username", username)
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        for (QueryDocumentSnapshot document: task.getResult()) {
                            Map<String, Object> UpdatedUserMap = new HashMap<>();
                            UpdatedUserMap.put("FirstName", FirstN.getText().toString());
                            UpdatedUserMap.put("LastName", LastN.getText().toString());
                            UpdatedUserMap.put("EmailAddress", EmailN.getText().toString());
                            UpdatedUserMap.put("Password", Password.getText().toString());

                            cloudDatabase.collection("UserList").document(UserID)
                                    .set(UpdatedUserMap, SetOptions.merge()).addOnSuccessListener(unused -> {
                                        Toast.makeText(EditProfile.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                                        switchToProviderListings();
                                    }).addOnFailureListener(e -> {
                                        Toast.makeText(EditProfile.this, "Did not update", Toast.LENGTH_SHORT).show();
                                        switchToProviderListings();
                                    });

                        }
                    }
                }));


    }
    protected void switchToProviderListings(){
        Intent switchToProvidersListings = new Intent(getApplicationContext(), LoginPage.class);
        switchToProvidersListings.putExtra("username", username);
        switchToProvidersListings.putExtra("usertype", usertype);
        startActivity(switchToProvidersListings);
    }

}
