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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class EditProduct extends AppCompatActivity {
    private EditText PrdctTitle, PrdctDescription, PrdctPrice;
    private Button UpdateProduct;


    FirebaseFirestore cloudDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Loading in the layout.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        cloudDatabase = FirebaseFirestore.getInstance();



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
                updateProduct();
            }
        });
    }
    String NewPrdctTitle, NewPrdctDesc;



    public void updateProduct(){
        NewPrdctTitle = PrdctTitle.getText().toString().trim();
        NewPrdctDesc = PrdctDescription.getText().toString().trim();

//        ArrayList<String> ref = new ArrayList<String>();
//
////        cloudDatabase.collection("ProductList").whereEqualTo("title", PrdctTitle.getText().toString()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
////            @Override
////            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
////                if (!queryDocumentSnapshots.isEmpty())
////                    Toast.makeText(EditProduct.this, "NOT EMPTY", Toast.LENGTH_SHORT).show();
////            }
////        });
//
//        cloudDatabase.collection("ProductList")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                document
//                            }
//                        } else {
//                            Log.d(TAG, "Error getting documents: ", task.getException());
//                        }
//                    }
//                });

        cloudDatabase.collection("ProductList").document("aRwy8Ji3cW2jhKvJqxED")
                .update(
                        "title", NewPrdctTitle,
                        "description", NewPrdctDesc
                );

        Toast.makeText(this, "Updated Successfully!", Toast.LENGTH_SHORT).show();

        switchToProviderListings();
    }
    protected void switchToProviderListings() {
        Intent switchToProvidersListings = new Intent(getApplicationContext(), ProvidersListings.class);
        finish();
        startActivity(switchToProvidersListings);
    }

}
