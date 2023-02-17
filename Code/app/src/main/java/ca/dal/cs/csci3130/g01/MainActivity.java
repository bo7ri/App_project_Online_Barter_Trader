package ca.dal.cs.csci3130.g01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.core.Tag;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    //private ArrayAdapter adapter;
    ListView listView;
    ArrayAdapter<String> adapter;
    List<String> list = new ArrayList<>();



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.myToolbar);
        listView = (ListView) findViewById(R.id.listview);

        list.add("Item1");
        list.add("Item2");
        list.add("Item3");
        list.add("Item4");

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, list);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type here to search..");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.search:
                Toast.makeText(this, "Search bar clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.profile:
                Toast.makeText(this, "Profile bar clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu:
                Toast.makeText(this, "Logout bar clicked", Toast.LENGTH_SHORT).show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}