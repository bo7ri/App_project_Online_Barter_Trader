package ca.dal.cs.csci3130.g01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

public class Rate extends AppCompatActivity {


    Button button, rateButton;
    RatingBar ratingStars;
    float myRating = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        button = findViewById(R.id.button);
        ratingStars = findViewById(R.id.ratingBar);
        //rateButton = findViewById(R.id.rating);



        ratingStars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged (RatingBar ratingBar, float v, boolean b){

                int rating = (int) v;
                String message = null;

                myRating = ratingBar.getRating();
                switch (rating) {
                    case 1:
                        message = "Sorry to hear that! :(";
                        break;
                    case 2:
                        message = "You always accept suggestions!";
                        break;
                    case 3:
                        message = "Good enough!";
                        break;
                    case 4:
                        message = "Great! Thank you!";
                        break;
                    case 5:
                        message = "Awesome! You are the best!";
                        break;
                }
                Toast.makeText(Rate.this, message, Toast.LENGTH_SHORT).show();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Rate.this, ItemDetails.class);
                intent.putExtra("rating", myRating);
                startActivity(intent);

            }
        });


    }


}



