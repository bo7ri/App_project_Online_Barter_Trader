package ca.dal.cs.csci3130.g01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Loading in the layout..
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerpage);

        // Creating a submitting button.
        Button registerSubmitButton = findViewById(R.id.registerSubmitButton);
        registerSubmitButton.setOnClickListener(view -> {

            // Initializing the fields.
            String inputUsername = getUsername();
            String inputPassword = getPassword();
            String inputFirstName = getFirstName();
            String inputLastName = getLastName();
            String inputEmailAddress = getEmailAddress();
            String inputUserType = getUserType();

            // Checking if one or more fields are empty.
            if (!isRegisterFieldsEmpty(inputUsername, inputPassword, inputFirstName, inputLastName, inputEmailAddress, inputUserType)) {

                // Error checking the fields.
                boolean errorFlag = false;
                if (inputUsername.length() < 3) {
                    errorFlag = true;
                    setRegisterStatus("Invalid! Username needs to contain at least three characters.");
                    Toast.makeText(RegisterPage.this, "Invalid! Username needs to contain at least three characters.", Toast.LENGTH_LONG).show();
                } else if (inputPassword.length() < 3) {
                    errorFlag = true;
                    setRegisterStatus("Invalid! Password needs to contain at least three characters.");
                    Toast.makeText(RegisterPage.this, "Invalid! Password needs to contain at least three characters.", Toast.LENGTH_LONG).show();
                } else if (inputFirstName.length() < 3) {
                    errorFlag = true;
                    setRegisterStatus("Invalid! First name needs to contain at least three characters.");
                    Toast.makeText(RegisterPage.this, "Invalid! First name needs to contain at least three characters.", Toast.LENGTH_LONG).show();
                } else if (inputLastName.length() < 3) {
                    errorFlag = true;
                    setRegisterStatus("Invalid! Last name needs to contain at least three characters.");
                    Toast.makeText(RegisterPage.this, "Invalid! Last name needs to contain at least three characters.", Toast.LENGTH_LONG).show();
                } else if (isValidEmailAddress(inputEmailAddress) == false) {
                    errorFlag = true;
                    setRegisterStatus("Invalid! Email address contains invalid length or characters.");
                    Toast.makeText(RegisterPage.this, "Invalid! Email address contains invalid length or characters.", Toast.LENGTH_LONG).show();
                } else if (isValidUserType(inputUserType) == false) {
                    errorFlag = true;
                    setRegisterStatus("Invalid! Must be 'Provider' or 'Receiver' exactly.");
                    Toast.makeText(RegisterPage.this, "Invalid! Must be 'Provider' or 'Receiver' exactly.", Toast.LENGTH_LONG).show();
                }

                if (errorFlag == false){

                    // Sending the data to firebase.
                    FirebaseFirestore cloudDatabase = FirebaseFirestore.getInstance();
                    Map<String, Object> registeredUser = new HashMap<>();
                    registeredUser.put("Username", inputUsername);
                    registeredUser.put("Password", inputPassword);
                    registeredUser.put("FirstName", inputFirstName);
                    registeredUser.put("LastName", inputLastName);
                    registeredUser.put("EmailAddress", inputEmailAddress);
                    registeredUser.put("UserType", inputUserType);
                    registeredUser.put("Rating", "0.0");
                    registeredUser.put("numberOfRatings", "0");
                    cloudDatabase.collection("UserList").add(registeredUser);

                    // Switching back to login page.
                    setRegisterStatus("Registration successful!");
                    Toast.makeText(RegisterPage.this, "Registration successful!", Toast.LENGTH_LONG).show();
                    switchToLoginPage();
                }

            } else {

                // Empty fields error message.
                setRegisterStatus("One or more fields are empty!");
                Toast.makeText(RegisterPage.this, "One or more fields are empty!", Toast.LENGTH_LONG).show();

            }

        });

    }

    // Getting the username from field.
    protected String getUsername() {
        EditText registerUsernameBox = findViewById(R.id.registerUsernameField);
        return registerUsernameBox.getText().toString().trim();
    }

    // Getting the password from field.
    protected String getPassword() {
        EditText registerPasswordBox = findViewById(R.id.registerPasswordField);
        return registerPasswordBox.getText().toString().trim();
    }

    // Getting the first name from field.
    protected String getFirstName() {
        EditText registerFirstNameBox = findViewById(R.id.registerFirstNameField);
        return registerFirstNameBox.getText().toString().trim();
    }

    // Getting the last name from field.
    protected String getLastName() {
        EditText registerLastNameBox = findViewById(R.id.registerLastNameField);
        return registerLastNameBox.getText().toString().trim();
    }

    // Getting email from field.
    protected String getEmailAddress() {
        EditText registerEmailBox = findViewById(R.id.registerEmailField);
        return registerEmailBox.getText().toString().trim();
    }

    // Getting user type from field.
    protected String getUserType() {
        EditText registerUserType = findViewById(R.id.registerUserTypeField);
        return registerUserType.getText().toString().trim();
    }

    // Switching back to login page.
    protected void switchToLoginPage() {
        Intent switchToLoginIntent = new Intent(RegisterPage.this, LoginPage.class);
        RegisterPage.this.startActivity(switchToLoginIntent);
    }

    // Checking if any of the fields are empty.
    protected boolean isRegisterFieldsEmpty(String username, String password, String firstName,
                                            String lastName, String emailAddress, String userType) {

        // Checking each of the field to be empty or not.
        boolean emptyFlag = false;
        if (username.isEmpty()) {
            emptyFlag = true;
        } else if (password.isEmpty()) {
            emptyFlag = true;
        } else if (firstName.isEmpty()) {
            emptyFlag = true;
        } else if (lastName.isEmpty()) {
            emptyFlag = true;
        } else if (emailAddress.isEmpty()) {
            emptyFlag = true;
        } else if (userType.isEmpty()) {
            emptyFlag = true;
        }
        return emptyFlag;

    }

    // Checking structure of email address.
    protected boolean isValidEmailAddress(String emailAddress) {

        // Initializing flags
        boolean firstSymbolFlag = false;
        boolean secondSymbolFlag = false;
        boolean resultFlag = false;
        int firstSymbolPosition = 0;

        // Looping through the email address.
        if (emailAddress.length() >= 5) {
            for (int i = 0; i < emailAddress.length(); i++) {
                if ((emailAddress.charAt(i) == '@') && (i > 0)) {
                    firstSymbolFlag = true;
                    firstSymbolPosition = i;
                } else if ((firstSymbolFlag == true) && (emailAddress.charAt(i) == '.') && (i >= (firstSymbolPosition + 2))) {
                    secondSymbolFlag = true;
                }
            }
        }

        // Checking if contains both @ and then . after it.
        if ((firstSymbolFlag == true) && (secondSymbolFlag == true)) {
            resultFlag = true;
        }

        // Returns true if valid, false if invalid.
        return resultFlag;

    }

    protected boolean isValidUserType(String userType) {

        // Initializing flag
        boolean resultFlag = false;

        // Checking if user type if "Provider" or "Receiver".
        if ((userType.equals("Provider")) || (userType.equals("Receiver"))) {
            resultFlag = true;
        }

        // Return true if valid, false if invalid.
        return resultFlag;

    }

    // Used to run and check tests.
    protected void setRegisterStatus(String errorMessage) {
        TextView statusLabel = findViewById(R.id.registerationTestMessage);
        statusLabel.setText(errorMessage.trim());
    }

}
