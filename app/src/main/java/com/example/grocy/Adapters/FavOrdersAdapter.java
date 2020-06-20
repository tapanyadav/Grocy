package com.example.grocy.Adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.grocy.Models.OrderModel;
import com.example.grocy.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class FavOrdersAdapter extends FirestoreRecyclerAdapter<OrderModel, FavOrdersAdapter.MyViewHolder> {
    public FavOrdersAdapter(@NonNull FirestoreRecyclerOptions<OrderModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull FavOrdersAdapter.MyViewHolder holder, int position, @NonNull OrderModel model) {

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
    public FavOrdersAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_order_layout,parent,false);
        return new FavOrdersAdapter.MyViewHolder(view);
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

            name=itemView.findViewById(R.id.shop_name);
            address=itemView.findViewById(R.id.shop_address);
            items=itemView.findViewById(R.id.ordered_items);
            dateTime=itemView.findViewById(R.id.ordered_date_time);
            deliveryStatus=itemView.findViewById(R.id.delivery_status);
            amount=itemView.findViewById(R.id.amount);
            image=itemView.findViewById(R.id.shop_image);


        }
    }

}