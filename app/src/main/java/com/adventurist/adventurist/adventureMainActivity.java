package com.adventurist.adventurist;



import androidx.appcompat.app.AppCompatActivity;



import android.content.Intent;
import android.os.Bundle;

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

    ImageView Places = findViewById(R.id.ImagePlaces);
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