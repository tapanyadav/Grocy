package com.example.grocy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.grocy.Adapters.MyOrderDetailItemsAdapter;
import com.example.grocy.Models.MyOrdersModel;
import com.example.grocy.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MyOrderDetailsActivity extends AppCompatActivity {

    TextView textViewOrderId, textViewOrderDateTime, textViewOrderPayment, textViewOrderAddress, textViewStatusDelivered, textViewStatusCancel;
    ImageView imageViewOrderImage;
    RecyclerView recyclerViewDetailsItem;
    MyOrderDetailItemsAdapter myOrderDetailItemsAdapter;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_details);

        textViewOrderId = findViewById(R.id.order_id);
        textViewOrderDateTime = findViewById(R.id.text_detail_order_time);
        textViewOrderPayment = findViewById(R.id.orderPaymentMode);
        textViewOrderAddress = findViewById(R.id.orderDeliveredAddress);
        imageViewOrderImage = findViewById(R.id.imageShopOrders);
        textViewStatusDelivered = findViewById(R.id.status_delivered);
        textViewStatusCancel = findViewById(R.id.status_cancel);
        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerViewDetailsItem = findViewById(R.id.recycler_details_items);
        firebaseAuth = FirebaseAuth.getInstance();


        Query query = firebaseFirestore.collection("Users").document(firebaseAuth.getCurrentUser().getUid()).collection("myOrders");

        FirestoreRecyclerOptions<MyOrdersModel> modelFirestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<MyOrdersModel>()
                .setQuery(query, MyOrdersModel.class).build();

        myOrderDetailItemsAdapter = new MyOrderDetailItemsAdapter(modelFirestoreRecyclerOptions);
        recyclerViewDetailsItem.setHasFixedSize(true);
        recyclerViewDetailsItem.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewDetailsItem.setAdapter(myOrderDetailItemsAdapter);
        myOrderDetailItemsAdapter.notifyDataSetChanged();


        Toolbar toolbar = findViewById(R.id.my_order_details_toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationIcon(R.drawable.icon_back_new);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        Intent intent = getIntent();
        @SuppressWarnings("unchecked")
        HashMap<String, Object> hashMapMyOrders = (HashMap<String, Object>) intent.getSerializableExtra("myOrderMap");
        String orderID = intent.getStringExtra("getOrderId");

        assert hashMapMyOrders != null;
        textViewOrderId.setText(orderID);
        textViewOrderDateTime.setText("" + hashMapMyOrders.get("orderDateTime"));
        textViewOrderAddress.setText((String) hashMapMyOrders.get("userAddress"));
        textViewOrderPayment.setText((String) hashMapMyOrders.get("orderPaymentMode"));
        Glide.with(this).load(hashMapMyOrders.get("shopImage")).into(imageViewOrderImage);
        if (Objects.equals(hashMapMyOrders.get("deliveryStatus"), "Delivered")) {
            textViewStatusDelivered.setText((String) hashMapMyOrders.get("deliveryStatus"));
            textViewStatusDelivered.setVisibility(View.VISIBLE);
            textViewStatusCancel.setVisibility(View.INVISIBLE);

        } else {
            textViewStatusCancel.setText((String) hashMapMyOrders.get("deliveryStatus"));
            textViewStatusCancel.setVisibility(View.VISIBLE);
            textViewStatusDelivered.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        myOrderDetailItemsAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        myOrderDetailItemsAdapter.stopListening();
    }
}