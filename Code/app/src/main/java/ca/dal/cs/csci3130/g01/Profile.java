package ca.dal.cs.csci3130.g01;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity implements View.OnClickListener{
    // Instance of Firebase Database
    FirebaseDatabase database = null;

    // Declaration of DatabaseReference variables
    DatabaseReference firstNameRef;
    DatabaseReference lastNameRef;
    DatabaseReference emailRef;
    DatabaseReference userName;
    DatabaseReference userType;
    DatabaseReference goodsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Get the instance of the Firebase database using the URL of the project
        database = FirebaseDatabase.getInstance("Project url");

        // Get the references to the nodes in the database
        firstNameRef = database.getReference("first name");
        lastNameRef = database.getReference("last name");
        userName = database.getReference("userName");
        emailRef = database.getReference("phoneNumber");
        userType = database.getReference("email");
        goodsRef = database.getReference("goods");

        // Add a listener to the nameRef node to get its value
        firstNameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Get the firebase value
                String stringName = snapshot.getValue(String.class);

                // Declare the TextView nameLabel to show the name
                TextView nameLabel = findViewById(R.id.first_name_edit_text);

                // Show the name in the label
                nameLabel.setText(stringName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        // Add a listener to the nameRef node to get its value
        lastNameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Get the firebase value
                String stringName = snapshot.getValue(String.class);

                // Declare the TextView nameLabel to show the name
                TextView nameLabel = findViewById(R.id.last_name_edit_text);

                // Show the name in the label
                nameLabel.setText(stringName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        // Add a listener to the phoneRef node to get its value
        userType.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String stringEmail = snapshot.getValue(String.class);
                TextView numberLabel = findViewById(R.id.user_type_edit_text);
                numberLabel.setText(stringEmail);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        // Add a listener to the phoneRef node to get its value
        userName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String stringEmail = snapshot.getValue(String.class);
                TextView numberLabel = findViewById(R.id.username_edit_text);
                numberLabel.setText(stringEmail);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        // Add a listener to the emailRef node to get its value
        emailRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String stringPhone = snapshot.getValue(String.class);
                TextView emailLabel = findViewById(R.id.email_edit_text);
                emailLabel.setText(stringPhone);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        // Add a listener to the goodsRef node to get its value
        goodsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String stringGoods= snapshot.getValue(String.class);
                TextView goodsLabel = findViewById(R.id.goods);
                goodsLabel.setText(stringGoods);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    // This is an overridden method of the View.OnClickListener interface
    @Override
    public void onClick(View view) {

    }
}
