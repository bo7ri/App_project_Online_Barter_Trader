package ca.dal.cs.csci3130.g01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.security.Provider;

public class LoginPage extends AppCompatActivity {

    EditText username, password;
    Button login;
    //Firebase connection
    FirebaseFirestore databaseInstance = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.Username);
        password = findViewById(R.id.Password);
        login = findViewById(R.id.Login);

        // Creating the registering page button.
//        Button registerPageButton = findViewById(R.id.Register);
//        registerPageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent movingToRegisterPageIntent = new Intent(LoginPage.this, RegisterPage.class);
//                LoginPage.this.startActivity(movingToRegisterPageIntent);
//            }
//        });



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = username.getText().toString().trim();
                String pass = password.getText().toString().trim();

                databaseInstance.collection("UserList").whereEqualTo("Username", userName).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                         for (QueryDocumentSnapshot document : task.getResult()){
                             String timp = document.get("Password").toString();
                             if (pass.equals(timp)){
                                 Toast.makeText(LoginPage.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                 //Intent to main page here
//                                Intent moveToListPage = new Intent(LoginPage.this, ProvidersListings.class);
//                                LoginPage.this.startActivity(moveToListPage);
                             }
                             else {
                                 TextView loginStatus = findViewById(R.id.LoginStatusText);
                                 String validity = "Invalid Login!";
                                 loginStatus.setText(validity.trim());
                                 Toast.makeText(LoginPage.this, "Invalid Login", Toast.LENGTH_SHORT).show();
                             }

                         }


                        }

                    }
                });

            }
        });
    }
    }
