package com.adventurist.adventurist;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;



import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;




public class adventureMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adventure_main);
        setUpButtonsAndclickableImage();
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



    TextView profileName = findViewById(R.id.profileName);
    profileName.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent1 = new Intent(adventureMainActivity.this, ProfileActivity.class);
            startActivity(intent1);
        }
    });

}

}