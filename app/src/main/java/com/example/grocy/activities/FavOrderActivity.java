package com.example.grocy.activities;

import android.os.Bundle;

import com.example.grocy.Adapters.FavOrdersAdapter;
import com.example.grocy.Models.MyOrdersModel;
import com.example.grocy.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FavOrderActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
    FavOrdersAdapter favAdapter;
    DocumentReference documentReference;

    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_order);
        recyclerView = findViewById(R.id.fav_recyclerview);
        firebaseFirestore = FirebaseFirestore.getInstance();


        Toolbar toolbarFavOrders = findViewById(R.id.fav_orders_toolbar);

        setSupportActionBar(toolbarFavOrders);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        toolbarFavOrders.setNavigationIcon(R.drawable.icon_back_new);
        toolbarFavOrders.setNavigationOnClickListener(v -> onBackPressed());

        userId = getIntent().getStringExtra("user_id");

        documentReference = firebaseFirestore.collection("Users").document(userId);
        Query query = documentReference.collection("myOrders").whereEqualTo("favOrder", true);
        FirestoreRecyclerOptions<MyOrdersModel> options = new FirestoreRecyclerOptions.Builder<MyOrdersModel>().setQuery(query, MyOrdersModel.class).build();


        favAdapter = new FavOrdersAdapter(options);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(favAdapter);


    }

    @Override
    protected void onStop() {
        super.onStop();
        favAdapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        favAdapter.startListening();
    }


}