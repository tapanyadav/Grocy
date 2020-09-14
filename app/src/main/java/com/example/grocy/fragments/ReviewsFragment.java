package com.example.grocy.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocy.Adapters.ReviewAdapter;
import com.example.grocy.Models.ReviewModel;
import com.example.grocy.R;
import com.example.grocy.activities.MainActivity;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ReviewsFragment extends Fragment {

    FirebaseFirestore firebaseFirestore;
    RecyclerView review_recycler;
    ArrayList<ReviewModel> arrayList;
    ReviewAdapter reviewAdapter;
    String user_id;
    DocumentReference documentReference;
    HashMap<String, Object> hm = new HashMap();
    private String Id;

    public ReviewsFragment() {
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
        View view = inflater.inflate(R.layout.fragment_reviews, container, false);


        firebaseFirestore = FirebaseFirestore.getInstance();
        review_recycler = view.findViewById(R.id.review_recycler);

        user_id = (String) MainActivity.proile_activity_data.get("userId");

        String user_id_new = Objects.requireNonNull(getActivity()).getIntent().getStringExtra("usersDocumentId");

        if (user_id_new != null) {
            Id = user_id_new;
        } else {
            Id = user_id;
        }

        documentReference = firebaseFirestore.collection("Users").document(Id);
        Query query = documentReference.collection("Reviews");

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                    hm.put(document.getId(), document.getData());
                }
                setAdapter(view);
            }
        });

        return view;
    }

    private void setAdapter(View view) {

        arrayList = new ArrayList();
        review_recycler = view.findViewById(R.id.review_recycler);
        review_recycler.setHasFixedSize(false);
        review_recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        reviewAdapter = new ReviewAdapter(getActivity(), arrayList);


        review_recycler.setAdapter(reviewAdapter);

        for (Map.Entry mapElement : hm.entrySet()) {
            ReviewModel reviewModel = new ReviewModel();
            String key = (String) mapElement.getKey();
            HashMap item = (HashMap) mapElement.getValue();
            System.out.println("-----------------------------");
            System.out.println(item.toString());
            System.out.println("-----------------------------");
            reviewModel.setDetailedReview((String) item.get("detailedReview"));
            reviewModel.setLikeData((HashMap<String, String>) item.get("likeData"));
            reviewModel.setNotLikeData((HashMap<String, String>) item.get("notLikeData"));
            reviewModel.setRating((Double) item.get("rating"));
            reviewModel.setReviewImage((String) item.get("reviewImage"));
            reviewModel.setNumberOfLikes((Long) item.get("numberOfLikes"));
            reviewModel.setNumberOfComments((Long) item.get("numberOfComments"));
            reviewModel.setReviewId((String) key);
            reviewModel.setShopImage((String) item.get("shopImage"));
            reviewModel.setShopName((String) item.get("shopName"));
            reviewModel.setShopAddress((String) item.get("shopAddress"));

            arrayList.add(reviewModel);

        }
        reviewAdapter.notifyDataSetChanged();
    }
}