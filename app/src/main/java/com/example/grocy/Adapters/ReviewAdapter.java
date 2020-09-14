package com.example.grocy.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.grocy.Models.ReviewModel;
import com.example.grocy.R;
import com.example.grocy.activities.AddReviewDetailActivity;
import com.example.grocy.helper.ExpandableTextView;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    Context context;
    ArrayList<ReviewModel> review_list;

    public ReviewAdapter(FragmentActivity context, ArrayList<ReviewModel> review_list) {
        this.context = context;
        this.review_list = review_list;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_items_frag_review, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        ReviewModel reviewModel = review_list.get(position);
        holder.frag_review_details.setText(reviewModel.getDetailedReview());
        holder.review_rating.setText(String.valueOf(reviewModel.getRating()));
        holder.no_of_likes.setText("" + reviewModel.getNumberOfLikes());
        holder.no_of_comments.setText("" + reviewModel.getNumberOfComments());
        if (reviewModel.getReviewImage() != null) {
            holder.cardViewReviewImage.setVisibility(View.VISIBLE);
            Glide.with(holder.image_review.getContext())
                    .load(reviewModel.getReviewImage())
                    .into(holder.image_review);
        }


        holder.frag_review_details.setOnClickListener(v -> {
            Intent intent = new Intent(context, AddReviewDetailActivity.class);
            intent.putExtra("review_data", reviewModel);
            context.startActivity(intent);
        });

        holder.textViewReviewShopAddress.setText(reviewModel.getShopAddress());
        holder.textViewReviewShopName.setText(reviewModel.getShopName());
        Glide.with(holder.imageViewReviewShopImage.getContext()).load(reviewModel.getShopImage()).into(holder.imageViewReviewShopImage);

    }

    @Override
    public int getItemCount() {
        return review_list.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        ExpandableTextView frag_review_details;
        ImageView image_review;
        TextView no_of_likes;
        TextView no_of_comments;
        TextView review_rating;
        ImageView imageViewReviewShopImage;
        TextView textViewReviewShopName, textViewReviewShopAddress;
        CardView cardViewReviewImage;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            frag_review_details = itemView.findViewById(R.id.frag_review_details);
            image_review = itemView.findViewById(R.id.image_review);
            no_of_likes = itemView.findViewById(R.id.no_of_likes);
            no_of_comments = itemView.findViewById(R.id.no_of_comments);
            review_rating = itemView.findViewById(R.id.review_rating);
            cardViewReviewImage = itemView.findViewById(R.id.cardImageReview);
            imageViewReviewShopImage = itemView.findViewById(R.id.image_shopReviewPhoto);
            textViewReviewShopName = itemView.findViewById(R.id.text_shopReviewName);
            textViewReviewShopAddress = itemView.findViewById(R.id.text_shop_ReviewAddress);
        }
    }
}
