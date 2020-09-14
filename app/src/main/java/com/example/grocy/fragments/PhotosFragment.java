package com.example.grocy.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocy.Adapters.PhotosAdapter;
import com.example.grocy.Models.PhotosModel;
import com.example.grocy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PhotosFragment extends Fragment {

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    RecyclerView photos_recycler;
    PhotosAdapter photosAdapter;
    String user_id;
    ArrayList<PhotosModel> arrayList;
    DocumentReference documentReference;
    private String Id;
    String user_id_new;
    HashMap<String, Object> hm = new HashMap();


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
        user_id_new = getActivity().getIntent().getStringExtra("usersDocumentId");

        if (user_id_new != null) {
            Id = user_id_new;
        } else {
            Id = user_id;
        }

        Toast.makeText(view.getContext(), "Id:" + Id, Toast.LENGTH_SHORT).show();

        photos_recycler = view.findViewById(R.id.recycler_photos);


        documentReference = firebaseFirestore.collection("Users").document(Id);
        Query query = documentReference.collection("Photos");

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    hm.put(document.getId(), document.getData());
                }
                setAdapter(view);
            }
        });

        return view;
    }


    private void setAdapter(View view) {

        arrayList = new ArrayList();
        photos_recycler.setHasFixedSize(true);
        photos_recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        photosAdapter = new PhotosAdapter(getActivity(), arrayList);
        photos_recycler.setAdapter(photosAdapter);

        for (Map.Entry mapElement : hm.entrySet()) {
            PhotosModel photosModel = new PhotosModel();
            String key = (String) mapElement.getKey();
            HashMap item = (HashMap) mapElement.getValue();
            photosModel.setPhotoCaption((String) item.get("photoCaption"));
            photosModel.setPhotoImage((String) item.get("photoImage"));
            photosModel.setShopImage((String) item.get("shopImage"));
            photosModel.setShopName((String) item.get("shopName"));
            photosModel.setShopAddress((String) item.get("shopAddress"));

            arrayList.add(photosModel);

        }

        photosAdapter.notifyDataSetChanged();

    }
}