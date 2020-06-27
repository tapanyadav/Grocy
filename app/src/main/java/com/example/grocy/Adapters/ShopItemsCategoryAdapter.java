package com.example.grocy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.grocy.Models.CartItemsModel;
import com.example.grocy.Models.ShopItemsCategoryModel;
import com.example.grocy.Models.ShopItemsModel;
import com.example.grocy.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ShopItemsCategoryAdapter extends RecyclerView.Adapter<ShopItemsCategoryAdapter.ShopItemsCategoryViewHolder> {

    Context context;
    ArrayList<ShopItemsCategoryModel> shop_items_list;
    static int price_of_currentlyAddedItems = 0;
    static int no_of_currentlyAddedItems = 0;
    static ArrayList<CartItemsModel> added_items = new ArrayList();

    public ShopItemsCategoryAdapter(Context context, ArrayList<ShopItemsCategoryModel> shop_items_list) {
        super();
        this.context = context;
        this.shop_items_list = shop_items_list;
    }


    @NonNull
    @Override
    public ShopItemsCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categories, parent, false);
        return new ShopItemsCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopItemsCategoryViewHolder holder, int position) {
        final ShopItemsCategoryModel shopItemsCategoryModel = this.shop_items_list.get(position);
        String item_category_name = shopItemsCategoryModel.getItem_category_name();
        ArrayList<ShopItemsModel> singleItem = shopItemsCategoryModel.getShop_items_list();

        holder.item_category_name.setText(item_category_name);

        final ShopItemsAdapter shopItemsAdapter = new ShopItemsAdapter(context, singleItem, shop_items_list, position);

        holder.shop_items_recycler.setHasFixedSize(true);
        holder.shop_items_recycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        holder.shop_items_recycler.setAdapter(shopItemsAdapter);

    }

    @Override
    public int getItemCount() {
        return this.shop_items_list.size();
    }


    public class ShopItemsCategoryViewHolder extends RecyclerView.ViewHolder {
        TextView item_category_name;
        RecyclerView shop_items_recycler;
        Button buttonAdd;

        public ShopItemsCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            item_category_name = itemView.findViewById(R.id.item_category_name);
            shop_items_recycler = itemView.findViewById(R.id.shop_items_recycler);
        }
    }
}
