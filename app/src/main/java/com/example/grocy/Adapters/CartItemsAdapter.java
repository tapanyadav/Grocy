package com.example.grocy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.grocy.Models.CartItemsModel;
import com.example.grocy.R;
import com.example.grocy.activities.CartActivity;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CartItemsAdapter extends RecyclerView.Adapter<CartItemsAdapter.CartItemsViewHolder> {

    Context context;
    ArrayList<CartItemsModel> cart_items_list;

    public CartItemsAdapter(Context context, ArrayList<CartItemsModel> cart_items_list) {
        this.context = context;
        this.cart_items_list = cart_items_list;
    }

    @NonNull
    @Override
    public CartItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_cart_items, parent, false);
        return new CartItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemsViewHolder holder, int position) {
        final CartItemsModel cartItemsModel = this.cart_items_list.get(position);

        Glide.with(holder.cart_item_image.getContext())
                .load(cartItemsModel.getItemsImage())
                .into(holder.cart_item_image);

        holder.cart_item_name.setText(cartItemsModel.getItemsName());
        holder.cart_item_price.setText(cartItemsModel.getItemsPrice());
        holder.cart_item_quantity.setText(cartItemsModel.getItemsQuantity());
        holder.cart_item_total_price.setText(String.valueOf(Integer.parseInt(cartItemsModel.getItemsPrice()) * cartItemsModel.getItemCount()));
        holder.item_count.setText(String.valueOf(cartItemsModel.getItemCount()));

        holder.add_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < cart_items_list.size(); i++) {
                    CartItemsModel item = cart_items_list.get(i);
                    if (cartItemsModel.getVariantID() == null) {
                        if (cartItemsModel.getItemID().equals(item.getItemID())) {
                            cart_items_list.get(i).setItemCount(item.getItemCount() + 1);
                            holder.item_count.setText("" + cart_items_list.get(i).getItemCount());
                            holder.cart_item_price.setText(cart_items_list.get(i).getItemsPrice());
                            holder.cart_item_total_price.setText(String.valueOf(Integer.parseInt(cart_items_list.get(i).getItemsPrice()) * cart_items_list.get(i).getItemCount()));
                        }
                    } else {
                        if (cartItemsModel.getItemID().equals(item.getItemID()) && cartItemsModel.getVariantID().equals(item.getVariantID())) {

                            if (cartItemsModel.getItemID().equals(item.getItemID())) {
                                cart_items_list.get(i).setItemCount(item.getItemCount() + 1);
                                holder.item_count.setText("" + cart_items_list.get(i).getItemCount());
                                holder.cart_item_price.setText(cart_items_list.get(i).getItemsPrice());
                                holder.cart_item_total_price.setText(String.valueOf(Integer.parseInt(cart_items_list.get(i).getItemsPrice()) * cart_items_list.get(i).getItemCount()));
                            }

                        }
                    }
                }

                double itemsAmt = 0, taxAmt = 0, totalAmt = 0;
                for (int i = 0; i < cart_items_list.size(); i++) {
                    itemsAmt = itemsAmt + Integer.parseInt(cart_items_list.get(i).getItemsPrice()) * cart_items_list.get(i).getItemCount();
                }
                taxAmt = 0.02 * itemsAmt;
                totalAmt = itemsAmt + taxAmt;


                CartActivity.items_amount.setText(String.format("%.2f", itemsAmt));
                CartActivity.tax_amount.setText(String.format("%.2f", taxAmt));
                CartActivity.total_amount.setText(String.format("%.2f", totalAmt));

                for (int i = 0; i < ShopItemsCategoryAdapter.added_items.size(); i++) {
                    CartItemsModel item = ShopItemsCategoryAdapter.added_items.get(i);
                    if (cartItemsModel.getVariantID() == null) {
                        if (cartItemsModel.getItemID().equals(item.getItemID())) {
                            ShopItemsCategoryAdapter.added_items.get(i).setItemCount(item.getItemCount() + 1);
                        }
                    } else {
                        if (cartItemsModel.getItemID().equals(item.getItemID()) && cartItemsModel.getVariantID().equals(item.getVariantID())) {

                            if (cartItemsModel.getItemID().equals(item.getItemID())) {
                                ShopItemsCategoryAdapter.added_items.get(i).setItemCount(item.getItemCount() + 1);
                            }

                        }
                    }
                }
            }
        });


        holder.remove_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < cart_items_list.size(); i++) {
                    CartItemsModel item = cart_items_list.get(i);
                    if (cartItemsModel.getVariantID() == null) {
                        if (cartItemsModel.getItemID().equals(item.getItemID())) {
                            if (cartItemsModel.getItemCount() > 1) {
                                cart_items_list.get(i).setItemCount(item.getItemCount() - 1);
                                holder.item_count.setText("" + cart_items_list.get(i).getItemCount());
                                holder.cart_item_price.setText(cart_items_list.get(i).getItemsPrice());
                                holder.cart_item_total_price.setText(String.valueOf(Integer.parseInt(cart_items_list.get(i).getItemsPrice()) * cart_items_list.get(i).getItemCount()));
                            } else {
                                cart_items_list.remove(i);
                                notifyDataSetChanged();
                            }
                        }
                    } else {
                        if (cartItemsModel.getItemID().equals(item.getItemID()) && cartItemsModel.getVariantID().equals(item.getVariantID())) {

                            if (cartItemsModel.getItemID().equals(item.getItemID())) {
                                if (cartItemsModel.getItemCount() > 1) {
                                    cart_items_list.get(i).setItemCount(item.getItemCount() - 1);
                                    holder.item_count.setText("" + cart_items_list.get(i).getItemCount());
                                    holder.cart_item_price.setText(cart_items_list.get(i).getItemsPrice());
                                    holder.cart_item_total_price.setText(String.valueOf(Integer.parseInt(cart_items_list.get(i).getItemsPrice()) * cart_items_list.get(i).getItemCount()));
                                } else {
                                    cart_items_list.remove(i);
                                    notifyDataSetChanged();

                                }
                            }

                        }
                    }
                }

                double itemsAmt = 0, taxAmt = 0, totalAmt = 0;
                for (int i = 0; i < cart_items_list.size(); i++) {
                    itemsAmt = itemsAmt + Integer.parseInt(cart_items_list.get(i).getItemsPrice()) * cart_items_list.get(i).getItemCount();
                }
                taxAmt = 0.02 * itemsAmt;
                totalAmt = itemsAmt + taxAmt;

                CartActivity.items_amount.setText(String.format("%.2f", itemsAmt));
                CartActivity.tax_amount.setText(String.format("%.2f", taxAmt));
                CartActivity.total_amount.setText(String.format("%.2f", totalAmt));

                for (int i = 0; i < ShopItemsCategoryAdapter.added_items.size(); i++) {
                    CartItemsModel item = ShopItemsCategoryAdapter.added_items.get(i);
                    if (cartItemsModel.getVariantID() == null) {
                        if (cartItemsModel.getItemID().equals(item.getItemID())) {
                            if (cartItemsModel.getItemCount() > 1) {
                                ShopItemsCategoryAdapter.added_items.get(i).setItemCount(item.getItemCount() - 1);
                            } else {
                                ShopItemsCategoryAdapter.added_items.remove(i);
                            }
                        }
                    } else {
                        if (cartItemsModel.getItemID().equals(item.getItemID()) && cartItemsModel.getVariantID().equals(item.getVariantID())) {

                            if (cartItemsModel.getItemID().equals(item.getItemID())) {
                                if (cartItemsModel.getItemCount() > 1) {
                                    ShopItemsCategoryAdapter.added_items.get(i).setItemCount(item.getItemCount() - 1);
                                } else {
                                    ShopItemsCategoryAdapter.added_items.remove(i);

                                }
                            }

                        }
                    }
                }


            }
        });


    }

    @Override
    public int getItemCount() {
        return cart_items_list.size();
    }

    public class CartItemsViewHolder extends RecyclerView.ViewHolder {
        ImageView cart_item_image;
        TextView cart_item_name;
        TextView cart_item_price;
        TextView cart_item_quantity;
        TextView cart_item_total_price;
        TextView item_count;
        TextView add_one;
        TextView remove_one;
//        TextView items_amount;
//        TextView tax_amount;
//        TextView total_amount;

        public CartItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            cart_item_image = itemView.findViewById(R.id.cart_item_image);
            cart_item_name = itemView.findViewById(R.id.cart_item_name);
            cart_item_price = itemView.findViewById(R.id.cart_item_price);
            cart_item_quantity = itemView.findViewById(R.id.cart_item_quantity);
            cart_item_total_price = itemView.findViewById(R.id.cart_item_total_price);
            item_count = itemView.findViewById(R.id.item_count);
            add_one = itemView.findViewById(R.id.add_one);
            remove_one = itemView.findViewById(R.id.remove_one);
//            items_amount=itemView.findViewById(R.id.items_amount1);
//            tax_amount=itemView.findViewById(R.id.tax_amount1);
//            total_amount=itemView.findViewById(R.id.total_amount1);
        }
    }
}
