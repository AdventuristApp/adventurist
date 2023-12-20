package com.adventurist.adventurist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adventurist.adventurist.R;
import com.amplifyframework.datastore.generated.model.FavHotels;
import com.amplifyframework.datastore.generated.model.Hotel;

import java.util.List;

public class HotelsAdapter extends RecyclerView.Adapter<HotelsAdapter.ViewHolder> {

    private List<Hotel> hotelsList;
    private Context callingActivity;

    // Constructor to initialize the data
    public HotelsAdapter(List<Hotel> hotelsList, Context callingActivity) {
        this.hotelsList = hotelsList;
        this.callingActivity = callingActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_hotels, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TextView favHotelTextView = holder.itemView.findViewById(R.id.FavHotelTextView);
        Hotel hotel = hotelsList.get(position);
        favHotelTextView.setText(hotel.getHotel());
    }

    @Override
    public int getItemCount() {
        return hotelsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView favHotelTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            favHotelTextView = itemView.findViewById(R.id.FavHotelTextView);
        }
    }

}
