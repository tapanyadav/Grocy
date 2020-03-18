package com.example.grocy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ForgotOtpActivity extends AppCompatActivity {

    Button btnForgotNext;
    TextView tvResend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_otp);
        btnForgotNext = findViewById(R.id.otp_login_forgot_btn);
        tvResend = findViewById(R.id.textViewResend);

        btnForgotNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotOtpActivity.this, ResetPassActivity.class);
                startActivity(intent);
            }
        });
        tvResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ForgotOtpActivity.this, "Code is resend!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
