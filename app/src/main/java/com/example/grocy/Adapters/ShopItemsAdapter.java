package com.example.grocy.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.grocy.Models.ShopItemsModel;
import com.example.grocy.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class ShopItemsAdapter extends FirestoreRecyclerAdapter<ShopItemsModel, ShopItemsAdapter.MyViewHolder> {

    public ShopItemsAdapter(@NonNull FirestoreRecyclerOptions<ShopItemsModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull ShopItemsModel model) {

        holder.textViewItemName.setText(model.getItemsProductName());
        holder.textViewItemDescription.setText(model.getItemsProductDescription());
        holder.textViewItemPrice.setText(model.getItemsPrice());
        holder.textViewItemQuantity.setText(model.getItemsQuantity());

        Glide.with(holder.imageViewItem.getContext())
                .load(model.getItemsImage())
                .into(holder.imageViewItem);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_details_items_recycler, parent, false);
        return new MyViewHolder(view);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewItem;
        TextView textViewItemName, textViewItemDescription, textViewItemPrice, textViewItemQuantity;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewItem = itemView.findViewById(R.id.item_image);
            textViewItemName = itemView.findViewById(R.id.tv_name_item);
            textViewItemDescription = itemView.findViewById(R.id.tv_item_description);
            textViewItemPrice = itemView.findViewById(R.id.tv_item_price);
            textViewItemQuantity = itemView.findViewById(R.id.tv_quantity_item);
        }
    }
}
