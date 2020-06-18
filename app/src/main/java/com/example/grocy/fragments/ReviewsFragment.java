package com.example.grocy.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.grocy.R;
import com.example.grocy.activities.CommentsActivity;

public class ReviewsFragment extends Fragment {


    public ReviewsFragment() {
        // Required empty public constructor
    }

//    public static ReviewsFragment newInstance(String param1, String param2) {
//        ReviewsFragment fragment = new ReviewsFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reviews, container, false);


        TextView textViewComment = (TextView) view.findViewById(R.id.text_comment_reviews);

        textViewComment.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CommentsActivity.class);
            startActivity(intent);
        });
        return view;
    }
}