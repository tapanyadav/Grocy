package com.example.grocy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.grocy.R;
import com.example.grocy.fragments.PhotosFragment;
import com.example.grocy.fragments.ReviewsFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class UserProfileActivity extends AppCompatActivity {

    ImageView imageViewProfile;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private PhotosFragment photosFragment;
    private ReviewsFragment reviewsFragment;
    TextView textViewAddReview, textViewAddPhoto;
    private ImageView imageViewProfileEdit;
    FirebaseFirestore firebaseFirestore;
    DocumentReference documentReference;
    private TextView userName, number_of_orders;
    private ImageView userImage;
    private HashMap user_data = new HashMap<>();

    // ImageView imageViewBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        userName = findViewById(R.id.userName);
        number_of_orders = findViewById(R.id.number_of_orders);
        userImage = findViewById(R.id.userImage);

        firebaseFirestore = FirebaseFirestore.getInstance();
        user_data = (HashMap<String, Object>) getIntent().getSerializableExtra("user_data");

        documentReference = firebaseFirestore.collection("Users").document((String) user_data.get("userId"));
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
        Toolbar toolbar = findViewById(R.id.profile_toolbar);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        imageViewProfileEdit = findViewById(R.id.image_editProf);
        textViewAddReview = findViewById(R.id.text_addReview);
        textViewAddPhoto = findViewById(R.id.text_addPhoto);


        setSupportActionBar(toolbar);

        //imageViewBack.findViewById(R.id.image_back_btn);
        toolbar.setNavigationIcon(R.drawable.icon_back_new);
        toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });

        textViewAddReview.setOnClickListener(v -> {
            Intent intent = new Intent(UserProfileActivity.this, AddReviewActivity.class);
            startActivity(intent);
        });

        textViewAddPhoto.setOnClickListener(v -> {
            Intent intent = new Intent(UserProfileActivity.this, AddPhotoActivity.class);
            startActivity(intent);
        });
        photosFragment = new PhotosFragment();
        reviewsFragment = new ReviewsFragment();

        tabLayout.setupWithViewPager(viewPager);

//        imageViewBack.setOnClickListener(v -> {
//            onBackPressed();
//        });

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);
        viewPagerAdapter.addFragment(reviewsFragment, "Reviews");
        viewPagerAdapter.addFragment(photosFragment, "Photos");
        viewPager.setAdapter(viewPagerAdapter);

        Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(R.drawable.icon_review);
        Objects.requireNonNull(tabLayout.getTabAt(1)).setIcon(R.drawable.icon_photo);

        openUserProfileImage();
    }

    private void openUserProfileImage() {

        imageViewProfileEdit.setOnClickListener(v -> {

            Intent intent = new Intent(UserProfileActivity.this, EditProfileActivity.class);
            startActivity(intent);

        });

        if (user_data.size() != 0) {
            String user_name = (String) user_data.get("fName");
            userName.setText(user_name.split(" ")[0]);
            if (user_data.containsKey("profilePic")) {
                Glide.with(userImage.getContext())
                        .load(user_data.get("profilePic"))
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