package com.example.grocy.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.grocy.Adapters.OrdersAdapter;
import com.example.grocy.Models.OrderModel;
import com.example.grocy.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyOrderActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
    OrdersAdapter ordersAdapter;
    DocumentReference documentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_recyclerview);
        recyclerView=findViewById(R.id.recyclerview);
        firebaseFirestore=FirebaseFirestore.getInstance();


        documentReference = firebaseFirestore.collection("Users").document("1o5bK89YbUeXPznKbIjNc7U4gqS2");
        Query query= documentReference.collection("myOrder");
        FirestoreRecyclerOptions<OrderModel> options= new FirestoreRecyclerOptions.Builder<OrderModel>().setQuery(query, OrderModel.class).build();
        ordersAdapter=new OrdersAdapter(options);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(ordersAdapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        ordersAdapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        ordersAdapter.startListening();
    }
}
