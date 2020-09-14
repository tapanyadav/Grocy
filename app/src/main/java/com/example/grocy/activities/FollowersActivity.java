package com.example.grocy.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocy.Adapters.UsersAdapter;
import com.example.grocy.Models.UsersModel;
import com.example.grocy.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class FollowersActivity extends AppCompatActivity {

    Button buttonFindUsers;
    UsersAdapter usersFollowersAdapter;
    RecyclerView recyclerViewFollowers;
    LinearLayout linearLayoutUsersNotFound;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private RelativeLayout linearLayoutFriendList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);

        Toolbar toolbar = findViewById(R.id.followers_toolbar);
        //buttonFindUsers = findViewById(R.id.button_find_users);
        linearLayoutFriendList = findViewById(R.id.relativeFriendsList);
        //linearLayoutUsersNotFound = findViewById(R.id.linear_userNotFound);

        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.icon_back_new);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());


        firebaseAuth = FirebaseAuth.getInstance();

//        String userId = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
        firebaseFirestore = FirebaseFirestore.getInstance();

        String userId = getIntent().getStringExtra("Id");
        recyclerViewFollowers = findViewById(R.id.recycler_friends_list_followers);
        linearLayoutFriendList.setVisibility(View.VISIBLE);

        Query query = firebaseFirestore.collection("Users").document(userId).collection("Followers");
        FirestoreRecyclerOptions<UsersModel> usersModelFirestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<UsersModel>()
                .setQuery(query, UsersModel.class).build();

        usersFollowersAdapter = new UsersAdapter(usersModelFirestoreRecyclerOptions);
        recyclerViewFollowers.setHasFixedSize(true);
        recyclerViewFollowers.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewFollowers.setAdapter(usersFollowersAdapter);
        usersFollowersAdapter.notifyDataSetChanged();
    }

    protected void onStart() {
        super.onStart();
        usersFollowersAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        usersFollowersAdapter.stopListening();
    }
}