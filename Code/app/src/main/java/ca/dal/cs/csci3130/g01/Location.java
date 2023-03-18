package ca.dal.cs.csci3130.g01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class Location extends AppCompatActivity {


    //initializing the spinners for provenience and cities
    private Spinner provincesSpinner;
    private Spinner citiesSpinner;

    //Array to adapt the changes of the values
    private ArrayAdapter<String> provincesAdapter;
    private ArrayAdapter<String> citiesAdapter;


    //will update the Cities Spinner with the appropriate list of cities when a province is selecte
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);provincesSpinner = findViewById(R.id.provinces_spinner);
        citiesSpinner = findViewById(R.id.cities_spinner);

        provincesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.provinces_list));
        provincesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        provincesSpinner.setAdapter(provincesAdapter);

        citiesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.cities_list));
        citiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citiesSpinner.setAdapter(citiesAdapter);


        // Note: Might be used for the future.
        // checks which province was selected and updates the cities list accordingly
        provincesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String selectedProvince = (String) adapterView.getItemAtPosition(position);
//                if (selectedProvince.equals("Nova Scotia")) {
////                    citiesAdapter.clear();
//                    citiesAdapter.addAll(getResources().getStringArray(R.array.cities_list));
//                } else if (selectedProvince.equals("Ontario")) {
//                    citiesAdapter.clear();
//                    citiesAdapter.addAll(getResources().getStringArray(R.array.ontario_cities_list));
//                } else if (selectedProvince.equals("Quebec")) {
//                    citiesAdapter.clear();
//                    citiesAdapter.addAll(getResources().getStringArray(R.array.quebec_cities_list));
//                }
//
//                citiesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //Doing nothing
            }
        });

        // Getting intents.
        String username = getIntent().getStringExtra("username");
        String usertype = getIntent().getStringExtra("usertype");

        // apply btn
        Button applyBtn = findViewById(R.id.apply_location);
        applyBtn.setOnClickListener(view -> {
            Intent moveToProfilePage = new Intent(getApplicationContext(), Profile.class);
            moveToProfilePage.putExtra("username", username);
            moveToProfilePage.putExtra("usertype", usertype);
            startActivity(moveToProfilePage);
        });
    }
}
