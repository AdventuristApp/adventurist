package com.adventurist.adventurist.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adventurist.adventurist.R;
import com.amplifyframework.datastore.generated.model.FavResturants;
import com.amplifyframework.datastore.generated.model.Resturant;

import java.util.List;

public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.ViewHolder> {

    private List<Resturant> restaurantsList;
    private Context callingActivity;

    // Constructor to initialize the data
    public RestaurantsAdapter(List<Resturant> restaurantsList, Context callingActivity) {
        this.restaurantsList = restaurantsList;
        this.callingActivity = callingActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_rest, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        FavResturants restaurant = restaurantsList.get(position);
        // Bind data to views in the ViewHolder
//        holder.bindData(restaurant);


        TextView FavRestTextView=holder.itemView.findViewById(R.id.FavRestTextView);
        Resturant restaurant = restaurantsList.get(position);
        FavRestTextView.setText(restaurant.getResturant());
        Log.d("restactivity", restaurant.getResturant() );
        Log.i("restAdapter"," in rest recycleview adapter");
    }

    @Override
    public int getItemCount() {
        return restaurantsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewrest;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewrest = itemView.findViewById(R.id.FavRestTextView);
        }

    }
}
