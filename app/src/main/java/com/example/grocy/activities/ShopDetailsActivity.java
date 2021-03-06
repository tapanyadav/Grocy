package com.example.grocy.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.grocy.Adapters.ShopItemsCategoryAdapter;
import com.example.grocy.Models.ItemVariantsModel;
import com.example.grocy.Models.ShopItemsCategoryModel;
import com.example.grocy.Models.ShopItemsModel;
import com.example.grocy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

public class ShopDetailsActivity extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore;
    RecyclerView recyclerViewShopDetails;
    DocumentReference documentReference;

    SortedSet<String> item_categories_name = new TreeSet();

    HashMap<String, Object> item_list = new HashMap();

    ArrayList<ShopItemsCategoryModel> arrayList;

    ShopItemsCategoryAdapter shopItemsCategoryAdapter;
    static HashMap<String, Object> shop_detail = new HashMap();

    TextView textViewShopName, textViewShopAddress, textViewShopTime, textViewShopCategoryType, textViewShopOff, textViewShopRating;
    ImageView imageViewCollapseShopImage;
    String shopsId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.WHITE);
        setContentView(R.layout.activity_shop_details);
        Toolbar toolbar = findViewById(R.id.shop_toolbar);
        imageViewCollapseShopImage = findViewById(R.id.recyclerIvShopDetails);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        toolbar.setNavigationIcon(R.drawable.icon_back_new);
//        toolbar.setContentInsetStartWithNavigation(R.drawable.icon_back_new);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("Gupta Grocery Store");
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.black));

        firebaseFirestore = FirebaseFirestore.getInstance();
//        recyclerViewShopDetails = findViewById(R.id.shop_items_recycler);

        textViewShopName = findViewById(R.id.shop_details_name);
        textViewShopAddress = findViewById(R.id.shop_item_address);
        textViewShopCategoryType = findViewById(R.id.shop_category_items);
        textViewShopOff = findViewById(R.id.shop_off_items);
        textViewShopRating = findViewById(R.id.shop_rating_items);


        shopsId = Objects.requireNonNull(Objects.requireNonNull(getIntent().getExtras()).get("shopId")).toString();

        documentReference = firebaseFirestore.collection("ShopsMain").document(shopsId);

        documentReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                shop_detail = (HashMap<String, Object>) task.getResult().getData();
                System.out.println("----------------------------------------------------");

                System.out.println(task.getResult().getData());

                System.out.println("----------------------------------------------------");
                collapsingToolbarLayout.setTitle((String) shop_detail.get("shopName"));
                Glide.with(this).load(shop_detail.get("shopImage")).into(imageViewCollapseShopImage);
                textViewShopName.setText((String) shop_detail.get("shopName"));
                textViewShopAddress.setText((String) shop_detail.get("shopAddress"));
                textViewShopCategoryType.setText((String) shop_detail.get("shopCategory"));
                textViewShopOff.setText((String) shop_detail.get("shopOff"));
                textViewShopRating.setText("" + shop_detail.get("shopRating"));

            } else {
                Toast.makeText(this, task.getException().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        Query query = documentReference.collection("Items");

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                        String itemID = document.getId();
                        item_list.put(document.getId(), document.getData());
                        item_categories_name.add((String) document.getData().get("itemCategory"));

                        DocumentReference variantReference = documentReference.collection("Items").document(document.getId());
                        Query query1 = variantReference.collection("Variants");

                        query1.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    HashMap<String, HashMap> itemVariants = new HashMap<>();
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        System.out.println("----------------------");
                                        System.out.println(document.getId());
                                        System.out.println(document.getData());
                                        HashMap<String, Object> temp = (HashMap<String, Object>) document.getData();
                                        temp.put("itemId", itemID);
                                        System.out.println("----------------------");
                                        itemVariants.put(document.getId(), temp);
                                    }
                                    HashMap<String, Object> hm = (HashMap) item_list.get(itemID);
                                    if (itemVariants.size() != 0) {
                                        hm.put("Variants", itemVariants);
                                    } else {
                                        hm.put("Variants", null);
                                    }
                                    setAdapter();
                                    System.out.println(item_list.toString());
                                    System.out.println("------------------------------------------------------");
                                    System.out.println(item_categories_name);
                                }
                            }
                        });

                    }
                }
            }
        });

        ImageView cart_image = findViewById(R.id.cart_image);
        cart_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShopDetailsActivity.this, CartActivity.class);
                intent.putExtra("added_items", ShopItemsCategoryAdapter.added_items);
                startActivity(intent);
            }
        });
    }

    void setAdapter() {
        arrayList = new ArrayList<>();
        recyclerViewShopDetails = findViewById(R.id.item_category_recycler);
        recyclerViewShopDetails.setHasFixedSize(false);

        recyclerViewShopDetails.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        shopItemsCategoryAdapter = new ShopItemsCategoryAdapter(this, arrayList);

        recyclerViewShopDetails.setAdapter(shopItemsCategoryAdapter);

        setData();

    }

    void setData() {
        for (String category : item_categories_name) {
            ShopItemsCategoryModel shopItemsCategoryModel = new ShopItemsCategoryModel();
            shopItemsCategoryModel.setItem_category_name(category);
            ArrayList<ShopItemsModel> items = new ArrayList();
            for (Map.Entry mapElement : item_list.entrySet()) {
                String key = (String) mapElement.getKey();
                HashMap item = (HashMap) mapElement.getValue();
                if (((String) item.get("itemCategory")).equals(category)) {
                    ShopItemsModel shopItemsModel = new ShopItemsModel();
                    shopItemsModel.setItemsImage((String) item.get("itemsImage"));
                    shopItemsModel.setItemsPrice((String) item.get("itemsPrice"));
                    shopItemsModel.setItemsProductDescription((String) item.get("itemsProductDescription"));
                    shopItemsModel.setItemsQuantity((String) item.get("itemsQuantity"));
                    shopItemsModel.setItemsProductName((String) item.get("itemsProductName"));
                    shopItemsModel.setItemID(key);
                    shopItemsModel.setShopID(shopsId);
                    shopItemsModel.setCount(1);
                    shopItemsModel.setItemVariants((HashMap<String, ItemVariantsModel>) item.get("Variants"));
                    items.add(shopItemsModel);
                }
            }
            shopItemsCategoryModel.setShop_items_list(items);
            arrayList.add(shopItemsCategoryModel);

        }

        shopItemsCategoryAdapter.notifyDataSetChanged();

    }


    @Override
    protected void onStart() {
        super.onStart();
//        shopItemsCategoryAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
//        shopItemsCategoryAdapter.stopListening();
    }

//    @Override
//    public void onEvent(DocumentSnapshot snapshot, FirebaseFirestoreException e) {
//        if (e != null) {
//            Log.w("CdActivity", "cat details:onEvent", e);
//            return;
//        }
//
//        addShopItemsData(Objects.requireNonNull(snapshot.toObject(ShopItemsModel.class)));
//    }
//
//    private void addShopItemsData(ShopItemsModel shopItemsModel) {
//
//        Glide.with(imageViewShopItem.getContext())
//                .load(shopItemsModel.getItemsImage())
//                .into(imageViewShopItem);
//
//        textViewItemName.setText(shopItemsModel.getItemsProductName());
//        textViewPrice.setText(shopItemsModel.getItemsPrice());
//        textViewItemQuantity.setText(shopItemsModel.getItemsQuantity());
//        textViewItemDesc.setText(shopItemsModel.getItemsProductDescription());
//    }

}