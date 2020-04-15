package com.example.grocy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.grocy.Models.CategoriesModel;
import com.example.grocy.R;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.MyViewHolder>{

    private LayoutInflater inflater;
    private ArrayList<CategoriesModel> imageModelArrayList;

    public CategoriesAdapter(Context ctx, ArrayList<CategoriesModel> imageModelArrayList){

        inflater = LayoutInflater.from(ctx);
        this.imageModelArrayList = imageModelArrayList;
    }

    @Override
    public CategoriesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.recycler_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(CategoriesAdapter.MyViewHolder holder, int position) {

        holder.iv.setImageResource(imageModelArrayList.get(position).getImage_drawable());
        holder.name.setText(imageModelArrayList.get(position).getText_cat());
    }

    @Override
    public int getItemCount() {

        return imageModelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView iv;
        TextView name;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv_categories_recycler);
            name=itemView.findViewById(R.id.name_cat);
        }

    }
}
