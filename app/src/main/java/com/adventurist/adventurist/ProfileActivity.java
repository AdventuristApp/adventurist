package com.adventurist.adventurist;



import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
//import android.view.Vi
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.core.Amplify;
import com.google.android.material.navigation.NavigationView;

public class ProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Menu menu;
    TextView textView;
    public static final String TAG_PROFILE = "profileActivity";


    //=========

    private Dialog dialog;
    private ImageView ShowDialog;
    //=======
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
//        setupAlertcostumWindow();

        AuthUser authUser = Amplify.Auth.getCurrentUser();
        String username="";
        String username2 = username; // ugly way for lambda hack
        Amplify.Auth.fetchUserAttributes(
                success ->
                {
                    Log.i(TAG_PROFILE, "Fetch user attributes succeeded for username: "+username2);
                    for (AuthUserAttribute userAttribute: success){
                        if(userAttribute.getKey().getKeyString().equals("nickname")){
                            String userName = userAttribute.getValue();
                            runOnUiThread(() ->
                            {
                                ((TextView)findViewById(R.id.profileNameTextView)).setText(userName);
                                ((TextView) findViewById(R.id.navNAME)).setText(userName);
                            });
                        }
                    }
                },
                failure ->
                {
                    Log.i(TAG_PROFILE, "Fetch user attributes failed: "+failure.toString());
                }
        );
        /*---------------------Hooks------------------------*/
        drawerLayout = findViewById(R.id.drawer_layout3);
        navigationView = findViewById(R.id.nav_view3);
        textView = findViewById(R.id.textView);
        toolbar = findViewById(R.id.toolbar2);
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



        setUpClickableImage();
    }

    public  void setUpClickableImage(){
        ImageView Gallery = findViewById(R.id.GalleryImageView2);
        Gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ProfileActivity.this, GalleryActivity.class);
                startActivity(intent1);
            }
        });

        ImageView FavHotels = findViewById(R.id.FavHotels);
        FavHotels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ProfileActivity.this, FavHotelsActivity.class);
                startActivity(intent1);
            }
        });

        ImageView mytripsimage = findViewById(R.id.mytripsimage);
        mytripsimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ProfileActivity.this, PlanActivity.class);
                startActivity(intent1);
            }
        });



        ImageView FavResimageView = findViewById(R.id.FavResimageView);
        FavResimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ProfileActivity.this, FavRestaurantsActivity.class);
                startActivity(intent1);
            }
        });

        TextView GalleryTEXT=findViewById(R.id.GallerytextView);
        GalleryTEXT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(ProfileActivity.this, GalleryActivity.class);
                startActivity(intent2);
            }
        });

    }   @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        menu.findItem(R.id.nav_logout).setVisible(true);
        if (itemId == R.id.nav_home) {
            Intent intent = new Intent(ProfileActivity.this, adventureMainActivity.class);
            startActivity(intent);

        } else if (itemId == R.id.nav_Map) {
            Intent intent = new Intent(ProfileActivity.this, googleMap.class);
            startActivity(intent);
        } else if (itemId == R.id.nav_places) {
            Intent intent = new Intent(ProfileActivity.this, placesActivity.class);
            startActivity(intent);
        }

        else if (itemId == R.id.nav_profile) {
            Intent intent = new Intent(ProfileActivity.this, ProfileActivity.class);
            startActivity(intent);
        }


        else if (itemId == R.id.nav_logout) {
            menu.findItem(R.id.nav_logout).setVisible(true);
            menu.findItem(R.id.nav_profile).setVisible(true);
            menu.findItem(R.id.nav_Gallery).setVisible(true);

            Amplify.Auth.signOut(()->{
                        Log.i(TAG_PROFILE, "Log Out Succeeded :D");
                        runOnUiThread(() -> {
//                            ((TextView)findViewById(R.id.usernameTextView)).setText("");
                        });
                        Intent goToSignInIntent = new Intent(this, signInActivity.class);
                        startActivity(goToSignInIntent);
                    },
                    fail -> {
                        Log.i(TAG_PROFILE, "Log Out failed");
                        runOnUiThread(() -> {
                            Toast.makeText(this, "Log Out failed", Toast.LENGTH_LONG);
                        });
                    });
        } else if (itemId == R.id.nav_Gallery) {
            Intent intent = new Intent(ProfileActivity.this, GalleryActivity.class);
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



//    public void setupAlertcostumWindow(){
//        ShowDialog = findViewById(R.id.setting);
//
//        //Create the Dialog here
//        dialog = new Dialog(this);
//        dialog.setContentView(R.layout.custom_dialog_layout);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.elements));
//        }
//        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        dialog.setCancelable(false); //Optional
//        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //Setting the animations to dialog
//
//        Button Okay = dialog.findViewById(R.id.btn_okay);
//        Button Cancel = dialog.findViewById(R.id.btn_cancel);
//
//        Okay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent goToSignInIntent = new Intent(ProfileActivity.this, signInActivity.class);
//                startActivity(goToSignInIntent);
//
//                Toast.makeText(ProfileActivity.this, "Okay", Toast.LENGTH_SHORT).show();
//                dialog.dismiss();
//            }
//        });
//
//        Cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Toast.makeText(ProfileActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
//                dialog.dismiss();
//            }
//        });
//
//
//        ShowDialog.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                dialog.show(); // Showing the dialog here
//            }
//        });



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

                Toast.makeText(ProfileActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}

