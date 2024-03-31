package com.example.smda2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InputBarActivity extends AppCompatActivity {
    private EditText editTextName, editTextLocation, editTextPhone, editTextDescription, editTextRatings;
    private Button buttonSave;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_bar);

        sharedPreferences = getSharedPreferences("RestaurantPrefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        
        editTextName = findViewById(R.id.editTextName);
        editTextLocation = findViewById(R.id.editTextLocation);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextRatings = findViewById(R.id.editTextRatings);
        buttonSave = findViewById(R.id.buttonSave);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               
                saveRestaurant();
                finish(); 
            }
        });
    }

    private void saveRestaurant() {
        String name = editTextName.getText().toString();
        String location = editTextLocation.getText().toString();
        String phone = editTextPhone.getText().toString();
        String description = editTextDescription.getText().toString();
        String ratings = editTextRatings.getText().toString();

        MainActivity.Restaurant newRestaurant = new MainActivity.Restaurant(name, location, phone, description, ratings);
        List<MainActivity.Restaurant> restaurantList = null;
        restaurantList.add(newRestaurant); // Add new restaurant to the list
        saveRestaurantListToPrefs(restaurantList);

        
    }

    private void saveRestaurantListToPrefs(List<MainActivity.Restaurant> restaurantList) {
        Gson gson = new Gson();
        String json = gson.toJson(restaurantList);
        editor.putString("restaurantList", json);
        editor.apply();
    }
    private List<MainActivity.Restaurant> getRestaurantListFromPrefs() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString("restaurantList", "");
        Type type = new TypeToken<List<MainActivity.Restaurant>>() {}.getType();
        return gson.fromJson(json, type);
    }

    private void saveRestaurantSetToPrefs(Set<String> restaurantSet) {
        editor.putStringSet("restaurants", restaurantSet);
        editor.apply();
    }
}
