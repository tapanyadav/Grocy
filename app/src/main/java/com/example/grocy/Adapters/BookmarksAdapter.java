package com.example.grocy.Adapters;

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
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Objects;

public class BookmarksAdapter extends FirestoreRecyclerAdapter<MyOrdersModel, BookmarksAdapter.MyViewHolder> {

    int noOfBookmark;

    public BookmarksAdapter(@NonNull FirestoreRecyclerOptions<MyOrdersModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull BookmarksAdapter.MyViewHolder holder, int position, @NonNull MyOrdersModel model) {


        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String user_id = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
        DocumentReference documentReference;
        String document_id = getSnapshots().getSnapshot(position).getId();
        documentReference = firebaseFirestore.collection("Users").document(user_id);
        String documentId = getSnapshots().getSnapshot(position).getId();

        HashMap<String, Object> updateFavStatus = new HashMap<>();
        holder.name.setText(model.getShopName());
        holder.textViewShopTimings.setText("" + model.getDateTime());
        holder.textViewShopAddress.setText(model.getShopAddress());
        Glide.with(holder.image.getContext()).load(model.getShopImage()).into(holder.image);


        ScaleAnimation scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f);
        scaleAnimation.setDuration(500);
        BounceInterpolator bounceInterpolator = new BounceInterpolator();
        scaleAnimation.setInterpolator(bounceInterpolator);

        holder.toggleButtonRemoveBookmark.setOnClickListener(v -> {


            HashMap<String, Object> bookmarkHashMap = new HashMap<>();
            holder.toggleButtonRemoveBookmark.startAnimation(scaleAnimation);
            holder.toggleButtonRemoveBookmark.setChecked(true);
            updateFavStatus.put("bookmarkStatus", false);
            bookmarkHashMap.put("noOfBookmarks", noOfBookmark -= 1);
            documentReference.collection("myOrders").document(documentId).update(updateFavStatus).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(holder.itemView.getContext(), "Store remove from bookmarks", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(holder.itemView.getContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            documentReference.update(bookmarkHashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(holder.itemView.getContext(), "no. of bookmarks updated", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(holder.itemView.getContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        });

        documentReference.get().addOnCompleteListener(task -> {
            DocumentSnapshot document = task.getResult();
            if (document.getData().containsKey("noOfBookmarks")) {
                noOfBookmark = Integer.parseInt("" + document.get("noOfBookmarks"));
                //Toast.makeText(this, "Bookmarks:"+document.get("noOfBookmarks"), Toast.LENGTH_SHORT).show();
            } else {
                noOfBookmark = 0;
            }
        });

        //TODO Add ratings,address,category and shop time from shop in my orders

    }

    @NonNull
    @Override
    public BookmarksAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmark_layout, parent, false);
        return new BookmarksAdapter.MyViewHolder(view);
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, textViewShopTimings, textViewShopRating, textViewShopAddress, textViewShopCategory;
        ImageView image;
        ToggleButton toggleButtonRemoveBookmark;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.fav_shop_name);
            textViewShopTimings = itemView.findViewById(R.id.bookmark_shop_time);
            image = itemView.findViewById(R.id.fav_shop_image);
            textViewShopRating = itemView.findViewById(R.id.bookmark_shop_rating);
            textViewShopAddress = itemView.findViewById(R.id.bookmark_shop_address);
            textViewShopCategory = itemView.findViewById(R.id.bookmark_shop_category);
            toggleButtonRemoveBookmark = itemView.findViewById(R.id.button_bookmark);

        }
    }
}
