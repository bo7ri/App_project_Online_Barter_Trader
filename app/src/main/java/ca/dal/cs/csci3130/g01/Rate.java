package ca.dal.cs.csci3130.g01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

public class Rate extends AppCompatActivity {


    Button button;
    RatingBar ratingStars;
    float myRating = 0;
    private String username;
    private String usertype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        button = findViewById(R.id.button);
        ratingStars = findViewById(R.id.ratingBar);


        ratingStars.setOnRatingBarChangeListener((ratingBar, v, b) -> {

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
                default:
                    message = "Please select a rating";
                    break;
            }
            Toast.makeText(Rate.this, message, Toast.LENGTH_SHORT).show();
        });

        // Getting the username from intent.
        username = getIntent().getStringExtra("username");
        usertype = getIntent().getStringExtra("usertype");

        // Get parcel from ProvidersList
        Product product = getIntent().getParcelableExtra("product");
        button.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), ItemDetails.class);
            intent.putExtra("rating", myRating);
            intent.putExtra("product", product);
            intent.putExtra("username", username);
            intent.putExtra("product", product);
            intent.putExtra("usertype", usertype);
            startActivity(intent);
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
            home.putExtra("usertype", "Receiver");
            startActivity(home);
        }
        else if(item.getItemId() == R.id.savedItems){
            // transfer to saved items page
            Intent savedPage = new Intent(getApplicationContext(), SavedItems.class);
            savedPage.putExtra("username", username);
            savedPage.putExtra("usertype", "Receiver");
            startActivity(savedPage);
        }
        else if(item.getItemId() == R.id.logout){
            // transfer to login page
            Intent logout = new Intent(getApplicationContext(), LoginPage.class);
            logout.putExtra("username", username);
            logout.putExtra("usertype", "Receiver");
            startActivity(logout);
        }

        return super.onOptionsItemSelected(item);
    }


}



