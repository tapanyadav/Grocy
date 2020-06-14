package com.example.grocy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grocy.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        Thread thread=new Thread(){
            public void run(){
                try{
                    sleep(1000);
                    Intent intent =new Intent(SplashActivity.this, WelcomeActivity.class);
                    startActivity(intent);
                    finish();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

}
