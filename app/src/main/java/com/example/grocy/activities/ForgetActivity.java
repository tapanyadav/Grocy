package com.example.grocy.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.grocy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetActivity extends AppCompatActivity {

    Button btnForgotPass;
    EditText editTextEmailInput;
    TextView textViewForgotSignIn;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        btnForgotPass=findViewById(R.id.forgotSend);
        editTextEmailInput = findViewById(R.id.forgot_emailText);
        textViewForgotSignIn=findViewById(R.id.textViewSignForgot);
        mAuth = FirebaseAuth.getInstance();

        textViewForgotSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ForgetActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userEmail = editTextEmailInput.getText().toString();
                showProgress();
                if (TextUtils.isEmpty(userEmail)) {
                    Toast.makeText(ForgetActivity.this, "Please enter valid email address!", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.sendPasswordResetEmail(userEmail)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ForgetActivity.this, "E-mail sent", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ForgetActivity.this, EmailSentActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        String message = task.getException().getMessage();
                                        Toast.makeText(ForgetActivity.this, "Error occured:" + message, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                progressDialog.dismiss();

            }
        });


    }

    private void showProgress() {
        Context context;
        progressDialog = new ProgressDialog(ForgetActivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.process_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );
    }
}
