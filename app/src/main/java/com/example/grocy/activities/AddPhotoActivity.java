package com.example.grocy.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.grocy.R;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class AddPhotoActivity extends AppCompatActivity {

    int PICK_IMAGES = 1;
    CardView cardViewAddPhoto, cardViewAddPhotoImage;
    ImageView imageViewAddPhotoImage;
    EditText editTextCaption;
    ProgressDialog progressDialog;
    Button buttonSubmitPhoto;
    String captionData;
    int photoCount;
    HashMap<String, Object> reviewData = new HashMap<>();
    FirebaseFirestore firebaseFirestore;
    String user_id;
    private Uri getImageUri;
    private StorageReference storageReference;
    TextView textViewPhotoStoreName;
    String textShopName, textShopAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);

        cardViewAddPhoto = findViewById(R.id.cardAddPhoto);
        cardViewAddPhotoImage = findViewById(R.id.cardImage);
        imageViewAddPhotoImage = findViewById(R.id.addPhotoImage);
        editTextCaption = findViewById(R.id.editCaption);
        buttonSubmitPhoto = findViewById(R.id.btn_submit_photo);
        textViewPhotoStoreName = findViewById(R.id.photo_storeName);

        firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        user_id = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();

        Toolbar toolbar = findViewById(R.id.add_photo_toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.icon_back_new);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

//        Bundle bundle = getIntent().getExtras();
//        if (bundle!=null){
//            imageShop = bundle.getInt("storeImage");
//
//        }
        String image1 = getIntent().getStringExtra("storeImage");
        textShopAddress = getIntent().getStringExtra("storeAddress");
        textShopName = getIntent().getStringExtra("storeName");
        textViewPhotoStoreName.setText(textShopName);

        reviewData.put("shopName", textShopName);
        reviewData.put("shopImage", image1);
        reviewData.put("shopAddress", textShopAddress);

        cardViewAddPhoto.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                if (ContextCompat.checkSelfPermission(AddPhotoActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(AddPhotoActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();
                    ActivityCompat.requestPermissions(AddPhotoActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                } else {

                    getPickImageIntentPhoto();

                }

            } else {

                getPickImageIntentPhoto();

            }
        });
//        cardViewAddPhoto.setOnClickListener(v -> getPickImageIntentPhoto());

        buttonSubmitPhoto.setOnClickListener(v -> {
            showProgress();
            captionData = editTextCaption.getText().toString();
            if (getImageUri != null) {
                UploadTask image_path = storageReference.child("photo_images/").child(user_id + " (" + UUID.randomUUID() + " )" + ".jpg").putFile(getImageUri);
                image_path.addOnSuccessListener(taskSnapshot -> {

                    Task<Uri> task = Objects.requireNonNull(Objects.requireNonNull(taskSnapshot.getMetadata()).getReference()).getDownloadUrl();
                    task.addOnSuccessListener(uri -> {
                        String file = uri.toString();
                        addPhotoData(file, captionData);
                    });

                });//TODO add onFailure listener
            } else {
                Toast.makeText(this, "Please add one photo!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addPhotoData(String image, String caption) {
        reviewData.put("photoImage", image);
        reviewData.put("photoCaption", caption);

        HashMap<String, Object> hashMapPhotoCount = new HashMap<>();
        firebaseFirestore.collection("Users").document(user_id).get().addOnCompleteListener(task -> {
            DocumentSnapshot documentSnapshot = task.getResult();
            if (documentSnapshot.getData().containsKey("photoCount")) {
                photoCount = Integer.parseInt("" + documentSnapshot.get("photoCount"));
                //Toast.makeText(this, "Bookmarks:"+documentSnapshot.get("noOfBookmarks"), Toast.LENGTH_SHORT).show();
            } else {
                photoCount = 0;
            }
            Toast.makeText(this, "Count: " + photoCount, Toast.LENGTH_SHORT).show();
        });

        firebaseFirestore.collection("Users").document(user_id).collection("Photos").add(reviewData).addOnCompleteListener(task1 -> {

            if (task1.isSuccessful()) {
                progressDialog.dismiss();

                photoCount += 1;
                hashMapPhotoCount.put("photoCount", photoCount);
                firebaseFirestore.collection("Users").document(user_id).update(hashMapPhotoCount).addOnCompleteListener(task2 -> {
                    if (task2.isSuccessful()) {
                        Toast.makeText(this, "Review Count successfully updated", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Firestore error!", Toast.LENGTH_SHORT).show();

                    }
                });
                Toast.makeText(AddPhotoActivity.this, "Photo is added", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AddPhotoActivity.this, UserProfileActivity.class);
                startActivity(intent);
                finish();

            } else {
                progressDialog.dismiss();
                String error = Objects.requireNonNull(task1.getException()).getMessage();
                Toast.makeText(AddPhotoActivity.this, "(FIRESTORE Error) : " + error, Toast.LENGTH_LONG).show();

            }

        });
    }

    private void getPickImageIntentPhoto() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGES);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_IMAGES) {
                getImageUri = data.getData();
                imageViewAddPhotoImage.setImageURI(getImageUri);
                cardViewAddPhotoImage.setVisibility(View.VISIBLE);
                editTextCaption.setVisibility(View.VISIBLE);
            }
        }
    }

    private void showProgress() {
        progressDialog = new ProgressDialog(AddPhotoActivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.process_dialog);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(
                android.R.color.transparent
        );
    }
}