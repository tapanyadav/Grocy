package com.example.grocy.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
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
import com.example.grocy.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final int AUTOCOMPLETE_REQUEST_CODE = 1;
    private FirebaseAuth mAuth;

    //location
    public static final int REQUEST_CODE = 9001;
    public static final int PLAY_SERVICES_ERROR_CODE = 9002;
    public static final String TAG = "Map";
    public static final int GPS_REQUEST_CODE = 903;
    static final float END_SCALE = 0.7f;

    AppBarLayout appBarLayout;

    boolean providerEnabled;
    CoordinatorLayout coordinatorLayout;

    //navigation drawer
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    BottomSheetDialog bottomSheetDialog;
    TextView toolbarTitle, textViewFeaturedAll;
    EditText editTextSearch;
    ImageButton imageButtonFilter;
    ShimmerFrameLayout shimmerFrameLayout;

    EditText editTextLocation;
    PlacesClient placesClient;
    String apiKey = "AIzaSyD3RtCpsidRz7EMJiR2EkWrYzoXFuwaUkI";

    String finalAddress;
    FusedLocationProviderClient fusedLocationProviderClient;
    private FeaturedAdapter adapterFeatured;
    private ShopsAdapter shopsAdapter;
    private HorizontalAdapter horizontalAdapter;
    private CategoriesAdapter categoriesAdapter;

    TextView textViewCurrentLocationDialog;
    String setUserLiveLocation;
    String setUserCustomLocation;
    public static FragmentManager fragmentManager;
    String globalAddress;
    Query queryShops;
    FirestoreRecyclerOptions<ShopsModel> shopsModelFirestoreRecyclerOptions;
    String flag="0";
    Intent main_intent;
    ArrayList<String> checkList=null;
    double rating=1;
    CircleImageView circleImageView;

    @SuppressLint({"RestrictedApi", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        shimmerFrameLayout = findViewById(R.id.shimmerAnimation);
        fragmentManager = getSupportFragmentManager();
        appBarLayout = findViewById(R.id.app_bar_layout);
        coordinatorLayout = findViewById(R.id.cordLay);

        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        fusedLocationProviderClient = new FusedLocationProviderClient(MainActivity.this);

        RecyclerView recyclerViewCat = findViewById(R.id.recycler);
        RecyclerView recyclerViewFea = findViewById(R.id.recycler_featured);
        RecyclerView recyclerViewShop = findViewById(R.id.recycler_shops);
        RecyclerView recyclerViewHorizontal = findViewById(R.id.recycler_horizontalShops);
        editTextSearch = findViewById(R.id.etSearch);
        imageButtonFilter = findViewById(R.id.image_button_filter);
        textViewFeaturedAll = findViewById(R.id.tv_content_featured_all);

        setUserCustomLocation = getIntent().getStringExtra("userEnterLocation");
        setUserLiveLocation = getIntent().getStringExtra("userLiveLocation");

//        linearLayout.setVisibility(View.GONE);
//        appBarLayout.setVisibility(View.GONE);
        coordinatorLayout.setVisibility(View.GONE);
        checkList = (ArrayList<String>) getIntent().getSerializableExtra("checkList");
        if(getIntent().getIntExtra("rating",1)>=1){
            rating=Double.parseDouble(String.valueOf(getIntent().getIntExtra("rating",1)));
        }

        new Handler().postDelayed(() -> {
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);
//            linearLayout.setVisibility(View.VISIBLE);
//            appBarLayout.setVisibility(View.VISIBLE);
            coordinatorLayout.setVisibility(View.VISIBLE);
        }, 2000);


        Query queryCategories = firebaseFirestore.collection("Categories").orderBy("catArrange");
        FirestoreRecyclerOptions<CategoriesModel> categoriesModelFirestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<CategoriesModel>()
                .setQuery(queryCategories, CategoriesModel.class).build();
        categoriesAdapter = new CategoriesAdapter(categoriesModelFirestoreRecyclerOptions);
        recyclerViewCat.setHasFixedSize(true);
        recyclerViewCat.setAdapter(categoriesAdapter);
        recyclerViewCat.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

        categoriesAdapter.setOnListItemClick((snapshot, position) -> {
            String resId = snapshot.getId();
            Toast.makeText(MainActivity.this, "Position: " + position + " and Id is " + resId, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, CategoriesDetailsActivity.class);
            intent.putExtra("resId", resId);
            intent.putExtra("catType", (Serializable) snapshot.getData().get("catType"));
            startActivity(intent);
        });

//        PagedList.Config config = new PagedList.Config.Builder()
//                .setInitialLoadSizeHint(4)
//                .setPageSize(3)
//                .build();

        Query queryFeatured = firebaseFirestore.collection("FeaturedItems").orderBy("featuredArrange");
        FirestoreRecyclerOptions<FeaturedModel> featuredModelFirestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<FeaturedModel>()
                .setQuery(queryFeatured, FeaturedModel.class).build();
        adapterFeatured = new FeaturedAdapter(featuredModelFirestoreRecyclerOptions);
        recyclerViewFea.setHasFixedSize(true);
        recyclerViewFea.setAdapter(adapterFeatured);
        recyclerViewFea.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        if(checkList!=null && rating>1) {
            queryShops = firebaseFirestore.collection("ShopsMain").whereIn("shopCategory",checkList).whereGreaterThanOrEqualTo("shopRating",rating).orderBy("shopRating", Query.Direction.DESCENDING);
        }
        else if(checkList==null && rating>1){
            queryShops = firebaseFirestore.collection("ShopsMain").whereGreaterThanOrEqualTo("shopRating",rating).orderBy("shopRating", Query.Direction.DESCENDING);
        }
        else{
            queryShops = firebaseFirestore.collection("ShopsMain").orderBy("shopArrange");
        }
            shopsModelFirestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<ShopsModel>()
                    .setQuery(queryShops, ShopsModel.class).build();
            shopsAdapter = new ShopsAdapter(shopsModelFirestoreRecyclerOptions);
            shopsAdapter.notifyDataSetChanged();
            shopsAdapter.setHasStableIds(true);

//        DisplayMetrics displayMetrics=new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        int a=(displayMetrics.heightPixels*99)/100;
//        recyclerViewShop.getLayoutParams().height=a;

//            recyclerViewShop.setHasFixedSize(false);
//            recyclerViewShop.setAdapter(shopsAdapter);
//            recyclerViewShop.setItemViewCacheSize(20);
//            recyclerViewShop.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewShop.setHasFixedSize(false);
        recyclerViewShop.setAdapter(shopsAdapter);
        recyclerViewShop.setLayoutManager(new LinearLayoutManager(this));

//        categoriesAdapter.setOnListItemClick((snapshot, position) -> {
//            String resId = snapshot.getId();
//            Toast.makeText(MainActivity.this, "Position: " + position + " and Id is " + resId, Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(MainActivity.this, CategoriesDetailsActivity.class);
//            intent.putExtra("resId", resId);
//            startActivity(intent);
//        });

        shopsAdapter.setOnListItemClick((snapshot, position) -> {
            String resId = snapshot.getId();
            Toast.makeText(MainActivity.this, "Position: " + position + " and Id is " + resId, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, ShopDetailsActivity.class);
            intent.putExtra("shopId", resId);
            startActivity(intent);

        });

        Query query = firebaseFirestore.collection("shopHorizontal").orderBy("shopArrange");
        FirestoreRecyclerOptions<HorizontalModel> horizontalModelFirestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<HorizontalModel>()
                .setQuery(query, HorizontalModel.class).build();

        horizontalAdapter = new HorizontalAdapter(horizontalModelFirestoreRecyclerOptions);

        recyclerViewHorizontal.setHasFixedSize(true);
        recyclerViewHorizontal.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewHorizontal.setAdapter(horizontalAdapter);

        ActionBarDrawerToggle toggle = new
                ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openNavDrawer, R.string.closeNavDrawer);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();
        navigationView.setItemIconTintList(null);
        //onNavigationItemSelected(navigationView.getMenu().findItem(R.id.home));
        //navigationView.setCheckedItem(R.id.home);
        navigationView.getMenu().getItem(0).setChecked(true);
        //navigationView.setCheckedItem(home);
        toolbar.setNavigationIcon(R.drawable.icon_menu);
        animateNavigationDrawer();
        View view = navigationView.getHeaderView(0);
        circleImageView = view.findViewById(R.id.profilePic);

        circleImageView.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, UserProfileActivity.class);
            startActivity(intent);
        });

        Objects.requireNonNull(getSupportActionBar()).setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarTitle = findViewById(R.id.loc_text_toolbar);

        if (setUserCustomLocation != null) {
            globalAddress = setUserCustomLocation;
            toolbarTitle.setText(globalAddress);
        } else {
            if (setUserLiveLocation != null) {
                globalAddress = setUserLiveLocation;
                toolbarTitle.setText(globalAddress);
            } else {
                currentLocation();
            }
        }
        if (!Places.isInitialized()) {
            Places.initialize(MainActivity.this, apiKey);
        }
        // Retrieve a PlacesClient (previously initialized - see MainActivity)
        placesClient = Places.createClient(MainActivity.this);

        toolbarTitle.setOnClickListener(v -> {
            bottomSheetDialog = new BottomSheetDialog(MainActivity.this);
            bottomSheetDialog.setContentView(R.layout.content_dialog_bottom_sheet);
            bottomSheetDialog.show();
            bottomSheetDialog.setCanceledOnTouchOutside(true);
            textViewCurrentLocationDialog = bottomSheetDialog.findViewById(R.id.textViewCurrentLoc);

            assert textViewCurrentLocationDialog != null;
            textViewCurrentLocationDialog.setOnClickListener(v12 -> {

                initGoogleMap();

                if (isGpsEnabled()) {
                    Toast.makeText(MainActivity.this, "All set up!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, MapActivity.class);
                    startActivity(intent);
                }
            });

            editTextLocation = bottomSheetDialog.findViewById(R.id.editTextLoc);

            assert editTextLocation != null;
            editTextLocation.setOnClickListener(v1 -> startAutocomplete());

            ImageView ivBottomClose = bottomSheetDialog.findViewById(R.id.imageView_close);
            assert ivBottomClose != null;
            ivBottomClose.setOnClickListener(bottomView -> bottomSheetDialog.dismiss());
        });

        textViewFeaturedAll.setOnClickListener(tvFeaturedView -> {
            Intent intent = new Intent(MainActivity.this, FeaturedAllActivity.class);
            startActivity(intent);
        });

        imageButtonFilter.setOnClickListener(filterView -> {
            FilterActivity bottomSheetDialogFilter = new FilterActivity();

            bottomSheetDialogFilter.show(getSupportFragmentManager(), "exampleBottomSheet");

        });


    }


    private void animateNavigationDrawer() {

        //Add any color or remove it to use the default one!
        //To make it transparent use Color.Transparent in side setScrimColor();
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                // Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                coordinatorLayout.setScaleX(offsetScale);
                coordinatorLayout.setScaleY(offsetScale);

                // Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = coordinatorLayout.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                coordinatorLayout.setTranslationX(xTranslation);
            }
        });

    }

    public void startAutocomplete() {

        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.OVERLAY,
                Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS))
                .setCountry("IN")
                .build(this);
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {

            case R.id.home:
                Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawer(Gravity.LEFT);
                return true;
            case R.id.notification:
                Intent intentNoti = new Intent(this, NotificationActivity.class);
                startActivity(intentNoti);
                return true;
            case R.id.orders:
                Toast.makeText(this, "All Orders are shown here!", Toast.LENGTH_SHORT).show();
                Intent intentOrders = new Intent(this, MyOrdersActivity.class);
                startActivity(intentOrders);
                return true;
            case R.id.favourite_orders:
                Toast.makeText(this, "Favourite orders are shown here!", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.feedback:
                showFeedbackDialog();
                return true;
            case R.id.about:
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                return true;
            case R.id.setting:
                Toast.makeText(this, "Settings for this app shown here!", Toast.LENGTH_SHORT).show();
                Intent intentSetting = new Intent(this, SettingsActivity.class);
                startActivity(intentSetting);
                return true;
            case R.id.log_out:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Are you sure you want to logout?")
                        .setPositiveButton("OK", (dialogInterface, i) -> logOut()).setNegativeButton("Cancel", null);
                AlertDialog alert = builder.create();
                alert.show();
                return true;
            case R.id.rate_us:
                showRatingDialog();
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

    private void showFeedbackDialog() {

        ViewGroup viewGroup = findViewById(android.R.id.content);

        View dialogView = LayoutInflater.from(this).inflate(R.layout.feedback_dialog, viewGroup, false);


        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button buttonLove = alertDialog.findViewById(R.id.btnLove);
        Button buttonImprove = alertDialog.findViewById(R.id.btnImprove);

        assert buttonImprove != null;
        buttonImprove.setOnClickListener(v -> {
            alertDialog.dismiss();
            showExpDialog();
        });

        assert buttonLove != null;
        buttonLove.setOnClickListener(v -> {
            alertDialog.dismiss();
            showRatingDialog();
        });
    }

    private void showRatingDialog() {
        ViewGroup viewGroup = findViewById(android.R.id.content);

        View dialogView = LayoutInflater.from(this).inflate(R.layout.rating_dialog, viewGroup, false);


        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button buttonRate = alertDialog.findViewById(R.id.btnRatingPlay);
        TextView textViewNo = alertDialog.findViewById(R.id.textNoThanks);
        assert buttonRate != null;
        buttonRate.setOnClickListener(v -> {
            Toast.makeText(this, "Opening Play store!", Toast.LENGTH_SHORT).show();
            alertDialog.dismiss();
        });

        assert textViewNo != null;
        textViewNo.setOnClickListener(v -> {
            alertDialog.dismiss();
        });
    }

    private void showExpDialog() {
        ViewGroup viewGroup = findViewById(android.R.id.content);

        View dialogView = LayoutInflater.from(this).inflate(R.layout.experience_dialog, viewGroup, false);


        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(false);

        Button buttonFeedback = alertDialog.findViewById(R.id.btnFeed);
        ImageView imageViewClose = alertDialog.findViewById(R.id.imageClose);
        assert buttonFeedback != null;
        buttonFeedback.setOnClickListener(v -> {
            Toast.makeText(this, "Feedback sent!!", Toast.LENGTH_SHORT).show();
            alertDialog.dismiss();
        });

        assert imageViewClose != null;
        imageViewClose.setOnClickListener(v -> {
            alertDialog.dismiss();
        });
    }

    private void initGoogleMap() {

        if (isServicesOk()) {
            if (checkLocationPermission()) {
                Toast.makeText(this, "All set up!", Toast.LENGTH_SHORT).show();
//
            } else {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
                    }
                }
            }
        }

    }

    private boolean checkLocationPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

    private boolean isServicesOk() {

        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int result = googleApiAvailability.isGooglePlayServicesAvailable(this);

        if (result == ConnectionResult.SUCCESS) {
            return true;
        } else if (googleApiAvailability.isUserResolvableError(result)) {
            Dialog dialog = googleApiAvailability.getErrorDialog(this, result, PLAY_SERVICES_ERROR_CODE);
            dialog.show();
        } else {
            Toast.makeText(this, "Play services are required to use some features ", Toast.LENGTH_SHORT).show();

        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            boolean mLocationPermissionGranted = true;
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isGpsEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        assert locationManager != null;
        providerEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (providerEnabled) {
            return true;
        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setTitle("GPS Permission")
                    .setMessage("GPS is required for this app")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(intent, GPS_REQUEST_CODE);
                    })
                    .setNegativeButton("No Thanks", ((dialog, which) -> dialog.dismiss()))
                    .setCancelable(false)
                    .show();
        }

        return false;
    }

    private void currentLocation() {
        initGoogleMap();
        if (isGpsEnabled()) {
            Toast.makeText(MainActivity.this, "All set up!", Toast.LENGTH_SHORT).show();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Location location = task.getResult();
                    if (location != null) {
                        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                        try {
                            List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                            if (addressList != null) {
                                Address address = addressList.get(0);

                                finalAddress = address.getSubLocality() + "," + address.getSubAdminArea();
                                toolbarTitle.setText(finalAddress);
                                Toast.makeText(this, finalAddress, Toast.LENGTH_SHORT).show();

                            }

                            assert addressList != null;
                            for (Address address : addressList) {
                                Log.d(TAG, "geoLocate: Address" + address.getAddressLine(address.getMaxAddressLineIndex()));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        LatLng latlngValue;
        String placeAddress;

        if (requestCode == GPS_REQUEST_CODE){

            LocationManager locationManager= (LocationManager) getSystemService(LOCATION_SERVICE);
            assert locationManager != null;
            boolean providerEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (providerEnabled){
                Toast.makeText(this, "GPS is enabled", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "GPS not enabled, Unable to show location.", Toast.LENGTH_SHORT).show();

            }
        }

        if (requestCode == AUTOCOMPLETE_REQUEST_CODE){
            if (resultCode == RESULT_OK){
                assert data != null;
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.i(TAG,"Place: "+place.getName()+" , "+place.getId());

                latlngValue=place.getLatLng();
                placeAddress=place.getAddress();

                Intent intent=new Intent(MainActivity.this,UserEnterLocationActivity.class);
                intent.putExtra("latlngValue",latlngValue);
                intent.putExtra("userAddress",placeAddress);
                startActivity(intent);
                Toast.makeText(this, "Place: "+place, Toast.LENGTH_SHORT).show();


            }else if (resultCode == AutocompleteActivity.RESULT_ERROR){
                assert data != null;
                Status status = Autocomplete.getStatusFromIntent(data);
                assert status.getStatusMessage() != null;
                Log.i(TAG,status.getStatusMessage());
            }else if (resultCode == RESULT_CANCELED){

            }
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        horizontalAdapter.startListening();
        adapterFeatured.startListening();
        categoriesAdapter.startListening();
        shopsAdapter.startListening();

    }


    @Override
    protected void onStop() {
        super.onStop();
        horizontalAdapter.stopListening();
        adapterFeatured.stopListening();
        categoriesAdapter.stopListening();
        shopsAdapter.stopListening();
    }

}

