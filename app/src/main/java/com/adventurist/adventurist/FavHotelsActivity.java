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

import com.adventurist.adventurist.adapter.HotelsAdapter;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.FavHotels;
import com.amplifyframework.datastore.generated.model.Hotel;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class FavHotelsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG = "FavHotelsActivity";

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Menu menu;
    TextView textView;

    private String targetHotelId;
    List<Hotel> hotelsList = new ArrayList<>();
    HotelsAdapter hotelsAdapter;
    private AuthUser authUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_hotels_activty);

        authUser = Amplify.Auth.getCurrentUser();

        // Get the intent that started this activity
        Intent intent = getIntent();

        if (intent.hasExtra("placeId")) {
            // Retrieve the value associated with the key
            String hotelName = intent.getStringExtra("placeId");

            // Now you can use the hotelName as needed
            // Move the saveHotelName call inside the condition
            saveHotelName(hotelName);
            setRecyclerViewList();
        } else {
            setRecyclerViewList();
        }




        /*---------------------Hooks------------------------*/
        drawerLayout = findViewById(R.id.FavHotels);
        navigationView = findViewById(R.id.nav_view6);
        textView = findViewById(R.id.textView10);
        toolbar = findViewById(R.id.toolbar6);
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

    private void saveHotelName(String name) {
        // Add record in DynamoDB
        Hotel newHotel = Hotel.builder()
                .hotel(name)
                .userId(authUser.getUserId())
                .build();

        Amplify.API.mutate(
                ModelMutation.create(newHotel),
                successResponse -> {
                    Log.i(TAG, "FavHotelsActivity.onCreate(): Hotel added successfully");
                    runOnUiThread(() -> Toast.makeText(FavHotelsActivity.this, "Hotel Added Successfully", Toast.LENGTH_SHORT).show());
                    Log.i(TAG, "Before  - targetHotelId: " + targetHotelId);
                    targetHotelId = newHotel.getId();
                    Log.i(TAG, "After  - targetHotelId: " + targetHotelId);
                    readHotels();
                },
                failureResponse -> {
                    Log.e(TAG, "FavHotelsActivity.onCreate(): Failed with this response" + failureResponse);
                    Toast.makeText(this, "Failed to add hotel. Please try again.", Toast.LENGTH_SHORT).show();
                }
        );
    }

    private void setRecyclerViewList() {


        // Set the RecyclerView layout manager
        RecyclerView recyclerView = findViewById(R.id.HotelsRecycleview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Set the RecyclerView adapter
        hotelsAdapter = new HotelsAdapter(hotelsList, this);
        recyclerView.setAdapter(hotelsAdapter);

        // Now, call readHotels to populate the data
        readHotels();
    }



    private void readHotels() {
        Log.i(TAG, "Entering readHotels");
        Amplify.API.query(
                ModelQuery.list(Hotel.class, Hotel.USER_ID.eq(authUser.getUserId())),
                success -> {
                    Log.i(TAG, "Success data: " + success.getData());
                    if (success.getData() != null) {
                        hotelsList.clear();

                        for (Hotel hotel : success.getData()) {
                            Log.i(TAG, "INSIDE FOR LOOP" + hotelsList);
                            hotelsList.add(hotel);
                            Log.i(TAG, "ADD SUCCESSFULLY" + hotelsList);
                        }
                        runOnUiThread(() -> {
//                            RecyclerView recyclerView = findViewById(R.id.recyclerViewHotels);
//                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
//                            recyclerView.setLayoutManager(layoutManager);
//                            hotelsAdapter = new HotelsAdapter(hotelsList, this);
//                            recyclerView.setAdapter(hotelsAdapter);
                            hotelsAdapter.notifyDataSetChanged();

                            Log.i(TAG, "RecyclerView adapter set successfully");
                        });
                    }
                },
                fail -> {
                    // Handle the failure case
                    Log.e(TAG, "Error querying hotels", fail);
                    Toast.makeText(FavHotelsActivity.this, "Failed to query hotels. Please try again.", Toast.LENGTH_SHORT).show();
                }
        );
    }





    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        menu.findItem(R.id.nav_logout).setVisible(true);
        if (itemId == R.id.nav_home) {
            Intent intent = new Intent(FavHotelsActivity.this, adventureMainActivity.class);
            startActivity(intent);
        } else if (itemId == R.id.nav_Map) {
            Intent intent = new Intent(FavHotelsActivity.this, googleMap.class);
            startActivity(intent);
        } else if (itemId == R.id.nav_places) {
            Intent intent = new Intent(FavHotelsActivity.this, placesActivity.class);
            startActivity(intent);
        }

        else if (itemId == R.id.nav_profile) {
            Intent intent = new Intent(FavHotelsActivity.this, ProfileActivity.class);
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
            Intent intent = new Intent(FavHotelsActivity.this, GalleryActivity.class);
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

                Toast.makeText(FavHotelsActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}
