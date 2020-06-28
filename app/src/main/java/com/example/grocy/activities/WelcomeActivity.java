package com.example.grocy.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grocy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class WelcomeActivity extends AppCompatActivity {

    Button btnLogin, btnSignUp;
    TextView tvSkip;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignup);
        tvSkip = findViewById(R.id.textViewSkip);
        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(v -> {
            showProgress();
            Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            progressDialog.dismiss();
        });

        btnSignUp.setOnClickListener(v -> {
            showProgress();
            Intent intent = new Intent(WelcomeActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
            progressDialog.dismiss();
        });
        tvSkip.setOnClickListener(v -> {
            showProgress();
            Intent intent = new Intent(WelcomeActivity.this, StartLocationActivity.class);
            startActivity(intent);
            finish();
            progressDialog.dismiss();
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {

            sendToMain();

        }


    }

    private void sendToMain() {

        Intent mainIntent = new Intent(WelcomeActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();

    }

    private void showProgress() {
        Context context;
        progressDialog = new ProgressDialog(WelcomeActivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.process_dialog);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(
                android.R.color.transparent
        );
    }
}
