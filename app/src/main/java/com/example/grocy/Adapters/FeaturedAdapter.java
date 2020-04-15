package com.example.grocy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.grocy.Models.FeaturedModel;
import com.example.grocy.R;

import java.util.ArrayList;

public class FeaturedAdapter extends RecyclerView.Adapter<FeaturedAdapter.MyViewHolder>{


    private LayoutInflater inflater;
    private ArrayList<FeaturedModel> imageModelFeaturedArrayList;

    public FeaturedAdapter(Context ctx, ArrayList<FeaturedModel> imageModelArrayList){

        inflater = LayoutInflater.from(ctx);
        this.imageModelFeaturedArrayList = imageModelArrayList;
    }

    @Override
    public FeaturedAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.recycler_item_featured, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(FeaturedAdapter.MyViewHolder holder, int position) {

        holder.iv.setImageResource(imageModelFeaturedArrayList.get(position).getImage_featured_drawable());
    }

    @Override
    public int getItemCount() {

        return imageModelFeaturedArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView iv;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv_featured_recycler);
        }

    }

}
