package ca.dal.cs.csci3130.g01;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class EditProfile extends AppCompatActivity {

    private EditText FirstN, LastN, Password, EmailN;
    private Button SubmitButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_profile);

        FirstN = findViewById(R.id.FirstName);
        LastN = findViewById(R.id.LastName);
        Password = findViewById(R.id.PasswordChange);
        EmailN = findViewById(R.id.Email);

        SubmitButton = findViewById(R.id.Submit);


    }
}
