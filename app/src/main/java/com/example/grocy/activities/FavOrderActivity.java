package com.example.grocy.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import com.example.grocy.Adapters.FavOrdersAdapter;
import com.example.grocy.Models.OrderModel;
import com.example.grocy.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class FavOrderActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
    FavOrdersAdapter favAdapter;
    DocumentReference documentReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_order);
        recyclerView=findViewById(R.id.fav_recyclerview);
        firebaseFirestore=FirebaseFirestore.getInstance();


        documentReference = firebaseFirestore.collection("Users").document("1o5bK89YbUeXPznKbIjNc7U4gqS2");
        Query query= documentReference.collection("myOrder");
        FirestoreRecyclerOptions<OrderModel> options= new FirestoreRecyclerOptions.Builder<OrderModel>().setQuery(query, OrderModel.class).build();




        favAdapter=new FavOrdersAdapter(options);
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