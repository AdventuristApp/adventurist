package com.adventurist.adventurist;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class showActivity extends AppCompatActivity {

    public static final String TAG = "ShowActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);


        ImageView Previous = findViewById(R.id.prev);
        Previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(showActivity.this, GalleryActivity.class);
                startActivity(intent1);
            }
        });


        Intent callingIntent = getIntent();
        String imageUriString = callingIntent.getStringExtra("imageUri");

        // Set the imageUri to the ImageView
        ImageView imageView = findViewById(R.id.Imagefromgallrey);

        try {
            // Convert the string back to Uri and set the image
            Uri imageUri = Uri.parse(imageUriString);
            imageView.setImageURI(imageUri);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Error loading image: " + e.getMessage());
        }
    }}