package ca.dal.cs.csci3130.g01;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EditProduct extends AppCompatActivity {
    private EditText PrdctTitle, PrdctDescription, PrdctPrice;
    private Button UpdateProduct;

    String username;
    FirebaseFirestore cloudDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Loading in the layout.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        cloudDatabase = FirebaseFirestore.getInstance();

        username = getIntent().getStringExtra("username");

        PrdctTitle = findViewById(R.id.productName);
        PrdctDescription = findViewById(R.id.productDescription);
        UpdateProduct = findViewById(R.id.updateProduct);

        // Get parcel from ProvidersList
        Product product = getIntent().getParcelableExtra("product");

        if(product != null){
            PrdctTitle.setText(product.getTitle());
            PrdctDescription.setText(product.getDescription());
        }

        UpdateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Product newProduct = new Product(PrdctTitle.getText().toString(), PrdctDescription.getText().toString(),
//                        product.getKey_id(), product.getFavStatus(), product.getImageResource(), product.getUsername(), product.getPrice());
//
//                cloudDatabase.collection("ProductList").add(newProduct);

                Map<String, Object> UpdatedProductMap = new HashMap<>();
                UpdatedProductMap.put("title", PrdctTitle.getText().toString());
                UpdatedProductMap.put("description", PrdctDescription.getText().toString());


                    cloudDatabase.collection("ProductList").whereEqualTo("title",product.getTitle())
                            .whereEqualTo("username",username).whereEqualTo("description",product.getDescription())
                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()){
                                        for(QueryDocumentSnapshot document : task.getResult()){
                                            String productID = document.getId();
                                            cloudDatabase.collection("ProductList").document(productID)
                                                    .set(UpdatedProductMap, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {
                                                            Toast.makeText(EditProduct.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                                                            switchToProviderListings();
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(EditProduct.this, "Did not update", Toast.LENGTH_SHORT).show();
                                                            switchToProviderListings();
                                                        }
                                                    });
                                        }
                                    }
                                }
                            });




            }
        });

    }




    protected void switchToProviderListings() {
        Intent switchToProvidersListings = new Intent(getApplicationContext(), ProvidersListings.class);
        switchToProvidersListings.putExtra("username", username);
        startActivity(switchToProvidersListings);
    }

}
