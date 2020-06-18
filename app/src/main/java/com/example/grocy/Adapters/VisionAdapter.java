package com.example.grocy.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.grocy.Models.VisionModel;
import com.example.grocy.R;

import java.util.List;

public class VisionAdapter extends RecyclerView.Adapter<VisionAdapter.VisionViewHolder> {

    private List<VisionModel> visionModelList;
    private ViewPager2 viewPager2;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            visionModelList.addAll(visionModelList);
            notifyDataSetChanged();
        }
    };

    public VisionAdapter(List<VisionModel> visionModelList, ViewPager2 viewPager2) {
        this.visionModelList = visionModelList;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public VisionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pager_item_vision, parent, false);
        return new VisionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VisionViewHolder holder, int position) {

        holder.setItems(visionModelList.get(position));
        if (position == visionModelList.size() - 2) {
            viewPager2.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return visionModelList.size();
    }

    public static class VisionViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewVision;
        TextView textViewVisionHead, textViewVisionData;

        public VisionViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewVision = itemView.findViewById(R.id.aboutTeamVisionImage);
            textViewVisionData = itemView.findViewById(R.id.aboutTeamVisionV);
            textViewVisionHead = itemView.findViewById(R.id.aboutTeamVisionH);
        }

        void setItems(VisionModel visionModel) {

            imageViewVision.setImageResource(visionModel.getImageVision());
            textViewVisionHead.setText(visionModel.getTextVisionHead());
            textViewVisionData.setText(visionModel.getTextVisionData());
        }
    }
}
