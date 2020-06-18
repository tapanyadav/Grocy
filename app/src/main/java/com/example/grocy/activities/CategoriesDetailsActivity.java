package com.example.grocy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grocy.Adapters.CategoriesDetailsAdapter;
import com.example.grocy.Adapters.ShopsAdapter;
import com.example.grocy.Models.CategoriesDetailsModel;
import com.example.grocy.Models.ShopsModel;
import com.example.grocy.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CategoriesDetailsActivity extends AppCompatActivity implements EventListener<DocumentSnapshot> {

    FirebaseFirestore firebaseFirestore;
    RecyclerView recyclerViewCatDetails;
    DocumentReference documentReference;
    CategoriesDetailsAdapter categoriesDetailsAdapter;
    private ImageView imageViewCatShopImage, imageViewCatShopStatusBackground;
    private TextView textViewCatShopName, textViewCatShopStatus, textViewCatShopRating, textViewCatShopAddress, textViewCatShopOff, textViewCatShopLimits, textViewCatShopType, textViewCatShopCat;


    FirestoreRecyclerOptions<ShopsModel> shopsModelFirestoreRecyclerOptions;
    private ShopsAdapter shopsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_details);

        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerViewCatDetails = findViewById(R.id.recycler_categories_all);

        imageViewCatShopImage = findViewById(R.id.imageCatDetails);
        imageViewCatShopStatusBackground = findViewById(R.id.backgroundStatusImageCat);
        textViewCatShopName = findViewById(R.id.shop_name_cat);
        textViewCatShopStatus = findViewById(R.id.shop_status_cat);
        textViewCatShopAddress = findViewById(R.id.shop_address_cat);
        textViewCatShopRating = findViewById(R.id.shop_rating_cat);
        textViewCatShopLimits = findViewById(R.id.tv_limit_shop_cat);
        textViewCatShopOff = findViewById(R.id.shop_off_cat);
        textViewCatShopType = findViewById(R.id.tv_type_shop_cat);
        textViewCatShopCat = findViewById(R.id.shop_category_cat);

        String recResId = Objects.requireNonNull(Objects.requireNonNull(getIntent().getExtras()).get("resId")).toString();
        String catType = Objects.requireNonNull(Objects.requireNonNull(getIntent().getExtras()).get("catType")).toString();
        System.out.println(catType);
        documentReference = firebaseFirestore.collection("Categories").document(recResId);

//        PagedList.Config config = new PagedList.Config.Builder()
//                .setInitialLoadSizeHint(4)
//                .setPageSize(3)
//                .build();

//        Query query = documentReference.collection("subCategory").orderBy("shopArrange", Query.Direction.ASCENDING);
        Query queryShops = firebaseFirestore.collection("ShopsMain").whereEqualTo("shopCategory", catType).orderBy("shopArrange");
        FirestoreRecyclerOptions<CategoriesDetailsModel> categoriesDetailsModelFirestoreRecyclerOptions = new FirestoreRecyclerOptions
                .Builder<CategoriesDetailsModel>().setQuery(queryShops, CategoriesDetailsModel.class).build();

        categoriesDetailsAdapter = new CategoriesDetailsAdapter(categoriesDetailsModelFirestoreRecyclerOptions);
        categoriesDetailsAdapter.notifyDataSetChanged();
        recyclerViewCatDetails.setHasFixedSize(true);
        recyclerViewCatDetails.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCatDetails.setAdapter(categoriesDetailsAdapter);
        categoriesDetailsAdapter.setOnListItemClick((snapshot, position) -> {
            String shopId = snapshot.getId();
            Toast.makeText(this, "Position: " + position + " and Id is " + shopId, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CategoriesDetailsActivity.this, ShopDetailsActivity.class);
            intent.putExtra("shopId", shopId);
            startActivity(intent);
        });
//        shopsModelFirestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<ShopsModel>()
//                .setQuery(queryShops, ShopsModel.class).build();
//        shopsAdapter = new ShopsAdapter(shopsModelFirestoreRecyclerOptions);
//        shopsAdapter.notifyDataSetChanged();
//        shopsAdapter.setHasStableIds(true);

    }

    @Override
    protected void onStart() {
        super.onStart();
        categoriesDetailsAdapter.startListening();
//        shopsAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        categoriesDetailsAdapter.stopListening();
//        shopsAdapter.stopListening();
    }

    @Override
    public void onEvent(DocumentSnapshot snapshot, FirebaseFirestoreException e) {
        if (e != null) {
            Log.w("CdActivity", "cat details:onEvent", e);
            return;
        }

        addUserData(Objects.requireNonNull(snapshot.toObject(CategoriesDetailsModel.class)));
    }

    private void addUserData(CategoriesDetailsModel categoriesDetailsModel) {

        Picasso.get().load(categoriesDetailsModel.getShopImage()).into(imageViewCatShopImage);
        Picasso.get().load(categoriesDetailsModel.getShopStatusBackground()).into(imageViewCatShopStatusBackground);
        textViewCatShopCat.setText(categoriesDetailsModel.getShopCategory());
        textViewCatShopType.setText(categoriesDetailsModel.getShopType());
        textViewCatShopOff.setText(categoriesDetailsModel.getShopOff());
        textViewCatShopLimits.setText(categoriesDetailsModel.getShopLimits());
        textViewCatShopStatus.setText(categoriesDetailsModel.getShopStatus());
        textViewCatShopRating.setText(""+categoriesDetailsModel.getShopRating());
        textViewCatShopAddress.setText(categoriesDetailsModel.getShopAddress());
        textViewCatShopName.setText(categoriesDetailsModel.getShopName());

    }
}
