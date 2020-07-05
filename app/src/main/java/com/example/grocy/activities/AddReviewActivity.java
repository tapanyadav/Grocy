package com.example.grocy.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.grocy.R;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;


public class AddReviewActivity extends AppCompatActivity {

    int PICK_IMAGES = 1;
    ImageView imageViewAddReviewPhotoOne;
    CardView cardViewImagePicker;
    CardView cardViewFirst;
    Chip chipLike1, chipLike2, chipLike3, chipLike4, chipLike5, chipLike6;
    Chip chipNotLike1, chipNotLike2, chipNotLike3, chipNotLike4, chipNotLike5;
    ChipGroup chipGroupNotLike;
    Button buttonSubmitReview;
    EditText editTextDetailReview;
    ProgressDialog progressDialog;
    private RatingBar ratingBar;
    HashMap<String, Object> chipData = new HashMap<>();
    FirebaseFirestore firebaseFirestore;
    String user_id;
    private StorageReference storageReference;
    private Uri mainImageURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        imageViewAddReviewPhotoOne = findViewById(R.id.image_first_review);
        cardViewImagePicker = findViewById(R.id.add_review_photo);
        cardViewFirst = findViewById(R.id.add_review_first_photo);
        buttonSubmitReview = findViewById(R.id.btn_submit_review);
        firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        ratingBar = findViewById(R.id.rating_bar);
        editTextDetailReview = findViewById(R.id.detailedReview);

        user_id = (String) MainActivity.proile_activity_data.get("userId");

        Toolbar toolbar = findViewById(R.id.add_review_toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.icon_back_new);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        //cardViewImagePicker.setOnClickListener(v -> getPickImageIntent());
        cardViewImagePicker.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                if (ContextCompat.checkSelfPermission(AddReviewActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(AddReviewActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();
                    ActivityCompat.requestPermissions(AddReviewActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                } else {

                    getPickImageIntent();

                }

            } else {

                getPickImageIntent();

            }
        });

        chipWork();

        buttonSubmitReview.setOnClickListener(v -> {
            showProgress();
            float ratingReview = ratingBar.getRating();
            String detailedReview = editTextDetailReview.getText().toString();
            if (ratingReview != 0.0 && !TextUtils.isEmpty(detailedReview)) {
                chipData.put("rating", ratingReview);
                chipData.put("detailedReview", detailedReview);
                HashMap<String, String> hm1 = new HashMap();
                HashMap<String, String> hm2 = new HashMap();
                if (chipLike1.isChecked()) {
                    CharSequence data1 = chipLike1.getText();
                    hm1.put("data1", (String) data1);
                }
                if (chipLike2.isChecked()) {
                    CharSequence data2 = chipLike2.getText();
                    hm1.put("data2", (String) data2);
                }
                if (chipLike3.isChecked()) {
                    CharSequence data3 = chipLike3.getText();
                    hm1.put("data3", (String) data3);
                }
                if (chipLike4.isChecked()) {
                    CharSequence data4 = chipLike4.getText();
                    hm1.put("data4", (String) data4);
                }
                if (chipLike5.isChecked()) {
                    CharSequence data5 = chipLike5.getText();
                    hm1.put("data5", (String) data5);
                }
                if (chipLike6.isChecked()) {
                    CharSequence data6 = chipLike6.getText();
                    hm1.put("data6", (String) data6);
                }
                if (chipNotLike1.isChecked()) {
                    CharSequence notData1 = chipNotLike1.getText();
                    hm2.put("notData1", (String) notData1);
                }
                if (chipNotLike2.isChecked()) {
                    CharSequence notData2 = chipNotLike2.getText();
                    hm2.put("notData2", (String) notData2);
                }
                if (chipNotLike3.isChecked()) {
                    CharSequence notData3 = chipNotLike3.getText();
                    hm2.put("notData3", (String) notData3);
                }
                if (chipNotLike4.isChecked()) {
                    CharSequence notData4 = chipNotLike4.getText();
                    hm2.put("notData4", (String) notData4);
                }
                if (chipNotLike5.isChecked()) {
                    CharSequence notData5 = chipNotLike5.getText();
                    hm2.put("notData5", (String) notData5);
                }

                chipData.put("likeData", hm1);
                chipData.put("notLikeData", hm2);
                chipData.put("numberOfLikes", 0);
                chipData.put("numberOfComments", 0);

                if (mainImageURI != null) {
                    UploadTask image_path = storageReference.child("review_images/").child(user_id + " (" + UUID.randomUUID() + " )" + ".jpg").putFile(mainImageURI);
                    image_path.addOnSuccessListener(taskSnapshot -> {

                        Task<Uri> task = Objects.requireNonNull(Objects.requireNonNull(taskSnapshot.getMetadata()).getReference()).getDownloadUrl();
                        task.addOnSuccessListener(uri -> {
                            String file = uri.toString();
                            sendReviewData(file);
                        });

                    });//TODO add onFailure listener
                } else {
                    sendReviewData(null);
                }

            } else {
                progressDialog.dismiss();
                if (ratingReview != 0.0) {
                    Toast.makeText(this, "Rating is required", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Please enter detailed review", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    private void sendReviewData(String image) {

        chipData.put("reviewImage", image);

        firebaseFirestore.collection("Users").document(user_id).collection("Reviews").add(chipData).addOnCompleteListener(task1 -> {

            if (task1.isSuccessful()) {
                progressDialog.dismiss();
                Toast.makeText(AddReviewActivity.this, "Review is added", Toast.LENGTH_LONG).show();
                finish();

            } else {
                progressDialog.dismiss();
                String error = Objects.requireNonNull(task1.getException()).getMessage();
                Toast.makeText(AddReviewActivity.this, "(FIRESTORE Error) : " + error, Toast.LENGTH_LONG).show();

            }

        });
    }

    public void getPickImageIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGES);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_IMAGES) {
                mainImageURI = data.getData();
                imageViewAddReviewPhotoOne.setImageURI(mainImageURI);
                cardViewFirst.setVisibility(View.VISIBLE);
            }
        }
    }


