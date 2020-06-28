package com.example.grocy.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.grocy.R;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import id.zelory.compressor.Compressor;


public class EditProfileActivity extends AppCompatActivity {

    CardView cardViewEditProfImage;
    Button buttonSave;
    EditText editTextName;
    ImageView imageViewProfileEdit;
    ProgressDialog progressDialog;
    String name = null;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private Bitmap compressedImageFile;
    private Uri mainImageURI = null;
    private StorageReference storageReference;
    private String user_id;
    private boolean isChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        cardViewEditProfImage = findViewById(R.id.cardEditProf);
        buttonSave = findViewById(R.id.btn_save_changes);
        editTextName = findViewById(R.id.name);
        imageViewProfileEdit = findViewById(R.id.userProfileEditImage);

        firebaseAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        user_id = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();

        Toolbar toolbar = findViewById(R.id.edit_profile_toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.icon_back_new);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        buttonSave.setEnabled(false);

        firebaseFirestore = FirebaseFirestore.getInstance();
        showProgress();


        firebaseFirestore.collection("Users").document(user_id).get().addOnCompleteListener(task -> {


            if (task.isSuccessful()) {

                progressDialog.dismiss();

                if (Objects.requireNonNull(task.getResult()).exists()) {

                    name = task.getResult().getString("fName");
                    String image = task.getResult().getString("profilePic");

                    mainImageURI = Uri.parse(image);
                    editTextName.setText(name);

                    Glide.with(getApplicationContext()).load(image).placeholder(R.drawable.user_profile).into(imageViewProfileEdit);


                }

            } else {

                String error = Objects.requireNonNull(task.getException()).getMessage();
                Toast.makeText(EditProfileActivity.this, "(FIRESTORE Retrieve Error) : " + error, Toast.LENGTH_LONG).show();

            }


            editTextName.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) {
                    String textName = editTextName.getText().toString();
                    if (textName.equals(name)) {
                        buttonSave.setEnabled(false);
                    }

                }

                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {

                    buttonSave.setEnabled(true);
                }
            });

        });


        buttonSave.setOnClickListener(v -> {
            showProgress();

            final String user_name = editTextName.getText().toString();

            if (!TextUtils.isEmpty(user_name) && mainImageURI != null) {

                if (isChanged) {

                    user_id = firebaseAuth.getCurrentUser().getUid();

                    File newImageFile = new File(Objects.requireNonNull(mainImageURI.getPath()));
                    try {

                        compressedImageFile = new Compressor(this)
                                .setMaxHeight(125)
                                .setMaxWidth(125)
                                .setQuality(50)
                                .compressToBitmap(newImageFile);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    compressedImageFile.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] thumbData = baos.toByteArray();

                    UploadTask image_path = storageReference.child("profile_images/").child(user_id + ".jpg").putBytes(thumbData);

                    image_path.addOnSuccessListener(taskSnapshot -> {

                        Task<Uri> task = Objects.requireNonNull(Objects.requireNonNull(taskSnapshot.getMetadata()).getReference()).getDownloadUrl();
                        task.addOnSuccessListener(uri -> {
                            String file = uri.toString();
                            storeFirestore(file, user_name);
                        });

                    });  //TODO add onFailure listener

                } else {

                    storeFirestore(null, user_name);

                }
            }

        });

        imageViewProfileEdit.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                if (ContextCompat.checkSelfPermission(EditProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(EditProfileActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();
                    ActivityCompat.requestPermissions(EditProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                } else {

                    BringImagePicker();

                }

            } else {

                BringImagePicker();

            }
        });
    }

    private void storeFirestore(String generatedFilePath, String user_name) {


        Map<String, Object> userMap = new HashMap<>();
        userMap.put("fName", user_name);
        userMap.put("profilePic", generatedFilePath);

        firebaseFirestore.collection("Users").document(user_id).update(userMap).addOnCompleteListener(task1 -> {

            if (task1.isSuccessful()) {

                progressDialog.dismiss();
                Toast.makeText(EditProfileActivity.this, "The user Settings are updated.", Toast.LENGTH_LONG).show();
                Intent mainIntent = new Intent(EditProfileActivity.this, UserProfileActivity.class);
                startActivity(mainIntent);
                finish();

            } else {

                String error = Objects.requireNonNull(task1.getException()).getMessage();
                Toast.makeText(EditProfileActivity.this, "(FIRESTORE Error) : " + error, Toast.LENGTH_LONG).show();

            }

        });


    }

    private void BringImagePicker() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .start(EditProfileActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                mainImageURI = result.getUri();
                imageViewProfileEdit.setImageURI(mainImageURI);

                buttonSave.setEnabled(true);
                isChanged = true;

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();
                Toast.makeText(this, "Error: " + error, Toast.LENGTH_SHORT).show();

            }
        }
    }

    private void showProgress() {
        progressDialog = new ProgressDialog(EditProfileActivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.process_dialog);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(
                android.R.color.transparent
        );
    }
}