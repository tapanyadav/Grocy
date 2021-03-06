package com.example.grocy.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocy.Adapters.StoresAddedAdapter;
import com.example.grocy.Models.StoresAddedModel;
import com.example.grocy.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Objects;

public class StoresAddedActivity extends AppCompatActivity {

    Toolbar toolbarRequestStores;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    StoresAddedAdapter storesAddedAdapter;
    DocumentReference documentReference;
    RecyclerView recyclerViewStoresAdded;
//    ArrayList<StoresAddedModel> arrayList = new ArrayList();
//    HashMap<String, Object> store_info = new HashMap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stores_added);

        toolbarRequestStores = findViewById(R.id.addedStore_toolbar);
        recyclerViewStoresAdded = findViewById(R.id.recycler_stores_added);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        String userId = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();

        setSupportActionBar(toolbarRequestStores);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        toolbarRequestStores.setNavigationIcon(R.drawable.icon_back_new);
        toolbarRequestStores.setNavigationOnClickListener(v -> onBackPressed());

        documentReference = firebaseFirestore.collection("Users").document(userId);
        Query query = documentReference.collection("storesSuggestions");


        FirestoreRecyclerOptions<StoresAddedModel> storesAddedModelFirestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<StoresAddedModel>()
                .setQuery(query, StoresAddedModel.class).build();

        storesAddedAdapter = new StoresAddedAdapter(storesAddedModelFirestoreRecyclerOptions);
        recyclerViewStoresAdded.setHasFixedSize(true);
        recyclerViewStoresAdded.setLayoutManager(new LinearLayoutManager(this));
        storesAddedAdapter.notifyDataSetChanged();
        recyclerViewStoresAdded.setAdapter(storesAddedAdapter);

//        query.get().addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                for (QueryDocumentSnapshot document : task.getResult()) {
//                    store_info.put(document.getId(), document.getData());
//                }
//                setAdapter();
//            }
//        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        storesAddedAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        storesAddedAdapter.stopListening();
    }
    //    private void setAdapter() {
//        storesAddedAdapter = new StoresAddedAdapter(this, arrayList);
//        recyclerViewStoresAdded.setHasFixedSize(true);
//        recyclerViewStoresAdded.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
//
//        for (Map.Entry mapElement : store_info.entrySet()) {
//            StoresAddedModel storesAddedModel = new StoresAddedModel();
//            String key = (String) mapElement.getKey();
//            HashMap<String, Object> item = (HashMap<String, Object>) mapElement.getValue();
//            storesAddedModel.setStoreAddStatus((String) item.get("storeAddStatus"));
//            storesAddedModel.setStoreImage((String) item.get("storeImage"));
//            storesAddedModel.setStoreLocation((String) item.get("storeLocation"));
//            storesAddedModel.setStoreName((String) item.get("storeName"));
//            arrayList.add(storesAddedModel);
//
//        }
//
//        recyclerViewStoresAdded.setAdapter(storesAddedAdapter);
//        storesAddedAdapter.notifyDataSetChanged();
////        storesAddedAdapter.remove(storesAddedAdapter.getItem(position));
//    }
}