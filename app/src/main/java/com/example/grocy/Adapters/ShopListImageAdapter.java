package com.example.grocy.Adapters;

import android.content.Intent;
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
import com.example.grocy.activities.AddPhotoActivity;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class ShopListImageAdapter extends FirestoreRecyclerAdapter<MyOrdersModel, ShopListImageAdapter.MyViewHolder> {

    //private ShopListImageAdapter.OnListItemClick onListItemClick;

    public ShopListImageAdapter(@NonNull FirestoreRecyclerOptions<MyOrdersModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ShopListImageAdapter.MyViewHolder holder, int position, @NonNull MyOrdersModel model) {
        holder.textViewShopListName.setText(model.getShopName());
        holder.textViewShopListAddress.setText(model.getShopAddress());
        Glide.with(holder.imageViewShopListImage.getContext()).load(model.getShopImage()).into(holder.imageViewShopListImage);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), AddPhotoActivity.class);
            intent.putExtra("storeName", model.getShopName());
            intent.putExtra("storeImage", model.getShopImage());
            intent.putExtra("storeAddress", model.getShopAddress());
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @NonNull
    @Override
    public ShopListImageAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_shop_list_review, parent, false);
        return new ShopListImageAdapter.MyViewHolder(view);
    }

//    public void setOnListItemClick(ShopListImageAdapter.OnListItemClick onListItemClick) {
//        this.onListItemClick = onListItemClick;
//    }
//
//    public interface OnListItemClick {
//        void onItemClick(DocumentSnapshot snapshot, int position);
//    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewShopListName, textViewShopListAddress;
        ImageView imageViewShopListImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewShopListAddress = itemView.findViewById(R.id.text_shopAddress);
            textViewShopListName = itemView.findViewById(R.id.text_shopName);
            imageViewShopListImage = itemView.findViewById(R.id.image_shop_list);

//            itemView.setOnClickListener(v -> {
//                int position = getAdapterPosition();
//
//                if (position != RecyclerView.NO_POSITION && onListItemClick != null) {
//                    onListItemClick.onItemClick(getSnapshots().getSnapshot(position), position);
//                }
//            });
        }
    }
}

