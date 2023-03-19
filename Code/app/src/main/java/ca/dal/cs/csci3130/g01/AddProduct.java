package ca.dal.cs.csci3130.g01;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;


public class AddProduct extends AppCompatActivity {

    private EditText PrdctTitle, PrdctDescription, PrdctPrice;
    private Button SubmitPrdct, CancelPrdct;

    String keyCountString;
    int key_count;
    double price;
    private String username;
    private Product product;
    private String usertype;
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


        // Get Extra username
        username = getIntent().getStringExtra("username");
        usertype = getIntent().getStringExtra("usertype");

        SubmitPrdct.setOnClickListener(view -> addProductData());

        CancelPrdct.setOnClickListener(view -> {
            Intent home = new Intent(getApplicationContext(), ProvidersListings.class);
            startActivity(home);
        });

        getKeys();
    }

    /** Adding product data */
    private String ProductName, ProductDescription, ProductPrice, currentUsername;
    private void addProductData(){
        ProductName = PrdctTitle.getText().toString().trim();
        ProductDescription = PrdctDescription.getText().toString().trim();
        ProductPrice = PrdctPrice.getText().toString().trim();
        price = Double.parseDouble(ProductPrice);
        currentUsername = username.trim();

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


        // DB
        addProductToDB();
    }

    private void addProductToDB() {
        // Sending the data to firebase.
        Product newProduct = new Product(ProductName, ProductDescription, keyCountString, "0", R.drawable.no_image_found_default, username, price);
        product = newProduct;

        updateKeys();

        cloudDatabase.collection("ProductList").add(newProduct);

        /** Success Toast */
        Toast.makeText(this, "Product added successfully!", Toast.LENGTH_LONG).show();

        /** Back To Add Product Page */
        switchToProviderListings();
    }

    protected void switchToProviderListings() {
        Intent switchToProvidersListings = new Intent(getApplicationContext(), ProvidersListings.class);
        switchToProvidersListings.putExtra("username", username);
        switchToProvidersListings.putExtra("product", product);
        switchToProvidersListings.putExtra("usertype", usertype);
        startActivity(switchToProvidersListings);
    }

    public void getKeys(){
        DocumentReference documentReference = cloudDatabase.collection("NumberKeys").document("XokHk1KXtd8RlTSvZGGN");
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                keyCountString = documentSnapshot.getString("numberOfKeys");
            }
        });
    }

    public void updateKeys(){
        key_count = Integer.parseInt(keyCountString);
        key_count++;
        keyCountString = Integer.toString(key_count);
        Map<String, Object> keyUpdate = new HashMap<>();
        keyUpdate.put("numberOfKeys", keyCountString);

        cloudDatabase.collection("NumberKeys").whereEqualTo("numberOfKeys", keyCountString).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        cloudDatabase.collection("NumberKeys").document("XokHk1KXtd8RlTSvZGGN").update(keyUpdate);

                    }
                });
    }
}