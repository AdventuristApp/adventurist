package com.adventurist.adventurist;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adventurist.adventurist.R;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private ArrayList<Uri> imageUris;
    private OnItemClickListener listener;

    public ImageAdapter(ArrayList<Uri> imageUris) {
        this.imageUris = imageUris;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);

    }

    public void OnItemClickListner(OnItemClickListener clickListener){
        listener=clickListener;

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
        // Assuming you have an ImageView in your item layout, set the image to it
        holder.imageView.setImageURI(imageUri);





    }

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
