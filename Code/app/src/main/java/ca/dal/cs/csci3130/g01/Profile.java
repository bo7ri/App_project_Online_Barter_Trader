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


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Profile extends AppCompatActivity implements View.OnClickListener{

    Toolbar toolbar;

    FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toolbar = findViewById(R.id.toolBar);

        // Get the instance of the Firebase
        database = FirebaseFirestore.getInstance();

        String username = getIntent().getStringExtra("username");
        if(username != null) getDataDB(username);
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
            // transfer to login page
            Intent logout = new Intent(getApplicationContext(), LoginPage.class);
            startActivity(logout);
        }

        return super.onOptionsItemSelected(item);
    }

    private void getDataDB(String username){
        // Declaration of DocumentReference variables
        database.collection("UserList").whereEqualTo("Username",username)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        // retrieve the data from firebase
                        String firstName = document.get("FirstName").toString();
                        String lastName = document.get("LastName").toString();
                        String userName = document.get("Username").toString();
                        String userType = document.get("UserType").toString();
                        String email = document.get("EmailAddress").toString();

                        // set the profile labels
                        TextView firstNameLabel = findViewById(R.id.first_name_edit_text);
                        firstNameLabel.setText(firstName.trim());

                        TextView lastNameLabel = findViewById(R.id.last_name_edit_text);
                        lastNameLabel.setText(lastName.trim());

                        TextView userNameLabel = findViewById(R.id.username_edit_text);
                        userNameLabel.setText(userName.trim());

                        TextView userTypeLabel = findViewById(R.id.user_type_edit_text);
                        userTypeLabel.setText(userType.trim());

                        TextView emailLabel = findViewById(R.id.email_edit_text);
                        emailLabel.setText(email.trim());
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}
