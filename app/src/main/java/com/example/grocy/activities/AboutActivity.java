package com.example.grocy.activities;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.grocy.Adapters.AboutTeamAdapter;
import com.example.grocy.Adapters.VisionAdapter;
import com.example.grocy.Models.AboutTeamModel;
import com.example.grocy.Models.VisionModel;
import com.example.grocy.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AboutActivity extends AppCompatActivity {

    private ViewPager2 viewPager2About, viewPager2Vision;
    private Handler sliderHandler = new Handler();
    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2About.setCurrentItem(viewPager2About.getCurrentItem() + 1);
            viewPager2Vision.setCurrentItem(viewPager2About.getCurrentItem() + 1);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about);

        viewPager2About = findViewById(R.id.pagerSliderTeam);
        viewPager2Vision = findViewById(R.id.pagerVisionTeam);
        Toolbar toolbar = findViewById(R.id.about_toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationIcon(R.drawable.icon_back_new);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        List<AboutTeamModel> aboutTeamModelList = new ArrayList<>();
        aboutTeamModelList.add(new AboutTeamModel(R.drawable.tapan, "Tapan Yadav", "Not yet decided", R.drawable.gradient));
        aboutTeamModelList.add(new AboutTeamModel(R.drawable.prashant, "Prashant Bhardwaj", "Not yet decided", R.drawable.gradient_main));
        aboutTeamModelList.add(new AboutTeamModel(R.drawable.utkarsh, "Utkarsh Gupta", "Not yet decided", R.drawable.gradient));
        aboutTeamModelList.add(new AboutTeamModel(R.drawable.user_profile, "Utkarsh Raghu", "Not yet decided", R.drawable.gradient_subu));

        List<VisionModel> visionModelList = new ArrayList<>();
        visionModelList.add(new VisionModel(R.drawable.vision_new, "Our vision", getString(R.string.vision)));
        visionModelList.add(new VisionModel(R.drawable.mission, "Our Mission", getString(R.string.mission)));
        visionModelList.add(new VisionModel(R.drawable.values, "Our values", getString(R.string.values)));

        viewPager2About.setAdapter(new AboutTeamAdapter(aboutTeamModelList, viewPager2About));
        viewPager2Vision.setAdapter(new VisionAdapter(visionModelList, viewPager2Vision));


        viewPager2About.setClipToPadding(false);
        viewPager2About.setClipChildren(false);
        viewPager2About.setOffscreenPageLimit(3);
        viewPager2About.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        viewPager2Vision.setClipToPadding(false);
        viewPager2Vision.setClipChildren(false);
        viewPager2Vision.setOffscreenPageLimit(3);
        viewPager2Vision.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleY(0.85f + r * 0.15f);
        });
        viewPager2About.setPageTransformer(compositePageTransformer);
        viewPager2Vision.setPageTransformer(compositePageTransformer);

        viewPager2About.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 3000);
            }

        });

//        viewPager2Vision.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageSelected(int position) {
//                super.onPageSelected(position);
//                sliderHandler.removeCallbacks(sliderRunnable);
//                sliderHandler.postDelayed(sliderRunnable,5000);
//            }
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable, 3000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }
}