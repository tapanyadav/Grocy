package com.example.grocy.activities;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.grocy.R;
import com.example.grocy.fragments.PhotosFragment;
import com.example.grocy.fragments.ReviewsFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserProfileActivity extends AppCompatActivity {

    ImageView imageViewProfile;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private PhotosFragment photosFragment;
    private ReviewsFragment reviewsFragment;

    // ImageView imageViewBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        //imageViewProfile= findViewById(R.id.image_user_profile);
        Toolbar toolbar = findViewById(R.id.profile_toolbar);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);


        setSupportActionBar(toolbar);

        //imageViewBack.findViewById(R.id.image_back_btn);
        toolbar.setNavigationIcon(R.drawable.icon_back_new);
        toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
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