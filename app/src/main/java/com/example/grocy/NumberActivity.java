package com.example.grocy;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class NumberActivity extends AppCompatActivity {
    Button buttonSendOTP;
    EditText editTextNumber;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);

        buttonSendOTP = findViewById(R.id.btnSendOtp);
        editTextNumber = findViewById(R.id.reg_NumText);

        buttonSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgress();
                String uPhone = editTextNumber.getText().toString();
                String phone_number = "+91" + uPhone;
                System.out.println(phone_number);

                HashMap<String, String> hm = new HashMap<>();
                hm.put("phone_number", phone_number);

                Intent otp_intent = new Intent(NumberActivity.this, OtpActivity.class);
                otp_intent.putExtra("hm", hm);
                System.out.println("Hello");
                startActivity(otp_intent);
                finish();
                progressDialog.dismiss();
            }
        });


    }

    private void showProgress() {
        Context context;
        progressDialog = new ProgressDialog(NumberActivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.process_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );
    }
}
