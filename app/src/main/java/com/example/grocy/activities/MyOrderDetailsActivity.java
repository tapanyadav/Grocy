package com.example.grocy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.grocy.Adapters.MyOrderDetailItemsAdapter;
import com.example.grocy.Models.MyOrdersModel;
import com.example.grocy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class MyOrderDetailsActivity extends AppCompatActivity {

    TextView textViewOrderId, textViewOrderDateTime, textViewOrderPayment, textViewOrderAddress, textViewStatusDelivered, textViewStatusCancel;
    ImageView imageViewOrderImage;
    RecyclerView recyclerViewDetailsItem;
    MyOrderDetailItemsAdapter myOrderDetailItemsAdapter;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    ArrayList<HashMap<String, Object>> arrayList = new ArrayList<>();
    ArrayList<MyOrdersModel> myOrdersModelArrayList = new ArrayList<>();
    TextView textViewItemsDetailTaxAmount, textViewItemsAmount, textViewTotalAmount;
    CardView cardViewAddPhotoDetails, cardViewAddReviewDetails;


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
        textViewItemsDetailTaxAmount = findViewById(R.id.tax_details_amount);
        textViewItemsAmount = findViewById(R.id.items_amount);
        textViewTotalAmount = findViewById(R.id.total_amount);
        cardViewAddPhotoDetails = findViewById(R.id.card_addPhotoDetails);
        cardViewAddReviewDetails = findViewById(R.id.card_addReviewDetails);


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
        textViewItemsDetailTaxAmount.setText("" + hashMapMyOrders.get("taxAmount"));
        textViewItemsAmount.setText("" + hashMapMyOrders.get("itemAmount"));
        textViewTotalAmount.setText("" + hashMapMyOrders.get("orderAmount"));
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

        assert orderID != null;
        DocumentReference documentReference = firebaseFirestore.collection("Users").document(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())
                .collection("myOrders").document(orderID);

        documentReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                arrayList = (ArrayList<HashMap<String, Object>>) document.getData().get("items");
                setAdapter();
            }
        });

        cardViewAddReviewDetails.setOnClickListener(v -> {
            Intent intent1 = new Intent(this, AddReviewActivity.class);

            intent1.putExtra("storeName", (String) hashMapMyOrders.get("shopName"));
            intent1.putExtra("storeImage", (String) hashMapMyOrders.get("shopImage"));
            intent1.putExtra("storeAddress", (String) hashMapMyOrders.get("shopAddress"));

            Toast.makeText(this, "Name: " + (String) hashMapMyOrders.get("shopName"), Toast.LENGTH_SHORT).show();
            startActivity(intent1);
        });
        cardViewAddPhotoDetails.setOnClickListener(v -> {
            Intent intent1 = new Intent(this, AddPhotoActivity.class);
            intent1.putExtra("storeName", (String) hashMapMyOrders.get("shopName"));
            intent1.putExtra("storeImage", (String) hashMapMyOrders.get("shopImage"));
            intent1.putExtra("storeAddress", (String) hashMapMyOrders.get("shopAddress"));
            startActivity(intent1);
        });

    }

    private void setAdapter() {

        myOrderDetailItemsAdapter = new MyOrderDetailItemsAdapter(this, arrayList);
        recyclerViewDetailsItem.setHasFixedSize(true);
        recyclerViewDetailsItem.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewDetailsItem.setAdapter(myOrderDetailItemsAdapter);
        myOrderDetailItemsAdapter.notifyDataSetChanged();
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        myOrderDetailItemsAdapter.startListening();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        myOrderDetailItemsAdapter.stopListening();
//    }
}