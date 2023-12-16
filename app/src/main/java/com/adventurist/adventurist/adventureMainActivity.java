package com.adventurist.adventurist;



import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adventurist.adventurist.Fragments.weatherapi;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.core.Amplify;
import com.google.android.material.navigation.NavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class adventureMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    EditText et;
    TextView tv;
    String url = "api.openweathermap.org/data/2.5/weather?q={city name}&appid={your api key}";
    String apikey = "a66f2f468eda4be91f3d4b46e63a34c4";
    LocationManager manager;
    LocationListener locationListener;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Menu menu;
    TextView textView;
    public static final String TAG_ADVENTURE = "profileActivity";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adventure_main);
        setUpButtonsAndclickableImage();
//        getweather(View);
        et = findViewById(R.id.et);
        tv = findViewById(R.id.tv);

        AuthUser authUser = Amplify.Auth.getCurrentUser();
        if (authUser != null) {
            String username = "";
            username = authUser.getUsername();
            Log.i(TAG_ADVENTURE, "UserName found" + username);
            String username2 = username; // ugly way for lambda hack
            Amplify.Auth.fetchUserAttributes(
                    success ->
                    {
                        Log.i(TAG_ADVENTURE, "Fetch user attributes succeeded for username: " + username2);
                        for (AuthUserAttribute userAttribute : success) {
                            if (userAttribute.getKey().getKeyString().equals("nickname")) {
                                String userName = userAttribute.getValue();
                                runOnUiThread(() ->
                                {
                                    ((TextView) findViewById(R.id.NICENAME)).setText(userName);
                                    ((TextView) findViewById(R.id.navNAME)).setText(userName);
                                });
                            }
                        }
                    },
                    failure ->
                    {
                        Log.i(TAG_ADVENTURE, "Fetch user attributes failed: " + failure.toString());
                    }
            );
        }



        /*---------------------Hooks------------------------*/
        drawerLayout = findViewById(R.id.drawer_layout2);
        navigationView = findViewById(R.id.nav_view2);
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
/*-----------------------------------------------------------------------*/

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



    @SuppressLint("NonConstantResourceId")
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        menu.findItem(R.id.nav_logout).setVisible(true);
        if (itemId == R.id.nav_home) {
            Intent intent = new Intent(adventureMainActivity.this, MainActivity.class);
            startActivity(intent);
        } else if (itemId == R.id.nav_Hotels) {
            Intent intent = new Intent(adventureMainActivity.this, ProfileActivity.class);
            startActivity(intent);
        }

        else if (itemId == R.id.nav_profile) {
            Intent intent = new Intent(adventureMainActivity.this, ProfileActivity.class);
            startActivity(intent);
        }


         else if (itemId == R.id.nav_logout) {
            menu.findItem(R.id.nav_logout).setVisible(true);
            menu.findItem(R.id.nav_profile).setVisible(true);
            menu.findItem(R.id.nav_Gallery).setVisible(true);

            Amplify.Auth.signOut(()->{
                        Log.i(TAG_ADVENTURE, "Log Out Succeeded :D");
                        runOnUiThread(() -> {
//                            ((TextView)findViewById(R.id.usernameTextView)).setText("");
                        });
                        Intent goToSignInIntent = new Intent(this, signInActivity.class);
                        startActivity(goToSignInIntent);
                    },
                    fail -> {
                        Log.i(TAG_ADVENTURE, "Log Out failed");
                        runOnUiThread(() -> {
                            Toast.makeText(this, "Log Out failed", Toast.LENGTH_LONG);
                        });
                    });
        } else if (itemId == R.id.nav_Gallery) {
            Intent intent = new Intent(adventureMainActivity.this, GalleryActivity.class);
            startActivity(intent);}

        else if (itemId == R.id.nav_share) {
            Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override

    protected void onResume() {
        super.onResume();




    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.botton_nav, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.homeFragment) {
            // Handle Home item click
            // You might want to navigate to the corresponding fragment or activity
            return true;
        } else if (itemId == R.id.profileFragment) {
            // Handle Profile item click
            return true;
        } else if (itemId == R.id.settingsFragment) {
            // Handle Logout item click
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }



    public void setUpButtonsAndclickableImage(){

    ImageView Trips = findViewById(R.id.imageTrips);
    Trips.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent1 = new Intent(adventureMainActivity.this, MainActivity.class);
            startActivity(intent1);
        }
    });

    ImageView Hotels = findViewById(R.id.imageHotels);
    Hotels.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent1 = new Intent(adventureMainActivity.this, MainActivity.class);
            startActivity(intent1);
        }
    });

    ImageView Places = findViewById(R.id.GalleryImageView);
    Places.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent1 = new Intent(adventureMainActivity.this, MainActivity.class);
            startActivity(intent1);
        }
    });

    ImageView profileImage = findViewById(R.id.profileImage);
    profileImage.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent1 = new Intent(adventureMainActivity.this, ProfileActivity.class);
            startActivity(intent1);
        }
    });



    TextView profileName = findViewById(R.id.NICENAME);
    profileName.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent1 = new Intent(adventureMainActivity.this, TEST.class);
            startActivity(intent1);
        }
    });

}



    public void getweather(View v) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        weatherapi myapi = retrofit.create(weatherapi.class);
        Call<weather> examplecall = myapi.getweather(et.getText().toString().trim(), apikey);
        examplecall.enqueue(new Callback<weather>() {
            @Override
            public void onResponse(Call<weather> call, Response<weather> response) {
                if (response.code() == 404) {
                    Toast.makeText(adventureMainActivity.this, "Please Enter a valid City", Toast.LENGTH_LONG).show();
                } else if (!(response.isSuccessful())) {
                    Toast.makeText(adventureMainActivity.this, response.code() + " ", Toast.LENGTH_LONG).show();
                    return;
                }
                weather mydata = response.body();
                mainWeatherClass main = mydata.getMain();
                Double temp = main.getTemp();
                Integer temperature = (int) (temp - 273.15);
                tv.setText(String.valueOf(temperature) + "C");
            }

            @Override
            public void onFailure(Call<weather> call, Throwable t) {
                Toast.makeText(adventureMainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }
}