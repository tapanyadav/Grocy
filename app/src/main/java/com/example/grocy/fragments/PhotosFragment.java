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

public class PhotosFragment extends Fragment {


//    public static PhotosFragment newInstance(String param1, String param2) {
//        PhotosFragment fragment = new PhotosFragment();
//        Bundle args = new Bundle();
//
//        return fragment;
//    }

    public PhotosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_photos, container, false);


        TextView textViewComment = (TextView) view.findViewById(R.id.text_comment);

        textViewComment.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CommentsActivity.class);
            startActivity(intent);
        });
        return view;
    }
}