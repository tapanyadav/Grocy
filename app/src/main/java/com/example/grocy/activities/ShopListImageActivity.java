package com.example.grocy.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocy.Adapters.ShopListImageAdapter;
import com.example.grocy.Models.MyOrdersModel;
import com.example.grocy.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Objects;

public class ShopListImageActivity extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    RecyclerView recyclerViewShopList;
    TextView textViewShopListTitle, textViewShopListDetails;
    ShopListImageAdapter shopListImageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list_review);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        recyclerViewShopList = findViewById(R.id.recycler_shop_list);
        textViewShopListDetails = findViewById(R.id.text_shopList_details);
        textViewShopListTitle = findViewById(R.id.text_shopListTitle);

        Toolbar toolbar = findViewById(R.id.review_shop_list_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationIcon(R.drawable.icon_back_new);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        String user_id = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();

        textViewShopListTitle.setText("Select a store to add photo");
        textViewShopListDetails.setText("You will add photo only for those stores from which you order something.");

        Query query = firebaseFirestore.collection("Users").document(user_id).collection("myOrders");

        FirestoreRecyclerOptions<MyOrdersModel> modelFirestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<MyOrdersModel>()
                .setQuery(query, MyOrdersModel.class).build();

        shopListImageAdapter = new ShopListImageAdapter(modelFirestoreRecyclerOptions);
        recyclerViewShopList.setHasFixedSize(true);
        recyclerViewShopList.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewShopList.setAdapter(shopListImageAdapter);
        shopListImageAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onStop() {
        super.onStop();
        shopListImageAdapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        shopListImageAdapter.startListening();
    }
}