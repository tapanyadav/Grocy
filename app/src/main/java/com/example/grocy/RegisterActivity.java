package com.example.grocy;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class RegisterActivity extends AppCompatActivity {
    TextView textRegLogIn;
    EditText name;
    EditText email;
    EditText pass;
    EditText phone;
    Button btnSignUp;
    CheckBox cb;
    Member member;
    DatabaseReference reff;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = findViewById(R.id.reg_nameText);
        email = findViewById(R.id.reg_emailText);
        pass = findViewById(R.id.reg_passText);
        phone = findViewById(R.id.reg_NumText);
        btnSignUp = findViewById(R.id.btnRegNext);
        member = new Member();
        reff = FirebaseDatabase.getInstance().getReference().child("Member");

        // TextView textView = findViewById(R.id.text_view);
        cb = findViewById(R.id.checkBox);
        textRegLogIn = findViewById(R.id.textViewSignReg);
        btnSignUp = findViewById(R.id.btnRegNext);


        //make terms nd conditions text clickable 36-65

        String text = "Accept the Terms and Conditions";

        SpannableString ss = new SpannableString(text);

        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(RegisterActivity.this, "One", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
            }
        };

        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(RegisterActivity.this, "Two", Toast.LENGTH_SHORT).show();
            }
        };

        ss.setSpan(clickableSpan1, 11, 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(clickableSpan2, 21, 31, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        cb.setText(ss);
        cb.setMovementMethod(LinkMovementMethod.getInstance());


        textRegLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = name.getText().toString();
                String userEmail = email.getText().toString();
                String userPass = pass.getText().toString();
                String userPhone = phone.getText().toString();
                member.setUser_name(userName);
                member.setUser_email(userEmail);
                member.setUser_pass(userPass);
                member.setUser_phone(userPhone);
                reff.push().setValue(member);
                Toast.makeText(RegisterActivity.this, "Data Inserted Successfull", Toast.LENGTH_SHORT).show();
                String mobile_number = "+91"+userPhone;

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        mobile_number,        // Phone number to verify
                        60,                  // Timeout duration
                        TimeUnit.SECONDS,   // Unit of timeout
                        RegisterActivity.this,               // Activity (for callback binding)
                        mCallbacks);

            }
        });
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Intent intent = new Intent(RegisterActivity.this, OtpActivity.class);
                startActivity(intent);

            }
        };
    }



}
