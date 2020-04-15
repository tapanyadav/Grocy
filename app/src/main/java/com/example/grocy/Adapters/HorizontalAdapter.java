package com.example.grocy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.grocy.Models.HorizontalModel;
import com.example.grocy.R;
import java.util.ArrayList;

//HorizontalModel

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.MyViewHolder>{

    private LayoutInflater inflater;
    private ArrayList<HorizontalModel> imageModelArrayList;

    public HorizontalAdapter(Context ctx, ArrayList<HorizontalModel> imageModelArrayList){

        inflater = LayoutInflater.from(ctx);
        this.imageModelArrayList = imageModelArrayList;

    }

    @Override
    public HorizontalAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_horizontal_recycler, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(HorizontalAdapter.MyViewHolder holder, int position) {

        holder.textViewStoreNameHorizontal.setText(imageModelArrayList.get(position).getText_horizontal_shopName());
        holder.imageView_horizontal.setImageResource(imageModelArrayList.get(position).getImageHorizontalDrawable());
        holder.imageView_background.setImageResource(imageModelArrayList.get(position).getImageBackgroundHorizontalDrawable());

    }

    @Override
    public int getItemCount() {

        return imageModelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textViewStoreNameHorizontal;
        ImageView imageView_horizontal,imageView_background;

        public MyViewHolder(View itemView) {
            super(itemView);

            textViewStoreNameHorizontal=itemView.findViewById(R.id.name_store_horizontal);
            imageView_background=itemView.findViewById(R.id.card_background_horizontal);
            imageView_horizontal=itemView.findViewById(R.id.iv_horizontal_recycler);
        }

    }
}
