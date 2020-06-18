package com.example.grocy.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.grocy.R;

import java.util.Objects;

public class NotificationActivity extends AppCompatActivity {

    ImageView imageViewReadAll;
    Toolbar toolbarNoti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        toolbarNoti = findViewById(R.id.notification_toolbar);
        setSupportActionBar(toolbarNoti);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ;

        toolbarNoti.setNavigationIcon(R.drawable.icon_back_new);
        toolbarNoti.setNavigationOnClickListener(v -> {
            onBackPressed();
        });


        imageViewReadAll = findViewById(R.id.image_noti_all);
        imageViewReadAll.setOnClickListener(v -> {
            Toast.makeText(this, "All notifications are read.", Toast.LENGTH_SHORT).show();
        });
    }
}