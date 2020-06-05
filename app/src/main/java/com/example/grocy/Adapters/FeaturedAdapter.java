package com.example.grocy.Adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.grocy.Models.FeaturedModel;
import com.example.grocy.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;


public class FeaturedAdapter extends FirestoreRecyclerAdapter<FeaturedModel, FeaturedAdapter.MyViewHolder> {


    public FeaturedAdapter(@NonNull FirestoreRecyclerOptions<FeaturedModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull FeaturedModel model) {

        //Picasso.get().load(model.getFeaturedImage()).into(holder.imageViewFeatured);
        Glide.with(holder.imageViewFeatured.getContext())
                .load(model.getFeaturedImage())
                .into(holder.imageViewFeatured);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_featured, parent, false);
        return new MyViewHolder(view);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewFeatured;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewFeatured = itemView.findViewById(R.id.iv_featured_recycler);
        }
    }
}
