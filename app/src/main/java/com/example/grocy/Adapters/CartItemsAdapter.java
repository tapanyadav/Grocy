package com.example.grocy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.grocy.Models.CartItemsModel;
import com.example.grocy.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CartItemsAdapter extends RecyclerView.Adapter<CartItemsAdapter.CartItemsViewHolder> {

    Context context;
    ArrayList<CartItemsModel> cart_items_list;

    public CartItemsAdapter(Context context, ArrayList<CartItemsModel> cart_items_list) {
        this.context = context;
        this.cart_items_list = cart_items_list;
    }

    @NonNull
    @Override
    public CartItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_cart_items, parent, false);
        return new CartItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemsViewHolder holder, int position) {
        final CartItemsModel cartItemsModel = this.cart_items_list.get(position);

        Glide.with(holder.cart_item_image.getContext())
                .load(cartItemsModel.getItemsImage())
                .into(holder.cart_item_image);

        holder.cart_item_name.setText(cartItemsModel.getItemsName());
        holder.cart_item_price.setText(cartItemsModel.getItemsPrice());
        holder.cart_item_quantity.setText(cartItemsModel.getItemsQuantity());
        holder.cart_item_total_price.setText(String.valueOf(Integer.parseInt(cartItemsModel.getItemsPrice()) * cartItemsModel.getItemCount()));
        holder.item_count.setText(String.valueOf(cartItemsModel.getItemCount()));
    }

    @Override
    public int getItemCount() {
        return cart_items_list.size();
    }

    public class CartItemsViewHolder extends RecyclerView.ViewHolder {
        ImageView cart_item_image;
        TextView cart_item_name;
        TextView cart_item_price;
        TextView cart_item_quantity;
        TextView cart_item_total_price;
        TextView item_count;

        public CartItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            cart_item_image = itemView.findViewById(R.id.cart_item_image);
            cart_item_name = itemView.findViewById(R.id.cart_item_name);
            cart_item_price = itemView.findViewById(R.id.cart_item_price);
            cart_item_quantity = itemView.findViewById(R.id.cart_item_quantity);
            cart_item_total_price = itemView.findViewById(R.id.cart_item_total_price);
            item_count = itemView.findViewById(R.id.item_count);
        }
    }
}
