package com.example.grocy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grocy.R;

public class EmailSentActivity extends AppCompatActivity {

    int counter=5;
    TextView textViewTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_sent);

        textViewTimer=findViewById(R.id.textViewEmailTimer);

    }

    @Override
    protected void onStart() {
        super.onStart();
        new CountDownTimer(5000, 1000){
            public void onTick(long millisUntilFinished){
                textViewTimer.setText("0"+counter);
                counter--;
            }
            public  void onFinish(){
                Intent intent=new Intent(EmailSentActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }.start();
    }
}
