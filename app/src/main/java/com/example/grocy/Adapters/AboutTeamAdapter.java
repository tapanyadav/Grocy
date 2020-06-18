package com.example.grocy.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.grocy.Models.AboutTeamModel;
import com.example.grocy.R;

import java.util.List;

public class AboutTeamAdapter extends RecyclerView.Adapter<AboutTeamAdapter.TeamViewHolder> {

    private List<AboutTeamModel> aboutTeamModelList;
    private ViewPager2 viewPager2;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            aboutTeamModelList.addAll(aboutTeamModelList);
            notifyDataSetChanged();
        }
    };

    public AboutTeamAdapter(List<AboutTeamModel> aboutTeamModelList, ViewPager2 viewPager2) {
        this.aboutTeamModelList = aboutTeamModelList;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pager_item_team, parent, false);
        return new TeamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamViewHolder holder, int position) {
        holder.setItems(aboutTeamModelList.get(position));
        if (position == aboutTeamModelList.size() - 2) {
            viewPager2.post(runnable);
        }

    }

    @Override
    public int getItemCount() {
        return aboutTeamModelList.size();
    }

    public static class TeamViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewTeam, imageViewBack;
        TextView textViewName, textViewDesignation;

        public TeamViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewBack = itemView.findViewById(R.id.back_about_color);
            imageViewTeam = itemView.findViewById(R.id.aboutTeamImage);
            textViewName = itemView.findViewById(R.id.aboutTeamName);
            textViewDesignation = itemView.findViewById(R.id.aboutTeamDesignation);


        }

        void setItems(AboutTeamModel aboutTeamModel) {

            imageViewTeam.setImageResource(aboutTeamModel.getImageTeam());
            imageViewBack.setImageResource(aboutTeamModel.getImageBack());
            textViewName.setText(aboutTeamModel.getNameTeam());
            textViewDesignation.setText(aboutTeamModel.getTeamDesignation());
        }
    }
}
