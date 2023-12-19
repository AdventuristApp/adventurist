package com.adventurist.adventurist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adventurist.adventurist.R;
import com.adventurist.adventurist.adapter.placesAdapter;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Nearest;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class placesActivity extends AppCompatActivity {

    public static final String TAG = "placesActivity";

    private String targetPlaceId;
    List<Nearest> placesList = new ArrayList<>();
    placesAdapter placesAdapter;
    private AuthUser authuser;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        authuser=Amplify.Auth.getCurrentUser();

        // Get the intent that started this activity
        Intent intent = getIntent();

        // Check if the intent has extra data with key "locationName"
        if (intent.hasExtra("locationName")) {
            // Retrieve the value associated with the key
            String locationName = intent.getStringExtra("locationName");

            // Now you can use the locationName as needed, for example, set it to a TextView
//            TextView locationNameTextView = findViewById(R.id.locationNameTextView);
//            locationNameTextView.setText("Location Name: " + locationName);

            // Move the saveNamePlaces call inside the condition
            savePlacesName(locationName);
            setRecyclerViewList();
        }
        else {
        setRecyclerViewList();
        }
    }

    private void savePlacesName(String name) {
        //add record in dynamoDB===============
        Nearest newplace = Nearest.builder()
                .placeName(name)
                .userId(authuser.getUserId())
                .build();

        Amplify.API.mutate(
                ModelMutation.create(newplace),
                successResponse -> {
                    Log.i(TAG, " placesActivity.onCreate(): made a a place add successfully");
                    runOnUiThread(() -> Toast.makeText(placesActivity.this, "place Added Successfully", Toast.LENGTH_SHORT).show());
                    Log.i(TAG, "Before  - targetPlaceId: " + targetPlaceId);
                    targetPlaceId = newplace.getId();
                    Log.i(TAG, "After  - targetPlaceId: " + targetPlaceId);
                    ReadPlaces();


                },
                failureResponse -> {
                    Log.e(TAG, "placesActivity.onCreate(): failed with this response" + failureResponse);
                    Toast.makeText(this, "Failed to add places. Please try again.", Toast.LENGTH_SHORT).show();
                }
        );//=================
        Snackbar.make(findViewById(R.id.placesAcitity), "place saved!", Snackbar.LENGTH_SHORT).show();
    }


    private void setRecyclerViewList() {
        ReadPlaces();
    }

    private void ReadPlaces() {
        Amplify.API.query(
                ModelQuery.list(Nearest.class, Nearest.USER_ID.eq(authuser.getUserId())),
                success -> {
                    placesList.clear();
                    for (Nearest place : success.getData()) {
                        placesList.add(place);
                    }

                    // Set the RecyclerView adapter after updating placesList
                    runOnUiThread(() -> {
                        RecyclerView recyclerView = findViewById(R.id.placesRecycleview);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
                        recyclerView.setLayoutManager(layoutManager);
                        placesAdapter = new placesAdapter(placesList, this);
                        recyclerView.setAdapter(placesAdapter);
                        placesAdapter.notifyDataSetChanged();
                    });
                },
                fail -> {
                    Toast.makeText(this, "Can't read from AWS", Toast.LENGTH_SHORT).show();
                }
        );
    }


    protected void onResume() {
        super.onResume();
    }
//    @SuppressLint("NotifyDataSetChanged")
//    private void ReadPlaces() {
//        // Query the specific place by ID
//        Amplify.API.query(
//                ModelQuery.list(places.class)
//                success -> {
//                    // Check if the response contains the queried place
//                    Log.i(TAG, "Read places recycleview successfully  ");
//                    places queriedPlace = success.getData();
//                    if (queriedPlace != null) {
//                        // Clear the placesList and add the retrieved place
////                        placesList.clear();
//                        placesList.add(queriedPlace);
//Log.i(TAG,"correct add "+ queriedPlace);
//                        // Notify adapter of data change
//                        runOnUiThread(() -> placesAdapter.notifyDataSetChanged());
//                    } else {
//                        Log.e(TAG, "No place found with ID: " + targetPlaceId);
//                    }
//                },
//                error -> Log.e(TAG, "Error querying place by ID: " + targetPlaceId, error)
//        );
//    }


//        @SuppressLint("NotifyDataSetChanged")
//        private void ReadPlaces() {
//
//       Amplify.API.query(
//               ModelQuery.list(Nearest.class,Nearest.USER_ID.eq(authuser.getUserId())),
//               success -> {
//                   placesList.clear();
//                   Log.d(TAG, "ReadPlaces: " + success.getData());
//                   for (Nearest place: success.getData()
//                        ) {
//                       placesList.add(place);
//                   }
//               },
//               fail -> { Toast.makeText(this,"can't read from aws" , Toast.LENGTH_SHORT).show();}
//       );
//
//    }

}