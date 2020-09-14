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
    HashMap<String, Object> userDetailsUpdate = new HashMap<>();
    private TextView userName, number_of_orders, textViewReviewsCount, textViewPhotosCount, textViewFollowersCount, textViewFollowingCount, textViewCity;
    private String userId;
    String userLevel, userDetailedStatus;
    int orderCount, photosCount, reviewCount;
    TextView textViewBookmarkCount;
    String userCurrentLevel;
    TextView textViewUserLevel, textViewUserDetailedStatus;

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
        CardView cardViewBookmarks = findViewById(R.id.materialCardBookmarks);
        textViewPhotosCount = findViewById(R.id.number_of_photos);
        textViewReviewsCount = findViewById(R.id.number_of_reviews);
        textViewFollowersCount = findViewById(R.id.text_followers_count);
        textViewFollowingCount = findViewById(R.id.text_following_count);
        textViewBookmarkCount = findViewById(R.id.text_bookmark_count);
        textViewCity = findViewById(R.id.text_city);
        textViewUserLevel = findViewById(R.id.text_user_level);
        textViewUserDetailedStatus = findViewById(R.id.text_user_detailed_status);

        // user_id = (String) MainActivity.proile_activity_data.get("userId");
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
                        userLevel = task1.getResult().getString("userLevel");
                        userDetailedStatus = task1.getResult().getString("userDetailedStatus");
                        reviewCount = Integer.parseInt("" + task1.getResult().get("reviewCount"));
                        photosCount = Integer.parseInt("" + task1.getResult().get("photoCount"));
                        orderCount = Integer.parseInt("" + task1.getResult().get("ordersCount"));
                        textViewBookmarkCount.setText(task1.getResult().getString("noOfBookmarks"));
                        updateLevel();
                        Toast.makeText(this, "" + name, Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, "" + city, Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, "" + userLevel, Toast.LENGTH_SHORT).show();
                        assert name != null;
                        userName.setText(name.split(" ")[0]);
                        textViewCity.setText(city);
                        //textViewBookmarkCount.setText(task1.getResult().getString("noOfBookmarks"));
                        Glide.with(getApplicationContext()).load(image).placeholder(R.drawable.user_profile).into(userImage);
                    }

                } else {

                    String error = Objects.requireNonNull(task1.getException()).getMessage();
                    Toast.makeText(UserProfileActivity.this, "(FIRESTORE Retrieve Error) : " + error, Toast.LENGTH_LONG).show();
                }
            });
        } else {
            userId = (String) Objects.requireNonNull(MainActivity.proile_activity_data.get("userId"));
            reviewCount = Integer.parseInt("" + MainActivity.proile_activity_data.get("reviewCount"));
            photosCount = Integer.parseInt("" + MainActivity.proile_activity_data.get("photoCount"));
            orderCount = Integer.parseInt("" + MainActivity.proile_activity_data.get("ordersCount"));

            updateLevel();
        }
