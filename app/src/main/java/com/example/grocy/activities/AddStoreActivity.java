package com.example.grocy.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.grocy.R;

import java.util.Objects;

public class AddStoreActivity extends AppCompatActivity {

    Toolbar toolbarAdd;
    EditText editTextName;
    EditText editTextLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_store);

        toolbarAdd = findViewById(R.id.addStore_toolbar);
        editTextName = findViewById(R.id.storeName);
        editTextLocation = findViewById(R.id.storeLocation);

        setSupportActionBar(toolbarAdd);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        toolbarAdd.setNavigationIcon(R.drawable.icon_back_new);
        toolbarAdd.setNavigationOnClickListener(v -> onBackPressed());

        setAsterisk();

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
        editTextName.setHint(spannableStringBuilder);

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
}