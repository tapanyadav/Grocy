package com.example.grocy.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocy.Adapters.CartItemsAdapter;
import com.example.grocy.Models.CartItemsModel;
import com.example.grocy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class CartActivity extends AppCompatActivity {


    RecyclerView cart_items_recycler;

    CartItemsAdapter cartItemsAdapter;

    ArrayList<CartItemsModel> arrayList;

    TextView items_amount, tax_amount, total_amount, user_name, user_phone, user_address;

    FirebaseAuth firebaseAuth;
    Button order_button;
    int orderCount;

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
        firebaseAuth = FirebaseAuth.getInstance();
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

        items_amount = findViewById(R.id.items_amount);
        tax_amount = findViewById(R.id.tax_amount);
        total_amount = findViewById(R.id.total_amount);

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
        hm.put("orderPaymentMode", "Upi payment");
        hm.put("deliveryStatus", "Order cancelled");
        hm.put("userAddress", (String) MainActivity.proile_activity_data.get("address"));
        // hm.put("dateTime", FieldValue.serverTimestamp().toString());
        // hm.put("dateTime", FieldValue.serverTimestamp().toString());
        hm.put("shopName", (String) ShopDetailsActivity.shop_detail.get("shopName"));
        hm.put("shopImage", (String) ShopDetailsActivity.shop_detail.get("shopImage"));

        HashMap<String, Object> hashMapOrderCount = new HashMap<>();

        firebaseFirestore.collection("Users").document(firebaseAuth.getCurrentUser().getUid()).get().addOnCompleteListener(task -> {
            DocumentSnapshot documentSnapshot = task.getResult();
            if (documentSnapshot.getData().containsKey("ordersCount")) {
                orderCount = Integer.parseInt("" + documentSnapshot.get("ordersCount"));
                //Toast.makeText(this, "Bookmarks:"+documentSnapshot.get("noOfBookmarks"), Toast.LENGTH_SHORT).show();
            } else {
                orderCount = 0;
            }
            Toast.makeText(this, "Count: " + orderCount, Toast.LENGTH_SHORT).show();
        });


        order_button.setOnClickListener(v -> {


            firebaseFirestore.collection("Users").document((String) MainActivity.proile_activity_data.get("userId"))
                    .collection("myOrders").add(hm).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    orderCount += 1;
                    hashMapOrderCount.put("ordersCount", orderCount);
                    firebaseFirestore.collection("Users").document((String) MainActivity.proile_activity_data.get("userId")).update(hashMapOrderCount).addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            Toast.makeText(this, "Order Count successfully updated", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Firestore error!", Toast.LENGTH_SHORT).show();

                        }
                    });
                    Toast.makeText(this, "Data Submitted", Toast.LENGTH_SHORT).show();
                }

            });


        });

    }
}