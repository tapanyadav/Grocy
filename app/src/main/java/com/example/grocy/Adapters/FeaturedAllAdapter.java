package com.example.grocy.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.grocy.Models.FeaturedAllModel;
import com.example.grocy.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class FeaturedAllAdapter extends FirestoreRecyclerAdapter<FeaturedAllModel, FeaturedAllAdapter.FeaturedViewHolder> {

    private OnListItemClick onListItemClick;

    public FeaturedAllAdapter(@NonNull FirestoreRecyclerOptions<FeaturedAllModel> options,OnListItemClick onListItemClick) {
        super(options);
        this.onListItemClick=onListItemClick;
    }

    @Override
    protected void onBindViewHolder(@NonNull FeaturedViewHolder holder, int position, @NonNull FeaturedAllModel model) {
        holder.shopStatus.setText(model.getShopStatus());
        holder.shopRating.setText(model.getShopRating()+ "");
        holder.shopNameAll.setText(model.getShopNameAll());
        holder.shopAddressAll.setText(model.getShopAddressAll());
        holder.shopCategory.setText(model.getShopCategory());
        holder.shopOff.setText(model.getShopOff());
        //Picasso.get().load(model.getShopImage()).into(holder.shopImage);

        Glide.with(holder.shopImage.getContext())
                .load(model.getShopImage())
                .into(holder.shopImage);
    }

    @NonNull
    @Override
    public FeaturedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_content_featured_all,parent,false);
        return new FeaturedViewHolder(view);
    }

    public class FeaturedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView shopStatus,shopRating,shopNameAll,shopAddressAll,shopCategory,shopOff;
        private ImageView shopImage;

        public FeaturedViewHolder(@NonNull View itemView) {

            super(itemView);
            shopStatus=itemView.findViewById(R.id.shop_status);
            shopRating=itemView.findViewById(R.id.shop_rating);
            shopNameAll=itemView.findViewById(R.id.shop_name_all);
            shopAddressAll=itemView.findViewById(R.id.shop_address_all);
            shopCategory=itemView.findViewById(R.id.shop_category);
            shopOff=itemView.findViewById(R.id.shop_off);
            shopImage=itemView.findViewById(R.id.iv_featured_recycler_all);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onListItemClick.onItemClick();
        }
    }

    public interface OnListItemClick{
        void onItemClick();
    }
}
