package com.example.grocy.activities;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocy.Adapters.CategoriesDetailsAdapter;
import com.example.grocy.Models.CategoriesDetailsModel;
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

public class CategoriesDetailsActivity extends AppCompatActivity implements EventListener<DocumentSnapshot> {

    FirebaseFirestore firebaseFirestore;
    RecyclerView recyclerViewCatDetails;
    DocumentReference documentReference;
    CategoriesDetailsAdapter categoriesDetailsAdapter;
    private ImageView imageViewCatShopImage, imageViewCatShopStatusBackground;
    private TextView textViewCatShopName, textViewCatShopStatus, textViewCatShopRating, textViewCatShopAddress, textViewCatShopOff, textViewCatShopLimits, textViewCatShopType, textViewCatShopCat;
    private Context context;


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

        documentReference = firebaseFirestore.collection("Categories").document(recResId);

        Query query = documentReference.collection("subCategory").orderBy("shopArrange", Query.Direction.ASCENDING).limit(4);

        FirestoreRecyclerOptions<CategoriesDetailsModel> categoriesDetailsModelFirestoreRecyclerOptions = new FirestoreRecyclerOptions
                .Builder<CategoriesDetailsModel>().setQuery(query, CategoriesDetailsModel.class).build();

        categoriesDetailsAdapter = new CategoriesDetailsAdapter(categoriesDetailsModelFirestoreRecyclerOptions);
        categoriesDetailsAdapter.notifyDataSetChanged();
        categoriesDetailsAdapter.setHasStableIds(true);
        recyclerViewCatDetails.setHasFixedSize(true);
        recyclerViewCatDetails.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCatDetails.setAdapter(categoriesDetailsAdapter);
        recyclerViewCatDetails.setItemViewCacheSize(20);
    }

    @Override
    protected void onStart() {
        super.onStart();
        categoriesDetailsAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        categoriesDetailsAdapter.stopListening();
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
        textViewCatShopRating.setText(categoriesDetailsModel.getShopRating());
        textViewCatShopAddress.setText(categoriesDetailsModel.getShopAddress());
        textViewCatShopName.setText(categoriesDetailsModel.getShopName());

    }
}
