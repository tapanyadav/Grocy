package com.example.grocy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.grocy.R;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {

    Toolbar toolbarSetting;
    LinearLayout linearLayoutAdd;
    LinearLayout linearLayoutRequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        toolbarSetting = findViewById(R.id.setting_toolbar);

        setSupportActionBar(toolbarSetting);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        toolbarSetting.setNavigationIcon(R.drawable.icon_back_new);
        toolbarSetting.setNavigationOnClickListener(v -> onBackPressed());
        linearLayoutAdd = findViewById(R.id.linearAddStore);
        linearLayoutRequests = findViewById(R.id.linearSavedStore);

        linearLayoutAdd.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddStoreActivity.class);
            startActivity(intent);
        });

        linearLayoutRequests.setOnClickListener(v -> {
            Intent intent = new Intent(this, StoresAddedActivity.class);
            startActivity(intent);
        });
    }
}