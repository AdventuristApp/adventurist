package com.adventurist.adventurist.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adventurist.adventurist.FavHotelsActivity;
import com.adventurist.adventurist.FavRestaurantsActivity;
import com.adventurist.adventurist.R; // Change this to the correct package name
import com.amplifyframework.datastore.generated.model.Nearest;

import java.util.List;

public class placesAdapter extends RecyclerView.Adapter<placesAdapter.viewholderlist> {

    List<Nearest> placesList;
    private String locationName;
    private String targetPlaceId;
    private Context callingActivity;
    public placesAdapter(List<Nearest> placesList, Context callingActivity) {
        this.placesList = placesList;
        this.callingActivity = callingActivity;
//       this.targetPlaceId=targetPlaceId;


    }

    @NonNull
    @Override
    public viewholderlist onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout
        Log.d("placesAdapter", "onCreateViewHolder called");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_place, parent, false);
        return new viewholderlist(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull viewholderlist holder, int position) {
        // Retrieve the TextView from the item layout
        TextView fragmentTextView = holder.itemView.findViewById(R.id.placeNameTextView);

        TextView typePlace = holder.itemView.findViewById(R.id.typePlace);
        // Get the data for the current position
        Nearest place = placesList.get(position);
        // Set the text of the TextView with the place name
        fragmentTextView.setText(place.getPlaceName() + "\n" );
        typePlace.setText(place.getType());
        Log.d("placesActivity", place.getPlaceName() + "/n " + place.getType());
//===============================================================================
        // Corrected button references
        Button btnFavHotels = holder.itemView.findViewById(R.id.btnFavHotels);
        Button btnFavRestaurants = holder.itemView.findViewById(R.id.btnFavRestaurants);

        // Handle favorite hotels button click
        btnFavHotels.setOnClickListener(v -> {
            Intent intent = new Intent(callingActivity, FavHotelsActivity.class);
            intent.putExtra("placeId", placesList.get(position).getPlaceName());
            callingActivity.startActivity(intent);
        });

        // Handle favorite restaurants button click
        btnFavRestaurants.setOnClickListener(v -> {
            Intent intent = new Intent(callingActivity, FavRestaurantsActivity.class);
            intent.putExtra("placeId", placesList.get(position).getPlaceName());
            callingActivity.startActivity(intent);
        });
    }




    @Override
    public int getItemCount() {
        return placesList.size();
    }
    public static class viewholderlist extends RecyclerView.ViewHolder {
        public TextView placeNameTextView;
        public Button btnFavHotels;
        public  Button btnFavRestaurants;

        public viewholderlist(@NonNull View itemView) {
            super(itemView);
            // Initialize views from the item layout
            placeNameTextView = itemView.findViewById(R.id.placeNameTextView);
            btnFavHotels = itemView.findViewById(R.id.btnFavHotels);
            btnFavRestaurants = itemView.findViewById(R.id.btnFavRestaurants);
        }


    }

}
