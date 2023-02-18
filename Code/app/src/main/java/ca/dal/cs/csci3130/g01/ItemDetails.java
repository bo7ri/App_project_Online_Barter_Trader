package ca.dal.cs.csci3130.g01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

/**
 * @author Mohamed Al-Maimani
 * This class desplays the item's titles and description.
 */
public class ItemDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        // Get the TextView
        TextView productTitle = findViewById(R.id.productTitle);
        TextView productDescription = findViewById(R.id.productDesp);

        // Get parcel from ProvidersList
        Product product = getIntent().getParcelableExtra("product");

        // set product title and desc.
        productTitle.setText(product.getTitle());
        productDescription.setText(product.getDescription());

    }
}