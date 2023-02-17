package ca.dal.cs.csci3130.g01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button registerPageButton = findViewById(R.id.registerPageButton);
        registerPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent movingToRegisterPageIntent = new Intent(MainActivity.this, RegisterPage.class);
                MainActivity.this.startActivity(movingToRegisterPageIntent);
            }
        });

    }
}