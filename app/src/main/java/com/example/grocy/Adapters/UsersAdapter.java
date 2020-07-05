package com.example.grocy.Adapters;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.grocy.Models.UsersModel;
import com.example.grocy.R;
import com.example.grocy.activities.UserProfileActivity;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Objects;

public class UsersAdapter extends FirestoreRecyclerAdapter<UsersModel, UsersAdapter.MyViewHolder> {

    private static final String TAG = "Users";
    DocumentReference documentReference;
    HashMap<String, Object> hashMapUserId = new HashMap<>();
    HashMap<String, Object> hashMapFollowers = new HashMap<>();

    public UsersAdapter(@NonNull FirestoreRecyclerOptions<UsersModel> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull UsersModel model) {

        FirebaseFirestore firebaseFirestore;
        FirebaseAuth firebaseAuth;
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        String document_id = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
        String Id = getSnapshots().getSnapshot(position).getId();

        documentReference = firebaseFirestore.collection("Users").document(document_id).collection("Following").document(Id);
        documentReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                assert document != null;
                if (document.contains("userId")) {

                    holder.buttonFollow.setVisibility(View.INVISIBLE);
                    holder.buttonFollowing.setVisibility(View.VISIBLE);
                    if (document_id.equals(Id)) {
                        holder.buttonFollowing.setVisibility(View.INVISIBLE);
                        holder.buttonFollow.setVisibility(View.INVISIBLE);
                    }

                    Log.d(TAG, "Document exists!");
                } else {
                    Log.d(TAG, "Document does not exist!");
                    holder.buttonFollow.setVisibility(View.VISIBLE);
                    if (document_id.equals(Id)) {
                        holder.buttonFollowing.setVisibility(View.INVISIBLE);
                        holder.buttonFollow.setVisibility(View.INVISIBLE);
                    }
                }
            } else {
                Log.d(TAG, "Failed with: ", task.getException());
            }
        });


        holder.buttonFollow.setOnClickListener(v -> isFollowing(document_id, Id, holder, position));

        holder.buttonFollowing.setOnClickListener(v -> removeFollowing(document_id, Id, holder, position));


        if (document_id.equals(Id)) {
            holder.buttonFollowing.setVisibility(View.INVISIBLE);
            holder.buttonFollow.setVisibility(View.INVISIBLE);
        }

        documentReference = firebaseFirestore.collection("Users").document(Id);
        firebaseFirestore.collection("Users").document(document_id).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                hashMapFollowers.put("fName", Objects.requireNonNull(task.getResult()).get("fName"));
                hashMapFollowers.put("profilePic", task.getResult().get("profilePic"));
            }
        });


        documentReference.collection("Reviews").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                holder.textViewUserNumberReviews.setText("" + task.getResult().size());

            }
        });
        documentReference.collection("Followers").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                holder.textViewUserNumberFollowers.setText("" + task.getResult().size());

            }
        });
        holder.textViewUserName.setText(model.getfName());

        Glide.with(holder.imageViewProfileImage.getContext()).load(model.getProfilePic()).into(holder.imageViewProfileImage);

        holder.imageViewProfileImage.setOnClickListener(v -> {
            Intent intent = new Intent(holder.imageViewProfileImage.getContext(), UserProfileActivity.class);
            intent.putExtra("usersDocumentId", Id);
            holder.imageViewProfileImage.getContext().startActivity(intent);
        });

        holder.linearLayoutFriendList.setOnClickListener(v -> {
            Intent intent = new Intent(holder.linearLayoutFriendList.getContext(), UserProfileActivity.class);
            intent.putExtra("usersDocumentId", Id);
            holder.linearLayoutFriendList.getContext().startActivity(intent);
        });

    }

    private void removeFollowing(String document_id, String id, MyViewHolder holder, int position) {
        documentReference = FirebaseFirestore.getInstance().collection("Users").document(document_id);
        documentReference.collection("Following").document(id).delete().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                holder.buttonFollow.setVisibility(View.VISIBLE);
                holder.buttonFollowing.setVisibility(View.INVISIBLE);
                Toast.makeText(holder.buttonFollow.getContext(), "Un followed", Toast.LENGTH_LONG).show();

            } else {

                String error = Objects.requireNonNull(task.getException()).getMessage();
                Toast.makeText(holder.buttonFollow.getContext(), "(FIRESTORE Error) : " + error, Toast.LENGTH_LONG).show();

            }
        });

        DocumentReference documentReference1 = FirebaseFirestore.getInstance().collection("Users").document(getSnapshots().getSnapshot(position).getId());
        documentReference1.collection("Followers").document(document_id).delete().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                holder.buttonFollow.setVisibility(View.VISIBLE);
                holder.buttonFollowing.setVisibility(View.INVISIBLE);
                Toast.makeText(holder.buttonFollow.getContext(), "Remove following", Toast.LENGTH_LONG).show();

            } else {

                String error = Objects.requireNonNull(task.getException()).getMessage();
                Toast.makeText(holder.buttonFollow.getContext(), "(FIRESTORE Error) : " + error, Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_recycler_friends_list, parent, false);
        return new MyViewHolder(view);
    }

    private void isFollowing(String document_Id, String userId, MyViewHolder myViewHolder, int pos) {

        hashMapUserId.put("userId", userId);
        hashMapUserId.put("fName", getSnapshots().getSnapshot(pos).get("fName"));
        hashMapUserId.put("profilePic", getSnapshots().getSnapshot(pos).get("profilePic"));
        documentReference = FirebaseFirestore.getInstance().collection("Users").document(document_Id);
        documentReference.collection("Following").document(userId).set(hashMapUserId).addOnCompleteListener(task -> {

            if (task.isSuccessful()) {

                myViewHolder.buttonFollow.setVisibility(View.INVISIBLE);
                myViewHolder.buttonFollowing.setVisibility(View.VISIBLE);
                Toast.makeText(myViewHolder.buttonFollow.getContext(), "New follower is added", Toast.LENGTH_LONG).show();

            } else {

                String error = Objects.requireNonNull(task.getException()).getMessage();
                Toast.makeText(myViewHolder.buttonFollow.getContext(), "(FIRESTORE Error) : " + error, Toast.LENGTH_LONG).show();

            }
        });
        hashMapFollowers.put("userId", document_Id);

        DocumentReference documentReference1 = FirebaseFirestore.getInstance().collection("Users").document(getSnapshots().getSnapshot(pos).getId());
        documentReference1.collection("Followers").document(document_Id).set(hashMapFollowers).addOnCompleteListener(task -> {

            if (task.isSuccessful()) {

                myViewHolder.buttonFollow.setVisibility(View.INVISIBLE);
                myViewHolder.buttonFollowing.setVisibility(View.VISIBLE);
                Toast.makeText(myViewHolder.buttonFollow.getContext(), "New following", Toast.LENGTH_LONG).show();

            } else {

                String error = Objects.requireNonNull(task.getException()).getMessage();
                Toast.makeText(myViewHolder.buttonFollow.getContext(), "(FIRESTORE Error) : " + error, Toast.LENGTH_LONG).show();

            }
        });

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewProfileImage;
        TextView textViewUserName, textViewUserNumberReviews, textViewUserNumberFollowers;
        RecyclerView recyclerViewUsers;
        Button buttonFollow, buttonFollowing;
        LinearLayout linearLayoutFriendList;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewProfileImage = itemView.findViewById(R.id.users_image_profile);
            textViewUserName = itemView.findViewById(R.id.users_text_name);
            textViewUserNumberReviews = itemView.findViewById(R.id.user_number_reviews);
            textViewUserNumberFollowers = itemView.findViewById(R.id.user_number_followers);
            recyclerViewUsers = itemView.findViewById(R.id.recycler_suggested_users);
            buttonFollow = itemView.findViewById(R.id.button_follow);
            buttonFollowing = itemView.findViewById(R.id.button_following);
            linearLayoutFriendList = itemView.findViewById(R.id.linear_friend_list);
        }


    }


}
