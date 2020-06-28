package com.example.grocy.activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.example.grocy.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Objects;

public class AddReviewActivity extends AppCompatActivity {

    int PICK_IMAGES = 1;
    ImageView imageViewAddReviewPhotoOne;
    CardView cardViewImagePicker;
    CardView cardViewFirst;
    Chip chipLike1, chipLike2, chipLike3, chipLike4, chipLike5, chipLike6;
    Chip chipNotLike1, chipNotLike2, chipNotLike3, chipNotLike4, chipNotLike5;
    ChipGroup chipGroupNotLike;
    Button buttonSubmitReview;
    HashMap<String, Object> chipData = new HashMap<>();
    FirebaseFirestore firebaseFirestore;
    String user_id;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        imageViewAddReviewPhotoOne = findViewById(R.id.image_first_review);
        cardViewImagePicker = findViewById(R.id.add_review_photo);
        cardViewFirst = findViewById(R.id.add_review_first_photo);
        buttonSubmitReview = findViewById(R.id.btn_submit_review);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        user_id = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();

        Toolbar toolbar = findViewById(R.id.add_review_toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.icon_back_new);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        cardViewImagePicker.setOnClickListener(v -> getPickImageIntent());


        chipWork();


        buttonSubmitReview.setOnClickListener(v -> {
            if (chipLike1.isChecked()) {
                CharSequence data1 = chipLike1.getText();
                chipData.put("data1", data1);
            }
            if (chipLike2.isChecked()) {
                CharSequence data2 = chipLike2.getText();
                chipData.put("data2", data2);
            }
            if (chipLike3.isChecked()) {
                CharSequence data3 = chipLike3.getText();
                chipData.put("data3", data3);
            }
            if (chipLike4.isChecked()) {
                CharSequence data4 = chipLike4.getText();
                chipData.put("data4", data4);
            }
            if (chipLike5.isChecked()) {
                CharSequence data5 = chipLike5.getText();
                chipData.put("data5", data5);
            }
            if (chipLike6.isChecked()) {
                CharSequence data6 = chipLike6.getText();
                chipData.put("data6", data6);
            }
            if (chipNotLike1.isChecked()) {
                CharSequence notData1 = chipNotLike1.getText();
                chipData.put("notData1", notData1);
            }
            if (chipNotLike2.isChecked()) {
                CharSequence notData2 = chipNotLike2.getText();
                chipData.put("notData2", notData2);
            }
            if (chipNotLike3.isChecked()) {
                CharSequence notData3 = chipNotLike3.getText();
                chipData.put("notData3", notData3);
            }
            if (chipNotLike4.isChecked()) {
                CharSequence notData4 = chipNotLike4.getText();
                chipData.put("notData4", notData4);
            }
            if (chipNotLike5.isChecked()) {
                CharSequence notData5 = chipNotLike5.getText();
                chipData.put("notData5", notData5);
            }
            System.out.println("-----------------------------------------------------------");
            System.out.println("-----------------------------------------------------------");
            System.out.println("-----------------------------------------------------------");
            System.out.println(chipData.toString());
            System.out.println("-----------------------------------------------------------");
            System.out.println("-----------------------------------------------------------");
            System.out.println("-----------------------------------------------------------");

            sendReviewData();
        });

    }

    private void sendReviewData() {

        firebaseFirestore.collection("Users").document(user_id).collection("Reviews").add(chipData).addOnCompleteListener(task1 -> {

            if (task1.isSuccessful()) {


                Toast.makeText(AddReviewActivity.this, "Reviews are added", Toast.LENGTH_LONG).show();
                Intent mainIntent = new Intent(AddReviewActivity.this, UserProfileActivity.class);
                startActivity(mainIntent);
                finish();

            } else {

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
                imageViewAddReviewPhotoOne.setImageURI(data.getData());
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
}