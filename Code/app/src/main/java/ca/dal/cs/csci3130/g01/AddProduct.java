package ca.dal.cs.csci3130.g01;

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
    private Button SubmitPrdct;


    FirebaseFirestore cloudDatabase;

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

        SubmitPrdct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProductData();
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


        Product newProduct = new Product(ProductName,ProductDescription);

        cloudDatabase.collection("ProductList").add(newProduct);

        /** Success Toast */
        Toast.makeText(this, "Product added successfully!", Toast.LENGTH_LONG).show();

        /** Back To Add Product Page */
        switchToProviderListings(newProduct);
    }

    protected void switchToProviderListings(Product newProduct) {
        Intent switchToProvidersListings = new Intent(AddProduct.this, ProvidersListings.class);

        // Sends new product to productList in ProvidersListings.java
        switchToProvidersListings.putExtra("new product", newProduct);
        AddProduct.this.startActivity(switchToProvidersListings);
    }



}
