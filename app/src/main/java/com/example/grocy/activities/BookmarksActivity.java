package com.example.grocy.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocy.Adapters.BookmarksAdapter;
import com.example.grocy.Models.MyOrdersModel;
import com.example.grocy.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class BookmarksActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
    BookmarksAdapter bookmarksAdapter;
    DocumentReference documentReference;

    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);

        recyclerView = findViewById(R.id.bookmark_recyclerview);
        firebaseFirestore = FirebaseFirestore.getInstance();
        Toolbar toolbar = findViewById(R.id.bookmarks_toolbar);

        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.icon_back_new);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        userId = getIntent().getStringExtra("user_id");

        documentReference = firebaseFirestore.collection("Users").document(userId);
        Query query = documentReference.collection("myOrders").whereEqualTo("bookmarkStatus", true);
        FirestoreRecyclerOptions<MyOrdersModel> options = new FirestoreRecyclerOptions.Builder<MyOrdersModel>().setQuery(query, MyOrdersModel.class).build();


        bookmarksAdapter = new BookmarksAdapter(options);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(bookmarksAdapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        bookmarksAdapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        bookmarksAdapter.startListening();
    }
}