package com.example.grocy.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.grocy.Models.CategoriesModel;
import com.example.grocy.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class CategoriesAdapter extends FirestoreRecyclerAdapter<CategoriesModel, CategoriesAdapter.MyViewHolder> {

    private OnListItemClick onListItemClick;

    public CategoriesAdapter(@NonNull FirestoreRecyclerOptions<CategoriesModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull CategoriesModel model) {

        Glide.with(holder.imageViewCategoriesBackground.getContext())
                .load(model.getCatBackground())
                .into(holder.imageViewCategoriesBackground);

        Glide.with(holder.imageViewCatImage.getContext())
                .load(model.getCatImage())
                .into(holder.imageViewCatImage);

//        Picasso.get().load(model.getCatBackground()).into(holder.imageViewCategoriesBackground);
//        Picasso.get().load(model.getCatImage()).into(holder.imageViewCatImage);
        holder.textViewCatType.setText(model.getCatType());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_cat, parent, false);
        return new MyViewHolder(view);
    }

    public void setOnListItemClick(OnListItemClick onListItemClick) {
        this.onListItemClick = onListItemClick;
    }

    public interface OnListItemClick {
        void onItemClick(DocumentSnapshot snapshot, int position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewCategoriesBackground, imageViewCatImage;
        TextView textViewCatType;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewCategoriesBackground = itemView.findViewById(R.id.card_background_categories);
            imageViewCatImage = itemView.findViewById(R.id.iv_categories_recycler);
            textViewCatType = itemView.findViewById(R.id.name_cat);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && onListItemClick != null) {
                    onListItemClick.onItemClick(getSnapshots().getSnapshot(position), position);
                }
            });
        }
    }
}
