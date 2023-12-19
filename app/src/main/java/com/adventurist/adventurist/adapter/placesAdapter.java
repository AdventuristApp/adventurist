package com.adventurist.adventurist.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        // Get the data for the current position
        Nearest place = placesList.get(position);
        // Set the text of the TextView with the place name
//        String PlaceName = place.getPlace();
        fragmentTextView.setText(place.getPlaceName());
        Log.d("placesActivity" , place.getPlaceName());
    }


    @Override
    public int getItemCount() {
        return placesList.size();
    }
    public static class viewholderlist extends RecyclerView.ViewHolder {
        public TextView placeNameTextView;

        public viewholderlist(@NonNull View itemView) {
            super(itemView);
            // Initialize views from the item layout
            placeNameTextView = itemView.findViewById(R.id.placeNameTextView);
        }


    }

}
