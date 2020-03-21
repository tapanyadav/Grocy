package com.example.grocy;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    TextView textRegLogIn;
    Button btnSignUp;
    CheckBox cb;
    EditText name,email,pass,phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // TextView textView = findViewById(R.id.text_view);
        cb = findViewById(R.id.checkBox);
        textRegLogIn = findViewById(R.id.textViewSignReg);
        btnSignUp = findViewById(R.id.btnRegNext);
        name=findViewById(R.id.reg_nameText);
        email=findViewById(R.id.reg_emailText);
        pass=findViewById(R.id.reg_passText);
        phone=findViewById(R.id.reg_NumText);

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
                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //Getting Text from Fields
                String uName=name.getText().toString();
                String uEmail=email.getText().toString();
                String uPass=pass.getText().toString();
                String uPhone=phone.getText().toString();
                String phone_number="+91"+uPhone;
                System.out.println(phone_number);

                HashMap<String,String> hm=new HashMap<>();
                hm.put("uName",uName);
                hm.put("uEmail",uEmail);
                hm.put("uPass",uPass);
                hm.put("phone_number",phone_number);

                Intent otp_intent=new Intent(RegisterActivity.this,OtpActivity.class);
                otp_intent.putExtra("hm",hm);
                System.out.println("Hello");
                startActivity(otp_intent);
            }
        });

    }



}
