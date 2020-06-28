package com.example.grocy.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.grocy.R;

public class PaymentActivity extends AppCompatActivity {

    CardView cardViewDebit, cardViewWallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        cardViewDebit = findViewById(R.id.cardDebit);
        cardViewWallet = findViewById(R.id.card_wallet_pay);

        cardViewDebit.setOnClickListener(v -> {
            Intent intent = new Intent(PaymentActivity.this, CardActivity.class);
            startActivity(intent);
        });

        cardViewWallet.setOnClickListener(v -> {
            Intent intent = new Intent(PaymentActivity.this, WalletActivity.class);
            startActivity(intent);
        });
    }
}