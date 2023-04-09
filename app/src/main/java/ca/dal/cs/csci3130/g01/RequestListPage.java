package ca.dal.cs.csci3130.g01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Source used for help with creating list page: https://www.geeksforgeeks.org/how-to-create-dynamic-listview-in-android-using-firebase-firestore/ [Mar 16, 2023].
public class RequestListPage extends AppCompatActivity {


    ListView requestItemListView;
    ArrayList<RequestItem> requestItemArrayList;
    FirebaseFirestore databaseInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Loading in the layout.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requestlist_page);

        // Getting intents.
        String username = getIntent().getStringExtra("username");

        // Initializing variables.
        requestItemListView = findViewById(R.id.listViewOfRequests);
        requestItemArrayList = new ArrayList<>();
        databaseInstance = FirebaseFirestore.getInstance();

        // Getting data from the database.
        // Looping through documentSnapshot captured by the whereEqualTo method.
        // Converting to RequestItem object before adding to arraylist.
        // Create and set request list adapter.
        // Sending a toast message if failed to load from database.
        databaseInstance.collection("RequestList").whereEqualTo("ProviderUsername", username).get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (!queryDocumentSnapshots.isEmpty()) {
                List<DocumentSnapshot> documentSnapshotList = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot document : documentSnapshotList) {
                    RequestItem requestItem = document.toObject(RequestItem.class);
                    requestItemArrayList.add(requestItem);
                }
                RequestListAdapter requestListAdapter = new RequestListAdapter(RequestListPage.this, requestItemArrayList);
                requestItemListView.setAdapter(requestListAdapter);
            } else {
                Toast.makeText(RequestListPage.this, "There is no request messages for you! Try again later!", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(e -> Toast.makeText(RequestListPage.this, "Error loading from Firestore Firebase!", Toast.LENGTH_LONG).show());

    }

}


