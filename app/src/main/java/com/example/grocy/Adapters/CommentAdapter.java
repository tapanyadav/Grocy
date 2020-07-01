package com.example.grocy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.grocy.Models.CommentModel;
import com.example.grocy.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    Context context;
    ArrayList<CommentModel> comment_list;

    public CommentAdapter(Context context, ArrayList<CommentModel> comment_list) {
        this.context = context;
        this.comment_list = comment_list;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        CommentModel commentModel = comment_list.get(position);

        holder.comment_user_name.setText(commentModel.getCommentUserName());
        Glide.with(holder.comment_image.getContext())
                .load(commentModel.getCommentUserImage())
                .into(holder.comment_image);
        holder.commentData.setText(commentModel.getCommentData());
        holder.comment_time.setText(commentModel.getCommentTime().toString());

    }

    @Override
    public int getItemCount() {
        return comment_list.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        ImageView comment_image;
        TextView comment_user_name;
        TextView commentData;
        TextView comment_time;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            comment_image = itemView.findViewById(R.id.comment_image);
            comment_user_name = itemView.findViewById(R.id.comment_user_name);
            commentData = itemView.findViewById(R.id.commentData);
            comment_time = itemView.findViewById(R.id.comment_time);
        }
    }
}
