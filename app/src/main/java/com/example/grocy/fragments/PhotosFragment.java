package com.example.grocy.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.grocy.R;

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
        return inflater.inflate(R.layout.fragment_photos, container, false);
    }
}