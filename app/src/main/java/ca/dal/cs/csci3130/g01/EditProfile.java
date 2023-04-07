package ca.dal.cs.csci3130.g01;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class EditProfile extends AppCompatActivity {

    private EditText FirstN, LastN, Password, EmailN;
    private Button SubmitButton;

    String username;

    FirebaseFirestore cloudDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_profile);

        FirstN = findViewById(R.id.FirstName);
        LastN = findViewById(R.id.LastName);
        Password = findViewById(R.id.PasswordChange);
        EmailN = findViewById(R.id.Email);

        SubmitButton = findViewById(R.id.Submit);

        // Get parcel from ProvidersList
        String username = getIntent().getParcelableExtra("username");

        SubmitButton.setOnClickListener(view -> addProductData());



    }

    private String ProfileFirstName, ProfileLastName, ProfilePassword, ProfileEmail;
    private void addProductData(){
        ProfileEmail = EmailN.getText().toString().trim();
        ProfileFirstName = FirstN.getText().toString().trim();
        ProfileLastName = LastN.getText().toString().trim();
        ProfilePassword = Password.getText().toString().trim();

        /** Validating The Data */
        if (TextUtils.isEmpty(ProfileEmail)){
            Toast.makeText(this, "Email is required!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(ProfileFirstName)){
            Toast.makeText(this, "First Name is required!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(ProfileLastName)){
            Toast.makeText(this, "Last Name is required!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(ProfilePassword)){
            Toast.makeText(this, "Password is required!", Toast.LENGTH_SHORT).show();
            return;
        }


        // DB
        addProductToDB();
    }
}
