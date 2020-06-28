package com.example.grocy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.grocy.R;

public class AddPhotoActivity extends AppCompatActivity {

    int PICK_IMAGES = 1;
    CardView cardViewAddPhoto, cardViewAddPhotoImage;
    ImageView imageViewAddPhotoImage;
    EditText editTextCaption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);

        cardViewAddPhoto = findViewById(R.id.cardAddPhoto);
        cardViewAddPhotoImage = findViewById(R.id.cardImage);
        imageViewAddPhotoImage = findViewById(R.id.addPhotoImage);
        editTextCaption = findViewById(R.id.editCaption);

        Toolbar toolbar = findViewById(R.id.add_photo_toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.icon_back_new);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        cardViewAddPhoto.setOnClickListener(v -> getPickImageIntentPhoto());
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
                imageViewAddPhotoImage.setImageURI(data.getData());
                cardViewAddPhotoImage.setVisibility(View.VISIBLE);
                editTextCaption.setVisibility(View.VISIBLE);
            }
        }
    }
}