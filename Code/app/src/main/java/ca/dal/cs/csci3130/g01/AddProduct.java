package ca.dal.cs.csci3130.g01;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;


public class AddProduct extends AppCompatActivity {

    private EditText PrdctTitle, PrdctDescription, PrdctPrice;
    private Button SubmitPrdct, CancelPrdct;

    int key_count = 0;


    FirebaseFirestore cloudDatabase;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Loading in the layout.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        cloudDatabase = FirebaseFirestore.getInstance();

        PrdctTitle = findViewById(R.id.addProductTitle);
        PrdctDescription = findViewById(R.id.addProductDescription);
        PrdctPrice = findViewById(R.id.addProductPrice);
        SubmitPrdct = findViewById(R.id.submitAddProduct);
        CancelPrdct = findViewById(R.id.cancelAddProduct);

        SubmitPrdct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProductData();
            }
        });

        CancelPrdct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(getApplicationContext(), ProvidersListings.class);
                startActivity(home);
            }
        });
    }

    /** Adding product data */
    private String ProductName, ProductDescription, ProductPrice;
    private void addProductData(){
        ProductName = PrdctTitle.getText().toString().trim();
        ProductDescription = PrdctDescription.getText().toString().trim();
        ProductPrice = PrdctPrice.getText().toString().trim();

        /** Validating The Data */
        if (TextUtils.isEmpty(ProductName)){
            Toast.makeText(this, "Product Name is required!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(ProductDescription)){
            Toast.makeText(this, "Product Description is required!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(ProductPrice)){
            Toast.makeText(this, "Product Price is required!", Toast.LENGTH_SHORT).show();
            return;
        }

//        Product newProduct = new Product(ProductName,ProductDescription);
//        switchToProviderListings(newProduct);

        // DB
        addProductToDB();
    }

    private void addProductToDB() {
        // Sending the data to firebase.
        String keyString = Integer.toString(key_count);
        key_count++;

        Product newProduct = new Product(ProductName, ProductDescription, keyString, "0", R.drawable.no_image_found_default);

        cloudDatabase.collection("ProductList").add(newProduct);

        /** Success Toast */
        Toast.makeText(this, "Product added successfully!", Toast.LENGTH_LONG).show();

        /** Back To Add Product Page */
        switchToProviderListings();
    }

    protected void switchToProviderListings() {
        Intent switchToProvidersListings = new Intent(getApplicationContext(), ProvidersListings.class);
        finish();
        startActivity(switchToProvidersListings);
    }
}