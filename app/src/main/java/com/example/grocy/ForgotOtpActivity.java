package com.example.grocy;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ForgotOtpActivity extends AppCompatActivity {

    Button btnForgotNext;
    TextView tvResend;
    EditText et1, et2, et3, et4, et5, et6;
    private TextWatcher forgotTextWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            String et1Input = et1.getText().toString().trim();
            String et2Input = et2.getText().toString().trim();
            String et3Input = et3.getText().toString().trim();
            String et4Input = et4.getText().toString().trim();
            String et5Input = et3.getText().toString().trim();
            String et6Input = et4.getText().toString().trim();
            if (et1Input.length() == 1)
                et2.requestFocus();
            if (et2Input.length() == 1)
                et3.requestFocus();
            if (et3Input.length() == 1)
                et4.requestFocus();
            if (et4Input.length() == 1)
                et4.requestFocus();
            if (et5Input.length() == 1)
                et5.requestFocus();
            btnForgotNext.setEnabled(!et1Input.isEmpty() && !et2Input.isEmpty() && !et3Input.isEmpty() && !et4Input.isEmpty() && !et5Input.isEmpty() && !et6Input.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_otp);
        btnForgotNext = findViewById(R.id.otp_login_forgot_btn);
        tvResend = findViewById(R.id.textViewResend);
        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        et3 = findViewById(R.id.et3);
        et4 = findViewById(R.id.et4);
        et5 = findViewById(R.id.et5);
        et6 = findViewById(R.id.et6);

        et1.addTextChangedListener(forgotTextWatcher);
        et2.addTextChangedListener(forgotTextWatcher);
        et3.addTextChangedListener(forgotTextWatcher);
        et4.addTextChangedListener(forgotTextWatcher);
        et5.addTextChangedListener(forgotTextWatcher);
        et6.addTextChangedListener(forgotTextWatcher);


        btnForgotNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotOtpActivity.this, ResetPassActivity.class);
                startActivity(intent);
                finish();
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
