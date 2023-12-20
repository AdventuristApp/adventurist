package com.adventurist.adventurist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adventurist.adventurist.adapter.RestaurantsAdapter;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.core.Amplify;

import com.amplifyframework.datastore.generated.model.FavResturants;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class FavRestaurantsActivity extends AppCompatActivity {

    public static final String TAG = "FavRestaurantsActivity";

    private String targetRestaurantId;
    List<FavResturants> restaurantsList = new ArrayList<>();
    RestaurantsAdapter restaurantsAdapter;
    private AuthUser authUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resturants);

        authUser = Amplify.Auth.getCurrentUser();

        //=============================


        // Get the intent that started this activity
        Intent intent = getIntent();


        if (intent.hasExtra("placeId")) {
            // Retrieve the value associated with the key
            String restaurantName = intent.getStringExtra("placeId");

            // Now you can use the restaurantName as needed
            // Move the saveNameRestaurants call inside the condition
            saveRestaurantName(restaurantName);
            setRecyclerViewList();
        } else {
            setRecyclerViewList();
        }
    }

    private void saveRestaurantName(String name) {
        // Add record in DynamoDB
        FavResturants newRestaurant = FavResturants.builder()
                .resturant(name)
                .userId(authUser.getUserId())
                .build();

        Amplify.API.mutate(
                ModelMutation.create(newRestaurant),
                successResponse -> {
                    Log.i(TAG, "FavRestaurantsActivity.onCreate(): Restaurant added successfully");
                    runOnUiThread(() -> Toast.makeText(FavRestaurantsActivity.this, "Restaurant Added Successfully", Toast.LENGTH_SHORT).show());
                    Log.i(TAG, "Before  - targetRestaurantId: " + targetRestaurantId);
                    targetRestaurantId = newRestaurant.getId();
                    Log.i(TAG, "After  - targetRestaurantId: " + targetRestaurantId);
                    readRestaurants();
                },
                failureResponse -> {
                    Log.e(TAG, "FavRestaurantsActivity.onCreate(): Failed with this response" + failureResponse);
                    Toast.makeText(this, "Failed to add restaurant. Please try again.", Toast.LENGTH_SHORT).show();
                }
        );
//        Snackbar.make(findViewById(R.id.), "Restaurant saved!", Snackbar.LENGTH_SHORT).show();
    }

    private void setRecyclerViewList() {
        readRestaurants();
    }

    private void readRestaurants() {
        Log.i(TAG, "Entering readRestaurants");
        Amplify.API.query(
                ModelQuery.list(FavResturants.class, FavResturants.USER_ID.eq(authUser.getUserId())),


                success -> {
                    Log.i(TAG,"LIST FAV RESTURANT");
                    if (success.getData() != null) {
                        restaurantsList.clear();
                        for (FavResturants restaurant : success.getData()) {
                            Log.i(TAG,"INSIDE FOR LOOP"+restaurantsList);
                            restaurantsList.add(restaurant);
                            Log.i(TAG,"ADD SUSSESFULLY"+restaurantsList);
                        }

                        // Set the RecyclerView adapter after updating restaurantsList
                        runOnUiThread(() -> {
                            RecyclerView recyclerView = findViewById(R.id.recyclerViewRest);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
                            recyclerView.setLayoutManager(layoutManager);
                            restaurantsAdapter = new RestaurantsAdapter(restaurantsList, this);
                            recyclerView.setAdapter(restaurantsAdapter);
                            restaurantsAdapter.notifyDataSetChanged();

                            Log.i(TAG, "RecyclerView adapter set successfully");
                        });
                    }
                },
                fail -> {
                    // Handle the failure case
                    Log.e(TAG, "Error querying restaurants", fail);
                  Toast.makeText(FavRestaurantsActivity.this, "Failed to query restaurants. Please try again.", Toast.LENGTH_SHORT).show();
                }
        );
    }

}
