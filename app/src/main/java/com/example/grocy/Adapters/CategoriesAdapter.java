package com.example.grocy.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocy.Models.CategoriesModel;
import com.example.grocy.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

public class CategoriesAdapter extends FirestoreRecyclerAdapter<CategoriesModel, CategoriesAdapter.MyViewHolder> {

    public CategoriesAdapter(@NonNull FirestoreRecyclerOptions<CategoriesModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull CategoriesModel model) {
        Picasso.get().load(model.getCatBackground()).into(holder.imageViewCategoriesBackground);
        Picasso.get().load(model.getCatImage()).into(holder.imageViewCatImage);
        holder.textViewCatType.setText(model.getCatType());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_cat, parent, false);
        return new MyViewHolder(view);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewCategoriesBackground, imageViewCatImage;
        TextView textViewCatType;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewCategoriesBackground = itemView.findViewById(R.id.card_background_categories);
            imageViewCatImage = itemView.findViewById(R.id.iv_categories_recycler);
            textViewCatType = itemView.findViewById(R.id.name_cat);
        }
    }
}
