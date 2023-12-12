package com.adventurist.adventurist;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.widget.ImageView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Images;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {
    public static final String TAG = "Galleryactivity";
    ActivityResultLauncher<Intent> activityResultLauncher;
    private String s3ImageKey = "";
    private RecyclerView imageRecyclerView;
    private ImageAdapter imageAdapter;
    private ArrayList<Uri> imageUris;
    private List<Images> ImageList = new ArrayList<>();

    public static final String Main_ID_TAG = "task ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        imageRecyclerView = findViewById(R.id.imageRecyclerView);
        imageUris = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        imageRecyclerView.setLayoutManager(layoutManager);
        imageAdapter = new ImageAdapter(imageUris, this);
        imageRecyclerView.setAdapter(imageAdapter);

        imageAdapter.OnItemClickListner(new ImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                imageUris.remove(position);
                imageAdapter.notifyItemRemoved(position);

            }
        });


        activityResultLauncher = getImagePickingActivityResultLauncher();

        setUpAddImageButton();





    }


    @SuppressLint("NotifyDataSetChanged")
    @Override

    protected void onResume() {
        super.onResume();

        Amplify.API.query(
                ModelQuery.list(Images.class),
                success -> {
                    Log.i(TAG, "Read images successfully");
                    ImageList.clear();
                    if (success.getData() != null) {
                        for (Images databaseProduct : success.getData()) {
                            ImageList.add(databaseProduct);
                            Log.d(TAG, "Image ID: " + databaseProduct.getId());
                        }
                        runOnUiThread(() -> {
                            imageAdapter.setImages(ImageList);
                            imageAdapter.notifyDataSetChanged();
                        });
                    }
                },
                failure -> Log.e(TAG, "Failed to read images", failure)
        );
    }







    private void setUpAddImageButton()
    {
        ImageView addImageButton =  findViewById(R.id.addImageClickable);
        addImageButton.setOnClickListener(b ->
        {
            launchImageSelectionIntent();
        });

    }

    private void launchImageSelectionIntent()
    {


        // Part 1: Launch activity to pick file
        Intent imageFilePickingIntent = new Intent(Intent.ACTION_GET_CONTENT);
        imageFilePickingIntent.setType("image/*");  // Only allow images
        imageFilePickingIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true); // Allow multiple image selection
        imageFilePickingIntent.setAction(Intent.ACTION_GET_CONTENT);

        // Part 2: Create an image picking activity result launcher
        activityResultLauncher.launch(imageFilePickingIntent);
    }







    private ActivityResultLauncher<Intent> getImagePickingActivityResultLauncher() {
        ActivityResultLauncher<Intent> imagePickingActivityResultLauncher =
                //to take the result use >>  registerForActivityResult
                //deal with contract >>to strat the activity
                registerForActivityResult(
                        //setup contract to setup activity
                        new ActivityResultContracts.StartActivityForResult(),
                        new ActivityResultCallback<ActivityResult>()
                        {


                            @Override
                            public void onActivityResult(ActivityResult result) {
                                if (result.getResultCode() == Activity.RESULT_OK) {
                                    if (result.getData() != null) {
                                        ClipData clipData = result.getData().getClipData();

                                        if (clipData != null) {
                                            for (int i = 0; i < clipData.getItemCount(); i++) {
                                                Uri pickedImageFileUri = clipData.getItemAt(i).getUri();
                                                handleSelectedImage(pickedImageFileUri);
                                            }
                                        } else {
                                            Uri pickedImageFileUri = result.getData().getData();
                                            handleSelectedImage(pickedImageFileUri);
                                        }
                                    }
                                } else {
                                    Log.e(TAG, "Activity result error in ActivityResultLauncher.onActivityResult");
                                }
                            }

                            private void handleSelectedImage(Uri pickedImageFileUri) {
                                try {
                                    InputStream pickedImageInputStream = getContentResolver().openInputStream(pickedImageFileUri);
                                    String pickedImageFilename = getFileNameFromUri(pickedImageFileUri);
                                    Log.i(TAG, "Succeeded in getting input stream from file on the phone! Filename is: " + pickedImageFilename);
                                    uploadInputStreamToS3(pickedImageInputStream, pickedImageFilename, pickedImageFileUri);
                                } catch (FileNotFoundException fnfe) {
                                    Log.e(TAG, "Could not get file from the file picker! " + fnfe.getMessage(), fnfe);
                                }
                            }

                        }
                );

        return imagePickingActivityResultLauncher;

    }

    private Bitmap resizeBitmap(Bitmap originalBitmap, int targetWidth, int targetHeight) {
        return Bitmap.createScaledBitmap(originalBitmap, targetWidth, targetHeight, false);
    }

    private void uploadInputStreamToS3(InputStream pickedImageInputStream, String pickedImageFilename,Uri pickedImageFileUri)
    {




        try {
            // Decode the InputStream into a Bitmap
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap originalBitmap = BitmapFactory.decodeStream(pickedImageInputStream, null, options);

            // Resize the Bitmap to your desired width and height
            int targetWidth = 800; // Set your desired width
            int targetHeight = 600; // Set your desired height
            Bitmap resizedBitmap = resizeBitmap(originalBitmap, targetWidth, targetHeight);

            // Convert the resized Bitmap back to InputStream
            InputStream resizedInputStream = getInputStreamFromBitmap(resizedBitmap);

            // Upload the resized image to S3
            Amplify.Storage.uploadInputStream(
                    pickedImageFilename,
                    resizedInputStream,
                    success -> {
                        Log.i(TAG, "Succeeded in getting file uploaded to S3! Key is: " + success.getKey());
                        saveS3KeyToDynamoDB(success.getKey());
                        addImageUri(pickedImageFileUri);
                    },
                    failure -> {
                        Log.e(TAG, "Failure in uploading file to S3 with filename: " + pickedImageFilename + " with error: " + failure.getMessage());
                    }
            );
        } catch (Exception e) {
            Log.e(TAG, "Error resizing and uploading image: " + e.getMessage(), e);
        }
    }

    private void saveS3KeyToDynamoDB(String s3Key) {
        // Create a model instance representing your data
        Images imageModel = Images.builder()
                .taskImageS3Key(s3Key)
                // Set other fields if needed
                .build();

        // Perform the create mutation to add a new item
        Amplify.API.mutate(
                ModelMutation.create(imageModel),
                response -> {
                    Images createdImage = response.getData();
                    if (createdImage != null) {
                        String createdImageId = createdImage.getId();
                        Log.i(TAG, "Image created successfully with ID: " + createdImageId);
                    } else {
                        Log.e(TAG, "Error creating Image: Response data is null");
                    }
                },
                error -> Log.e(TAG, "Error creating Image: " + error)
        );
    }


