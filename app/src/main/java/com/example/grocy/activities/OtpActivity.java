package com.example.grocy.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.grocy.R;
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
    EditText et1,et2,et3,et4,et5, et6;
    private EditText[] editTexts;

    Button btnOtpLogin;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    HashMap hm;

    private String mVerificationId;
    ProgressDialog progressDialog;

    TextView tvResend;
//    private TextWatcher forgotTextWatcher = new TextWatcher() {
//
//        //TODO on remove of element also focus changes
//
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//        }
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            String et1Input = et1.getText().toString().trim();
//            String et2Input = et2.getText().toString().trim();
//            String et3Input = et3.getText().toString().trim();
//            String et4Input = et4.getText().toString().trim();
//            String et5Input = et5.getText().toString().trim();
//            String et6Input = et6.getText().toString().trim();
//            if (et1Input.length() == 1)
//                et2.requestFocus();
//            if (et2Input.length() == 1)
//                et3.requestFocus();
//            if (et3Input.length() == 1)
//                et4.requestFocus();
//            if (et4Input.length() == 1)
//                et5.requestFocus();
//            if (et5Input.length() == 1)
//                et6.requestFocus();
//            btnOtpLogin.setEnabled(!et1Input.isEmpty() && !et2Input.isEmpty() && !et3Input.isEmpty() && !et4Input.isEmpty() && !et5Input.isEmpty() && !et6Input.isEmpty());
//
//            et6.setOnKeyListener((v, keyCode, event) -> {
//                if (keyCode == KeyEvent.KEYCODE_DEL){
//                    et5.requestFocus();
//                }
//                return false;
//            });
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        // Initialize Firebase Authentication object
        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        et3 = findViewById(R.id.et3);
        et4=findViewById(R.id.et4);
        et5=findViewById(R.id.et5);
        et6 = findViewById(R.id.et6);
        editTexts = new EditText[]{et1, et2, et3, et4, et5, et6};
        btnOtpLogin = findViewById(R.id.otp_login_btn);
        tvResend = findViewById(R.id.textViewResend);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser=mAuth.getCurrentUser();
         hm=(HashMap)getIntent().getSerializableExtra("hm");

        PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {

                Log.d(TAG, "onVerificationCompleted:" + credential);

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
                // [END_EXCLUDE]

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(OtpActivity.this, "Invalid phone number.", Toast.LENGTH_LONG).show();
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.",
                            Snackbar.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                Log.d(TAG, "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
//                et1.addTextChangedListener(forgotTextWatcher);
//                et2.addTextChangedListener(forgotTextWatcher);
//                et3.addTextChangedListener(forgotTextWatcher);
//                et4.addTextChangedListener(forgotTextWatcher);
//                et5.addTextChangedListener(forgotTextWatcher);
//                et6.addTextChangedListener(forgotTextWatcher);


                et1.addTextChangedListener(new PinTextWatcher(0));
                et2.addTextChangedListener(new PinTextWatcher(1));
                et3.addTextChangedListener(new PinTextWatcher(2));
                et4.addTextChangedListener(new PinTextWatcher(3));
                et5.addTextChangedListener(new PinTextWatcher(4));
                et6.addTextChangedListener(new PinTextWatcher(5));

                et1.setOnKeyListener(new PinOnKeyListener(0));
                et2.setOnKeyListener(new PinOnKeyListener(1));
                et3.setOnKeyListener(new PinOnKeyListener(2));
                et4.setOnKeyListener(new PinOnKeyListener(3));
                et5.setOnKeyListener(new PinOnKeyListener(0));
                et6.setOnKeyListener(new PinOnKeyListener(1));


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

        tvResend.setOnClickListener(v -> Toast.makeText(OtpActivity.this, "Code is resend!", Toast.LENGTH_SHORT).show());


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

        assert user != null;
        user.delete()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "User account deleted.");

                    }
                });
    }

    //TODO on home pressed


    public class PinTextWatcher implements TextWatcher {

        private int currentIndex;
        private boolean isFirst = false, isLast = false;
        private String newTypedString = "";

        PinTextWatcher(int currentIndex) {
            this.currentIndex = currentIndex;

            if (currentIndex == 0)
                this.isFirst = true;
            else if (currentIndex == editTexts.length - 1)
                this.isLast = true;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            newTypedString = s.subSequence(start, start + count).toString().trim();
        }

        @Override
        public void afterTextChanged(Editable s) {

            String text = newTypedString;
            String et1Input = et1.getText().toString().trim();
            String et2Input = et2.getText().toString().trim();
            String et3Input = et3.getText().toString().trim();
            String et4Input = et4.getText().toString().trim();
            String et5Input = et5.getText().toString().trim();
            String et6Input = et6.getText().toString().trim();

            btnOtpLogin.setEnabled(!et1Input.isEmpty() && !et2Input.isEmpty() && !et3Input.isEmpty() && !et4Input.isEmpty() && !et5Input.isEmpty() && !et6Input.isEmpty());

            /* Detect paste event and set first char */
            if (text.length() > 1)
                text = String.valueOf(text.charAt(0)); // TODO: We can fill out other EditTexts

            editTexts[currentIndex].removeTextChangedListener(this);
            editTexts[currentIndex].setText(text);
            editTexts[currentIndex].setSelection(text.length());
            editTexts[currentIndex].addTextChangedListener(this);

            if (text.length() == 1)
                moveToNext();
            else if (text.length() == 0)
                moveToPrevious();
        }

        private void moveToNext() {
            if (!isLast) {
                editTexts[currentIndex + 1].requestFocus();
                editTexts[currentIndex].clearFocus();
            }


            if (isAllEditTextsFilled()) { // isLast is optional
//                editTexts[currentIndex].clearFocus();
                hideKeyboard();
//                btnOtpLogin.setEnabled(true);
            }
        }

        private void moveToPrevious() {
            if (!isFirst) {
                editTexts[currentIndex - 1].requestFocus();
                editTexts[currentIndex].clearFocus();
            }

        }

        private boolean isAllEditTextsFilled() {
            for (EditText editText : editTexts)
                if (editText.getText().toString().trim().length() == 0)
                    return false;
            return true;
        }

        private void hideKeyboard() {
            if (getCurrentFocus() != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                assert inputMethodManager != null;
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        }

    }

    public class PinOnKeyListener implements View.OnKeyListener {

        private int currentIndex;

        PinOnKeyListener(int currentIndex) {
            this.currentIndex = currentIndex;
        }

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                if (editTexts[currentIndex].getText().toString().isEmpty() && currentIndex != 0)
                    editTexts[currentIndex - 1].requestFocus();
            }
            return false;
        }

    }

}




