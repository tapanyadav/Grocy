package com.example.grocy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class OtpActivity extends AppCompatActivity {

    private static final String TAG ="OTP ACTIVITY" ;
    EditText et1,et2,et3,et4,et5,et6;

    Button btnOtpLogin;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    HashMap<String,String> hm;


    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    TextView tvResend;
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
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser=mAuth.getCurrentUser();
         hm=(HashMap)getIntent().getSerializableExtra("hm");

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
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

                // [START_EXCLUDE silent]
                // Update the UI and attempt sign in with the phone credential
                //updateUI(STATE_VERIFY_SUCCESS, credential);
                // [END_EXCLUDE]
                signInWithPhoneAuthCredential(credential);
            }


            @Override
            public void onVerificationFailed(FirebaseException e) {
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

                // [START_EXCLUDE]
                // Update UI
                //updateUI(STATE_CODE_SENT);
                // [END_EXCLUDE]
            }
        };

        btnOtpLogin = findViewById(R.id.otp_login_btn);
        tvResend = findViewById(R.id.textViewResend);

        PhoneAuthProvider.getInstance().verifyPhoneNumber(hm.get("phone_number"),60, TimeUnit.SECONDS,OtpActivity.this,mCallbacks);

        btnOtpLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code=et1.getText().toString()+et2.getText().toString()+et3.getText().toString()+et4.getText().toString()+et5.getText().toString()+et6.getText().toString();
                try {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
                    signInWithPhoneAuthCredential(credential);
                }catch (Exception e){
                    Toast toast = Toast.makeText(OtpActivity.this, "Verification Code is wrong", Toast.LENGTH_SHORT);

                    toast.show();
                }

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
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(OtpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");

                            mCurrentUser = task.getResult().getUser();

                            Intent intent=new Intent(OtpActivity.this,MainActivity.class);
                            startActivity(intent);
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            System.out.println("Sign in  failed");
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            Toast.makeText(OtpActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }
}
