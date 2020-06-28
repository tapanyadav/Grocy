package com.example.grocy.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grocy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class NumberActivity extends AppCompatActivity {
    Button buttonSendOTP;
    EditText editTextNumber;
    private ProgressDialog progressDialog;

    FirebaseFirestore fStore;
    String userID;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);

        buttonSendOTP = findViewById(R.id.btnSendOtp);
        editTextNumber = findViewById(R.id.reg_NumText);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        buttonSendOTP.setOnClickListener(v -> {
            showProgress();
            String uPhone = editTextNumber.getText().toString();
            String phone_number = "+91" + uPhone;
            System.out.println(phone_number);

            Map<String, Object> phone = new HashMap<>();
            phone.put("pNumber", phone_number);

            userID = mAuth.getCurrentUser().getUid();
            fStore.collection("Users").document(userID).update(phone);

            HashMap<String, String> hm = new HashMap<>();
            hm.put("phone_number", phone_number);
            Intent otp_intent = new Intent(NumberActivity.this, OtpActivity.class);
            otp_intent.putExtra("hm", hm);
            System.out.println("Hello");
            startActivity(otp_intent);
            finish();
            progressDialog.dismiss();
            Toast.makeText(NumberActivity.this, "Otp sent!", Toast.LENGTH_SHORT).show();
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
