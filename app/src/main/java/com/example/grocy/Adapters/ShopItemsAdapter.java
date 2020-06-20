package com.example.grocy.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.grocy.Models.ShopItemsModel;
import com.example.grocy.R;
import com.example.grocy.activities.CartActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class ShopItemsAdapter extends RecyclerView.Adapter<ShopItemsAdapter.ShopItemsViewHolder> {

    Context context;
    ArrayList<ShopItemsModel> singleItem;

    public ShopItemsAdapter(Context context, ArrayList<ShopItemsModel> singleItem) {
        super();
        this.context = context;
        this.singleItem = singleItem;
    }

    @NonNull
    @Override
    public ShopItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_details_items_recycler, parent, false);
        return new ShopItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopItemsViewHolder holder, int position) {
        final ShopItemsModel shopItemsModel = singleItem.get(position);
        holder.textViewItemName.setText(shopItemsModel.getItemsProductName());
        holder.textViewItemDescription.setText(shopItemsModel.getItemsProductDescription());
        holder.textViewItemPrice.setText(shopItemsModel.getItemsPrice());
        holder.textViewItemQuantity.setText(shopItemsModel.getItemsQuantity());

        Glide.with(holder.imageViewItem.getContext())
                .load(shopItemsModel.getItemsImage())
                .into(holder.imageViewItem);

        holder.buttonAdd.setOnClickListener(v -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
            bottomSheetDialog.setContentView(R.layout.bottom_sheet_items_quantity);
            bottomSheetDialog.show();
            bottomSheetDialog.setCanceledOnTouchOutside(true);

            Button buttonCart = bottomSheetDialog.findViewById(R.id.buttonCartShow);
            assert buttonCart != null;
            buttonCart.setOnClickListener(v1 -> {
                bottomSheetDialog.dismiss();
                BottomSheetDialog bottomSheetDialog1 = new BottomSheetDialog(context);
                bottomSheetDialog1.setContentView(R.layout.bottom_sheet_cart);
                bottomSheetDialog1.show();

                TextView textViewCartShow = bottomSheetDialog1.findViewById(R.id.textViewCart);
                textViewCartShow.setOnClickListener(v2 -> {
                    bottomSheetDialog1.dismiss();
                    openCart();

                });
            });
        });
    }

    private void openCart() {
        Intent intent = new Intent(context, CartActivity.class);
        context.startActivity(intent);
    }


    @Override
    public int getItemCount() {
        return this.singleItem.size();
    }


    public static class ShopItemsViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewItem;
        TextView textViewItemName, textViewItemDescription, textViewItemPrice, textViewItemQuantity;
        Button buttonAdd;

        public ShopItemsViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewItem = itemView.findViewById(R.id.item_image);
            textViewItemName = itemView.findViewById(R.id.tv_name_item);
            textViewItemDescription = itemView.findViewById(R.id.tv_item_description);
            textViewItemPrice = itemView.findViewById(R.id.tv_item_price);
            textViewItemQuantity = itemView.findViewById(R.id.tv_quantity_item);
            buttonAdd = itemView.findViewById(R.id.materialButtonAdd);

        }
    }
}
