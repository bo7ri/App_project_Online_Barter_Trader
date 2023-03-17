package ca.dal.cs.csci3130.g01;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

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

//        UpdateProduct.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                inputProductData();
//            }
//        });
    }


    private String ProductName, ProductDescription;

//    private void inputProductData(){
//        ProductName = PrdctTitle.getText().toString().trim();
//        ProductDescription = PrdctDescription.getText().toString().trim();
//
//        /** Validating The Data */
//        if (TextUtils.isEmpty(ProductName)){
//            Toast.makeText(this, "Product Name is required!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (TextUtils.isEmpty(ProductDescription)){
//            Toast.makeText(this, "Product Description is required!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        // DB
//        addProductToDB();
//    }
}
