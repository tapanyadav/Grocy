package com.example.grocy.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class OtpActivity extends AppCompatActivity {

    private static final String TAG ="OTP ACTIVITY" ;
    EditText et1,et2,et3,et4,et5,et6;

    Button btnOtpLogin;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    HashMap hm;

    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    ProgressDialog progressDialog;

    TextView tvResend;
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
            String et5Input = et5.getText().toString().trim();
            String et6Input = et6.getText().toString().trim();
            if (et1Input.length() == 1)
                et2.requestFocus();
            if (et2Input.length() == 1)
                et3.requestFocus();
            if (et3Input.length() == 1)
                et4.requestFocus();
            if (et4Input.length() == 1)
                et5.requestFocus();
            if (et5Input.length() == 1)
                et6.requestFocus();
            btnOtpLogin.setEnabled(!et1Input.isEmpty() && !et2Input.isEmpty() && !et3Input.isEmpty() && !et4Input.isEmpty() && !et5Input.isEmpty() && !et6Input.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        // Initialize Firebase Authentication object
        et1=findViewById(R.id.et1);
        et2=findViewById(R.id.et2);
        et3=findViewById(R.id.et3);
        et4=findViewById(R.id.et4);
        et5=findViewById(R.id.et5);
        et6=findViewById(R.id.et6);
        btnOtpLogin = findViewById(R.id.otp_login_btn);
        tvResend = findViewById(R.id.textViewResend);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser=mAuth.getCurrentUser();
         hm=(HashMap)getIntent().getSerializableExtra("hm");



        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d(TAG, "onVerificationCompleted:" + credential);
                // [START_EXCLUDE silent]
                mVerificationInProgress = false;
                // [END_EXCLUDE]
//123456
                // [START_EXCLUDE silent]
                // Update the UI and attempt sign in with the phone credential
                //updateUI(STATE_VERIFY_SUCCESS, credential);
                // [END_EXCLUDE]
                String str = credential.getSmsCode();
                assert str != null;
                et1.setText("" + str.charAt(0));
                et2.setText("" + str.charAt(1));
                et3.setText("" + str.charAt(2));
                et4.setText("" + str.charAt(3));
                et5.setText("" + str.charAt(4));
                et6.setText("" + str.charAt(5));

                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                signInWithPhoneAuthCredential(credential);
            }


            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);
                // [START_EXCLUDE silent]
                mVerificationInProgress = false;
                // [END_EXCLUDE]

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // [START_EXCLUDE]
                    //mPhoneNumberField.setError("Invalid phone number.");
                    Toast.makeText(OtpActivity.this,"Invalid phone number.",Toast.LENGTH_LONG).show();
                    // [END_EXCLUDE]
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // [START_EXCLUDE]
                    Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.",
                            Snackbar.LENGTH_SHORT).show();
                    // [END_EXCLUDE]
                }

                // Show a message and update the UI
                // [START_EXCLUDE]
                //updateUI(STATE_VERIFY_FAILED);
                // [END_EXCLUDE]
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;
                et1.addTextChangedListener(forgotTextWatcher);
                et2.addTextChangedListener(forgotTextWatcher);
                et3.addTextChangedListener(forgotTextWatcher);
                et4.addTextChangedListener(forgotTextWatcher);
                et5.addTextChangedListener(forgotTextWatcher);
                et6.addTextChangedListener(forgotTextWatcher);

                // [START_EXCLUDE]
                // Update UI
                //updateUI(STATE_CODE_SENT);
                // [END_EXCLUDE]
            }
        };

        PhoneAuthProvider.getInstance().verifyPhoneNumber((String) Objects.requireNonNull(hm.get("phone_number")), 60, TimeUnit.SECONDS, OtpActivity.this, mCallbacks);

        btnOtpLogin.setOnClickListener(v -> {

            String code=et1.getText().toString()+et2.getText().toString()+et3.getText().toString()+et4.getText().toString()+et5.getText().toString()+et6.getText().toString();
            try {
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
                signInWithPhoneAuthCredential(credential);

            }catch (Exception e){
                Toast toast = Toast.makeText(OtpActivity.this, "Verification Code is wrong", Toast.LENGTH_SHORT);

                toast.show();
            }
        });

        tvResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(OtpActivity.this, "Code is resend!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        showProgress();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(OtpActivity.this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("TAG", "signInWithCredential:success");

                        mCurrentUser = Objects.requireNonNull(task.getResult()).getUser();

                        Intent intent=new Intent(OtpActivity.this, StartLocationActivity.class);
                        startActivity(intent);
                        finish();
                        // ...
                    } else {
                        // Sign in failed, display a message and update the UI
                        System.out.println("Sign in  failed");
                        Log.w("TAG", "signInWithCredential:failure", task.getException());
                        Toast.makeText(OtpActivity.this, Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_LONG).show();
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            // The verification code entered was invalid
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    private void showProgress() {
        progressDialog = new ProgressDialog(OtpActivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.process_dialog);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(
                android.R.color.transparent
        );
    }

    @Override
    public void onBackPressed() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User account deleted.");

                        }
                    }
                });
    }

    //TODO on home pressed
}


