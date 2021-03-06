package com.example.grocy.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.grocy.Models.StoresAddedModel;
import com.example.grocy.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class StoresAddedAdapter extends FirestoreRecyclerAdapter<StoresAddedModel, StoresAddedAdapter.MyViewHolder> {

    public StoresAddedAdapter(@NonNull FirestoreRecyclerOptions<StoresAddedModel> options) {
        super(options);
    }

//    Context context;
//    ArrayList<StoresAddedModel> store_list;

//    public StoresAddedAdapter(Context context, ArrayList<StoresAddedModel> store_list) {
//        this.context = context;
//        this.store_list = store_list;
//    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_requests_stores, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull StoresAddedModel model) {
        Glide.with(holder.imageViewStore.getContext()).load(model.getStoreImage()).placeholder(R.drawable.user_profile).into(holder.imageViewStore);
        holder.textViewStoreName.setText(model.getStoreName());
        holder.textViewRequestStatus.setText(model.getStoreAddStatus());
        holder.textViewStoreLocation.setText(model.getStoreLocation());
    }

//    @Override
//    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        StoresAddedModel model = store_list.get(position);
//        Glide.with(holder.imageViewStore.getContext()).load(model.getStoreImage()).into(holder.imageViewStore);
//        holder.textViewStoreName.setText(model.getStoreName());
//        holder.textViewRequestStatus.setText(model.getStoreAddStatus());
//        holder.textViewStoreLocation.setText(model.getStoreLocation());
//    }

//    @Override
//    public int getItemCount() {
//        return store_list.size();
//    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewStoreName, textViewStoreLocation, textViewRequestStatus;
        ImageView imageViewStore;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewStoreName = itemView.findViewById(R.id.addedStoreName);
            textViewStoreLocation = itemView.findViewById(R.id.addedStoreLocation);
            textViewRequestStatus = itemView.findViewById(R.id.addedStoreStatus);
            imageViewStore = itemView.findViewById(R.id.addedImageStore);
        }
    }
}
