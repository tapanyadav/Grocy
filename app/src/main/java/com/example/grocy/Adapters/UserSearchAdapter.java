package com.example.grocy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.grocy.Models.UsersModel;
import com.example.grocy.R;

import java.util.ArrayList;

public class UserSearchAdapter extends RecyclerView.Adapter<UserSearchAdapter.MyViewHolder> {
    Context context;
    ArrayList<UsersModel> usersModelArrayList;

    public UserSearchAdapter(Context context, ArrayList<UsersModel> usersModelArrayList) {
        this.context = context;
        this.usersModelArrayList = usersModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_recycler_friends_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        UsersModel usersModel = usersModelArrayList.get(position);

        holder.textViewUserName.setText(usersModel.getfName());
        Glide.with(holder.imageViewProfileImage.getContext()).load(usersModel.getProfilePic()).into(holder.imageViewProfileImage);

    }

    @Override
    public int getItemCount() {
        return usersModelArrayList.size();
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

//    private void removeFollowing(String document_id, String id, UsersAdapter.MyViewHolder holder, int position) {
//        documentReference = FirebaseFirestore.getInstance().collection("Users").document(document_id);
//        documentReference.collection("Following").document(id).delete().addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//
//                holder.buttonFollow.setVisibility(View.VISIBLE);
//                holder.buttonFollowing.setVisibility(View.INVISIBLE);
//                Toast.makeText(holder.buttonFollow.getContext(), "Un followed", Toast.LENGTH_LONG).show();
//
//            } else {
//
//                String error = Objects.requireNonNull(task.getException()).getMessage();
//                Toast.makeText(holder.buttonFollow.getContext(), "(FIRESTORE Error) : " + error, Toast.LENGTH_LONG).show();
//
//            }
//        });
//
//        DocumentReference documentReference1 = FirebaseFirestore.getInstance().collection("Users").document(getSnapshots().getSnapshot(position).getId());
//        documentReference1.collection("Followers").document(document_id).delete().addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//
//                holder.buttonFollow.setVisibility(View.VISIBLE);
//                holder.buttonFollowing.setVisibility(View.INVISIBLE);
//                Toast.makeText(holder.buttonFollow.getContext(), "Remove following", Toast.LENGTH_LONG).show();
//
//            } else {
//
//                String error = Objects.requireNonNull(task.getException()).getMessage();
//                Toast.makeText(holder.buttonFollow.getContext(), "(FIRESTORE Error) : " + error, Toast.LENGTH_LONG).show();
//
//            }
//        });
//    }
//
//    private void isFollowing(String document_Id, String userId, UsersAdapter.MyViewHolder myViewHolder, int pos) {
//
//        hashMapUserId.put("userId", userId);
//        hashMapUserId.put("fName",getSnapshots().getSnapshot(pos).get("fName"));
//        hashMapUserId.put("profilePic",getSnapshots().getSnapshot(pos).get("profilePic"));
//        documentReference = FirebaseFirestore.getInstance().collection("Users").document(document_Id);
//        documentReference.collection("Following").document(userId).set(hashMapUserId).addOnCompleteListener(task -> {
//
//            if (task.isSuccessful()) {
//
//                myViewHolder.buttonFollow.setVisibility(View.INVISIBLE);
//                myViewHolder.buttonFollowing.setVisibility(View.VISIBLE);
//                Toast.makeText(myViewHolder.buttonFollow.getContext(), "New follower is added", Toast.LENGTH_LONG).show();
//
//            } else {
//
//                String error = Objects.requireNonNull(task.getException()).getMessage();
//                Toast.makeText(myViewHolder.buttonFollow.getContext(), "(FIRESTORE Error) : " + error, Toast.LENGTH_LONG).show();
//
//            }
//        });
//        hashMapFollowers.put("userId", document_Id);
//
//        DocumentReference documentReference1 = FirebaseFirestore.getInstance().collection("Users").document(getSnapshots().getSnapshot(pos).getId());
//        documentReference1.collection("Followers").document(document_Id).set(hashMapFollowers).addOnCompleteListener(task -> {
//
//            if (task.isSuccessful()) {
//
//                myViewHolder.buttonFollow.setVisibility(View.INVISIBLE);
//                myViewHolder.buttonFollowing.setVisibility(View.VISIBLE);
//                Toast.makeText(myViewHolder.buttonFollow.getContext(), "New following", Toast.LENGTH_LONG).show();
//
//            } else {
//
//                String error = Objects.requireNonNull(task.getException()).getMessage();
//                Toast.makeText(myViewHolder.buttonFollow.getContext(), "(FIRESTORE Error) : " + error, Toast.LENGTH_LONG).show();
//
//            }
//        });
//
//    }
}
