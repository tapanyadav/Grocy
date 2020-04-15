package com.example.grocy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.grocy.R;
import com.example.grocy.Models.ShopsModel;

import java.util.ArrayList;

//ShopsAdapter  ShopsModel
public class ShopsAdapter extends RecyclerView.Adapter<ShopsAdapter.MyViewHolder>{

    private LayoutInflater inflater;
    private ArrayList<ShopsModel> imageModelArrayList;

    public ShopsAdapter(Context ctx, ArrayList<ShopsModel> imageModelArrayList){

        inflater = LayoutInflater.from(ctx);
        this.imageModelArrayList = imageModelArrayList;
    }

    @Override
    public ShopsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.recycler_shop, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ShopsAdapter.MyViewHolder holder, int position) {

        holder.iv.setImageResource(imageModelArrayList.get(position).getImage_shop_drawable());
        holder.name.setText(imageModelArrayList.get(position).getText_shop_name());
        holder.type.setText(imageModelArrayList.get(position).getText_shop_type());
        holder.limit.setText(imageModelArrayList.get(position).getText_shop_limit());
        holder.off.setText(imageModelArrayList.get(position).getText_shop_off());
        holder.rating.setText(imageModelArrayList.get(position).getText_shop_rating());
    }

    @Override
    public int getItemCount() {

        return imageModelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView iv;
        TextView name,type,limit,off,rating;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv_shop_recycler);
            name=itemView.findViewById(R.id.tv_name_shop);
            type=itemView.findViewById(R.id.tv_type_shop);
            limit=itemView.findViewById(R.id.tv_limit_shop);
            off=itemView.findViewById(R.id.tv_off_shop);
            rating=itemView.findViewById(R.id.tv_rating_shop);
        }

    }
}
