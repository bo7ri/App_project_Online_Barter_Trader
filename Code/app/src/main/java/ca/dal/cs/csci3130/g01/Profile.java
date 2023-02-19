package ca.dal.cs.csci3130.g01;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toolbar = findViewById(R.id.toolBar);

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

    /**
     * Inflates the toolbar with items
     * @param menu the menu that has the items
     * @return super onCreateOptionsMenu result
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile_menu, menu);


        return super.onCreateOptionsMenu(menu);
    }

    /**
     * When clicked on item in the toolbar go to desired activity
     * @param item item in the toolbar
     * @return super onCreateOptionsMenu result
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        if(item.getItemId() == R.id.logout){
            // TODO
            // transfer to login page
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

    }
}
