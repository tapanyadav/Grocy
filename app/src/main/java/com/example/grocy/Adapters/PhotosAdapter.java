package com.example.grocy.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.grocy.Models.PhotosModel;
import com.example.grocy.R;
import com.example.grocy.helper.ImagePreviewer;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class PhotosAdapter extends FirestoreRecyclerAdapter<PhotosModel, PhotosAdapter.MyViewHolder> {

    public PhotosAdapter(@NonNull FirestoreRecyclerOptions<PhotosModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull PhotosModel model) {
        Glide.with(holder.imageViewPhotos.getContext()).load(model.getPhotoImage()).into(holder.imageViewPhotos);
        holder.imageViewPhotos.setOnLongClickListener(v -> {
            new ImagePreviewer().show(holder.imageViewPhotos.getContext(), holder.imageViewPhotos);
            return false;
        });

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_items_frag_photos, parent, false);
        return new MyViewHolder(view);
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewPhotos;
        TextView textViewCaption;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewPhotos = itemView.findViewById(R.id.image_photo);
            //textViewCaption = itemView.findViewById(R.id.);
        }
    }
}
