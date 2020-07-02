package com.example.grocy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.grocy.Models.MyOrdersModel;
import com.example.grocy.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.MyViewHolder> {

    Context context;
    ArrayList<MyOrdersModel> ordersModelArrayList;

    public TestAdapter(Context context, ArrayList<MyOrdersModel> ordersModelArrayList) {
        this.context = context;
        this.ordersModelArrayList = ordersModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_order_items, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MyOrdersModel myOrdersModel = ordersModelArrayList.get(position);
        holder.name.setText(myOrdersModel.getShopName());
        holder.amount.setText("" + myOrdersModel.getOrderAmount());
        holder.dateTime.setText("" + myOrdersModel.getDateTime());
        holder.deliveryStatus.setText(myOrdersModel.getDeliveryStatus());
        Glide.with(holder.image.getContext()).load(myOrdersModel.getShopImage()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return ordersModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, amount, dateTime, deliveryStatus, textViewMarkFav;
        ImageView image, imageViewFav;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.shop_name);
            dateTime = itemView.findViewById(R.id.ordered_date_time);
            deliveryStatus = itemView.findViewById(R.id.delivery_status);
            amount = itemView.findViewById(R.id.amount);
            image = itemView.findViewById(R.id.shop_image);
            textViewMarkFav = itemView.findViewById(R.id.text_fav);
            //imageViewFav = itemView.findViewById(R.id.image_fav);
        }
    }
}
