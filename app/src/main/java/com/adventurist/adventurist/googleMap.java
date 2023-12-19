package com.adventurist.adventurist;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.adventurist.adventurist.databinding.ActivityGoogleMapBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class googleMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityGoogleMapBinding binding;
    private UiSettings mapSettings;
    private SearchView mapSearchView;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();
        binding = ActivityGoogleMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mapSearchView = findViewById(R.id.googleSearchView);
        Button searchHospitalButton = findViewById(R.id.searchHospitalButton);
        Button searchResButton = findViewById(R.id.searchResturantButton);
        Button searchHotelsButton = findViewById(R.id.searchHotelButton);
        searchHospitalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findNearest("Hospital");
            }
        });
        searchResButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findNearest("Restaurant");
            }
        });
        searchHotelsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findNearest("Hotel");
            }
        });
        mapSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = mapSearchView.getQuery().toString();
                List<Address> addressList = null;
                if (location != null) {
                    Geocoder geocoder = new Geocoder(googleMap.this);
                    try {
                        addressList = geocoder.getFromLocationName(location,1);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(),address.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(latLng).title("My location"));
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLastLocation();
            }else {
                Toast.makeText(this,"Location permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mapSettings = googleMap.getUiSettings();
        LatLng userLocation = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        mMap.addMarker(new MarkerOptions().position(userLocation).title("My location"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation,15.0f));
        mapSettings.setMyLocationButtonEnabled(true);
        mapSettings.setAllGesturesEnabled(true);
        mapSettings.setZoomControlsEnabled(true);
    }
    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null){
                    currentLocation = location;

                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.map);
                    mapFragment.getMapAsync(googleMap.this);
                }
            }
        });

    }
    private void findNearest(String type) {
        if (currentLocation == null) {
            return;
        }

        String apiKey = "AIzaSyBNR7_th2remU0Eb37Se16GME5ts3TuzcI";
        int radius = 5000;

        String urlString = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" +
                "location=" + currentLocation.getLatitude() + "," + currentLocation.getLongitude() +
                "&radius=" + radius +
                "&type=" + type +
                "&key=" + apiKey;
        Log.d("STRINGURL", "findNearest: " + urlString);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        reader.close();
                        Log.i("Hospital", "run: " + response.toString());
                        parseNearbyResponse(response.toString(),type);
                    }
                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseNearbyResponse(String jsonResponse,String type) {
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray results = jsonObject.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject place = results.getJSONObject(i);
                JSONObject location = place.getJSONObject("geometry").getJSONObject("location");
                String name = place.getString("name");
                double lat = location.getDouble("lat");
                double lng = location.getDouble("lng");
                LatLng hospitalLatLng = new LatLng(lat, lng);
                Log.d("Hospital", "Hospital Name: " + name + " LatLng: " + hospitalLatLng.toString());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mMap != null) {
                            Marker marker = mMap.addMarker(new MarkerOptions().position(hospitalLatLng).title(name));
                            marker.setTag(name);
                            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                @Override
                                public boolean onMarkerClick(Marker marker) {
                                    String name = (String) marker.getTag();
                                    if (name != null) {
                                        showAddToListDialog(name,type);
                                        return true;
                                    }
                                    return false;
                                }
                            });
                        } else {
                            Log.e("Hospital", "mMap is null");
                        }
                    }
                });
            }

            // Set a single marker click listener for the entire map


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
//    private void showAddToListDialog(String name) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        Intent locationName = new Intent(this, PlanActivity.class);
//        builder.setTitle(name)
//                .setMessage("Do you want to add this location to your list?")
//
//                .setPositiveButton("Add to Hotels", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        locationName.putExtra("locationName",name);
//                        startActivity(locationName);
//                    }
//                })
//
//                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//        AlertDialog dialog = builder.create();
//        dialog.show();
//    }


    @SuppressLint("SetTextI18n")
    private void showAddToListDialog(String name,String type) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog_layout);


        GifImageView gifImageView = dialog.findViewById(R.id.gifImageView);
        TextView textView = dialog.findViewById(R.id.textView);
        TextView textView2 = dialog.findViewById(R.id.textView2);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);
        Button btnOkay = dialog.findViewById(R.id.btn_okay);

        // Set values or perform any additional customization
        textView.setText("Add Favorite " + name);
        textView2.setText("Add this place to favorite list");


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle cancel button click
                dialog.dismiss();
            }
        });

        btnOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle okay button click
                Intent locationName = new Intent(googleMap.this, placesActivity.class);
                locationName.putExtra("locationName", name);
                locationName.putExtra("locationType", type);
                startActivity(locationName);

                // Dismiss the dialog
                dialog.dismiss();
            }
        });

        // Show the custom dialog
        dialog.show();
    }




}