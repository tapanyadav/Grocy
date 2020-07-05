package com.example.grocy.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocy.Adapters.UserSearchAdapter;
import com.example.grocy.Adapters.UsersAdapter;
import com.example.grocy.Models.UsersModel;
import com.example.grocy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class UserSearchBarActivity extends AppCompatActivity {

    SearchView searchViewUsers;
    UsersAdapter usersSearchAdapter;
    RecyclerView recyclerViewSearch;
    EditText editTextSearch;
    ImageButton imageButtonSearch;
    UserSearchAdapter userSearchAdapter;
    ArrayList<UsersModel> usersModelList;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_search_bar);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        Toolbar toolbar = findViewById(R.id.search_bar_toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.icon_back_new);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        searchViewUsers = findViewById(R.id.searchUsersView);
        recyclerViewSearch = findViewById(R.id.recycler_users_search_list);
        editTextSearch = findViewById(R.id.search_field);
        imageButtonSearch = findViewById(R.id.search_btn);


        // userSearchAdapter.notifyDataSetChanged();

        imageButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchUserFirebase();
            }
        });


//        firebaseFirestore.collection("Users").get().addOnCompleteListener(task -> {
//
//            if (task.isSuccessful()){
//                for(QueryDocumentSnapshot document : task.getResult()) {
//
//
//                    recyclerViewSearch.setHasFixedSize(true);
//                    recyclerViewSearch.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//                    recyclerViewSearch.setAdapter(usersSearchAdapter);
//                    usersSearchAdapter.notifyDataSetChanged();
//                }
//
//            }
//        });


//        FirestoreRecyclerOptions<UsersModel> usersModelFirestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<UsersModel>()
//                .setQuery(query,UsersModel.class).build();


    }

    private void SearchUserFirebase() {

        String name = editTextSearch.getText().toString();
        Toast.makeText(this, "User " + name, Toast.LENGTH_SHORT).show();
        if (!name.isEmpty()) {

            Query query = firebaseFirestore.collection("Users").orderBy("fName");

            query.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    if (e != null) {

                        Log.d("TAG", "Error : " + e.getMessage());
                        Toast.makeText(UserSearchBarActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    // ArrayList adsList = new ArrayList();

                    for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {

                        if (doc.getType() == DocumentChange.Type.ADDED) {

                            usersModelList = new ArrayList<>();
                            UsersModel users = doc.getDocument().toObject(UsersModel.class);
                            usersModelList.add(users);
                            recyclerViewSearch.setHasFixedSize(true);
                            recyclerViewSearch.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                            userSearchAdapter = new UserSearchAdapter(getApplicationContext(), usersModelList);
                            userSearchAdapter.notifyDataSetChanged();
                        }
                    }

                    //Log.d("TAG", "no of records of the search is " + adsList.size());
                }
            });

        } else {
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
        }

    }


//    private void firebaseUserSearch(String searchText) {
//
//        Toast.makeText(UserSearchBarActivity.this, "Started Search", Toast.LENGTH_LONG).show();
//
//        Query firebaseSearchQuery = mUserDatabase.orderByChild("name").startAt(searchText).endAt(searchText + "\uf8ff");
//
//        FirestoreRecyclerAdapter<UsersModel, MyViewHolder> firebaseRecyclerAdapter = new FirestoreRecyclerAdapter<UsersModel, MyViewHolder>(
//
//                UsersModel.class,
//                R.layout.content_recycler_friends_list,
//                MyViewHolder.class,
//                firebaseSearchQuery
//
//        ) {
//            @NonNull
//            @Override
//            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                return null;
//            }
//
//            @Override
//            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull UsersModel model) {
//
//            }
//
//            @Override
//            protected void populateViewHolder(MyViewHolder viewHolder, UsersModel model, int position) {
//
//
//                viewHolder.setDetails(getApplicationContext(), model.getName(), model.getStatus(), model.getImage());
//
//            }
//        };
//
//        recyclerViewSearch.setAdapter(firebaseRecyclerAdapter);
//
//    }


}