//        // Save to DynamoDB
//        Amplify.API.mutate(
//                imageModel,
//                ModelQuery.get(Images.class, Main_ID_TAG),
//                response -> Log.i(TAG, "Saved to DynamoDB: " + response.getData().getId()),
//                error -> Log.e(TAG, "Error saving to DynamoDB", error)
//        );



    private void addImageUri(Uri imageUri) {
        imageUris.add(imageUri);
        imageAdapter.notifyDataSetChanged();}

    private InputStream getInputStreamFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return new ByteArrayInputStream(outputStream.toByteArray());
    }



    @SuppressLint("Range")
    public String getFileNameFromUri(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }
//     private void setUpDeleteImageButton()
//    {
//        Button deleteImageButton = (Button)findViewById(R.id.deleteImage);
//        String s3ImageKey = this.s3ImageKey;
//        deleteImageButton.setOnClickListener(v ->
//        {
//            Amplify.Storage.remove(
//                    s3ImageKey,
//                    success ->
//                    {
//                        Log.i(TAG, "Succeeded in deleting file on S3! Key is: " + success.getKey());
//
//                    },
//                    failure ->
//                    {
//                        Log.e(TAG, "Failure in deleting file on S3 with key: " + s3ImageKey + " with error: " + failure.getMessage());
//                    }
//            );
////            ImageView productImageView = findViewById(R.id.addtaskimageView2);
////            productImageView.setImageResource(android.R.color.transparent);
//
////            saveTask("");
////            switchFromDeleteButtonToAddButton(deleteImageButton);
//        });
//    }
//    // Assuming you have a method to get the S3 key from the Uri
//
//
//
//
//    private void updateImageButtons() {
//        ImageView addImage = findViewById(R.id.addImageClickable);
//        Button deleteImageButton = findViewById(R.id.deleteImage);
//        runOnUiThread(() -> {
//            if (s3ImageKey == null || s3ImageKey.isEmpty()) {
//                deleteImageButton.setVisibility(View.INVISIBLE);
////                addImage.setVisibility(View.VISIBLE);
//            } else {
//                deleteImageButton.setVisibility(View.VISIBLE);
////                addImage.setVisibility(View.INVISIBLE);
//            }
//        });
//    }

//    private void switchFromDeleteButtonToAddButton(Button deleteImageButton) {
//        ImageView addImageButton = findViewById(R.id.addImageClickable);
//        deleteImageButton.setVisibility(View.INVISIBLE);
//        addImageButton.setVisibility(View.VISIBLE);
//    }
}