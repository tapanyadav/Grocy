package com.example.grocy;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    TextView textRegLogIn;
    Button btnSignUp;
    CheckBox cb;
    EditText name, email, pass, confPass;
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        // TextView textView = findViewById(R.id.text_view);
        cb = findViewById(R.id.checkBox);
        textRegLogIn = findViewById(R.id.textViewSignReg);
        btnSignUp = findViewById(R.id.btnRegNext);
        name=findViewById(R.id.reg_nameText);
        email=findViewById(R.id.reg_emailText);
        pass=findViewById(R.id.reg_passText);
        confPass = findViewById(R.id.reg_ConfPassText);

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
                finish();
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Getting Text from Fields
                String uName=name.getText().toString();
                String uEmail=email.getText().toString();
                String uPass=pass.getText().toString();
                String uConfirmPass = confPass.getText().toString();
                // String phone_number="+91"+uPhone;
                // System.out.println(phone_number);

              /*  HashMap<String,String> hm=new HashMap<>();
                hm.put("uName",uName);
                hm.put("uEmail",uEmail);
                hm.put("uPass",uPass);
                hm.put("phone_number",phone_number);


                otp_intent.putExtra("hm",hm);
               */
                if (!TextUtils.isEmpty(uName) && !TextUtils.isEmpty(uEmail) & !TextUtils.isEmpty(uPass) & !TextUtils.isEmpty(uConfirmPass)) {


                    if (uPass.equals(uConfirmPass)) {
                        showProgress();

                        mAuth.createUserWithEmailAndPassword(uEmail, uPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {

                                    Intent setupIntent = new Intent(RegisterActivity.this, NumberActivity.class);
                                    startActivity(setupIntent);
                                    finish();
                                    // Toast.makeText(RegisterActivity.this, " Otp sent!", Toast.LENGTH_SHORT).show();

                                } else {

                                    String errorMessage = task.getException().getMessage();
                                    Toast.makeText(RegisterActivity.this, "Error : " + errorMessage, Toast.LENGTH_LONG).show();

                                }

                                progressDialog.dismiss();
                            }
                        });
                    } else {
                        Toast.makeText(RegisterActivity.this, "Password and Confirm Password Field doesn't match.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }

    private void showProgress() {
        Context context;
        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.process_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );
    }
  /*  @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){

            Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(mainIntent);
            finish();

        }

    }*/



}
