package com.adventurist.adventurist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.amplifyframework.core.Amplify;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GalleryActivity extends AppCompatActivity {
    public static final String TAG = "Galleryactivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);






//
//        String emptyFilename= "emptyTestFileName";
//        File emptyFile = new File(getApplicationContext().getFilesDir(), emptyFilename);
//
//        try {
//            //createbufferWriter
//            BufferedWriter emptyFileBufferedWriter= new BufferedWriter(new FileWriter(emptyFile));
//
//            emptyFileBufferedWriter.append("Some text here from ghofran\nAnother lib from ghofran");
////to make sure every thing is save use "close"
//            emptyFileBufferedWriter.close();
//        }catch (IOException ioe){
//            Log.i(TAG, "could not write locally with filename: "+ emptyFilename);
//        }
//
//        String emptyFileS3Key = "someFileOnS3.txt";
//        Amplify.Storage.uploadFile(
//                emptyFileS3Key,
//                emptyFile,
//                success ->
//                {
//                    Log.i(TAG, "S3 upload succeeded and the Key is: " + success.getKey());
//                },
//                failure ->
//                {
//                    Log.i(TAG, "S3 upload failed! " + failure.getMessage());
//                }
//        );
    }
}