//        DocumentReference documentReference1 = firebaseFirestore.collection("Users").document(userId);

        countNumbers();

        Toolbar toolbar = findViewById(R.id.profile_toolbar);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager viewPager = findViewById(R.id.viewPager);
        imageViewProfileEdit = findViewById(R.id.image_editProf);

        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.icon_back_new);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());


        documentReference.update(userDetailsUpdate).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Level and status updated", Toast.LENGTH_SHORT).show();
            }
        });
        cardViewFollowing.setOnClickListener(v -> {
            Intent intent = new Intent(UserProfileActivity.this, FollowingActivity.class);
            intent.putExtra("Id", userId);
            startActivity(intent);
        });

        cardViewFollowers.setOnClickListener(v -> {
            Intent intent = new Intent(UserProfileActivity.this, FollowersActivity.class);
            intent.putExtra("Id", userId);
            startActivity(intent);
        });
        cardViewBookmarks.setOnClickListener(v -> {
            Intent intent = new Intent(this, BookmarksActivity.class);
            if (user_id != null) {
                userId = user_id;
            } else {
                userId = (String) MainActivity.proile_activity_data.get("userId");
            }
            intent.putExtra("user_id", userId);
            startActivity(intent);
        });

        cardViewAddReview.setOnClickListener(v -> {
            Intent intent = new Intent(UserProfileActivity.this, ShopListReviewActivity.class);
            startActivity(intent);
        });

        cardViewAddPhoto.setOnClickListener(v -> {
            Intent intent = new Intent(UserProfileActivity.this, ShopListImageActivity.class);
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


//        reviewCount = Integer.parseInt(""+ MainActivity.proile_activity_data.get("reviewCount"));
//        photosCount = Integer.parseInt(""+ MainActivity.proile_activity_data.get("photoCount"));
//        orderCount = Integer.parseInt(""+ MainActivity.proile_activity_data.get("ordersCount"));
//        Toast.makeText(this, "Level:  " + reviewCount, Toast.LENGTH_SHORT).show();


//        Toast.makeText(this, "Level: " + photosCount, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "Level: " + orderCount, Toast.LENGTH_SHORT).show();


//                    Toast.makeText(this, "Final Level: " + userCurrentLevel, Toast.LENGTH_SHORT).show();

        profileShare.setOnClickListener(v -> {


        });

    }

    private void updateLevel() {
        if (reviewCount == 0 || photosCount == 0 || orderCount == 0) {
            userCurrentLevel = "1";
            userDetailedStatus = "Bronze";

        }
        if (reviewCount >= 1 && reviewCount < 5 || photosCount >= 1 && photosCount < 5 || orderCount >= 1 && orderCount < 5) {
            userCurrentLevel = "2";
            userDetailedStatus = "Silver";

        }
        if ((reviewCount >= 5 && reviewCount < 9 || photosCount >= 5 && photosCount < 9 || orderCount >= 5 && orderCount < 9)) {
            userCurrentLevel = "3";
            userDetailedStatus = "Gold";
            Toast.makeText(this, "Level: GG " + userCurrentLevel, Toast.LENGTH_SHORT).show();
        }
        if ((photosCount >= 9 && photosCount < 14 || reviewCount >= 9 && reviewCount < 14 || orderCount >= 9 && orderCount < 14)) {
            userCurrentLevel = "4";
            userDetailedStatus = "Platinum";
            Toast.makeText(this, "Level: " + userCurrentLevel + "userDetailedStatus: " + userDetailedStatus, Toast.LENGTH_SHORT).show();
        }
        if ((reviewCount >= 14 && reviewCount < 19 || photosCount >= 14 && photosCount < 19 || orderCount >= 14 && orderCount < 19)) {
            userCurrentLevel = "5";
            userDetailedStatus = "Diamond";
        }
        if ((reviewCount >= 19 && reviewCount < 24 || photosCount >= 19 && photosCount < 24 || orderCount >= 19 && orderCount < 24)) {
            userCurrentLevel = "6";
            userDetailedStatus = "Crown";
        }

        textViewUserLevel.setText(userCurrentLevel);
        textViewUserDetailedStatus.setText(userDetailedStatus);

        userDetailsUpdate.put("userLevel", userCurrentLevel);
        userDetailsUpdate.put("userDetailedStatus", userDetailedStatus);

    }

    private void countNumbers() {
        documentReference = firebaseFirestore.collection("Users").document(userId);
        documentReference.collection("myOrders").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                number_of_orders.setText("" + task.getResult().size());
//                orderCount = task.getResult().size();
            }
        });
        documentReference.collection("Photos").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                textViewPhotosCount.setText("" + task.getResult().size());
//                photosCount = task.getResult().size();

            }
        });

        documentReference.collection("Followers").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                textViewFollowersCount.setText("" + task.getResult().size());

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
//                reviewCount = task.getResult().size();
                Toast.makeText(this, "ReviewCount: " + reviewCount, Toast.LENGTH_SHORT).show();

            }
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
//            userCurrentLevel = (String) MainActivity.proile_activity_data.get("userLevel");
//            userDetailedStatus = (String) MainActivity.proile_activity_data.get("userDetailedStatus");
            textViewBookmarkCount.setText("" + MainActivity.proile_activity_data.get("noOfBookmarks"));
            Toast.makeText(this, "Level:" + (String) MainActivity.proile_activity_data.get("userLevel"), Toast.LENGTH_SHORT).show();

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

    @Override
    protected void onRestart() {
        super.onRestart();
        documentReference.collection("Followers").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                textViewFollowersCount.setText("" + task.getResult().size());

            }
        });
        documentReference.collection("Following").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                textViewFollowingCount.setText("" + task.getResult().size());

            }
        });

    }
}