    private void chipWork() {
        chipGroupNotLike = findViewById(R.id.chip_group_not);

        chipLike1 = findViewById(R.id.chip1);
        chipLike2 = findViewById(R.id.chip2);
        chipLike3 = findViewById(R.id.chip3);
        chipLike4 = findViewById(R.id.chip4);
        chipLike5 = findViewById(R.id.chip5);
        chipLike6 = findViewById(R.id.chip6);

        chipNotLike1 = findViewById(R.id.chipNot1);
        chipNotLike2 = findViewById(R.id.chipNot2);
        chipNotLike3 = findViewById(R.id.chipNot3);
        chipNotLike4 = findViewById(R.id.chipNot4);
        chipNotLike5 = findViewById(R.id.chipNot5);


        chipLike1.setOnClickListener(v -> chipListener(chipLike1));
        chipLike1.setOnCloseIconClickListener(v -> chipCloseListener(chipLike1));
        chipLike2.setOnClickListener(v -> chipListener(chipLike2));
        chipLike2.setOnCloseIconClickListener(v -> chipCloseListener(chipLike2));
        chipLike3.setOnClickListener(v -> chipListener(chipLike3));
        chipLike3.setOnCloseIconClickListener(v -> chipCloseListener(chipLike3));
        chipLike4.setOnClickListener(v -> chipListener(chipLike4));
        chipLike4.setOnCloseIconClickListener(v -> chipCloseListener(chipLike4));
        chipLike5.setOnClickListener(v -> chipListener(chipLike5));
        chipLike5.setOnCloseIconClickListener(v -> chipCloseListener(chipLike5));
        chipLike6.setOnClickListener(v -> chipListener(chipLike6));
        chipLike6.setOnCloseIconClickListener(v -> chipCloseListener(chipLike6));

        chipNotLike1.setOnClickListener(v -> chipListener(chipNotLike1));
        chipNotLike1.setOnCloseIconClickListener(v -> chipCloseListener(chipNotLike1));
        chipNotLike2.setOnClickListener(v -> chipListener(chipNotLike2));
        chipNotLike2.setOnCloseIconClickListener(v -> chipCloseListener(chipNotLike2));
        chipNotLike3.setOnClickListener(v -> chipListener(chipNotLike3));
        chipNotLike3.setOnCloseIconClickListener(v -> chipCloseListener(chipNotLike3));
        chipNotLike4.setOnClickListener(v -> chipListener(chipNotLike4));
        chipNotLike4.setOnCloseIconClickListener(v -> chipCloseListener(chipNotLike4));
        chipNotLike5.setOnClickListener(v -> chipListener(chipNotLike5));
        chipNotLike5.setOnCloseIconClickListener(v -> chipCloseListener(chipNotLike5));
    }

    void chipListener(Chip chipSelected) {
        chipSelected.setCloseIconVisible(true);
        chipSelected.setTextColor(getResources().getColor(R.color.white));
        chipSelected.setChecked(true);
        chipSelected.setCloseIconTint(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)));
        chipSelected.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.colorPrimary)));
    }

    void chipCloseListener(Chip chipClose) {
        chipClose.setCloseIconVisible(false);
        chipClose.setChecked(false);
        chipClose.setTextColor(getResources().getColor(R.color.black));
        chipClose.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.light)));
    }

    private void showProgress() {
        progressDialog = new ProgressDialog(AddReviewActivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.process_dialog);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(
                android.R.color.transparent
        );
    }
}