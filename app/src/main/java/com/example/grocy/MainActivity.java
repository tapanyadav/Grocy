package com.example.grocy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocy.Adapters.CategoriesAdapter;
import com.example.grocy.Adapters.FeaturedAdapter;
import com.example.grocy.Adapters.HorizontalAdapter;
import com.example.grocy.Adapters.ShopsAdapter;
import com.example.grocy.Models.CategoriesModel;
import com.example.grocy.Models.FeaturedModel;
import com.example.grocy.Models.HorizontalModel;
import com.example.grocy.Models.ShopsModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth mAuth;

    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    //navigation drawer
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    BottomSheetDialog bottomSheetDialog;
    TextView toolbarTitle;
    private Toolbar toolbar;

    //adapters

    private RecyclerView recyclerViewCat,recyclerViewFea,recyclerViewShop,recyclerViewHorizontal,recyclerViewTryHorizontal;
    private ArrayList<CategoriesModel> imageModelArrayList;
    private ArrayList<FeaturedModel> imageModelFeaturedArrayList;
    private ArrayList<ShopsModel> shopsModelArrayList;
    private ArrayList<HorizontalModel> horizontalModelArrayList;
    private CategoriesAdapter adapter;
    private FeaturedAdapter adapterFeatured;
    private ShopsAdapter shopsAdapter;
    private HorizontalAdapter horizontalAdapter;

    private int[] myImageList = new int[]{R.drawable.grocery,R.drawable.pharm,R.drawable.stationary};
    private int[] myFeaturedImageList=new int[]{R.drawable.epic_deals,R.drawable.gupta_groc,R.drawable.chotu_med,R.drawable.tyagi_stat};
    private String[] myImageNameList = new String[]{"Grocery","Pharmacy" ,"Stationary"};
    private int[] myShopImageList=new int[]{R.drawable.gupta_shop,R.drawable.chotu_shop,R.drawable.tyagi_shop,R.drawable.raghu_shop};
    private String[] myShopNameList=new String[]{"Gupta Grocery Store","Chotu Medical Store","Tyagi Stationary Store","Raghu Fruits Corner"};
    private String[] myShopTypeList=new String[]{"Daily need items","Medicines","Books,Pens,Calculator","Apple,Oranges,Banana"};
    private String[] myShopLimitList=new String[]{"200 per order | 40 min","300 per order | 30 min","100 per order | 50 min","100 per order | 20 min"};
    private String[] myShopOffList=new String[]{"30% OFF - use code WELCOME30","10% OFF - use code WELCOME10","20% OFF - use code WELCOME20","40% OFF - no code needed"};
    private String[] myShopRatingList=new String[]{"4.5","4.7","4.2","3.5"};
    private int[] myHorizontalImageList=new int[]{R.drawable.stationary,R.drawable.groc,R.drawable.medicines,R.drawable.tea,R.drawable.fruits};
    private int[] myBackgroundImageList=new int[]{R.color.mainAppColor,R.color.yellow,R.color.lightBlue,R.color.lightGreen,R.color.lightPink};
    private String[] myNameStoreHorizontalList=new String[]{"Tyagi Store","Gupta Store","Chotu Store","Ujjwal Tea Shop","Raghu Fruits Corner"};

