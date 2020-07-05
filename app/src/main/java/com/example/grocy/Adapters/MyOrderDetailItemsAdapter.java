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

import java.util.HashMap;

public class MyOrderDetailItemsAdapter extends FirestoreRecyclerAdapter<MyOrdersModel, MyOrderDetailItemsAdapter.MyViewHolder> {

    public MyOrderDetailItemsAdapter(@NonNull FirestoreRecyclerOptions<MyOrdersModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull MyOrdersModel model) {


//        for(int i=0;i<model.getItems().size();i++){
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap = model.getItems().get(position);
        holder.textViewItemName.setText((String) stringObjectHashMap.get("itemsName"));
        holder.textViewQuantity.setText((String) stringObjectHashMap.get("itemsQuantity"));
        holder.textViewItemsCount.setText("" + stringObjectHashMap.get("itemCount"));
        holder.textViewItemPrice.setText((String) stringObjectHashMap.get("itemsPrice"));
        Glide.with(holder.imageViewItems.getContext()).load(stringObjectHashMap.get("itemsImage")).into(holder.imageViewItems);

//        }

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_my_order_details, parent, false);
        return new MyViewHolder(view);
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