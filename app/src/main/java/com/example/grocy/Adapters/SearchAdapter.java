package com.example.grocy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.grocy.Models.SearchShopModel;
import com.example.grocy.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    Context context;
    ArrayList<SearchShopModel> search_items;

    public SearchAdapter(Context context, ArrayList<SearchShopModel> search_items) {
        this.context = context;
        this.search_items = search_items;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_search_item, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        SearchShopModel searchShopModel = search_items.get(position);
        holder.search_shop_name.setText(searchShopModel.getShopName());
        holder.search_shop_off.setText(searchShopModel.getShopOff());
        holder.search_shop_address.setText(searchShopModel.getShopAddress());
        holder.search_shop_category.setText(searchShopModel.getShopCategory());
        holder.search_tv_limit.setText(searchShopModel.getShopLimits());
        holder.search_tv_type.setText(searchShopModel.getShopType());
    }

    public void filteredList(ArrayList<SearchShopModel> filteredList) {
        search_items = filteredList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return search_items.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {

        TextView search_shop_name;
        TextView search_tv_type;
        TextView search_tv_limit;
        TextView search_shop_address;
        TextView search_shop_category;
        TextView search_shop_off;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);

            search_shop_name = itemView.findViewById(R.id.search_shop_name);
            search_tv_type = itemView.findViewById(R.id.search_tv_type);
            search_tv_limit = itemView.findViewById(R.id.search_tv_limit);
            search_shop_address = itemView.findViewById(R.id.search_shop_address);
            search_shop_category = itemView.findViewById(R.id.search_shop_category);
            search_shop_off = itemView.findViewById(R.id.search_shop_off);

        }
    }
}
