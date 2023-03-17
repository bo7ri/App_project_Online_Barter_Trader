package ca.dal.cs.csci3130.g01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
        Button registerPageButton = findViewById(R.id.RegisterButton);
        registerPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent movingToRegisterPageIntent = new Intent(LoginPage.this, RegisterPage.class);
                LoginPage.this.startActivity(movingToRegisterPageIntent);
            }
        });



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
                             String userType = document.get("UserType").toString();

                             if (pass.equals(timp)){
                                 //Intent to main page here
                                 Intent moveToListPage = new Intent(getApplicationContext(), ProvidersListings.class);
                                 moveToListPage.putExtra("username", userName);
                                 moveToListPage.putExtra("usertype", userType);
                                 LoginPage.this.startActivity(moveToListPage);

                             }

                             else {
                                 TextView loginStatus = findViewById(R.id.LoginStatusText);
                                 String validity = getResources().getString(R.string.INVALID_LOGIN).trim();
                                 loginStatus.setText(R.string.INVALID_LOGIN);
                                 Toast.makeText(LoginPage.this, validity, Toast.LENGTH_SHORT).show();
                             }

                         }


                        }

                    }
                });

            }
        });
    }
    }
