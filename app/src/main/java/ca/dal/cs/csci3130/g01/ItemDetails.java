package ca.dal.cs.csci3130.g01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;



/**
 * @author Mohamed Al-Maimani
 * This class desplays the item's titles and description.
 */
public class ItemDetails extends AppCompatActivity {

    Toolbar toolbar;
    Button rateButton;
    FirebaseFirestore database;
    private String username;

    Button providerPageBtn;



    private String usertype;
    private String lastName;
    private float rating;

    private float distance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        database = FirebaseFirestore.getInstance();


        // Get the TextView
        TextView productTitle = findViewById(R.id.productTitle);
        TextView productDescription = findViewById(R.id.productDesp);
        TextView provinceProduct = findViewById(R.id.provinceProduct);
        TextView cityProduct = findViewById(R.id.cityProduct);

        // Get custom toolbar
        toolbar = findViewById(R.id.toolBar);
        username = getIntent().getStringExtra("username");

        // Get parcel from ProvidersList
        Product product = getIntent().getParcelableExtra("product");
        username = getIntent().getStringExtra("username");
        usertype = getIntent().getStringExtra("usertype");
        lastName = getIntent().getStringExtra("lastName");

        // set product title and desc.
        if(product != null){
            productTitle.setText(product.getTitle());
            productDescription.setText(product.getDescription());

            if(product.getProvince() != null && product.getCity() != null){
                provinceProduct.setText(product.getProvince());
                cityProduct.setText(product.getCity());
            }

        }

        rateButton = findViewById(R.id.rating);

        //take to rating page
        rateButton.setOnClickListener(view -> {
            Intent movingToRatingPage = new Intent(ItemDetails.this, Rate.class);
            movingToRatingPage.putExtra("product", product);
            movingToRatingPage.putExtra("username", username);
            movingToRatingPage.putExtra("usertype", usertype);
            startActivity(movingToRatingPage);
        });

        //display the rating
        rating = getIntent().getFloatExtra("rating", 0.0f);

        // For example, you can display the rating in a TextView
        TextView ratingTextView = findViewById(R.id.rating_text_view);
        ratingTextView.setText("Rating: " + rating);

        //display distance
        distance = 0;

        //display the distance in a TextView
        TextView distanceTextView = findViewById(R.id.distanceTextView);
        if (product.getCity() != null) {
            if(product.getCity().equals("Halifax")){
                distance = 2;
                Toast.makeText(ItemDetails.this,"This Item is in your local area!", Toast.LENGTH_LONG).show();
            }
            if(product.getCity().equals("Dartmouth")){
                distance = 5;
                Toast.makeText(ItemDetails.this,"This Item is in your local area!", Toast.LENGTH_LONG).show();
            }
            if(product.getCity().equals("Sydney")){
                distance = 400;
            }
            if(product.getCity().equals("Truro")){
                distance = 95;
            }
            if(product.getCity().equals("Lower Sackville")){
                distance = 19;
            }
        }
        else{
            Toast.makeText(ItemDetails.this,"This Item is in your local area!", Toast.LENGTH_LONG).show();
        }
        distanceTextView.setText("Distance: " + distance);



        // Setting up the sendRequestButton.
        Button sendRequestButton = findViewById(R.id.sendRequestBtn);
        sendRequestButton.setOnClickListener(view -> {
            if (usertype.equals("Receiver")) {
                Intent moveToRequestPage = new Intent(ItemDetails.this, SendRequestPage.class);
                moveToRequestPage.putExtra("username", username);
                moveToRequestPage.putExtra("product", product);
                moveToRequestPage.putExtra("usertype", usertype);
                ItemDetails.this.startActivity(moveToRequestPage);
            } else {
                Intent moveBackToListPage = new Intent(ItemDetails.this, ProvidersListings.class);
                moveBackToListPage.putExtra("username", username);
                moveBackToListPage.putExtra("usertype", usertype);
                Toast.makeText(ItemDetails.this, "Provider cannot send request!", Toast.LENGTH_LONG).show();
                ItemDetails.this.startActivity(moveBackToListPage);
            }
        });


        // Got provider page
        providerPageBtn = findViewById(R.id.providerProfilePage);
        providerPageBtn.setOnClickListener(view -> {
            if(usertype.equals("Receiver")){
                Intent moveToProviderPage = new Intent(getApplicationContext(), ProviderPage.class);
                moveToProviderPage.putExtra("product", product);
                moveToProviderPage.putExtra("username", username);
                startActivity(moveToProviderPage);
            }
        });

        if (usertype.equals("Provider")) providerPageBtn.setEnabled(false);


        // GO TO EDIT PAGE
        ImageButton ProductEditPageButton = findViewById(R.id.EditPrdct);
        ProductEditPageButton.setOnClickListener(view -> {
            if (username.equals(product.getUsername())) {
                Intent movingToEditPage = new Intent(ItemDetails.this, EditProduct.class);
                movingToEditPage.putExtra("product", product);
                movingToEditPage.putExtra("username", username);
                startActivity(movingToEditPage);
            }
            else {
                Toast.makeText(this, "You do not own the product. You cannot edit the product", Toast.LENGTH_SHORT).show();
            }
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
        inflater.inflate(R.menu.item_details_menu, menu);


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
            // transfer to home pahe
            Intent home = new Intent(getApplicationContext(), ProvidersListings.class);
            home.putExtra("username", username);
            home.putExtra("usertype", usertype);
            startActivity(home);
        }
        else if(item.getItemId() == R.id.profile){
            // transfer to profile activity
            Intent profilePage = new Intent(getApplicationContext(), Profile.class);
            if(username != null) profilePage.putExtra("username", username);
            startActivity(profilePage);
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
            startActivity(logout);
        }

        return super.onOptionsItemSelected(item);
    }


}



