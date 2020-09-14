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
import com.example.grocy.R;

import java.util.ArrayList;
import java.util.HashMap;

public class MyOrderDetailItemsAdapter extends RecyclerView.Adapter<MyOrderDetailItemsAdapter.MyViewHolder> {

    Context context;
    ArrayList<HashMap<String, Object>> order_items_list;

    public MyOrderDetailItemsAdapter(Context context, ArrayList<HashMap<String, Object>> order_items_list) {
        this.context = context;
        this.order_items_list = order_items_list;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_my_order_details, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        HashMap<String, Object> stringObjectHashMap = order_items_list.get(position);
        holder.textViewItemName.setText((String) stringObjectHashMap.get("itemsName"));
        holder.textViewQuantity.setText((String) stringObjectHashMap.get("itemsQuantity"));
        holder.textViewItemsCount.setText("" + stringObjectHashMap.get("itemCount"));
        holder.textViewItemPrice.setText((String) stringObjectHashMap.get("itemsPrice"));
        Glide.with(holder.imageViewItems.getContext()).load(stringObjectHashMap.get("itemsImage")).into(holder.imageViewItems);


    }

    @Override
    public int getItemCount() {
        return order_items_list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewItemName, textViewItemsCount, textViewQuantity, textViewItemPrice;
        ImageView imageViewItems;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewItemName = itemView.findViewById(R.id.cart_item_name_order);
            textViewItemPrice = itemView.findViewById(R.id.text_item_total_price);
            textViewItemsCount = itemView.findViewById(R.id.text_items_count);
            textViewQuantity = itemView.findViewById(R.id.cart_item_quantity_order);
            imageViewItems = itemView.findViewById(R.id.cart_order_item_image);

        }
    }
}