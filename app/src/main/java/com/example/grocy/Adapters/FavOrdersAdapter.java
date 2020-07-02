package com.example.grocy.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.example.grocy.Models.MyOrdersModel;
import com.example.grocy.R;
import com.example.grocy.activities.MyOrderDetailsActivity;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FavOrdersAdapter extends FirestoreRecyclerAdapter<MyOrdersModel, FavOrdersAdapter.MyViewHolder> {

    public FavOrdersAdapter(@NonNull FirestoreRecyclerOptions<MyOrdersModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull FavOrdersAdapter.MyViewHolder holder, int position, @NonNull MyOrdersModel model) {

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        DocumentReference documentReference;
        String user_id = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
        String document_id = getSnapshots().getSnapshot(position).getId();
        documentReference = firebaseFirestore.collection("Users").document(user_id);

        HashMap<String, Object> updateFavStatus = new HashMap<>();
        holder.name.setText(model.getShopName());
        holder.amount.setText("" + model.getOrderAmount());
        holder.dateTime.setText("" + model.getDateTime());
        holder.deliveryStatus.setText(model.getDeliveryStatus());
        Glide.with(holder.image.getContext()).load(model.getShopImage()).into(holder.image);

        HashMap<String, Object> favOrders = new HashMap<>();
        favOrders.put("shopName", model.getShopName());
        favOrders.put("orderDateTime", model.getDateTime());
        favOrders.put("shopImage", model.getShopImage());
        favOrders.put("deliveryStatus", model.getDeliveryStatus());
        favOrders.put("orderAmount", model.getOrderAmount());
        favOrders.put("userAddress", model.getUserAddress());
        favOrders.put("orderPaymentMode", model.getOrderPaymentMode());

        ScaleAnimation scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f);
        scaleAnimation.setDuration(500);
        BounceInterpolator bounceInterpolator = new BounceInterpolator();
        scaleAnimation.setInterpolator(bounceInterpolator);

        holder.toggleButtonRemoveFav.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) {
                updateFavStatus.put("favOrder", false);
                documentReference.collection("myOrders").document(document_id).update(updateFavStatus).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(holder.itemView.getContext(), "Successful", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(holder.itemView.getContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        holder.textViewRemoveFav.setOnClickListener(v -> {
            holder.toggleButtonRemoveFav.startAnimation(scaleAnimation);
            updateFavStatus.put("favOrder", false);
            documentReference.collection("myOrders").document(document_id).update(updateFavStatus).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(holder.itemView.getContext(), "Successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(holder.itemView.getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            });
        });

        holder.textViewOrderDetails.setOnClickListener(v -> {
            Intent intent = new Intent(holder.textViewOrderDetails.getContext(), MyOrderDetailsActivity.class);
            intent.putExtra("getOrderId", document_id);
            intent.putExtra("myOrderMap", favOrders);
            holder.textViewOrderDetails.getContext().startActivity(intent);

        });

    }

    @NonNull
    @Override
    public FavOrdersAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_order_layout, parent, false);
        return new FavOrdersAdapter.MyViewHolder(view);
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, amount, deliveryStatus, dateTime, textViewRemoveFav, textViewOrderDetails;
        ImageView image;
        ToggleButton toggleButtonRemoveFav;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.fav_shop_name);
            dateTime = itemView.findViewById(R.id.fav_ordered_date_time);
            deliveryStatus = itemView.findViewById(R.id.fav_delivery_status);
            amount = itemView.findViewById(R.id.fav_amount);
            image = itemView.findViewById(R.id.fav_shop_image);
            toggleButtonRemoveFav = itemView.findViewById(R.id.button_remove_favourite);
            textViewRemoveFav = itemView.findViewById(R.id.text_remove_fav);
            textViewOrderDetails = itemView.findViewById(R.id.fav_orderDetails);
        }
    }

}