package com.adventurist.adventurist;



import static com.adventurist.adventurist.GalleryActivity.Gallery_TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adventurist.adventurist.R;
import com.amplifyframework.datastore.generated.model.Images;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    Context callingActivity;

    private ArrayList<Uri> imageUris;
    private List<Images> imagesList;
    private OnItemClickListener listener;

    public ImageAdapter(ArrayList<Uri> imageUris, Context callingActivity) {
        this.imageUris = imageUris;
        this.callingActivity=callingActivity;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);

    }

    public void OnItemClickListner(OnItemClickListener clickListener){
        listener=clickListener;

    }
    @SuppressLint("NotifyDataSetChanged")
    public void setImages(List<Images> images) {
        this.imagesList = images;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_gallery, parent, false);
        return new ImageViewHolder(view,listener);
    }


    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Uri imageUri = imageUris.get(position);
        holder.imageView.setImageURI(imageUri);

        View listViewHolder = holder.itemView;
        // Make it clickable
        listViewHolder.setOnClickListener(view -> {
            Intent goToshowIntent = new Intent(callingActivity, showActivity.class);
            goToshowIntent.putExtra(Gallery_TAG, Images.ID.toString());

            // Pass the URI of the clicked image to the new activity
            goToshowIntent.putExtra("imageUri", imageUri.toString());

            callingActivity.startActivity(goToshowIntent);


    });}
    //Images IMAGE=imagesList.get(position);
//holder.imageView.setImageBitmap();
    @Override
    public int getItemCount() {
        return imageUris.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        private ImageView imageView2;
        public ImageViewHolder(@NonNull View itemView,OnItemClickListener listener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageFragment);

            imageView2=itemView.findViewById(R.id.delFragmentImage);

            imageView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
listener.onItemClick(getAdapterPosition());
                }
            })

            ;
        }
    }
}
