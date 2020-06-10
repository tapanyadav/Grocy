package com.example.grocy.activities;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grocy.R;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        findViewById(R.id.btn_fb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicked_btn("https://m.facebook.com/utkarsh.gupta.319452");
            }
        });
        findViewById(R.id.btn_twitter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicked_btn("https:/twitter.com/Utkarsh080?s=09");
            }
        });
        findViewById(R.id.btn_insta).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicked_btn("https://instagram.com/speedster1001");
            }
        });
        findViewById(R.id.btn_linkedin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicked_btn("https://www.linkedin.com/in/utkarsh-gupta-396a49168");
            }
        });
    }
    public void clicked_btn(String url){
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}
