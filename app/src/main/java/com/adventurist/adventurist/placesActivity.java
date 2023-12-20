package com.adventurist.adventurist;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adventurist.adventurist.adapter.placesAdapter;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Nearest;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class placesActivity extends AppCompatActivity {

    public static final String TAG = "placesActivity";

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Menu menu;
    TextView textView;
    private String targetPlaceId;
    List<Nearest> placesList = new ArrayList<>();
    placesAdapter placesAdapter;
    private AuthUser authuser;
    @SuppressLint({"SetTextI18n", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        authuser=Amplify.Auth.getCurrentUser();


        /*---------------------Hooks------------------------*/
        drawerLayout = findViewById(R.id.placesAcitity);
        navigationView = findViewById(R.id.nav_view5);
        textView = findViewById(R.id.textView);
        toolbar = findViewById(R.id.toolbar2);
        navigationView.bringToFront();

        ActionBarDrawerToggle toggle = new
                ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
        navigationView.setCheckedItem(R.id.nav_home);


        menu = navigationView.getMenu();
        menu.findItem(R.id.nav_logout).setVisible(true);
        menu.findItem(R.id.nav_profile).setVisible(true);

        //=============================


        // Get the intent that started this activity
        Intent intent = getIntent();

        // Check if the intent has extra data with key "locationName"
        if (intent.hasExtra("locationName")) {
            // Retrieve the value associated with the key
            String locationName = intent.getStringExtra("locationName");
            String locationType = intent.getStringExtra("locationType");

            // Now you can use the locationName as needed, for example, set it to a TextView
//            TextView locationNameTextView = findViewById(R.id.locationNameTextView);
//            locationNameTextView.setText("Location Name: " + locationName);

            // Move the saveNamePlaces call inside the condition
            savePlacesName(locationName,locationType);
            setRecyclerViewList();
        }
        else {
        setRecyclerViewList();
        }
    }

    private void savePlacesName(String name, String locationType) {
        //add record in dynamoDB===============
        Nearest newplace = Nearest.builder()
                .placeName(name)
                .userId(authuser.getUserId())
                .type(locationType)
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


    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {super.onBackPressed();
        }
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        menu.findItem(R.id.nav_logout).setVisible(true);
        if (itemId == R.id.nav_home) {
            Intent intent = new Intent(placesActivity.this, adventureMainActivity.class);
            startActivity(intent);
        } else if (itemId == R.id.nav_Map) {
            Intent intent = new Intent(placesActivity.this, googleMap.class);
            startActivity(intent);
        } else if (itemId == R.id.nav_places) {
            Intent intent = new Intent(placesActivity.this, placesActivity.class);
            startActivity(intent);
        }

        else if (itemId == R.id.nav_profile) {
            Intent intent = new Intent(placesActivity.this, ProfileActivity.class);
            startActivity(intent);
        }


        else if (itemId == R.id.nav_logout) {
            menu.findItem(R.id.nav_logout).setVisible(true);
            menu.findItem(R.id.nav_profile).setVisible(true);
            menu.findItem(R.id.nav_Gallery).setVisible(true);

            Amplify.Auth.signOut(()->{
                        Log.i(TAG, "Log Out Succeeded :D");
                        runOnUiThread(() -> {
//                          ((TextView)findViewById(R.id.usernameTextView)).setText("");
                        });
                        Intent goToSignInIntent = new Intent(this, signInActivity.class);
                        startActivity(goToSignInIntent);
                    },
                    fail -> {
                        Log.i(TAG, "Log Out failed");
                        runOnUiThread(() -> {
                            Toast.makeText(this, "Log Out failed", Toast.LENGTH_LONG);
                        });
                    });
        } else if (itemId == R.id.nav_Gallery) {
            Intent intent = new Intent(placesActivity.this, GalleryActivity.class);
            startActivity(intent);}

        else if (itemId == R.id.nav_share) {
            Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
        }
        else if (itemId == R.id.nav_rate) {
            setupRatingBox();
            return true;

        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }



    @SuppressLint("UseCompatLoadingForDrawables")
    public void setupRatingBox() {
        // Create the Dialog here
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.rate);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.elements));
        }

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        // Initialize Views
        Button submitButton = dialog.findViewById(R.id.btn);
        RatingBar ratingBar = dialog.findViewById(R.id.rb);
        Button Cancel = dialog.findViewById(R.id.btn_cancel);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float rating = ratingBar.getRating();
                String data = String.valueOf(rating);
                Toast.makeText(getApplicationContext(), data + " star", Toast.LENGTH_SHORT).show();
                dialog.dismiss(); // Dismiss the dialog after submitting the rating
            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(placesActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}