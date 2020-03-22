package com.example.grocy;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);

        buttonSendOTP = findViewById(R.id.btnSendOtp);
        editTextNumber = findViewById(R.id.reg_NumText);

        buttonSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });


    }
}
