package com.example.grocy.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.grocy.Adapters.ShopItemsAdapter;
import com.example.grocy.Models.ShopItemsModel;
import com.example.grocy.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import java.util.Objects;

public class ShopDetailsActivity extends AppCompatActivity implements EventListener<DocumentSnapshot> {

    FirebaseFirestore firebaseFirestore;
    RecyclerView recyclerViewShopDetails;
    DocumentReference documentReference;

    ShopItemsAdapter shopItemsAdapter;

    private ImageView imageViewShopItem;
    private TextView textViewPrice, textViewItemDesc, textViewItemName, textViewItemQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        setContentView(R.layout.activity_shop_details);

        getWindow().setStatusBarColor(Color.TRANSPARENT);
        Toolbar toolbar = findViewById(R.id.shop_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("Gupta Grocery Store");

        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerViewShopDetails = findViewById(R.id.shop_items_recycler);

        imageViewShopItem = findViewById(R.id.item_image);
        textViewItemDesc = findViewById(R.id.tv_item_description);
        textViewItemName = findViewById(R.id.tv_name_item);
        textViewItemQuantity = findViewById(R.id.tv_quantity_item);
        textViewPrice = findViewById(R.id.tv_item_price);

        String shopsId = Objects.requireNonNull(Objects.requireNonNull(getIntent().getExtras()).get("shopId")).toString();

        documentReference = firebaseFirestore.collection("ShopsMain").document(shopsId);

        Query query = documentReference.collection("Items");
        FirestoreRecyclerOptions<ShopItemsModel> shopItemsModelFirestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<ShopItemsModel>()
                .setQuery(query, ShopItemsModel.class).build();

        shopItemsAdapter = new ShopItemsAdapter(shopItemsModelFirestoreRecyclerOptions);
        shopItemsAdapter.notifyDataSetChanged();
        recyclerViewShopDetails.setHasFixedSize(true);
        recyclerViewShopDetails.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewShopDetails.setAdapter(shopItemsAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        shopItemsAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        shopItemsAdapter.stopListening();
    }

    @Override
    public void onEvent(DocumentSnapshot snapshot, FirebaseFirestoreException e) {
        if (e != null) {
            Log.w("CdActivity", "cat details:onEvent", e);
            return;
        }

        addShopItemsData(Objects.requireNonNull(snapshot.toObject(ShopItemsModel.class)));
    }

    private void addShopItemsData(ShopItemsModel shopItemsModel) {

        Glide.with(imageViewShopItem.getContext())
                .load(shopItemsModel.getItemsImage())
                .into(imageViewShopItem);

        textViewItemName.setText(shopItemsModel.getItemsProductName());
        textViewPrice.setText(shopItemsModel.getItemsPrice());
        textViewItemQuantity.setText(shopItemsModel.getItemsQuantity());
        textViewItemDesc.setText(shopItemsModel.getItemsProductDescription());
    }

}