package ca.dal.cs.csci3130.g01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

/**
 * @author Mohamed Al-Maimani
 * This class desplays the item's titles and description.
 */
public class ItemDetails extends AppCompatActivity {

    Toolbar toolbar;

    FirebaseFirestore database;

    private String username;
    private String usertype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        database = FirebaseFirestore.getInstance();


        // Get the TextView
        TextView productTitle = findViewById(R.id.productTitle);
        TextView productDescription = findViewById(R.id.productDesp);

        // Get custom toolbar
        toolbar = findViewById(R.id.toolBar);

        // Get parcel from ProvidersList
        Product product = getIntent().getParcelableExtra("product");
        username = getIntent().getStringExtra("username");
        usertype = getIntent().getStringExtra("usertype");

        // set product title and desc.
        if(product != null){
            productTitle.setText(product.getTitle());
            productDescription.setText(product.getDescription());
        }

        // Setting up the sendRequestButton.
        Button sendRequestButton = findViewById(R.id.sendRequestBtn);
        sendRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

        if(item.getItemId() == R.id.logout){
            // transfer to login page
            Intent logout = new Intent(getApplicationContext(), LoginPage.class);
            startActivity(logout);
        }

        return super.onOptionsItemSelected(item);
    }
}