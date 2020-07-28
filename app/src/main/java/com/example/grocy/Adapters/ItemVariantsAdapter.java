package com.example.grocy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.grocy.Models.CartItemsModel;
import com.example.grocy.Models.ItemVariantsModel;
import com.example.grocy.Models.ShopItemsModel;
import com.example.grocy.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemVariantsAdapter extends RecyclerView.Adapter<ItemVariantsAdapter.ItemVariantsViewHolder> {

    Context context;
    static ArrayList<ItemVariantsModel> itemVariantsModelArrayList;
    static int lastCheckedPosition = 0;
    static ArrayList<ItemVariantsModel> selected_variants = new ArrayList<>();
    ShopItemsModel shop_item;

    public ItemVariantsAdapter(Context context, ArrayList<ItemVariantsModel> itemVariantsModelArrayList, ShopItemsModel shop_item) {
        this.context = context;
        this.itemVariantsModelArrayList = itemVariantsModelArrayList;
        this.shop_item = shop_item;

    }

    @NonNull
    @Override
    public ItemVariantsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.variants_recycler, parent, false);
        return new ItemVariantsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemVariantsViewHolder holder, int position) {
        ItemVariantsModel itemVariantsModel = itemVariantsModelArrayList.get(position);
        holder.item_variant_price.setText(itemVariantsModel.getItemPrice());
        holder.item_variant_quantity.setText(itemVariantsModel.getItemQuantity());
//        holder.item_id.setText(itemVariantsModel.getItemID());
//        ShopItemsModel shopItemsModel= ShopItemsAdapter.singleItem.get(parent_position);

        for (int i = 0; i < ShopItemsCategoryAdapter.added_items.size(); i++) {
            CartItemsModel item = ShopItemsCategoryAdapter.added_items.get(i);
            if (item.getItemID().equals(itemVariantsModel.getItemID()) && item.getVariantID().equals(itemVariantsModel.getVariant_id())) {
                if (item.getItemCount() >= 1) {
                    holder.add_button.setVisibility(View.INVISIBLE);
                    holder.linearLayoutItems.setVisibility(View.VISIBLE);
                    holder.item_variant_count.setText("" + item.getItemCount());
                }
            }
        }

        holder.add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemVariantsModel.setVariant_count(1);
                selected_variants.add(itemVariantsModel);
                holder.add_button.setVisibility(View.INVISIBLE);
                holder.linearLayoutItems.setVisibility(View.VISIBLE);

                CartItemsModel cartItemsModel = new CartItemsModel();
                cartItemsModel.setItemID(shop_item.getItemID());
                cartItemsModel.setItemsName(shop_item.getItemsProductName());
                cartItemsModel.setItemsImage(shop_item.getItemsImage());
                cartItemsModel.setItemCount(shop_item.getCount());
                cartItemsModel.setShopId(shop_item.getShopID());

                cartItemsModel.setItemsPrice(itemVariantsModel.getItemPrice());
                cartItemsModel.setItemsQuantity(itemVariantsModel.getItemQuantity());
                cartItemsModel.setVariantID(itemVariantsModel.getVariant_id());
                ShopItemsCategoryAdapter.added_items.add(cartItemsModel);
                ShopItemsCategoryAdapter.no_of_currentlyAddedItems = ShopItemsCategoryAdapter.no_of_currentlyAddedItems + 1;
            }
        });

        holder.add_one_variant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < ShopItemsCategoryAdapter.added_items.size(); i++) {
                    CartItemsModel itemsModel = ShopItemsCategoryAdapter.added_items.get(i);
                    if (itemsModel.getItemID().equals(itemVariantsModel.getItemID()) && itemsModel.getVariantID().equals(itemVariantsModel.getVariant_id())) {
                        ShopItemsCategoryAdapter.added_items.get(i).setItemCount(itemsModel.getItemCount() + 1);
                        holder.item_variant_count.setText("" + ShopItemsCategoryAdapter.added_items.get(i).getItemCount());
                    }
                }
                ShopItemsCategoryAdapter.no_of_currentlyAddedItems = ShopItemsCategoryAdapter.no_of_currentlyAddedItems + 1;

            }
        });

        holder.remove_one_variant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < ShopItemsCategoryAdapter.added_items.size(); i++) {
                    CartItemsModel item = ShopItemsCategoryAdapter.added_items.get(i);
                    if (item.getItemID().equals(itemVariantsModel.getItemID()) && item.getVariantID().equals(itemVariantsModel.getVariant_id())) {
                        if (item.getItemCount() > 1) {
                            ShopItemsCategoryAdapter.added_items.get(i).setItemCount(item.getItemCount() - 1);
                            holder.item_variant_count.setText("" + ShopItemsCategoryAdapter.added_items.get(i).getItemCount());
                        } else {
                            ShopItemsCategoryAdapter.added_items.remove(i);
                            holder.add_button.setVisibility(View.VISIBLE);
                            holder.linearLayoutItems.setVisibility(View.INVISIBLE);
                        }
                    }
                }
                ShopItemsCategoryAdapter.no_of_currentlyAddedItems = ShopItemsCategoryAdapter.no_of_currentlyAddedItems - 1;
            }
        });


    }

    @Override
    public int getItemCount() {
        return itemVariantsModelArrayList.size();
    }

    public class ItemVariantsViewHolder extends RecyclerView.ViewHolder {
        TextView item_variant_price;
        //        TextView item_id;
        Button add_button;
        TextView add_one_variant;
        LinearLayout linearLayoutItems;
        TextView item_variant_count;
        TextView remove_one_variant;
        TextView item_variant_quantity;

        public ItemVariantsViewHolder(@NonNull View itemView) {
            super(itemView);
//            itemVariantQP.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int copyOfLastCheckedPosition = lastCheckedPosition;
//                    lastCheckedPosition = getAdapterPosition();
//                    notifyItemChanged(copyOfLastCheckedPosition);
//                    notifyItemChanged(lastCheckedPosition);
//                }
//            });

//            item_id=itemView.findViewById(R.id.item_id);
            add_button = itemView.findViewById(R.id.add_button);
            add_one_variant = itemView.findViewById(R.id.add_one_variant);
            linearLayoutItems = itemView.findViewById(R.id.linearLayoutItems);
            item_variant_count = itemView.findViewById(R.id.item_variant_count);
            remove_one_variant = itemView.findViewById(R.id.remove_one_variant);
            item_variant_price = itemView.findViewById(R.id.item_variant_price);
            item_variant_quantity = itemView.findViewById(R.id.item_variant_quantity);
        }
    }
}
