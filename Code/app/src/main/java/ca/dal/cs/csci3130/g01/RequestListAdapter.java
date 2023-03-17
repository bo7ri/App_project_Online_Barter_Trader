package ca.dal.cs.csci3130.g01;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

// Source used for help with creating Adapter: https://www.geeksforgeeks.org/how-to-create-dynamic-listview-in-android-using-firebase-firestore/ [Mar 16, 2023].

public class RequestListAdapter extends ArrayAdapter<RequestItem> {

    public RequestListAdapter(@NonNull Context context, ArrayList<RequestItem> requestItemArrayList) {
        super(context, 0, requestItemArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View childView, @NonNull ViewGroup parent) {

        // Inflater view.
        View inflateRequestListView = childView;
        if (inflateRequestListView == null) {
            inflateRequestListView = LayoutInflater.from(getContext()).inflate(R.layout.request_list_item, parent, false);
        }

        // Get requestItem from ArrayList.
        RequestItem requestItem = getItem(position);

        // Get TextView of Product Title and Receiver Username.
        TextView requestProductTitle = inflateRequestListView.findViewById(R.id.requestListItemProductTitleInput);
        TextView requestReceiverName = inflateRequestListView.findViewById(R.id.requestListItemRequesterName);

        // Setting product title and receiver username view.
        requestProductTitle.setText(requestItem.getProductTitle());
        requestReceiverName.setText(requestItem.getReceiverUsername());

        // Adding onClick to the item.
        inflateRequestListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveToRequestDetailPage = new Intent(view.getContext(), RequestDetailsPage.class);
                moveToRequestDetailPage.putExtra("ReceiverUsername", requestItem.getReceiverUsername());
                moveToRequestDetailPage.putExtra("ProviderUsername", requestItem.getProviderUsername());
                moveToRequestDetailPage.putExtra("ProductTitle", requestItem.getProductTitle());
                moveToRequestDetailPage.putExtra("ProductDescription", requestItem.getProductDescription());
                moveToRequestDetailPage.putExtra("RequestMessage", requestItem.getRequestMessage());
                Toast.makeText(getContext(), "Product clicked is: " + requestItem.getProductTitle(), Toast.LENGTH_LONG).show();
                view.getContext().startActivity(moveToRequestDetailPage);
            }
        });

        // Returning the view.
        return inflateRequestListView;

    }

}
