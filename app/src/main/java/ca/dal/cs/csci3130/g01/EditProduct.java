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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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

        ArrayList<String> ref = new ArrayList<String>();

        final String[] NewPrdctTitle = new String[1];
        final String[] NewPrdctDesc = new String[1];


        UpdateProduct.setOnClickListener(view -> {
//            cloudDatabase.collection("ProductList").get().addOnSuccessListener(queryDocumentSnapshots -> {
//                List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
//
//                for (DocumentSnapshot document: snapshotList) {
//                    Product editProduct = document.toObject(Product.class);
//
//                    if (editProduct != null) {
//                        String userName = editProduct.getUsername();
//                        String productTitle = editProduct.getTitle();
//                        if(productTitle.equals(product.getTitle())){
//                            ref.add(document.getId());
//
//                        }
//                    }
//                }
//
//                NewPrdctTitle[0] = PrdctTitle.getText().toString().trim();
//                NewPrdctDesc[0] = PrdctDescription.getText().toString().trim();
//
//                // update database
//                cloudDatabase.collection("ProductList").document(ref.get(0)).update(
//                        "title", NewPrdctTitle[0],
//                        "description", NewPrdctDesc[0]);
//
//                Toast.makeText(this, "Updated Successfully!", Toast.LENGTH_SHORT).show();
//            });



            switchToProviderListings();
        });
    }



//    public void updateProduct(Product product){
//
//
//        String username  = product.getUsername();
//
//        ArrayList<String> ref = new ArrayList<String>();
//
//        cloudDatabase.collection("ProductList").get().addOnSuccessListener(queryDocumentSnapshots -> {
//            List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
//
//            for (DocumentSnapshot document: snapshotList) {
//                Product editProduct = document.toObject(Product.class);
//
//                if (editProduct != null) {
//                    if(editProduct.getTitle().equals(product.getTitle())){
//                        ref.add(document.getId());
//                    }
//                }
//            }
//        });
//
//
//    }
    protected void switchToProviderListings() {
        Intent switchToProvidersListings = new Intent(getApplicationContext(), ProvidersListings.class);
        switchToProvidersListings.putExtra("username", username);
        startActivity(switchToProvidersListings);
    }

}
