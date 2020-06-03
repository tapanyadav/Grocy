package com.example.grocy.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.grocy.Models.ShopsModel;
import com.example.grocy.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

//ShopsAdapter  ShopsModel
public class ShopsAdapter extends FirestoreRecyclerAdapter<ShopsModel, ShopsAdapter.MyViewHolder> {


    public ShopsAdapter(@NonNull FirestoreRecyclerOptions<ShopsModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull ShopsModel model) {

//        Picasso.get().load(model.getShopImage()).into(holder.imageViewShopImage);
//        Picasso.get().load(model.getShopStatusBackground()).into(holder.imageViewShopStatusBackground);

        Glide.with(holder.imageViewShopImage.getContext())
                .load(model.getShopImage())
                .placeholder(R.drawable.ic_launcher_background)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.imageViewShopImage);

        Glide.with(holder.imageViewShopStatusBackground.getContext())
                .load(model.getShopStatusBackground())
                .into(holder.imageViewShopStatusBackground);

        holder.textViewShopCat.setText(model.getShopCategory());
        holder.textViewShopType.setText(model.getShopType());
        holder.textViewShopOff.setText(model.getShopOff());
        holder.textViewShopLimits.setText(model.getShopLimits());
        holder.textViewShopStatus.setText(model.getShopStatus());
        holder.textViewShopRating.setText(model.getShopRating());
        holder.textViewShopAddress.setText(model.getShopAddress());
        holder.textViewShopName.setText(model.getShopName());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_shop, parent, false);
        return new MyViewHolder(view);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewShopImage, imageViewShopStatusBackground;
        TextView textViewShopName, textViewShopStatus, textViewShopRating, textViewShopAddress, textViewShopOff, textViewShopLimits, textViewShopType, textViewShopCat;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewShopImage = itemView.findViewById(R.id.imageShopMain);
            imageViewShopStatusBackground = itemView.findViewById(R.id.backgroundStatusImage);
            textViewShopName = itemView.findViewById(R.id.shop_name_main);
            textViewShopStatus = itemView.findViewById(R.id.shop_status);
            textViewShopAddress = itemView.findViewById(R.id.shop_address_main);
            textViewShopRating = itemView.findViewById(R.id.shop_rating);
            textViewShopLimits = itemView.findViewById(R.id.tv_limit_shop);
            textViewShopOff = itemView.findViewById(R.id.shop_off);
            textViewShopType = itemView.findViewById(R.id.tv_type_shop);
            textViewShopCat = itemView.findViewById(R.id.shop_category);
        }
    }
}
