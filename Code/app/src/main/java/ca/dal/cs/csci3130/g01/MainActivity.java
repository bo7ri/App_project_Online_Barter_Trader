package ca.dal.cs.csci3130.g01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton PrdctAddPageButton = findViewById(R.id.PrdctAddBtn);
        PrdctAddPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent movingToListPageIntent = new Intent(MainActivity.this, ListPage.class);
                MainActivity.this.startActivity(movingToListPageIntent);
            }
        });

    }



}