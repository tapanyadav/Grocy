package com.example.grocy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    TextView textForgotPass, textSignLog;
    Button btnSignIn;
    ImageView btnLoginGoogle, btnloginfacebook;

    EditText email_login;
    EditText pass_login;
    private DatabaseReference mUserDatabase;
    private FirebaseUser mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        textForgotPass=findViewById(R.id.textForgotPass);
        textSignLog = findViewById(R.id.textViewSignReg);
        btnSignIn = findViewById(R.id.login_btn);
        btnloginfacebook = findViewById(R.id.btnLoginFacebook);
        btnLoginGoogle = findViewById(R.id.btnLoginGoogle);
        email_login=findViewById(R.id.login_email);
        pass_login=findViewById(R.id.login_password);
        textForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,ForgetActivity.class);
                startActivity(intent);
            }
        });

        textSignLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email=email_login.getText().toString();
                String pass=pass_login.getText().toString();


//                mUserDatabase.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        // This method is called once with the initial value and again
//                        // whenever data at this location is updated.
//                        String value = dataSnapshot.getValue(String.class);
//                        Log.d("TAG", "Value is: " + value);
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError error) {
//                        // Failed to read value
//                        Log.w("TAG", "Failed to read value.", error.toException());
//                    }
//                });
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btnLoginGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Google login api", Toast.LENGTH_SHORT).show();
            }
        });
        btnloginfacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Facebook login api", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
