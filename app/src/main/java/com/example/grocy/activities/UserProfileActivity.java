package com.example.grocy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.grocy.R;
import com.example.grocy.fragments.PhotosFragment;
import com.example.grocy.fragments.ReviewsFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class UserProfileActivity extends AppCompatActivity {

    private ImageView imageViewProfileEdit;
    FirebaseFirestore firebaseFirestore;
    DocumentReference documentReference;
    String user_id;
    private ImageView userImage;
    private HashMap user_data = new HashMap<>();
    private TextView userName, number_of_orders, textViewReviewsCount, textViewPhotosCount, textViewFollowersCount, textViewFollowingCount, textViewCity;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        userName = findViewById(R.id.userName);
        number_of_orders = findViewById(R.id.number_of_orders);
        userImage = findViewById(R.id.userImage);
        CardView cardViewAddPhoto = findViewById(R.id.card_addPhoto);
        CardView cardViewAddReview = findViewById(R.id.card_addReview);
        CardView cardViewAddUser = findViewById(R.id.card_addUser);
        CardView cardViewFollowing = findViewById(R.id.materialCardFollowing);
        CardView cardViewFollowers = findViewById(R.id.materialCardFollowers);
        ImageView profileShare = findViewById(R.id.image_share_profile);
        textViewPhotosCount = findViewById(R.id.number_of_photos);
        textViewReviewsCount = findViewById(R.id.number_of_reviews);
        textViewFollowersCount = findViewById(R.id.text_followers_count);
        textViewFollowingCount = findViewById(R.id.text_following_count);
        textViewCity = findViewById(R.id.text_city);

        String user_id = (String) MainActivity.proile_activity_data.get("userId");
        firebaseFirestore = FirebaseFirestore.getInstance();
//        user_data = (HashMap<String, Object>) getIntent().getSerializableExtra("user_data");

        user_id = getIntent().getStringExtra("usersDocumentId");
        Toast.makeText(this, "Id:" + user_id, Toast.LENGTH_SHORT).show();

        if (user_id != null) {
            userId = user_id;

            firebaseFirestore.collection("Users").document(userId).get().addOnCompleteListener(task1 -> {


                if (task1.isSuccessful()) {


                    if (Objects.requireNonNull(task1.getResult()).exists()) {

                        String name = task1.getResult().getString("fName");
                        String image = task1.getResult().getString("profilePic");
                        String city = task1.getResult().getString("userCity");

                        Toast.makeText(this, "" + name, Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, "" + city, Toast.LENGTH_SHORT).show();
                        assert name != null;
                        userName.setText(name.split(" ")[0]);
                        textViewCity.setText(city);
                        Glide.with(getApplicationContext()).load(image).placeholder(R.drawable.user_profile).into(userImage);


                    }

                } else {

                    String error = Objects.requireNonNull(task1.getException()).getMessage();
                    Toast.makeText(UserProfileActivity.this, "(FIRESTORE Retrieve Error) : " + error, Toast.LENGTH_LONG).show();

                }
            });
        } else {
            userId = (String) Objects.requireNonNull(MainActivity.proile_activity_data.get("userId"));
        }
        DocumentReference documentReference1 = firebaseFirestore.collection("Users").document(userId);


        documentReference = firebaseFirestore.collection("Users").document(userId);
        documentReference.collection("myOrder").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                number_of_orders.setText("" + task.getResult().size());
//                for (QueryDocumentSnapshot document : task.getResult()) {
//                    System.out.println("------------------------------");
//                    System.out.println(document.getId());
//                    System.out.println("---------------------------------------");
//                }
            }
        });
        documentReference.collection("Photos").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                textViewPhotosCount.setText("" + task.getResult().size());

            }
        });
        documentReference.collection("Followers").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                textViewFollowersCount.setText("" + task.getResult().size());

        documentReference = firebaseFirestore.collection("Users").document(user_id);
        documentReference.collection("myOrder").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Object a = task.getResult();
                    number_of_orders.setText("" + task.getResult().size());
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        System.out.println("------------------------------");
                        System.out.println(document.getId());
                        System.out.println("---------------------------------------");
                    }
                }
            }
        });
        documentReference.collection("Following").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                textViewFollowingCount.setText("" + task.getResult().size());

            }
        });
        documentReference.collection("Reviews").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                textViewReviewsCount.setText("" + task.getResult().size());

            }
        });
        Toolbar toolbar = findViewById(R.id.profile_toolbar);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager viewPager = findViewById(R.id.viewPager);
        imageViewProfileEdit = findViewById(R.id.image_editProf);


        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.icon_back_new);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        cardViewFollowing.setOnClickListener(v -> {
            Intent intent = new Intent(UserProfileActivity.this, FollowingActivity.class);
            startActivity(intent);
        });

        cardViewFollowers.setOnClickListener(v -> {
            Intent intent = new Intent(UserProfileActivity.this, FollowersActivity.class);
            startActivity(intent);
        });

        cardViewAddReview.setOnClickListener(v -> {
            Intent intent = new Intent(UserProfileActivity.this, AddReviewActivity.class);
            startActivity(intent);
        });

        cardViewAddPhoto.setOnClickListener(v -> {
            Intent intent = new Intent(UserProfileActivity.this, AddPhotoActivity.class);
            startActivity(intent);
        });

        cardViewAddUser.setOnClickListener(v -> {
            Intent intent = new Intent(UserProfileActivity.this, SearchUserActivity.class);
            startActivity(intent);
        });
        PhotosFragment photosFragment = new PhotosFragment();
        ReviewsFragment reviewsFragment = new ReviewsFragment();

        tabLayout.setupWithViewPager(viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);
        viewPagerAdapter.addFragment(reviewsFragment, "Reviews");
        viewPagerAdapter.addFragment(photosFragment, "Photos");
        viewPager.setAdapter(viewPagerAdapter);

        Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(R.drawable.icon_review);
        Objects.requireNonNull(tabLayout.getTabAt(1)).setIcon(R.drawable.icon_photo);

        openUserProfileImage();

        profileShare.setOnClickListener(v -> {


        });

    }

    private void openUserProfileImage() {

        imageViewProfileEdit.setOnClickListener(v -> {

            Intent intent = new Intent(UserProfileActivity.this, EditProfileActivity.class);
            startActivity(intent);

        });

        if (MainActivity.proile_activity_data.size() != 0) {
            String user_name = (String) MainActivity.proile_activity_data.get("fName");
            textViewCity.setText((String) MainActivity.proile_activity_data.get("userCity"));
            assert user_name != null;
            userName.setText(user_name.split(" ")[0]);
            if (MainActivity.proile_activity_data.containsKey("profilePic")) {
                Glide.with(userImage.getContext())
                        .load((String) MainActivity.proile_activity_data.get("profilePic"))
                        .into(userImage);
            }
        }
    }


    private static class ViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments = new ArrayList<>();
        private List<String> fragmentTitle = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            fragmentTitle.add(title);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitle.get(position);
        }
    }


}