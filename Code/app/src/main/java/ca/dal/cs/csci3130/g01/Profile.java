package ca.dal.cs.csci3130.g01;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Profile extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Get the instance of the Firebase
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        // Declaration of DocumentReference variables
        DocumentReference document = firestore.collection("users").document("user1");

        // Add a listener to the document to get its value
        document.addSnapshotListener((snapshot, e) -> {
            if (e != null) {
                Log.w(TAG, "Listen failed.", e);
                return;
            }
            if (snapshot != null && snapshot.exists()) {
                // retrieve the data from firebase
                String firstName = snapshot.getString("firstName");
                String lastName = snapshot.getString("lastName");
                String userName = snapshot.getString("userName");
                String userType = snapshot.getString("userType");
                String email = snapshot.getString("email");
                String goods = snapshot.getString("goods");

                // set the profile labels
                TextView firstNameLabel = findViewById(R.id.first_name_edit_text);
                firstNameLabel.setText(firstName);

                TextView lastNameLabel = findViewById(R.id.last_name_edit_text);
                lastNameLabel.setText(lastName);

                TextView userNameLabel = findViewById(R.id.username_edit_text);
                userNameLabel.setText(userName);

                TextView userTypeLabel = findViewById(R.id.user_type_edit_text);
                userTypeLabel.setText(userType);

                TextView emailLabel = findViewById(R.id.email_edit_text);
                emailLabel.setText(email);

                TextView goodsLabel = findViewById(R.id.goods);
                goodsLabel.setText(goods);
            } else {
                Log.d(TAG, "Current data: null");
            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}
