package com.example.grocy.Adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocy.Models.HorizontalModel;
import com.example.grocy.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;


//HorizontalModel

public class HorizontalAdapter extends FirestoreRecyclerAdapter<HorizontalModel, HorizontalAdapter.MyViewHolder> {


    public HorizontalAdapter(@NonNull FirestoreRecyclerOptions<HorizontalModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull HorizontalModel model) {

        Picasso.get().load(model.getShopHorizontalBackgroundImage()).into(holder.imageViewBackground);
        Picasso.get().load(model.getShopHorizontalImage()).into(holder.imageViewHorizontalShopImage);
        holder.textViewHorizontalShopName.setText(model.getShopHorizontalName());

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_horizontal_recycler, parent, false);
        return new MyViewHolder(view);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewBackground, imageViewHorizontalShopImage;
        TextView textViewHorizontalShopName;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewBackground = itemView.findViewById(R.id.card_background_horizontal);
            imageViewHorizontalShopImage = itemView.findViewById(R.id.iv_horizontal_recycler);
            textViewHorizontalShopName = itemView.findViewById(R.id.name_store_horizontal);
        }
    }
}
