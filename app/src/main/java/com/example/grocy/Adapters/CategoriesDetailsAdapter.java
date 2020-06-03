package com.example.grocy.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.grocy.Models.CategoriesDetailsModel;
import com.example.grocy.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

public class CategoriesDetailsAdapter extends FirestoreRecyclerAdapter<CategoriesDetailsModel, CategoriesDetailsAdapter.MyViewHolder> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public CategoriesDetailsAdapter(@NonNull FirestoreRecyclerOptions<CategoriesDetailsModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull CategoriesDetailsModel model) {
        // Picasso.get().load(model.getShopImage()).into(holder.imageViewCatShopImage);
        Picasso.get().load(model.getShopStatusBackground()).into(holder.imageViewCatShopStatusBackground);

        Glide.with(holder.imageViewCatShopImage.getContext())
                .load(model.getShopImage())
                .placeholder(R.drawable.ic_launcher_background)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.imageViewCatShopImage);
        Glide.with(holder.imageViewCatShopStatusBackground.getContext())
                .load(model.getShopStatusBackground())
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.imageViewCatShopStatusBackground);

        holder.textViewCatShopCat.setText(model.getShopCategory());
        holder.textViewCatShopType.setText(model.getShopType());
        holder.textViewCatShopOff.setText(model.getShopOff());
        holder.textViewCatShopLimits.setText(model.getShopLimits());
        holder.textViewCatShopStatus.setText(model.getShopStatus());
        holder.textViewCatShopRating.setText(model.getShopRating());
        holder.textViewCatShopAddress.setText(model.getShopAddress());
        holder.textViewCatShopName.setText(model.getShopName());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_cat_all, parent, false);
        return new MyViewHolder(view);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewCatShopImage, imageViewCatShopStatusBackground;
        TextView textViewCatShopName, textViewCatShopStatus, textViewCatShopRating, textViewCatShopAddress, textViewCatShopOff, textViewCatShopLimits, textViewCatShopType, textViewCatShopCat;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewCatShopImage = itemView.findViewById(R.id.imageCatDetails);
            imageViewCatShopStatusBackground = itemView.findViewById(R.id.backgroundStatusImageCat);
            textViewCatShopName = itemView.findViewById(R.id.shop_name_cat);
            textViewCatShopStatus = itemView.findViewById(R.id.shop_status_cat);
            textViewCatShopAddress = itemView.findViewById(R.id.shop_address_cat);
            textViewCatShopRating = itemView.findViewById(R.id.shop_rating_cat);
            textViewCatShopLimits = itemView.findViewById(R.id.tv_limit_shop_cat);
            textViewCatShopOff = itemView.findViewById(R.id.shop_off_cat);
            textViewCatShopType = itemView.findViewById(R.id.tv_type_shop_cat);
            textViewCatShopCat = itemView.findViewById(R.id.shop_category_cat);
        }
    }
}