//    private int[] myHorizontalImageTryList=new int[]{R.drawable.stationary,R.drawable.groc,R.drawable.medicines};
//    private int[] myBackgroundImageTryList=new int[]{R.color.mainAppColor,R.color.yellow,R.color.lightBlue};
//    private String[] myNameStoreHorizontalTryList=new String[]{"Tyagi Store","Gupta Store","Chotu Store"};


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        mAuth = FirebaseAuth.getInstance();

        recyclerViewCat = findViewById(R.id.recycler);
        recyclerViewFea=findViewById(R.id.recycler_featured);
        recyclerViewShop=findViewById(R.id.recycler_shops);
        recyclerViewHorizontal=findViewById(R.id.recycler_horizontalShops);
       // recyclerViewTryHorizontal=findViewById(R.id.recycler_horizontalShopsTry);

        imageModelArrayList = eatFruits();
        adapter = new CategoriesAdapter(this, imageModelArrayList);
        recyclerViewCat.setAdapter(adapter);
        recyclerViewCat.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

        imageModelFeaturedArrayList=featuredItem();
        adapterFeatured=new FeaturedAdapter(this,imageModelFeaturedArrayList);
        recyclerViewFea.setAdapter(adapterFeatured);
        recyclerViewFea.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));

        shopsModelArrayList=shopsItem();
        shopsAdapter=new ShopsAdapter(this,shopsModelArrayList);
        recyclerViewShop.setAdapter(shopsAdapter);
        recyclerViewShop.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));

        horizontalModelArrayList=horizontalItems();
        horizontalAdapter=new HorizontalAdapter(this,horizontalModelArrayList);
        recyclerViewHorizontal.setAdapter(horizontalAdapter);
        recyclerViewHorizontal.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));

        ActionBarDrawerToggle toggle = new
                ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openNavDrawer, R.string.closeNavDrawer);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();
        toolbar.setNavigationIcon(R.drawable.icon_menu);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        navigationView.setCheckedItem(R.id.orders);
        toolbarTitle = (TextView) findViewById(R.id.loc_text_toolbar);
        toolbarTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog = new BottomSheetDialog(MainActivity.this);
                //View bottomSheetView=LayoutInflater.from(new ContextThemeWrapper(getApplicationContext(),R.style.AppTheme)).inflate(R.layout.content_dialog_bottom_sheet, (LinearLayout)findViewById(R.id.bottomSheetLayout));
                bottomSheetDialog.setContentView(R.layout.content_dialog_bottom_sheet);
                bottomSheetDialog.show();
                bottomSheetDialog.setCanceledOnTouchOutside(false);
                ImageView ivBottomClose = bottomSheetDialog.findViewById(R.id.imageView_close);
                ivBottomClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                    }
                });
            }
        });


    }

    private ArrayList<HorizontalModel> horizontalItems() {
        ArrayList<HorizontalModel> horizontalList = new ArrayList<>();

        for(int i = 0; i < 5; i++){
            HorizontalModel horizontalModel = new HorizontalModel();
            horizontalModel.setImageBackgroundHorizontalDrawable(myBackgroundImageList[i]);
            horizontalModel.setImageHorizontalDrawable(myHorizontalImageList[i]);
            horizontalModel.setText_horizontal_shopName(myNameStoreHorizontalList[i]);
            horizontalList.add(horizontalModel);
        }

        return horizontalList;
    }

    private ArrayList<ShopsModel> shopsItem() {
        ArrayList<ShopsModel> list = new ArrayList<>();

        for(int i = 0; i < 4; i++){
            ShopsModel shopsModel = new ShopsModel();
            shopsModel.setImage_shop_drawable(myShopImageList[i]);
            shopsModel.setText_shop_name(myShopNameList[i]);
            shopsModel.setText_shop_type(myShopTypeList[i]);
            shopsModel.setText_shop_limit(myShopLimitList[i]);
            shopsModel.setText_shop_off(myShopOffList[i]);
            shopsModel.setText_shop_rating(myShopRatingList[i]);
            list.add(shopsModel);
        }

        return list;
    }

    private ArrayList<CategoriesModel> eatFruits(){

        ArrayList<CategoriesModel> list = new ArrayList<>();

        for(int i = 0; i < 3; i++){
            CategoriesModel categoriesModel = new CategoriesModel();
            categoriesModel.setText_cat(myImageNameList[i]);
            categoriesModel.setImage_drawable(myImageList[i]);
            list.add(categoriesModel);
        }

        return list;
    }

    private ArrayList<FeaturedModel> featuredItem(){
        ArrayList<FeaturedModel> featuredList = new ArrayList<>();

        for(int i = 0; i < 4; i++){
            FeaturedModel featuredModel = new FeaturedModel();
            featuredModel.setImage_featured_drawable(myFeaturedImageList[i]);
            featuredList.add(featuredModel);
        }
        return featuredList;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.orders:
                Toast.makeText(this, "Orders are shown here!", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.about:
                Toast.makeText(this, "About is clicked!", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.feedback:
                Toast.makeText(this, "Give your feedback here!", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.log_out:
                logOut();
                return true;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logOut() {
        mAuth.signOut();
        sendToLogin();
    }

    private void sendToLogin() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
        Toast.makeText(MainActivity.this, "Signed Out", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
