package ca.dal.cs.csci3130.g01;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Mohamed Al-Maimani
 */
public class ProvidersListings extends AppCompatActivity implements RecyclerAdapter.onClickRecyclerView{

    List<Product> productList = new ArrayList<>();
    FirebaseFirestore database;
    RecyclerView recyclerView;
    TextView emptyList;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_providers_listings);

        recyclerView = findViewById(R.id.recyclerView);
        emptyList = findViewById(R.id.listIsEmpty);

        // Data base set up
        database = FirebaseFirestore.getInstance();

        // set data Locally
//        setData();

        // upload data to firebase
//        uploadData();

        // adapter set up
        RecyclerAdapter adapter = new RecyclerAdapter(productList);

        // Set data from DB
        setupDataDB(adapter);

        // adapter set up
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.setOnClickRecyclerView(this);

    }


    private void setupDataDB(RecyclerAdapter adapter) {
        database.collection("ProductList").get().addOnSuccessListener(queryDocumentSnapshots -> {

            if (queryDocumentSnapshots.isEmpty()){
                return;
            }

            List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
            for (DocumentSnapshot document: snapshotList) {
                Product product = document.toObject(Product.class);

                if (product != null) productList.add(product);
            }
            adapter.notifyDataSetChanged();

            if (adapter.getItemCount() == 0) emptyList.append("No products found");
        });
    }

    private void setData() {
        for (int i = 1; i <= 5; i++) {

            productList.add(new Product("title" + i,"desc" + i));
        }
    }

    private void uploadData(){

        for (int i = 0; i < productList.size(); i++) {
            database.collection("ProductList").add(productList.get(i));
        }
    }


    @Override
    public void onClick(int position) {
        viewItemDetails(productList.get(position));
    }

    public void viewItemDetails(Product product){
        Intent itemDetails = new Intent(getApplicationContext(), ItemDetails.class);
        itemDetails.putExtra("product", product);
        startActivity(itemDetails);
    }
}