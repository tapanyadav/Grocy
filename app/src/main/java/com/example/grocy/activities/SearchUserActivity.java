package com.example.grocy.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocy.Adapters.UsersAdapter;
import com.example.grocy.Models.UsersModel;
import com.example.grocy.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class SearchUserActivity extends AppCompatActivity {

    UsersAdapter usersAdapter;
    RecyclerView recyclerViewUsers;
    CardView cardViewSearch;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);

        Toolbar toolbar = findViewById(R.id.add_user_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.icon_back_new);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerViewUsers = findViewById(R.id.recycler_suggested_users);
        cardViewSearch = findViewById(R.id.search_card);

        cardViewSearch.setOnClickListener(v -> {
            Intent intent = new Intent(this, UserSearchBarActivity.class);
            startActivity(intent);
        });

        Query query = firebaseFirestore.collection("Users");
        FirestoreRecyclerOptions<UsersModel> usersModelFirestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<UsersModel>()
                .setQuery(query, UsersModel.class).build();

        usersAdapter = new UsersAdapter(usersModelFirestoreRecyclerOptions);
        recyclerViewUsers.setHasFixedSize(true);
        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(this));
        // usersAdapter.notifyDataSetChanged();
        recyclerViewUsers.setAdapter(usersAdapter);
        usersAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
        usersAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        usersAdapter.stopListening();
    }
}