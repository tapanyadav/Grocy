package com.example.grocy.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.grocy.Adapters.ReviewAdapter;
import com.example.grocy.Models.ReviewModel;
import com.example.grocy.R;
import com.example.grocy.activities.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ReviewsFragment extends Fragment {


    FirebaseFirestore firebaseFirestore;
    RecyclerView review_recycler;
    ArrayList<ReviewModel> arrayList;
    ReviewAdapter reviewAdapter;
    String user_id;
    DocumentReference documentReference;
    HashMap<String, Object> hm = new HashMap();

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


        TextView textViewComment = view.findViewById(R.id.text_comment_reviews);
        TextView frag_review_details = view.findViewById(R.id.frag_review_details);

        firebaseFirestore = FirebaseFirestore.getInstance();
        review_recycler = view.findViewById(R.id.review_recycler);

        user_id = (String) MainActivity.proile_activity_data.get("userId");

        documentReference = firebaseFirestore.collection("Users").document(user_id);
        Query query = documentReference.collection("Reviews");

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        hm.put(document.getId(), document.getData());
                    }
                    setAdapter(view);
                }
            }
        });

//        textViewComment.setOnClickListener(v -> {
//            Intent intent = new Intent(getActivity(), AddReviewDetailActivity.class);
//            startActivity(intent);
//        });
//
//        frag_review_details.setOnClickListener(v -> {
//            Intent intent = new Intent(getActivity(), AddReviewDetailActivity.class);
//            startActivity(intent);
//        });
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

            arrayList.add(reviewModel);

        }

        reviewAdapter.notifyDataSetChanged();

    }
}