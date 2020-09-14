package com.example.grocy.Adapters;

import android.content.Context;
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

import java.util.ArrayList;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.MyViewHolder> {

    Context context;
    ArrayList<PhotosModel> photos_list;

    public PhotosAdapter(Context context, ArrayList<PhotosModel> photos_list) {
        this.context = context;
        this.photos_list = photos_list;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_items_frag_photos, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        PhotosModel photosModel = photos_list.get(position);
        Glide.with(holder.imageViewPhotos.getContext()).load(photosModel.getPhotoImage()).into(holder.imageViewPhotos);
        holder.imageViewPhotos.setOnLongClickListener(v -> {
            new ImagePreviewer().show(holder.imageViewPhotos.getContext(), holder.imageViewPhotos);
            return false;
        });

        holder.textViewShopAddress.setText(photosModel.getShopAddress());
        holder.textViewShopName.setText(photosModel.getShopName());
        holder.textViewCaption.setText(photosModel.getPhotoCaption());
        Glide.with(holder.imageViewShopImage.getContext()).load(photosModel.getShopImage()).into(holder.imageViewShopImage);
    }

    @Override
    public int getItemCount() {
        return photos_list.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewPhotos, imageViewShopImage;
        TextView textViewCaption, textViewShopName, textViewShopAddress;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewPhotos = itemView.findViewById(R.id.image_photo);
            textViewCaption = itemView.findViewById(R.id.text_photoCaption);
            imageViewShopImage = itemView.findViewById(R.id.image_shopPhoto);
            textViewShopName = itemView.findViewById(R.id.text_shopName);
            textViewShopAddress = itemView.findViewById(R.id.text_shop_address);
        }
    }
}
