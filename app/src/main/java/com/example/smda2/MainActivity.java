
package com.example.smda2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private Button buttonAdd;
    private RecyclerView recyclerView;
    private RestaurantAdapter adapter;
    private List<Restaurant> restaurantList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        buttonAdd = findViewById(R.id.buttonAdd);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load restaurants from SharedPreferences
        restaurantList = getRestaurantListFromPrefs();

        // Set up RecyclerView
        adapter = new RestaurantAdapter(restaurantList);
        recyclerView.setAdapter(adapter);

        // Set up button click listener to navigate to AddRestaurantActivity
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, InputBarActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh RecyclerView when returning to MainActivity
        restaurantList.clear(); // Clear existing list
        restaurantList.addAll(getRestaurantListFromPrefs()); // Load updated list
        adapter.notifyDataSetChanged(); // Notify adapter of data change
    }

    private List<Restaurant> getRestaurantListFromPrefs() {
        // Retrieve restaurant list from SharedPreferences
        Gson gson = new Gson();
        String json = getSharedPreferences("RestaurantPrefs", Context.MODE_PRIVATE).getString("restaurantList", "");
        Type type = new TypeToken<List<Restaurant>>() {}.getType();
        return gson.fromJson(json, type);
    }

    static class Restaurant {
        private String name;
        private String location;
        private String phone;
        private String description;
        private String ratings;

        public Restaurant(String name, String location, String phone, String description, String ratings) {
            this.name = name;
            this.location = location;
            this.phone = phone;
            this.description = description;
            this.ratings = ratings;
        }

        public String getName() {
            return name;
        }

        public String getLocation() {
            return location;
        }

        public String getPhone() {
            return phone;
        }

        public String getDescription() {
            return description;
        }

        public String getRatings() {
            return ratings;
        }
    }
    static class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {
        private final List<Restaurant> restaurantList;

        public RestaurantAdapter(List<Restaurant> restaurantList) {
            this.restaurantList = restaurantList;
        }

        @NonNull
        @Override
        public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_item, parent, false);
            return new RestaurantViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
            Restaurant restaurant = restaurantList.get(position);
            holder.textViewName.setText(restaurant.getName());
            holder.textViewLocation.setText(restaurant.getLocation());
            holder.textViewPhone.setText(restaurant.getPhone());
            holder.textViewDescription.setText(restaurant.getDescription());
            holder.textViewRatings.setText(restaurant.getRatings());
        }

        @Override
        public int getItemCount() {
            return restaurantList.size();
        }

        static class RestaurantViewHolder extends RecyclerView.ViewHolder {
            TextView textViewName, textViewLocation, textViewPhone, textViewDescription, textViewRatings;

            public RestaurantViewHolder(@NonNull View itemView) {
                super(itemView);
                textViewName = itemView.findViewById(R.id.textViewName);
                textViewLocation = itemView.findViewById(R.id.textViewLocation);
                textViewPhone = itemView.findViewById(R.id.textViewPhone);
                textViewDescription = itemView.findViewById(R.id.textViewDescription);
                textViewRatings = itemView.findViewById(R.id.textViewRatings);
            }
        }
    }

    }

