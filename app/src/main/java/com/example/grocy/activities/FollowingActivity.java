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

public class FollowingActivity extends AppCompatActivity {

    Button buttonFindUsers;
    UsersAdapter usersFollowingAdapter;
    RecyclerView recyclerViewUsersFollowing;
    RelativeLayout linearLayoutFriendList;
    LinearLayout linearLayoutUsersNotFound;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following);

        Toolbar toolbar = findViewById(R.id.following_toolbar);
        buttonFindUsers = findViewById(R.id.button_find_users);
        linearLayoutFriendList = findViewById(R.id.linearFriendsList);
        linearLayoutUsersNotFound = findViewById(R.id.linear_userNotFound);

        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.icon_back_new);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());


        firebaseAuth = FirebaseAuth.getInstance();

//        String userId = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
        String userId = getIntent().getStringExtra("Id");

        firebaseFirestore = FirebaseFirestore.getInstance();

        recyclerViewUsersFollowing = findViewById(R.id.recycler_friends_list);
        linearLayoutFriendList.setVisibility(View.VISIBLE);


//        documentReference.collection("Users").document(userId).collection("Following");
//        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
//
//                linearLayoutUsersNotFound.setVisibility(View.INVISIBLE);
//                linearLayoutFriendList.setVisibility(View.VISIBLE);
//            }
//
//        });
        Query query = firebaseFirestore.collection("Users").document(userId).collection("Following");
        FirestoreRecyclerOptions<UsersModel> usersModelFirestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<UsersModel>()
                .setQuery(query, UsersModel.class).build();

        usersFollowingAdapter = new UsersAdapter(usersModelFirestoreRecyclerOptions);
        recyclerViewUsersFollowing.setHasFixedSize(true);
        recyclerViewUsersFollowing.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewUsersFollowing.setAdapter(usersFollowingAdapter);
        usersFollowingAdapter.notifyDataSetChanged();

//        if (usersFollowingAdapter.getItemCount() == 0){
//
//            linearLayoutUsersNotFound.setVisibility(View.VISIBLE);
//            linearLayoutFriendList.setVisibility(View.INVISIBLE);
//            buttonFindUsers.setOnClickListener(v -> {
//                Intent intent = new Intent(this,SearchUserActivity.class);
//                startActivity(intent);
//            });
//
//        }else {
//            linearLayoutUsersNotFound.setVisibility(View.INVISIBLE);
//            linearLayoutFriendList.setVisibility(View.VISIBLE);
//        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        usersFollowingAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        usersFollowingAdapter.stopListening();
    }
}