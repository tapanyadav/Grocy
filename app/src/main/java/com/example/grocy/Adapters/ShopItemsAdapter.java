package com.example.grocy.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.grocy.Models.CartItemsModel;
import com.example.grocy.Models.ItemVariantsModel;
import com.example.grocy.Models.ShopItemsCategoryModel;
import com.example.grocy.Models.ShopItemsModel;
import com.example.grocy.R;
import com.example.grocy.activities.CartActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ShopItemsAdapter extends RecyclerView.Adapter<ShopItemsAdapter.ShopItemsViewHolder> {

    Context context;
    ArrayList<ShopItemsModel> singleItem;
    ArrayList<ShopItemsCategoryModel> shop_items_list;
    int parent_position;

    public ShopItemsAdapter(Context context, ArrayList<ShopItemsModel> singleItem, ArrayList<ShopItemsCategoryModel> shop_items_list, int parent_position) {
        super();
        this.context = context;
        this.singleItem = singleItem;
        this.shop_items_list = shop_items_list;
        this.parent_position = parent_position;
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

        for (int i = 0; i < ShopItemsCategoryAdapter.added_items.size(); i++) {
            CartItemsModel item = ShopItemsCategoryAdapter.added_items.get(i);
            if (item.getItemID().equals(shopItemsModel.getItemID())) {
                if (shopItemsModel.getItemVariants() == null) {
                    holder.linearLayoutItemsAdd.setVisibility(View.VISIBLE);
                    holder.buttonAdd.setVisibility(View.INVISIBLE);
                    holder.item_count.setText("" + item.getItemCount());
                } else {
                    int variants_count = 0;
                    for (int j = 0; j < ShopItemsCategoryAdapter.added_items.size(); j++) {
                        if (shopItemsModel.getItemID().equals(ShopItemsCategoryAdapter.added_items.get(j).getItemID())) {
                            variants_count = variants_count + ShopItemsCategoryAdapter.added_items.get(j).getItemCount();
                        }
                    }
                    if (variants_count > 0) {
                        holder.linearLayoutItemsAdd.setVisibility(View.VISIBLE);
                        holder.buttonAdd.setVisibility(View.INVISIBLE);
                        holder.item_count.setText("" + variants_count);
                    }
                }
            }
        }

        holder.add_one.setOnClickListener(v -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
            bottomSheetDialog.setContentView(R.layout.bottom_sheet_items_quantity);
            bottomSheetDialog.show();
            bottomSheetDialog.setCanceledOnTouchOutside(false);

            setUpItemVariantsRecycler(bottomSheetDialog, position);
            if (shopItemsModel.getItemVariants() == null) {
                for (int i = 0; i < ShopItemsCategoryAdapter.added_items.size(); i++) {
                    CartItemsModel item = ShopItemsCategoryAdapter.added_items.get(i);
                    if (item.getItemID().equals(shopItemsModel.getItemID()) && shopItemsModel.getItemVariants() == null) {
                        holder.item_count.setText("" + item.getItemCount());
                    }
                }
            }
//            Button buttonCart = bottomSheetDialog.findViewById(R.id.buttonCartShow);
//            assert buttonCart != null;
//            buttonCart.setOnClickListener(v1 -> {
//                openBottomSheetCart(bottomSheetDialog, shopItemsModel);
//            });

            ImageView close_image_view = bottomSheetDialog.findViewById(R.id.close_image_view);
            close_image_view.setOnClickListener(v1 -> {
                bottomSheetDialog.dismiss();
                int price = 0;
                for (int i = 0; i < ShopItemsCategoryAdapter.added_items.size(); i++) {
                    price = price + Integer.parseInt(ShopItemsCategoryAdapter.added_items.get(i).getItemsPrice()) * ShopItemsCategoryAdapter.added_items.get(i).getItemCount();
                }
                int variants_count = 0;
                for (int i = 0; i < ShopItemsCategoryAdapter.added_items.size(); i++) {
                    if (shopItemsModel.getItemID().equals(ShopItemsCategoryAdapter.added_items.get(i).getItemID())) {
                        variants_count = variants_count + ShopItemsCategoryAdapter.added_items.get(i).getItemCount();
                    }
                }
                if (variants_count > 0) {
                    holder.linearLayoutItemsAdd.setVisibility(View.VISIBLE);
                    holder.buttonAdd.setVisibility(View.INVISIBLE);
                    holder.item_count.setText("" + variants_count);
                }
                ShopItemsCategoryAdapter.price_of_currentlyAddedItems = price;
                BottomSheetDialog bottomSheetDialog1 = new BottomSheetDialog(context);
                bottomSheetDialog1.setContentView(R.layout.bottom_sheet_cart);
                bottomSheetDialog1.show();
                bottomSheetDialog1.setCanceledOnTouchOutside(true);
//                ShopItemsCategoryAdapter.no_of_currentlyAddedItems = ShopItemsCategoryAdapter.no_of_currentlyAddedItems - 1;


                TextView currentlyAddedItems = bottomSheetDialog1.findViewById(R.id.currentlyAddedItems);
                TextView currentlyAddedItemsPrice = bottomSheetDialog1.findViewById(R.id.currentlyAddedItemsPrice);

                currentlyAddedItems.setText(String.valueOf(ShopItemsCategoryAdapter.no_of_currentlyAddedItems));
                currentlyAddedItemsPrice.setText(String.valueOf(ShopItemsCategoryAdapter.price_of_currentlyAddedItems));


                TextView textViewCartShow = bottomSheetDialog1.findViewById(R.id.textViewCart);
                textViewCartShow.setOnClickListener(v2 -> {
                    bottomSheetDialog1.dismiss();
                    openCart();
                });
            });


        });

        holder.remove_one.setOnClickListener(v -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
            bottomSheetDialog.setContentView(R.layout.bottom_sheet_items_quantity);
//            setUpItemVariantsRecycler(bottomSheetDialog, position);
            if (shopItemsModel.getItemVariants() == null) {
                for (int i = 0; i < ShopItemsCategoryAdapter.added_items.size(); i++) {
                    CartItemsModel item = ShopItemsCategoryAdapter.added_items.get(i);
                    int count = ShopItemsCategoryAdapter.added_items.get(i).getItemCount();
                    if (item.getItemID().equals(shopItemsModel.getItemID()) && shopItemsModel.getItemVariants() == null) {
                        if (count == 1) {
                            ShopItemsCategoryAdapter.added_items.remove(i);
                            holder.linearLayoutItemsAdd.setVisibility(View.INVISIBLE);
                            holder.buttonAdd.setVisibility(View.VISIBLE);
                        } else {
                            item.setItemCount(count - 1);
                            holder.item_count.setText("" + item.getItemCount());
                        }
                    }
                }
                int price = 0;
                for (int i = 0; i < ShopItemsCategoryAdapter.added_items.size(); i++) {
                    price = price + Integer.parseInt(ShopItemsCategoryAdapter.added_items.get(i).getItemsPrice()) * ShopItemsCategoryAdapter.added_items.get(i).getItemCount();
                }
                ShopItemsCategoryAdapter.price_of_currentlyAddedItems = price;
                BottomSheetDialog bottomSheetDialog1 = new BottomSheetDialog(context);
                bottomSheetDialog1.setContentView(R.layout.bottom_sheet_cart);
                bottomSheetDialog1.show();
                bottomSheetDialog1.setCanceledOnTouchOutside(true);
                ShopItemsCategoryAdapter.no_of_currentlyAddedItems = ShopItemsCategoryAdapter.no_of_currentlyAddedItems - 1;


                TextView currentlyAddedItems = bottomSheetDialog1.findViewById(R.id.currentlyAddedItems);
                TextView currentlyAddedItemsPrice = bottomSheetDialog1.findViewById(R.id.currentlyAddedItemsPrice);

                currentlyAddedItems.setText(String.valueOf(ShopItemsCategoryAdapter.no_of_currentlyAddedItems));
                currentlyAddedItemsPrice.setText(String.valueOf(ShopItemsCategoryAdapter.price_of_currentlyAddedItems));


                TextView textViewCartShow = bottomSheetDialog1.findViewById(R.id.textViewCart);
                textViewCartShow.setOnClickListener(v2 -> {
                    bottomSheetDialog1.dismiss();
                    openCart();
                });
            } else {
                bottomSheetDialog.show();
                bottomSheetDialog.setCanceledOnTouchOutside(false);
                HashMap<String, ItemVariantsModel> itemVariants;
                itemVariants = shopItemsModel.getItemVariants();
                setAdapter(bottomSheetDialog, itemVariants, shopItemsModel);
            }

            ImageView close_image_view = bottomSheetDialog.findViewById(R.id.close_image_view);
            close_image_view.setOnClickListener(v1 -> {
                bottomSheetDialog.dismiss();
                int price = 0;
                for (int i = 0; i < ShopItemsCategoryAdapter.added_items.size(); i++) {
                    price = price + Integer.parseInt(ShopItemsCategoryAdapter.added_items.get(i).getItemsPrice()) * ShopItemsCategoryAdapter.added_items.get(i).getItemCount();
                }
                int variants_count = 0;
                for (int i = 0; i < ShopItemsCategoryAdapter.added_items.size(); i++) {
                    if (shopItemsModel.getItemID().equals(ShopItemsCategoryAdapter.added_items.get(i).getItemID())) {
                        variants_count = variants_count + ShopItemsCategoryAdapter.added_items.get(i).getItemCount();
                    }
                }
                if (variants_count > 0) {
                    holder.linearLayoutItemsAdd.setVisibility(View.VISIBLE);
                    holder.buttonAdd.setVisibility(View.INVISIBLE);
                    holder.item_count.setText("" + variants_count);
                } else {

                    holder.linearLayoutItemsAdd.setVisibility(View.INVISIBLE);
                    holder.buttonAdd.setVisibility(View.VISIBLE);
                    holder.item_count.setText("" + 0);
                }
                ShopItemsCategoryAdapter.price_of_currentlyAddedItems = price;
                BottomSheetDialog bottomSheetDialog1 = new BottomSheetDialog(context);
                bottomSheetDialog1.setContentView(R.layout.bottom_sheet_cart);
                bottomSheetDialog1.show();
                bottomSheetDialog1.setCanceledOnTouchOutside(true);
//                ShopItemsCategoryAdapter.no_of_currentlyAddedItems = ShopItemsCategoryAdapter.no_of_currentlyAddedItems - 1;


                TextView currentlyAddedItems = bottomSheetDialog1.findViewById(R.id.currentlyAddedItems);
                TextView currentlyAddedItemsPrice = bottomSheetDialog1.findViewById(R.id.currentlyAddedItemsPrice);

                currentlyAddedItems.setText(String.valueOf(ShopItemsCategoryAdapter.no_of_currentlyAddedItems));
                currentlyAddedItemsPrice.setText(String.valueOf(ShopItemsCategoryAdapter.price_of_currentlyAddedItems));


                TextView textViewCartShow = bottomSheetDialog1.findViewById(R.id.textViewCart);
                textViewCartShow.setOnClickListener(v2 -> {
                    bottomSheetDialog1.dismiss();
                    openCart();
                });
            });

        });


        holder.buttonAdd.setOnClickListener(v -> {
            if (ShopItemsCategoryAdapter.added_items.size() != 0 && !ShopItemsCategoryAdapter.added_items.get(0).getShopId().equals(shopItemsModel.getShopID())) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("You can't add items from different shops!").setPositiveButton("Go to Cart", ((dialog, which) -> {
                    openCart();
                }))
                        .setNegativeButton("Empty Cart!", (dialog, which) -> {
                            ShopItemsCategoryAdapter.added_items = new ArrayList<>();
                        });
                AlertDialog alert = builder.create();
                alert.show();
                ShopItemsCategoryAdapter.no_of_currentlyAddedItems = 0;
                ShopItemsCategoryAdapter.price_of_currentlyAddedItems = 0;
                return;
            }
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
            bottomSheetDialog.setContentView(R.layout.bottom_sheet_items_quantity);
            bottomSheetDialog.show();
            bottomSheetDialog.setCanceledOnTouchOutside(false);
            setUpItemVariantsRecycler(bottomSheetDialog, position);

            if (shopItemsModel.getItemVariants() == null) {
                holder.linearLayoutItemsAdd.setVisibility(View.VISIBLE);
                holder.buttonAdd.setVisibility(View.INVISIBLE);
            }
            ImageView close_image_view = bottomSheetDialog.findViewById(R.id.close_image_view);
            close_image_view.setOnClickListener(v1 -> {
                bottomSheetDialog.dismiss();
                int price = 0;
                for (int i = 0; i < ShopItemsCategoryAdapter.added_items.size(); i++) {
                    price = price + Integer.parseInt(ShopItemsCategoryAdapter.added_items.get(i).getItemsPrice()) * ShopItemsCategoryAdapter.added_items.get(i).getItemCount();
                }
                int variants_count = 0;
                for (int i = 0; i < ShopItemsCategoryAdapter.added_items.size(); i++) {
                    if (shopItemsModel.getItemID().equals(ShopItemsCategoryAdapter.added_items.get(i).getItemID())) {
                        variants_count = variants_count + ShopItemsCategoryAdapter.added_items.get(i).getItemCount();
                    }
                }
                if (variants_count > 0) {
                    holder.linearLayoutItemsAdd.setVisibility(View.VISIBLE);
                    holder.buttonAdd.setVisibility(View.INVISIBLE);
                    holder.item_count.setText("" + variants_count);
                }
                ShopItemsCategoryAdapter.price_of_currentlyAddedItems = price;
                BottomSheetDialog bottomSheetDialog1 = new BottomSheetDialog(context);
                bottomSheetDialog1.setContentView(R.layout.bottom_sheet_cart);
                bottomSheetDialog1.show();
                bottomSheetDialog1.setCanceledOnTouchOutside(true);
//                ShopItemsCategoryAdapter.no_of_currentlyAddedItems = ShopItemsCategoryAdapter.no_of_currentlyAddedItems - 1;


                TextView currentlyAddedItems = bottomSheetDialog1.findViewById(R.id.currentlyAddedItems);
                TextView currentlyAddedItemsPrice = bottomSheetDialog1.findViewById(R.id.currentlyAddedItemsPrice);

                currentlyAddedItems.setText(String.valueOf(ShopItemsCategoryAdapter.no_of_currentlyAddedItems));
                currentlyAddedItemsPrice.setText(String.valueOf(ShopItemsCategoryAdapter.price_of_currentlyAddedItems));


                TextView textViewCartShow = bottomSheetDialog1.findViewById(R.id.textViewCart);
                textViewCartShow.setOnClickListener(v2 -> {
                    bottomSheetDialog1.dismiss();
                    openCart();
                });
            });

//            Button buttonCart = bottomSheetDialog.findViewById(R.id.buttonCartShow);
//            assert buttonCart != null;
//            buttonCart.setOnClickListener(v1 -> {
//                openBottomSheetCart(bottomSheetDialog, shopItemsModel);
//            });
        });


    }


    private void openBottomSheetCart(BottomSheetDialog bottomSheetDialog, ShopItemsModel shopItemsModel) {
        bottomSheetDialog.dismiss();
        BottomSheetDialog bottomSheetDialog1 = new BottomSheetDialog(context);
        bottomSheetDialog1.setContentView(R.layout.bottom_sheet_cart);


        System.out.println("--------------------------------------");
        System.out.println(shop_items_list.toString());
        for (int i = 0; i < shop_items_list.size(); i++) {
            System.out.println(shop_items_list.get(i).getShop_items_list().size());
        }
        System.out.println("--------------------------------------");

        bottomSheetDialog1.show();
        boolean found = false;
        for (int i = 0; i < ShopItemsCategoryAdapter.added_items.size(); i++) {
            CartItemsModel item = ShopItemsCategoryAdapter.added_items.get(i);
            if (item.getItemID().equals(shopItemsModel.getItemID()) && shopItemsModel.getItemVariants() == null) {
                int count = item.getItemCount();
                ShopItemsCategoryAdapter.added_items.get(i).setItemCount(count + 1);
                found = true;
            }
            if (item.getItemID().equals(shopItemsModel.getItemID()) && shopItemsModel.getItemVariants() != null) {
                if (item.getItemsPrice().equals(ItemVariantsAdapter.itemVariantsModelArrayList.get(ItemVariantsAdapter.lastCheckedPosition).getItemPrice())
                        && item.getItemsQuantity().equals(ItemVariantsAdapter.itemVariantsModelArrayList.get(ItemVariantsAdapter.lastCheckedPosition).getItemQuantity())) {
                    int count = item.getItemCount();
                    ShopItemsCategoryAdapter.added_items.get(i).setItemCount(count + 1);
                    found = true;
                }
            }
        }
        if (found == false) {
            CartItemsModel cartItemsModel = new CartItemsModel();
            cartItemsModel.setItemID(shopItemsModel.getItemID());
            cartItemsModel.setItemsName(shopItemsModel.getItemsProductName());
            cartItemsModel.setItemsImage(shopItemsModel.getItemsImage());
            cartItemsModel.setItemCount(shopItemsModel.getCount());
            cartItemsModel.setShopId(shopItemsModel.getShopID());

            if (shopItemsModel.getItemVariants() != null) {
                cartItemsModel.setItemsPrice(ItemVariantsAdapter.itemVariantsModelArrayList.get(ItemVariantsAdapter.lastCheckedPosition).getItemPrice());
                cartItemsModel.setItemsQuantity(ItemVariantsAdapter.itemVariantsModelArrayList.get(ItemVariantsAdapter.lastCheckedPosition).getItemQuantity());
            } else {
                cartItemsModel.setItemsQuantity(shopItemsModel.getItemsQuantity());
                cartItemsModel.setItemsPrice(shopItemsModel.getItemsPrice());
            }
            ShopItemsCategoryAdapter.added_items.add(cartItemsModel);
        }
        int price = 0;
        for (int i = 0; i < ShopItemsCategoryAdapter.added_items.size(); i++) {
            price = price + Integer.parseInt(ShopItemsCategoryAdapter.added_items.get(i).getItemsPrice()) * ShopItemsCategoryAdapter.added_items.get(i).getItemCount();
        }

        ShopItemsCategoryAdapter.price_of_currentlyAddedItems = price;
        ShopItemsCategoryAdapter.no_of_currentlyAddedItems = ShopItemsCategoryAdapter.no_of_currentlyAddedItems + 1;
        TextView currentlyAddedItems = bottomSheetDialog1.findViewById(R.id.currentlyAddedItems);
        TextView currentlyAddedItemsPrice = bottomSheetDialog1.findViewById(R.id.currentlyAddedItemsPrice);

        currentlyAddedItems.setText(String.valueOf(ShopItemsCategoryAdapter.no_of_currentlyAddedItems));
        currentlyAddedItemsPrice.setText(String.valueOf(ShopItemsCategoryAdapter.price_of_currentlyAddedItems));


        TextView textViewCartShow = bottomSheetDialog1.findViewById(R.id.textViewCart);
        textViewCartShow.setOnClickListener(v2 -> {
            bottomSheetDialog1.dismiss();
            openCart();
        });
    }

    private void setUpItemVariantsRecycler(BottomSheetDialog bottomSheetDialog, int position) {
        final ShopItemsModel shopItemsModel = singleItem.get(position);
        HashMap<String, ItemVariantsModel> itemVariants;
        itemVariants = shopItemsModel.getItemVariants();
        System.out.println("--------------------------");
        System.out.println(shopItemsModel.getItemID());
        System.out.println(shopItemsModel.getShopID());
        System.out.println("--------------------------");
        if (itemVariants != null) {
            setAdapter(bottomSheetDialog, itemVariants, shopItemsModel);
        } else {
            openBottomSheetCart(bottomSheetDialog, shopItemsModel);
            return;
        }
    }

    private void setAdapter(BottomSheetDialog bottomSheetDialog, HashMap<String, ItemVariantsModel> itemVariants, ShopItemsModel shopItemsModel) {

        TextView item_name = bottomSheetDialog.findViewById(R.id.item_name);
        item_name.setText(shopItemsModel.getItemsProductName());

        RecyclerView variantsRecyclerView;
        ArrayList<ItemVariantsModel> itemVariantsModelArrayList;
        ItemVariantsAdapter itemVariantsAdapter;
        itemVariantsModelArrayList = new ArrayList<>();
        variantsRecyclerView = bottomSheetDialog.findViewById(R.id.variantsRecyclerView);
        variantsRecyclerView.setHasFixedSize(false);

        variantsRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        itemVariantsAdapter = new ItemVariantsAdapter(context, itemVariantsModelArrayList, shopItemsModel);

        variantsRecyclerView.setAdapter(itemVariantsAdapter);
        for (Map.Entry mapElement : itemVariants.entrySet()) {
            String key = (String) mapElement.getKey();
            HashMap item = (HashMap) mapElement.getValue();
            ItemVariantsModel itemVariantsModel = new ItemVariantsModel();
            itemVariantsModel.setItemPrice((String) item.get("itemPrice"));
            itemVariantsModel.setItemQuantity((String) item.get("itemQuantity"));
            itemVariantsModel.setItemID((String) item.get("itemId"));
            itemVariantsModel.setVariant_id(key);
            itemVariantsModelArrayList.add(itemVariantsModel);
        }
        itemVariantsAdapter.notifyDataSetChanged();
    }


    private void openCart() {
        Intent intent = new Intent(context, CartActivity.class);
        intent.putExtra("added_items", ShopItemsCategoryAdapter.added_items);
        context.startActivity(intent);
        ((Activity) context).finish();
    }


    @Override
    public int getItemCount() {
        return this.singleItem.size();
    }


    public static class ShopItemsViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewItem;
        TextView textViewItemName, textViewItemDescription, textViewItemPrice, textViewItemQuantity;
        Button buttonAdd;
        LinearLayout linearLayoutItemsAdd;
        TextView add_one;
        TextView remove_one;
        TextView item_count;

        public ShopItemsViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewItem = itemView.findViewById(R.id.item_image);
            textViewItemName = itemView.findViewById(R.id.tv_name_item);
            textViewItemDescription = itemView.findViewById(R.id.tv_item_description);
            textViewItemPrice = itemView.findViewById(R.id.tv_item_price);
            textViewItemQuantity = itemView.findViewById(R.id.tv_quantity_item);
            buttonAdd = itemView.findViewById(R.id.materialButtonAdd);
            linearLayoutItemsAdd = itemView.findViewById(R.id.linearLayoutItems);
            add_one = itemView.findViewById(R.id.add_one);
            remove_one = itemView.findViewById(R.id.remove_one);
            item_count = itemView.findViewById(R.id.item_count);

        }
    }
}
