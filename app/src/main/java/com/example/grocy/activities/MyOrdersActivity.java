package com.example.grocy.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocy.Adapters.MyOrdersAdapter;
import com.example.grocy.Models.MyOrdersModel;
import com.example.grocy.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Objects;

public class MyOrdersActivity extends AppCompatActivity {

    RecyclerView recyclerViewOrders;
    FirebaseFirestore firebaseFirestore;
    MyOrdersAdapter myOrdersAdapter;
    DocumentReference documentReference;
    Toolbar toolbarMyOrders;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        userId = getIntent().getStringExtra("user_id");
        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerViewOrders = findViewById(R.id.recyclerOrders);

        toolbarMyOrders = findViewById(R.id.my_orders_toolbar);

        setSupportActionBar(toolbarMyOrders);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        toolbarMyOrders.setNavigationIcon(R.drawable.icon_back_new);
        toolbarMyOrders.setNavigationOnClickListener(v -> onBackPressed());

        documentReference = firebaseFirestore.collection("Users").document(userId);
        Query query = documentReference.collection("myOrder");
        FirestoreRecyclerOptions<MyOrdersModel> options = new FirestoreRecyclerOptions.Builder<MyOrdersModel>().setQuery(query, MyOrdersModel.class).build();

        myOrdersAdapter = new MyOrdersAdapter(options);
        myOrdersAdapter.notifyDataSetChanged();
        recyclerViewOrders.setHasFixedSize(true);
        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewOrders.setAdapter(myOrdersAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        myOrdersAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        myOrdersAdapter.stopListening();
    }
}