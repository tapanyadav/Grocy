package com.example.grocy.activities;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.grocy.Adapters.CommentAdapter;
import com.example.grocy.Models.CommentModel;
import com.example.grocy.Models.ReviewModel;
import com.example.grocy.R;
import com.google.android.material.chip.Chip;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddReviewDetailActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    TextView textViewUserName;
    ImageView imageViewImageProfile;
    Uri profileImageUri;
    DocumentReference documentReference2;


    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    private Chip chipLike1, chipLike2, chipLike3, chipLike4, chipLike5, chipLike6;
    private Chip chipNotLike1, chipNotLike2, chipNotLike3, chipNotLike4, chipNotLike5;
    ReviewModel reviewModel = new ReviewModel();
    CircleImageView userImage;
    RecyclerView comments_recycler;
    CommentAdapter commentAdapter;
    HashMap<String, Object> comment_info = new HashMap();
    ArrayList<CommentModel> arrayList;
    private TextView review_info;
    private ImageView image_review;
    private TextView numberOfLikes;
    private TextView numberOfComments;
    private EditText comment_data;
    private Button commentPostButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review_detail);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        String user_id = (String) MainActivity.proile_activity_data.get("userId");
        Toolbar toolbar = findViewById(R.id.add_review_detail_toolbar);
        setSupportActionBar(toolbar);
        textViewUserName = findViewById(R.id.reviewUserName);
        imageViewImageProfile = findViewById(R.id.reviewProfilePic);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.icon_back_new);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        TextView textViewFollowersDetails = findViewById(R.id.text_followers_details);
        TextView textViewReviewsDetails = findViewById(R.id.text_reviews_details);
        showProgress();

        reviewModel = (ReviewModel) getIntent().getSerializableExtra("review_data");


        DocumentReference documentReference1 = firebaseFirestore.collection("Users").document((String) MainActivity.proile_activity_data.get("userId"));
        documentReference1.get().addOnCompleteListener(task -> {

            if (task.isSuccessful()) {
                progressDialog.dismiss();
                if (Objects.requireNonNull(task.getResult()).exists()) {
                    String name = task.getResult().getString("fName");
                    String image = task.getResult().getString("profilePic");
                    profileImageUri = Uri.parse(image);
                    textViewUserName.setText(name);
                    Glide.with(getApplicationContext()).load(image).placeholder(R.drawable.user_profile).into(imageViewImageProfile);
                }
            } else {
                String error = Objects.requireNonNull(task.getException()).getMessage();
                Toast.makeText(AddReviewDetailActivity.this, "(FIRESTORE Retrieve Error) : " + error, Toast.LENGTH_LONG).show();
            }
        });
        documentReference1.collection("Followers").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                textViewFollowersDetails.setText("" + task.getResult().size());

            }
        });

        documentReference1.collection("Reviews").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                textViewReviewsDetails.setText("" + task.getResult().size());

            }
        });


        review_info = findViewById(R.id.review_info);
        image_review = findViewById(R.id.image_review);
        numberOfLikes = findViewById(R.id.numberOfLikes);
        numberOfComments = findViewById(R.id.numberOfComments);

        review_info.setText(reviewModel.getDetailedReview());
        if (reviewModel.getReviewImage() != null) {
            CardView cardViewImageReview = findViewById(R.id.cardImageReview);
            cardViewImageReview.setVisibility(View.VISIBLE);
            Glide.with(image_review.getContext())
                    .load(reviewModel.getReviewImage())
                    .into(image_review);

        }


        numberOfLikes.setText("" + reviewModel.getNumberOfLikes());
        numberOfComments.setText("" + reviewModel.getNumberOfComments());

        comment_data = findViewById(R.id.comment_data);
        commentPostButton = findViewById(R.id.commentPostButton);
        userImage = findViewById(R.id.userImage);
        Glide.with(userImage.getContext())
                .load((String) MainActivity.proile_activity_data.get("profilePic"))
                .into(userImage);


        DocumentReference documentReference = firebaseFirestore.collection("Users").document(user_id)
                .collection("Reviews").document(reviewModel.getReviewId());

        Query query = documentReference.collection("Comments").orderBy("comment_time", Query.Direction.DESCENDING);


        commentPostButton.setOnClickListener(v -> {


            String comment = comment_data.getText().toString().trim();

            HashMap<String, Object> hm = new HashMap();
            hm.put("comment", comment);
            hm.put("profilePic", (String) MainActivity.proile_activity_data.get("profilePic"));
            hm.put("fName", (String) MainActivity.proile_activity_data.get("fName"));
            hm.put("comment_time", FieldValue.serverTimestamp());
            if (comment.equals("") == false) {
                comment_data.setText("");
                firebaseFirestore.collection("Users").document(user_id)
                        .collection("Reviews").document(reviewModel.getReviewId()).collection("Comments")
                        .add(hm).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Comment Added Successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(this, "Please add comment First", Toast.LENGTH_SHORT).show();
            }
        });


