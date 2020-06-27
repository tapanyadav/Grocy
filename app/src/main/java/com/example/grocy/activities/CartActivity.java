package com.example.grocy.activities;

import android.os.Bundle;
import android.widget.TextView;

import com.example.grocy.Adapters.CartItemsAdapter;
import com.example.grocy.Models.CartItemsModel;
import com.example.grocy.R;

import java.util.ArrayList;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CartActivity extends AppCompatActivity {


    RecyclerView cart_items_recycler;

    CartItemsAdapter cartItemsAdapter;

    ArrayList<CartItemsModel> arrayList;

    TextView items_amount, tax_amount, total_amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Toolbar toolbar = findViewById(R.id.cart_toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationIcon(R.drawable.icon_back_new);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        arrayList = new ArrayList();
        arrayList = (ArrayList<CartItemsModel>) getIntent().getSerializableExtra("added_items");
        cart_items_recycler = findViewById(R.id.cart_items_recycler);
        cart_items_recycler.setHasFixedSize(false);

        cart_items_recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        cartItemsAdapter = new CartItemsAdapter(this, arrayList);

        cart_items_recycler.setAdapter(cartItemsAdapter);

        System.out.println("--------------------------------------");
        System.out.println(arrayList.toString());
        System.out.println("--------------------------------------");

        cartItemsAdapter.notifyDataSetChanged();
        double itemsAmt = 0, taxAmt = 0, totalAmt = 0;
        for (int i = 0; i < arrayList.size(); i++) {
            itemsAmt = itemsAmt + Integer.parseInt(arrayList.get(i).getItemsPrice()) * arrayList.get(i).getItemCount();
        }
        taxAmt = 0.02 * itemsAmt;
        totalAmt = itemsAmt + taxAmt;

        items_amount = findViewById(R.id.items_amount);
        tax_amount = findViewById(R.id.tax_amount);
        total_amount = findViewById(R.id.total_amount);

        items_amount.setText(String.format("%.2f", itemsAmt));
        tax_amount.setText(String.format("%.2f", taxAmt));
        total_amount.setText(String.format("%.2f", totalAmt));


    }
}