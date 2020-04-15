package com.example.grocy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.grocy.Models.HorizontalModel;
import com.example.grocy.Models.VerticalModel;
import com.example.grocy.R;

import java.util.ArrayList;
//VerticalAdapter  VerticalModel
public class VerticalAdapter extends RecyclerView.Adapter<VerticalAdapter.MyViewHolder>{

    private LayoutInflater inflater;
    private ArrayList<VerticalModel> imageModelArrayList;

    public VerticalAdapter(Context ctx, ArrayList<VerticalModel> imageModelArrayList){

        inflater = LayoutInflater.from(ctx);
        this.imageModelArrayList = imageModelArrayList;
    }

    @Override
    public VerticalAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_vertical_recycler, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(VerticalAdapter.MyViewHolder holder, int position) {

       VerticalModel verticalModel=imageModelArrayList.get(position);
       String title=verticalModel.getTitle();
       ArrayList<HorizontalModel> singleItem=verticalModel.getArrayList();

       holder.title.setText(title);

       //holder.title.setText(imageModelArrayList.get(position).getTitle());

    }

    @Override
    public int getItemCount() {

        return imageModelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        RecyclerView recyclerView;

        public MyViewHolder(View itemView) {
            super(itemView);
            recyclerView=itemView.findViewById(R.id.item_vertical_recycler);
            title=itemView.findViewById(R.id.tv_title);
        }

    }
}

