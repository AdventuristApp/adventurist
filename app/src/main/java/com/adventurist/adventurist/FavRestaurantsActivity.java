package com.adventurist.adventurist;

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

import com.adventurist.adventurist.adapter.RestaurantsAdapter;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.core.Amplify;

import com.amplifyframework.datastore.generated.model.FavResturants;
import com.amplifyframework.datastore.generated.model.Resturant;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class FavRestaurantsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG = "FavRestaurantsActivity";

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Menu menu;
    TextView textView;

    private String targetRestaurantId;
    List<Resturant> restaurantsList = new ArrayList<>();
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


        /*---------------------Hooks------------------------*/
        drawerLayout = findViewById(R.id.favresturant);
        navigationView = findViewById(R.id.nav_view6);
        textView = findViewById(R.id.textView10);
        toolbar = findViewById(R.id.toolbar7);
        navigationView.bringToFront();

        ActionBarDrawerToggle toggle = new
                ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);


        menu = navigationView.getMenu();
        menu.findItem(R.id.nav_logout).setVisible(true);
        menu.findItem(R.id.nav_profile).setVisible(true);

    }

    private void saveRestaurantName(String name) {
        // Add record in DynamoDB
        Resturant newRestaurant = Resturant.builder()
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
//                    readRestaurants();
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
                ModelQuery.list(Resturant.class,Resturant.USER_ID.eq(authUser.getUserId())),
                success -> {
                    Log.i(TAG, "Success data: " + success.getData());
                    if (success.getData() != null) {
                        restaurantsList.clear();

                        for (Resturant restaurant : success.getData()) {
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



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        menu.findItem(R.id.nav_logout).setVisible(true);
        if (itemId == R.id.nav_home) {
            Intent intent = new Intent(FavRestaurantsActivity.this, adventureMainActivity.class);
            startActivity(intent);
        } else if (itemId == R.id.nav_Map) {
            Intent intent = new Intent(FavRestaurantsActivity.this, googleMap.class);
            startActivity(intent);
        } else if (itemId == R.id.nav_places) {
            Intent intent = new Intent(FavRestaurantsActivity.this, placesActivity.class);
            startActivity(intent);
        }

        else if (itemId == R.id.nav_profile) {
            Intent intent = new Intent(FavRestaurantsActivity.this, ProfileActivity.class);
            startActivity(intent);
        }


        else if (itemId == R.id.nav_logout) {
            menu.findItem(R.id.nav_logout).setVisible(true);
            menu.findItem(R.id.nav_profile).setVisible(true);
            menu.findItem(R.id.nav_Gallery).setVisible(true);

            Amplify.Auth.signOut(()->{
                        Log.i("Gallery_TAG", "Log Out Succeeded :D");
                        runOnUiThread(() -> {
//                            ((TextView)findViewById(R.id.usernameTextView)).setText("");
                        });
                        Intent goToSignInIntent = new Intent(this, signInActivity.class);
                        startActivity(goToSignInIntent);
                    },
                    fail -> {
                        Log.i("Gallery_TAG", "Log Out failed");
                        runOnUiThread(() -> {
                            Toast.makeText(this, "Log Out failed", Toast.LENGTH_LONG);
                        });
                    });
        } else if (itemId == R.id.nav_Gallery) {
            Intent intent = new Intent(FavRestaurantsActivity.this, GalleryActivity.class);
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

                Toast.makeText(FavRestaurantsActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}
