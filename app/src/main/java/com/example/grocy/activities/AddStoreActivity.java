package com.example.grocy.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class AddStoreActivity extends AppCompatActivity {

    Toolbar toolbarAdd;
    EditText editTextStoreName, editTextLocation, editTextPhoneNumber, editTextTellMore;
    TextView textViewComp;
    CardView cardViewAddStore, cardViewFirstImage;
    int PICK_IMAGES = 1;
    ImageView imageViewFirstPhotoStore;
    String user_id;
    Button buttonAddStore;
    ProgressDialog progressDialog;
    String storeName, storePhoneNumber, storeLocation, tellUsMore;
    FirebaseFirestore firebaseFirestore;
    StorageReference storageReference;
    String ownerManager = "";
    RadioButton radioButtonNo, radioButtonYes;
    private Uri mainImageURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_store);

        firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        user_id = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();

        toolbarAdd = findViewById(R.id.addStore_toolbar);
        editTextStoreName = findViewById(R.id.storeName);
        editTextLocation = findViewById(R.id.storeLocation);
        textViewComp = findViewById(R.id.text_hint);
        cardViewAddStore = findViewById(R.id.add_store_photo);
        cardViewFirstImage = findViewById(R.id.add_store_first_photo);
        imageViewFirstPhotoStore = findViewById(R.id.image_first_store);
        buttonAddStore = findViewById(R.id.btnSubmit);
        editTextTellMore = findViewById(R.id.text_tellMore);
        editTextPhoneNumber = findViewById(R.id.text_storePhone);
        radioButtonNo = findViewById(R.id.noOwnerManger);
        radioButtonYes = findViewById(R.id.ownerManger);

        setSupportActionBar(toolbarAdd);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        toolbarAdd.setNavigationIcon(R.drawable.icon_back_new);
        toolbarAdd.setNavigationOnClickListener(v -> onBackPressed());

        cardViewAddStore.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                if (ContextCompat.checkSelfPermission(AddStoreActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(AddStoreActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();
                    ActivityCompat.requestPermissions(AddStoreActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                } else {

                    getPickImageIntent();

                }

            } else {

                getPickImageIntent();

            }
        });
        buttonAddStore.setOnClickListener(v -> {
            sendStoreData();
        });

        setAsterisk();
        setAsteriskLine();

    }

    private void sendStoreData() {
        showProgress();
        storeName = editTextStoreName.getText().toString();
        storePhoneNumber = editTextPhoneNumber.getText().toString();
        storeLocation = editTextLocation.getText().toString();
        tellUsMore = editTextTellMore.getText().toString();

        if (!TextUtils.isEmpty(storeName) && !TextUtils.isEmpty(storeLocation)) {
            if (mainImageURI != null) {
                UploadTask image_path = storageReference.child("store_images/").child(user_id + " (" + UUID.randomUUID() + " )" + ".jpg").putFile(mainImageURI);
                image_path.addOnSuccessListener(taskSnapshot -> {

                    Task<Uri> task = Objects.requireNonNull(Objects.requireNonNull(taskSnapshot.getMetadata()).getReference()).getDownloadUrl();
                    task.addOnSuccessListener(uri -> {
                        String file = uri.toString();
                        sendData(file);
                    });

                });//TODO add onFailure listener
            } else {
                sendData(null);
            }
        } else {
            progressDialog.dismiss();
            if (storeName.equals("")) {
                Toast.makeText(this, "Store name is required", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Store location is required", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void sendData(String file) {
        if (radioButtonNo.isChecked()) {
            ownerManager = "No Owner/Manager";
        }
        if (radioButtonYes.isChecked()) {
            ownerManager = "Owner/Manager";
        }
        HashMap<String, Object> storeMap = new HashMap<>();
        storeMap.put("storeName", storeName);
        storeMap.put("storeLocation", storeLocation);
        storeMap.put("storePhoneNumber", storePhoneNumber);
        storeMap.put("storeImage", file);
        storeMap.put("storeTellMore", tellUsMore);
        storeMap.put("storeAddStatus", "Pending");
        storeMap.put("userPost", ownerManager);

        firebaseFirestore.collection("Users").document(user_id).collection("storesSuggestions").add(storeMap).addOnCompleteListener(task1 -> {

            if (task1.isSuccessful()) {
                progressDialog.dismiss();
                Toast.makeText(AddStoreActivity.this, "Store is added", Toast.LENGTH_LONG).show();
                finish();

            } else {
                progressDialog.dismiss();
                String error = Objects.requireNonNull(task1.getException()).getMessage();
                Toast.makeText(AddStoreActivity.this, "(FIRESTORE Error) : " + error, Toast.LENGTH_LONG).show();

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
                imageViewFirstPhotoStore.setImageURI(mainImageURI);
                cardViewFirstImage.setVisibility(View.VISIBLE);
            }
        }
    }

    private void setAsterisk() {
        String storeName = "Store Name ";
        String colored = "*";
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append(storeName);
        int start = spannableStringBuilder.length();
        spannableStringBuilder.append(colored);
        int end = spannableStringBuilder.length();
        spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.RED), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        editTextStoreName.setHint(spannableStringBuilder);

        String storeLocation = "Store Location ";
        String coloredLoc = "*";
        SpannableStringBuilder spannable = new SpannableStringBuilder();
        spannable.append(storeLocation);
        int startL = spannable.length();
        spannable.append(coloredLoc);
        int endL = spannable.length();
            spannable.setSpan(new ForegroundColorSpan(Color.RED), startL, endL, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        editTextLocation.setHint(spannable);
    }

    private void setAsteriskLine() {
        String comp = "Field mark with ";
        String colored = "*";
        String compLast = " are mandatory";
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append(comp);
        int start = spannableStringBuilder.length();
        spannableStringBuilder.append(colored);
        int end = spannableStringBuilder.length();
        spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.RED), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.append(compLast);
        textViewComp.setHint(spannableStringBuilder);
    }

    private void showProgress() {
        progressDialog = new ProgressDialog(AddStoreActivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.process_dialog);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(
                android.R.color.transparent
        );
    }

}