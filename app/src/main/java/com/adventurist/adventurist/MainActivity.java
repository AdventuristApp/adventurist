package com.adventurist.adventurist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.adventurist.adventurist.MainActivity;
import com.adventurist.adventurist.adapter.ViewPagerAdapter;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.core.Amplify;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    EditText et;
    TextView tv;
    String url = "api.openweathermap.org/data/2.5/weather?q={city name}&appid={your api key}";
    String apikey = "a66f2f468eda4be91f3d4b46e63a34c4";
    LocationManager manager;
    LocationListener locationListener;
    public static final String TAG = "MainActivity";
    AuthUser authUser = null;


    ViewPager mSLideViewPager;
    LinearLayout mDotLayout;
    Button backbtn, nextbtn, skipbtn;

    TextView[] dots;
    ViewPagerAdapter viewPagerAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



                setUpSignInAndSignOutButtons();
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

        backbtn = findViewById(R.id.backbtn);
        nextbtn = findViewById(R.id.nextbtn);
//        skipbtn = findViewById(R.id.skipButton);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getitem(0) > 0){

                    mSLideViewPager.setCurrentItem(getitem(-1),true);

                }

            }
        });

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getitem(0) < 3)
                    mSLideViewPager.setCurrentItem(getitem(1),true);
                else {

                    Intent i = new Intent(MainActivity.this,firstActivity.class);
                    startActivity(i);
                    finish();

                }

            }
        });

//        skipbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                Intent i = new Intent(MainActivity.this,firstActivity.class);
//                startActivity(i);
////                finish();
//
//            }
//        });

        mSLideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        mDotLayout = (LinearLayout) findViewById(R.id.indicator_layout);

        viewPagerAdapter = new ViewPagerAdapter(this);

        mSLideViewPager.setAdapter(viewPagerAdapter);

        setUpindicator(0);
        mSLideViewPager.addOnPageChangeListener(viewListener);

    }

    public void setUpindicator(int position){

        dots = new TextView[4];
        mDotLayout.removeAllViews();

        for (int i = 0 ; i < dots.length ; i++){

            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.inactive,getApplicationContext().getTheme()));
            mDotLayout.addView(dots[i]);

        }

        dots[position].setTextColor(getResources().getColor(R.color.active,getApplicationContext().getTheme()));

    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            setUpindicator(position);

            if (position > 0){

                backbtn.setVisibility(View.VISIBLE);

            }else {

                backbtn.setVisibility(View.INVISIBLE);

            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private int getitem(int i){

        return mSLideViewPager.getCurrentItem() + i;
    }



    @Override
    protected void onResume() {
        super.onResume();
//        authUser = Amplify.Auth.getCurrentUser();
//
//        String email = "";
//        if (authUser == null) {
//
//            Button signInButton = (Button) findViewById(R.id.signInMainActivity);
//            signInButton.setVisibility(View.VISIBLE);
//            Button signOutButton = (Button) findViewById(R.id.logOutMainActivity);
//            signOutButton.setVisibility(View.INVISIBLE);
//        } else {
//            email = authUser.getUsername();
//            Log.i(TAG, "User Email is: " + email);
//
//            Button signInButton = (Button) findViewById(R.id.signInMainActivity);
//            signInButton.setVisibility(View.INVISIBLE);
//
//            Button signOutButton = (Button) findViewById(R.id.logOutMainActivity);
//            signOutButton.setVisibility(View.VISIBLE);
//
//            String visibleUserEmail = email;
//            Amplify.Auth.fetchUserAttributes(
//                    success -> {
//                        Log.i(TAG, "Fetching user email: " + visibleUserEmail);
//                        for (AuthUserAttribute userAttribute : success) {
//                            if (userAttribute.getKey().getKeyString().equals("email")) {
//                                String userEmail = userAttribute.getValue();
//                                runOnUiThread(() -> {
////                                    ((TextView) findViewById(R.id.usernameTextView)).setText(userEmail);
//                                });
//                            }
//                        }
//                    },
//                    fail -> {
//                        Log.i(TAG, "Fetching user email failed: " + fail.toString());
//                    }
//            );
//        }
    }


    private void setUpSignInAndSignOutButtons() {
        Button signInButton = (Button) findViewById(R.id.signInMain);
        signInButton.setOnClickListener(v -> {
            Intent goToSignInIntent = new Intent(this, signInActivity.class);
            startActivity(goToSignInIntent);
        });

        Button signupButton = (Button) findViewById(R.id.signUpMain);
        signupButton.setOnClickListener(v -> {

                    Intent goToSignUpIntent = new Intent(this, SignUpActivity.class);
                    startActivity(goToSignUpIntent);
                });
//            Amplify.Auth.signOut(() -> {
//                        Log.i(TAG, "Log Out Succeeded :D");
//                        runOnUiThread(() -> {
////                            ((TextView)findViewById(R.id.usernameTextView)).setText("");
//                        });
//                        Intent goToSignInIntent = new Intent(this, signInActivity.class);
//                        startActivity(goToSignInIntent);
//                    },
//                    fail -> {
//                        Log.i(TAG, "Log Out failed");
//                        runOnUiThread(() -> {
//                            Toast.makeText(this, "Log Out failed", Toast.LENGTH_LONG);
//                        });
//                    });
//        });
    }



}