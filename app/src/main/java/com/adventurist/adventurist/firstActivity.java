package com.adventurist.adventurist;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.adventurist.adventurist.Fragments.weatherapi;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.core.Amplify;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class firstActivity extends AppCompatActivity {
//
//    EditText et;
//    TextView tv;
//    String url = "api.openweathermap.org/data/2.5/weather?q={city name}&appid={your api key}";
//    String apikey = "a66f2f468eda4be91f3d4b46e63a34c4";
//    LocationManager manager;
//    LocationListener locationListener;
    public static final String TAG = "MainActivity";
    AuthUser authUser = null;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        setUpSignInAndSignOutButtons();
        String emptyFilename = "emptyTestFileName";
        File emptyFile = new File(getApplicationContext().getFilesDir(), emptyFilename);
        try {
            BufferedWriter emptyFileBufferedWriter = new BufferedWriter(new FileWriter(emptyFile));
            emptyFileBufferedWriter.append("Some text here from ghofran\nAnother lib from ghofran");
            emptyFileBufferedWriter.close();
        } catch (IOException ioe) {
            Log.i(TAG, "could not write locally with filename: " + emptyFilename);
        }
        String emptyFileS3Key = "someFileOnS3.txt";
        Amplify.Storage.uploadFile(
                emptyFileS3Key,
                emptyFile,
                success ->
                {
                    Log.i(TAG, "S3 upload succeeded and the Key is: " + success.getKey());
                },
                failure ->
                {
                    Log.i(TAG, "S3 upload failed! " + failure.getMessage());
                }
        );


//        Button goHome = findViewById(R.id.goHome);
//        goHome.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent1 = new Intent(firstActivity.this, adventureMainActivity.class);
//                startActivity(intent1);
//            }
//        });

//
//        et = findViewById(R.id.et);
//        tv = findViewById(R.id.tv);

    }

//    public void getweather(View v) {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://api.openweathermap.org/data/2.5/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        weatherapi myapi = retrofit.create(weatherapi.class);
//        Call<weather> examplecall = myapi.getweather(et.getText().toString().trim(), apikey);
//        examplecall.enqueue(new Callback<weather>() {
//            @Override
//            public void onResponse(Call<weather> call, Response<weather> response) {
//                if (response.code() == 404) {
//                    Toast.makeText(firstActivity.this, "Please Enter a valid City", Toast.LENGTH_LONG).show();
//                } else if (!(response.isSuccessful())) {
//                    Toast.makeText(firstActivity.this, response.code() + " ", Toast.LENGTH_LONG).show();
//                    return;
//                }
//                weather mydata = response.body();
//                mainWeatherClass main = mydata.getMain();
//                Double temp = main.getTemp();
//                Integer temperature = (int) (temp - 273.15);
//                tv.setText(String.valueOf(temperature) + "C");
//            }
//
//            @Override
//            public void onFailure(Call<weather> call, Throwable t) {
//                Toast.makeText(firstActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        });


//    }

    @Override
    protected void onResume() {
        super.onResume();
        authUser = Amplify.Auth.getCurrentUser();

        String email = "";
        if (authUser == null) {

            Button signInButton = (Button) findViewById(R.id.signInMainActivity);
            signInButton.setVisibility(View.VISIBLE);
            Button signOutButton = (Button) findViewById(R.id.logOutMainActivity);
            signOutButton.setVisibility(View.INVISIBLE);
        } else {
            email = authUser.getUsername();
            Log.i(TAG, "User Email is: " + email);

            Button signInButton = (Button) findViewById(R.id.signInMainActivity);
            signInButton.setVisibility(View.INVISIBLE);

            Button signOutButton = (Button) findViewById(R.id.logOutMainActivity);
            signOutButton.setVisibility(View.VISIBLE);

            String visibleUserEmail = email;
            Amplify.Auth.fetchUserAttributes(
                    success -> {
                        Log.i(TAG, "Fetching user email: " + visibleUserEmail);
                        for (AuthUserAttribute userAttribute : success) {
                            if (userAttribute.getKey().getKeyString().equals("email")) {
                                String userEmail = userAttribute.getValue();
                                runOnUiThread(() -> {
//                                    ((TextView) findViewById(R.id.usernameTextView)).setText(userEmail);
                                });
                            }
                        }
                    },
                    fail -> {
                        Log.i(TAG, "Fetching user email failed: " + fail.toString());
                    }
            );
        }
    }


    private void setUpSignInAndSignOutButtons() {
        Button signInButton = (Button) findViewById(R.id.signInMainActivity);
        signInButton.setOnClickListener(v -> {
            Intent goToSignInIntent = new Intent(this, signInActivity.class);
            startActivity(goToSignInIntent);
        });

        Button signOutButton = (Button) findViewById(R.id.logOutMainActivity);
        signOutButton.setOnClickListener(v -> {
            Amplify.Auth.signOut(() -> {
                        Log.i(TAG, "Log Out Succeeded :D");
                        runOnUiThread(() -> {
//                            ((TextView)findViewById(R.id.usernameTextView)).setText("");
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
        });
    }

    public void goToPlaneActivity(View view) {
        Button planeButton = (Button) findViewById(R.id.planeButton);
        planeButton.setOnClickListener(v -> {
            Intent goToPlaneIntent = new Intent(this, PlanActivity.class);
            startActivity(goToPlaneIntent);
        });
    }
}
