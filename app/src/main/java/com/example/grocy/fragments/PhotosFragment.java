package com.example.grocy.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocy.Adapters.PhotosAdapter;
import com.example.grocy.Models.PhotosModel;
import com.example.grocy.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Objects;

public class PhotosFragment extends Fragment {

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    RecyclerView photos_recycler;
    PhotosAdapter photosAdapter;
    String user_id;
    DocumentReference documentReference;
    private String Id;


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

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        user_id = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
        String user_id_new = getActivity().getIntent().getStringExtra("usersDocumentId");

        if (user_id_new != null) {
            Id = user_id_new;
        } else {
            Id = user_id;
        }


        photos_recycler = view.findViewById(R.id.recycler_photos);


        documentReference = firebaseFirestore.collection("Users").document(Id);
        Query query = documentReference.collection("Photos");

        FirestoreRecyclerOptions<PhotosModel> photosModelFirestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<PhotosModel>()
                .setQuery(query, PhotosModel.class).build();

        photosAdapter = new PhotosAdapter(photosModelFirestoreRecyclerOptions);
        photos_recycler.setHasFixedSize(true);
        photos_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        photos_recycler.setAdapter(photosAdapter);
        photosAdapter.notifyDataSetChanged();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        photosAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        photosAdapter.stopListening();
    }
}