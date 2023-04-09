package ca.dal.cs.csci3130.g01;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class Profile extends AppCompatActivity{

    Toolbar toolbar;
    Button sendEmailButton, viewInboxButton;
    FirebaseFirestore database;
    String username;
    String usertype;
    String firstName, lastName, userName, userType, email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toolbar = findViewById(R.id.toolBar);


        // Get the instance of the Firebase
        database = FirebaseFirestore.getInstance();

        // Getting intents.
        username = getIntent().getStringExtra("username");
        usertype = getIntent().getStringExtra("usertype");

        if(username != null) {
            getDataDB(username);
        }

        // Creating a onClick button to move to request list page.
        Button moveToRequestListBtn = findViewById(R.id.moveToRequestListButton);
        moveToRequestListBtn.setOnClickListener(view -> {
            if (usertype.equals("Provider")) {
                Intent moveToRequestListPage = new Intent(Profile.this, RequestListPage.class);
                moveToRequestListPage.putExtra("username", username);
                moveToRequestListPage.putExtra("usertype", usertype);
                Profile.this.startActivity(moveToRequestListPage);
            } else {
                Toast.makeText(Profile.this, "Only providers can access this page!", Toast.LENGTH_LONG).show();
            }

        });

        Button setLocationBtn = findViewById(R.id.SetLocationBtn);
        setLocationBtn.setOnClickListener(view -> {
            Intent moveToLocationPage = new Intent(getApplicationContext(), Location.class);
            moveToLocationPage.putExtra("username", username);
            moveToLocationPage.putExtra("usertype", usertype);
            startActivity(moveToLocationPage);
        });

        if(username != null) getDataDB(username);

        sendEmailButton = findViewById(R.id.send_email);
        sendEmailButton.setOnClickListener(view -> {
            // this button will open send email activity
            TextView emailLabel = findViewById(R.id.email_edit_text);
            Intent sendEmailIntent = new Intent(Profile.this, ComposeEmail.class);
            sendEmailIntent.putExtra("senderEmail", email);
            startActivity(sendEmailIntent);
        });


        viewInboxButton = findViewById(R.id.view_inbox);
        viewInboxButton.setOnClickListener(view -> {
            TextView emailLabel = findViewById(R.id.email_edit_text);
            String recipientEmail = emailLabel.getText().toString();
            Intent inboxIntent = new Intent(Profile.this, Inbox.class);
            inboxIntent.putExtra("recipientEmail", recipientEmail);
            startActivity(inboxIntent);
        });

        // GO TO EDIT PAGE
        Button ProfileEditPageButton = findViewById(R.id.EditProfile);
        ProfileEditPageButton.setOnClickListener(view -> {
            Intent movingToEditPage = new Intent(getApplicationContext(), EditProfile.class);;
            movingToEditPage.putExtra("username", username);
            movingToEditPage.putExtra("usertype",usertype);
            startActivity(movingToEditPage);
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

        if(item.getItemId() == R.id.homeButton){
            // transfer to home page
            Intent home = new Intent(getApplicationContext(), ProvidersListings.class);
            home.putExtra("username", username);
            home.putExtra("usertype", usertype);
            startActivity(home);
        }
        else if(item.getItemId() == R.id.savedItems){
            // transfer to saved items page
            Intent savedPage = new Intent(getApplicationContext(), SavedItems.class);
            savedPage.putExtra("username", username);
            savedPage.putExtra("usertype", usertype);
            startActivity(savedPage);
        }
        else if(item.getItemId() == R.id.logout){
            // transfer to login page
            Intent logout = new Intent(getApplicationContext(), LoginPage.class);
            logout.putExtra("username", username);
            logout.putExtra("usertype", usertype);
            startActivity(logout);
        }

        return super.onOptionsItemSelected(item);
    }

    private void getDataDB(String username){
        // Declaration of DocumentReference variables
        database.collection("UserList").whereEqualTo("Username",username)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // retrieve the data from firebase
                            firstName = document.get("FirstName").toString();
                            lastName = document.get("LastName").toString();
                            userName = document.get("Username").toString();
                            userType = document.get("UserType").toString();
                            email = document.get("EmailAddress").toString();

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
                });
    }

}




