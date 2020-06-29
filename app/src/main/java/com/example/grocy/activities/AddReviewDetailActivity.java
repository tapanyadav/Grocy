package com.example.grocy.activities;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.grocy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class AddReviewDetailActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    TextView textViewUserName;
    ImageView imageViewImageProfile;
    Uri profileImageUri;
    DocumentReference documentReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review_detail);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        String user_id = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
        Toolbar toolbar = findViewById(R.id.add_review_detail_toolbar);
        setSupportActionBar(toolbar);
        textViewUserName = findViewById(R.id.reviewUserName);
        imageViewImageProfile = findViewById(R.id.reviewProfilePic);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.icon_back_new);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        showProgress();

        firebaseFirestore.collection("Users").document(user_id).get().addOnCompleteListener(task -> {

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

        //TODO Get position from and review document id profile activity and then show data
//        documentReference = firebaseFirestore.collection("Users").document(user_id);
//        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()){
//                    DocumentSnapshot documentSnapshot = task.getResult();
//                    assert documentSnapshot != null;
//                    String reviewId = documentSnapshot.getId();
//
//                }
//            }
//        });

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