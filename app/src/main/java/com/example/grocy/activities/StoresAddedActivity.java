package com.example.grocy.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.grocy.R;

import java.util.Objects;

public class StoresAddedActivity extends AppCompatActivity {

    Toolbar toolbarRequestStores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stores_added);

        toolbarRequestStores = findViewById(R.id.addedStore_toolbar);

        setSupportActionBar(toolbarRequestStores);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        toolbarRequestStores.setNavigationIcon(R.drawable.icon_back_new);
        toolbarRequestStores.setNavigationOnClickListener(v -> onBackPressed());
    }
}