//        query.get().add(task -> {
//            if (task.isSuccessful()) {
//                for (QueryDocumentSnapshot document : task.getResult()) {
//                    comment_info.put(document.getId(), document.getData());
//                }
//                numberOfComments.setText(String.valueOf(comment_info.size()));
//                setAdapter();
//            }
//        });
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Toast.makeText(AddReviewDetailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }

                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    comment_info.put(document.getId(), document.getData());
                }
                numberOfComments.setText(String.valueOf(comment_info.size()));
                setAdapter();
            }
        });

        chipDataSet();


    }

    private void setAdapter() {

        arrayList = new ArrayList();

        comments_recycler = findViewById(R.id.comments_recycler);
        comments_recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        comments_recycler.setHasFixedSize(false);

        commentAdapter = new CommentAdapter(this, arrayList);
        comments_recycler.setAdapter(commentAdapter);


        for (Map.Entry mapElement : comment_info.entrySet()) {
            CommentModel commentModel = new CommentModel();
            String key = (String) mapElement.getKey();
            HashMap<String, Object> item = (HashMap<String, Object>) mapElement.getValue();
            commentModel.setCommentData((String) item.get("comment"));
            commentModel.setCommentUserImage((String) item.get("profilePic"));
            commentModel.setCommentUserName((String) item.get("fName"));
            Timestamp timestamp = (Timestamp) item.get("comment_time");
            if (timestamp != null) {
                commentModel.setCommentTime(timestamp.toDate());
            } else {
                commentModel.setCommentTime(Calendar.getInstance().getTime());
            }
            arrayList.add(commentModel);
        }

        Collections.sort(arrayList,
                (o1, o2) -> o1.getCommentTime().compareTo(o2.getCommentTime()));

        Collections.reverse(arrayList);


        commentAdapter.notifyDataSetChanged();

    }

    void chipDataSet() {
        //chipGroupNotLike = findViewById(R.id.chip_group_not);

        chipLike1 = findViewById(R.id.chip1);
        chipLike2 = findViewById(R.id.chip2);
        chipLike3 = findViewById(R.id.chip3);
        chipLike4 = findViewById(R.id.chip4);
        chipLike5 = findViewById(R.id.chip5);
        chipLike6 = findViewById(R.id.chip6);
        Chip[] like = {chipLike1, chipLike2, chipLike3, chipLike4, chipLike5, chipLike6};
        chipNotLike1 = findViewById(R.id.chipNot1);
        chipNotLike2 = findViewById(R.id.chipNot2);
        chipNotLike3 = findViewById(R.id.chipNot3);
        chipNotLike4 = findViewById(R.id.chipNot4);
        chipNotLike5 = findViewById(R.id.chipNot5);
        Chip[] notLike = {chipNotLike1, chipNotLike2, chipNotLike3, chipNotLike4, chipNotLike5};

        HashMap<String, String> chipDataLike = new HashMap<>();
        HashMap<String, String> chipDataNotLike = new HashMap<>();
        chipDataLike = reviewModel.getLikeData();
        chipDataNotLike = reviewModel.getNotLikeData();
        int count = 0;
        for (Map.Entry mapElement : chipDataLike.entrySet()) {
            String key = (String) mapElement.getKey();
            String value = (String) mapElement.getValue();
            Chip chip = like[count];
            chip.setText(value);
            chip.setVisibility(View.VISIBLE);
            count++;
        }
        count = 0;
        for (Map.Entry mapElement : chipDataNotLike.entrySet()) {
            String key = (String) mapElement.getKey();
            String value = (String) mapElement.getValue();
            Chip chip = notLike[count];
            chip.setText(value);
            chip.setVisibility(View.VISIBLE);
            count++;
        }


    }

    private void showProgress() {
        progressDialog = new ProgressDialog(AddReviewDetailActivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.process_dialog);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(
                android.R.color.transparent
        );
    }

}