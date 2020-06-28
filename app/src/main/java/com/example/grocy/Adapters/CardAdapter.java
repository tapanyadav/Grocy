package com.example.grocy.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.grocy.Models.CardModel;
import com.example.grocy.R;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    private List<CardModel> cardModelList;
    private ViewPager2 viewPager2;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            cardModelList.addAll(cardModelList);
            notifyDataSetChanged();
        }
    };

    public CardAdapter(List<CardModel> cardModelList, ViewPager2 viewPager2) {
        this.cardModelList = cardModelList;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pager_items_saved_cards, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {

        holder.setItems(cardModelList.get(position));
        if (position == cardModelList.size() - 2) {
            viewPager2.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return cardModelList.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewCardBack, imageViewCardLogo;
        TextView textViewFirstFour, textViewLastFour, textViewHolderName, textViewExpireDate;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewCardBack = itemView.findViewById(R.id.card_back);
            imageViewCardLogo = itemView.findViewById(R.id.card_logo);
            textViewFirstFour = itemView.findViewById(R.id.first_four);
            textViewLastFour = itemView.findViewById(R.id.fourth_four);
            textViewHolderName = itemView.findViewById(R.id.holder_name);
            textViewExpireDate = itemView.findViewById(R.id.expire_date);
        }

        void setItems(CardModel cardModel) {

            imageViewCardBack.setImageResource(cardModel.getImageCardBack());
            imageViewCardLogo.setImageResource(cardModel.getImageCardLogo());
            textViewFirstFour.setText(cardModel.getFirstFour());
            textViewLastFour.setText(cardModel.getFourthFour());
            textViewHolderName.setText(cardModel.getHolderName());
            textViewExpireDate.setText(cardModel.getHolderExpireDate());
        }
    }
}
