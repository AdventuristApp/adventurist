package com.adventurist.adventurist;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
//import android.view.Vi
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.core.Amplify;

public class ProfileActivity extends AppCompatActivity {
    public static final String TAG_PROFILE = "profileActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        AuthUser authUser = Amplify.Auth.getCurrentUser();
        String username="";
        String username2 = username; // ugly way for lambda hack
        Amplify.Auth.fetchUserAttributes(
                success ->
                {
                    Log.i(TAG_PROFILE, "Fetch user attributes succeeded for username: "+username2);
                    for (AuthUserAttribute userAttribute: success){
                        if(userAttribute.getKey().getKeyString().equals("nickName")){
                            String userName = userAttribute.getValue();
                            runOnUiThread(() ->
                            {
                                ((TextView)findViewById(R.id.profileName)).setText(userName);
                            });
                        }
                    }
                },
                failure ->
                {
                    Log.i(TAG_PROFILE, "Fetch user attributes failed: "+failure.toString());
                }
        );


        setUpClickableImage();
    }

    public  void setUpClickableImage(){
        ImageView Gallery = findViewById(R.id.GalleryImageView);
        Gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ProfileActivity.this, GalleryActivity.class);
                startActivity(intent1);
            }
        });

    }
}