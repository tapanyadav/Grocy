package com.example.grocy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.example.grocy.Models.ItemVariantsModel;
import com.example.grocy.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemVariantsAdapter extends RecyclerView.Adapter<ItemVariantsAdapter.ItemVariantsViewHolder> {

    Context context;
    static ArrayList<ItemVariantsModel> itemVariantsModelArrayList;
    static int lastCheckedPosition = 0;

    public ItemVariantsAdapter(Context context, ArrayList<ItemVariantsModel> itemVariantsModelArrayList) {
        this.context = context;
        this.itemVariantsModelArrayList = itemVariantsModelArrayList;
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
        holder.itemVariantQP.setText(itemVariantsModel.getItemPrice() + "\n" + itemVariantsModel.getItemQuantity());
        holder.itemVariantQP.setChecked(position == lastCheckedPosition);

    }

    @Override
    public int getItemCount() {
        return itemVariantsModelArrayList.size();
    }

    public class ItemVariantsViewHolder extends RecyclerView.ViewHolder {
        RadioButton itemVariantQP;

        public ItemVariantsViewHolder(@NonNull View itemView) {
            super(itemView);
            itemVariantQP = itemView.findViewById(R.id.itemVariantQP);
            itemVariantQP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int copyOfLastCheckedPosition = lastCheckedPosition;
                    lastCheckedPosition = getAdapterPosition();
                    notifyItemChanged(copyOfLastCheckedPosition);
                    notifyItemChanged(lastCheckedPosition);

                }
            });
        }
    }
}
