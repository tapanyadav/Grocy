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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.grocy.Models.MyOrdersModel;
import com.example.grocy.R;
import com.example.grocy.activities.MyOrderDetailsActivity;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Objects;

public class MyOrdersAdapter extends FirestoreRecyclerAdapter<MyOrdersModel, MyOrdersAdapter.MyViewHolder> {

    public MyOrdersAdapter(@NonNull FirestoreRecyclerOptions<MyOrdersModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyOrdersAdapter.MyViewHolder holder, int position, @NonNull MyOrdersModel model) {

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String user_id = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
        DocumentReference documentReference;
        documentReference = firebaseFirestore.collection("Users").document(user_id);
        String documentId = getSnapshots().getSnapshot(position).getId();

        HashMap<String, Object> favOrders = new HashMap<>();
        HashMap<String, Object> updateFavStatus = new HashMap<>();
        favOrders.put("shopName", model.getShopName());
        favOrders.put("orderDateTime", model.getDateTime());
        favOrders.put("shopImage", model.getShopImage());
        favOrders.put("deliveryStatus", model.getDeliveryStatus());
        favOrders.put("orderAmount", model.getOrderAmount());
        favOrders.put("userAddress", model.getUserAddress());
        favOrders.put("orderPaymentMode", model.getOrderPaymentMode());
        //favOrders.put("items",model.getItems());

        holder.name.setText(model.getShopName());
        holder.amount.setText("" + model.getOrderAmount());
        holder.dateTime.setText(model.getDateTime());
        holder.deliveryStatus.setText(model.getDeliveryStatus());
        Glide.with(holder.image.getContext()).load(model.getShopImage()).into(holder.image);

        ScaleAnimation scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f);
        scaleAnimation.setDuration(500);
        BounceInterpolator bounceInterpolator = new BounceInterpolator();
        scaleAnimation.setInterpolator(bounceInterpolator);
        if (model.isFavOrder()) {
            holder.toggleButtonFav.setChecked(true);
            holder.favText.setText("Added to favourite");

        } else {
            holder.toggleButtonFav.setChecked(false);
            holder.favText.setText("Mark as favourite");

        }

//        holder.toggleButtonFav.setOnCheckedChangeListener((compoundButton, isChecked) -> {
//            //animation
//            compoundButton.startAnimation(scaleAnimation);
//            holder.favText.setText("Added to favourite");
//            updateFavStatus.put("favOrder",true);
//            documentReference.collection("myOrder").document(documentId).update(updateFavStatus).addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//                    if (task.isSuccessful()){
//                        Toast.makeText(holder.itemView.getContext(), "Successful", Toast.LENGTH_SHORT).show();
//                    }
//                    else {
//                        Toast.makeText(holder.itemView.getContext(), "Error", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//
//
////            if (!isChecked){
////                holder.favText.setText("Mark as favourite");
////                updateFavStatus.put("favOrder",false);
////                documentReference.collection("myOrder").document(documentId).update(updateFavStatus).addOnCompleteListener(task -> {
////                if (task.isSuccessful()){
////                    Toast.makeText(holder.itemView.getContext(), "Successful", Toast.LENGTH_SHORT).show();
////                }
////                else {
////                    Toast.makeText(holder.itemView.getContext(), "Error", Toast.LENGTH_SHORT).show();
////                }
////            });
////            }
//        });

        holder.toggleButtonFav.setOnClickListener(v -> {
            holder.toggleButtonFav.startAnimation(scaleAnimation);
            holder.toggleButtonFav.setChecked(true);
            holder.favText.setText("Added to favourite");
            updateFavStatus.put("favOrder", true);
            documentReference.collection("myOrder").document(documentId).update(updateFavStatus).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(holder.itemView.getContext(), "Successful", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(holder.itemView.getContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });


        holder.favText.setOnClickListener(v -> {
            holder.toggleButtonFav.startAnimation(scaleAnimation);
            holder.favText.setText("Added to favourite");
            updateFavStatus.put("favOrder", true);
            documentReference.collection("myOrder").document(documentId).update(updateFavStatus).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(holder.itemView.getContext(), "Successful", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(holder.itemView.getContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });

        holder.textViewOrderDetails.setOnClickListener(v -> {
            Intent intent = new Intent(holder.textViewOrderDetails.getContext(), MyOrderDetailsActivity.class);
            intent.putExtra("getOrderId", documentId);
            intent.putExtra("myOrderMap", favOrders);
            holder.textViewOrderDetails.getContext().startActivity(intent);

        });

    }

    @NonNull
    @Override
    public MyOrdersAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_order_items, parent, false);
        return new MyOrdersAdapter.MyViewHolder(view);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, amount, deliveryStatus, dateTime, favText, textViewOrderDetails;
        ImageView image;
        ToggleButton toggleButtonFav;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.shop_name);
            dateTime = itemView.findViewById(R.id.ordered_date_time);
            deliveryStatus = itemView.findViewById(R.id.delivery_status);
            amount = itemView.findViewById(R.id.amount);
            image = itemView.findViewById(R.id.shop_image);
            toggleButtonFav = itemView.findViewById(R.id.button_favorite);
            favText = itemView.findViewById(R.id.text_fav);
            textViewOrderDetails = itemView.findViewById(R.id.my_order_details);
        }
    }
}
