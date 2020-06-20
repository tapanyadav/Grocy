package com.example.grocy.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.grocy.Models.MyOrdersModel;
import com.example.grocy.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class MyOrdersAdapter extends FirestoreRecyclerAdapter<MyOrdersModel, MyOrdersAdapter.MyViewHolder> {

    public MyOrdersAdapter(@NonNull FirestoreRecyclerOptions<MyOrdersModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull MyOrdersModel model) {

        holder.name.setText(model.getShopName());
        holder.address.setText(model.getShopAddress());
        holder.amount.setText(model.getOrderAmount());
        holder.dateTime.setText(model.getDateTime());
        holder.deliveryStatus.setText(model.getDeliveryStatus());
        holder.items.setText(model.getOrderedItems());
        Glide.with(holder.image.getContext()).load(model.getShopImage()).into(holder.image);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_order_items, parent, false);
        return new MyViewHolder(view);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView address;
        TextView items;
        TextView dateTime;
        TextView deliveryStatus;
        TextView amount;
        ImageView image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.shop_name);
            address = itemView.findViewById(R.id.shop_address);
            items = itemView.findViewById(R.id.ordered_items);
            dateTime = itemView.findViewById(R.id.ordered_date_time);
            deliveryStatus = itemView.findViewById(R.id.delivery_status);
            amount = itemView.findViewById(R.id.amount);
            image = itemView.findViewById(R.id.shop_image);


        }
    }
}
