package com.example.grocy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grocy.Adapters.CartItemsAdapter;
import com.example.grocy.Models.CartItemsModel;
import com.example.grocy.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CartActivity extends AppCompatActivity {


    RecyclerView cart_items_recycler;

    CartItemsAdapter cartItemsAdapter;

    ArrayList<CartItemsModel> arrayList;

    public static TextView items_amount, tax_amount, total_amount, user_name, user_phone, user_address;

    Button order_button;

    FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        user_name = findViewById(R.id.userName);
        user_phone = findViewById(R.id.userPhone);
        user_address = findViewById(R.id.userAddress);
        order_button = findViewById(R.id.order_button);

        Toolbar toolbar = findViewById(R.id.cart_toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationIcon(R.drawable.icon_back_new);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        firebaseFirestore = FirebaseFirestore.getInstance();
//
//        buttonOrder.setOnClickListener(v -> {
//            Intent intent = new Intent(CartActivity.this, PaymentActivity.class);
//            startActivity(intent);
//        });

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

        items_amount = findViewById(R.id.items_amount1);
        tax_amount = findViewById(R.id.tax_amount1);
        total_amount = findViewById(R.id.total_amount1);

        items_amount.setText(String.format("%.2f", itemsAmt));
        tax_amount.setText(String.format("%.2f", taxAmt));
        total_amount.setText(String.format("%.2f", totalAmt));
        user_name.setText(MainActivity.proile_activity_data.get("fName").toString() + ", ");
        user_phone.setText(MainActivity.proile_activity_data.get("pNumber").toString().substring(3));
        user_address.setText((String) MainActivity.proile_activity_data.get("address"));

        HashMap<String, Object> hm = new HashMap();
        hm.put("items", arrayList);
        hm.put("favOrder", false);
        hm.put("orderAmount", totalAmt);
        hm.put("taxAmount", taxAmt);
        hm.put("itemAmount", itemsAmt);
        hm.put("userAddress", (String) MainActivity.proile_activity_data.get("address"));
        Calendar cal = Calendar.getInstance();
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");
        String localTime = date.format(currentLocalTime);
        hm.put("dateTime", localTime);
        hm.put("shopName", (String) ShopDetailsActivity.shop_detail.get("shopName"));
        hm.put("shopImage", (String) ShopDetailsActivity.shop_detail.get("shopImage"));
        hm.put("shopId", ShopDetailsActivity.shopsId);
        hm.put("shopKeeperId", ShopDetailsActivity.shop_detail.get("shopKeeperId"));
        String[] dateTime = localTime.split(" ");
        String[] dates = dateTime[0].split("/");
        String[] times = dateTime[1].split(":");
        String orderNumberId = dates[0] + dates[1] + times[0] + times[1] + times[2];
        hm.put("orderNumberId", orderNumberId);
        order_button.setOnClickListener(v -> {
            order_button.setEnabled(false);
            firebaseFirestore.collection("Users").document((String) MainActivity.proile_activity_data.get("userId"))
                    .collection("myOrders").add(hm).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    HashMap<String, Object> temp = new HashMap();
                    int currentOrdersCount = Integer.parseInt("" + MainActivity.proile_activity_data.get("ordersCount"));
                    temp.put("ordersCount", currentOrdersCount + 1);
                    firebaseFirestore.collection("Users").document((String) MainActivity.proile_activity_data.get("userId")).update(temp).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            MainActivity.proile_activity_data.put("ordersCount", currentOrdersCount + 1);
                            Toast.makeText(CartActivity.this, "Count updated", Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CartActivity.this, "Count not updated" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
//
                    hm.put("userName", MainActivity.proile_activity_data.get("fName"));
//                    hm.put("ordersCount", currentOrdersCount+1);

                    DocumentReference document = firebaseFirestore.collection("ShopKeeper").document((String) ShopDetailsActivity.shop_detail.get("shopKeeperId"));
                    document.collection("Notifications").document(documentReference.getId()).set(hm).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(CartActivity.this, "Order Placed", Toast.LENGTH_LONG).show();
                            order_button.setEnabled(true);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CartActivity.this, "Order not Placed" + e.getMessage(), Toast.LENGTH_LONG).show();
                            order_button.setEnabled(true);
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    order_button.setEnabled(true);
                }
            });
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CartActivity.this, ShopDetailsActivity.class);
        intent.putExtra("shopId", ShopDetailsActivity.shopsId);
        startActivity(intent);
        finish();
    }
}