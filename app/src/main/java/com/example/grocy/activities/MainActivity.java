package com.example.grocy.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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
import androidx.core.content.ContextCompat;
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
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final int AUTOCOMPLETE_REQUEST_CODE = 1;
    private FirebaseAuth mAuth;

    //location
    public static final int REQUEST_CODE = 9001;
    public static final int PLAY_SERVICES_ERROR_CODE = 9002;
    public static final String TAG = "Map";
    public static final int GPS_REQUEST_CODE = 903;

    boolean providerEnabled;

    //navigation drawer
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    BottomSheetDialog bottomSheetDialog,bottomSheetDialogFilter;
    TextView toolbarTitle,textViewFeaturedAll;
    EditText editTextSearch;
    ImageButton imageButtonFilter;
    ShimmerFrameLayout shimmerFrameLayout;

    EditText editTextLocation;
    PlacesClient placesClient;
    String apiKey="AIzaSyD3RtCpsidRz7EMJiR2EkWrYzoXFuwaUkI";

    String finalAddress;
    FusedLocationProviderClient fusedLocationProviderClient;
    private FeaturedAdapter adapterFeatured;
    private ShopsAdapter shopsAdapter;
    private HorizontalAdapter horizontalAdapter;
    private CategoriesAdapter categoriesAdapter;

    TextView textViewCurrentLocationDialog;
    String setUserLiveLocation;
    String setUserCustomLocation;

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


        mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        fusedLocationProviderClient = new FusedLocationProviderClient(MainActivity.this);

        RecyclerView recyclerViewCat = findViewById(R.id.recycler);
        RecyclerView recyclerViewFea = findViewById(R.id.recycler_featured);
        RecyclerView recyclerViewShop = findViewById(R.id.recycler_shops);
        RecyclerView recyclerViewHorizontal = findViewById(R.id.recycler_horizontalShops);
        editTextSearch=findViewById(R.id.etSearch);
        imageButtonFilter=findViewById(R.id.image_button_filter);
        textViewFeaturedAll=findViewById(R.id.tv_content_featured_all);

        setUserCustomLocation = getIntent().getStringExtra("userEnterLocation");
        setUserLiveLocation = getIntent().getStringExtra("userLiveLocation");

        new Handler().postDelayed(() -> {
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);
        }, 5000);


        Query queryCategories = firebaseFirestore.collection("Categories").orderBy("catArrange");
        FirestoreRecyclerOptions<CategoriesModel> categoriesModelFirestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<CategoriesModel>()
                .setQuery(queryCategories, CategoriesModel.class).build();
        categoriesAdapter = new CategoriesAdapter(categoriesModelFirestoreRecyclerOptions);
        recyclerViewCat.setHasFixedSize(true);
        recyclerViewCat.setAdapter(categoriesAdapter);
        recyclerViewCat.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

        Query queryFeatured = firebaseFirestore.collection("FeaturedItems").orderBy("featuredArrange");
        FirestoreRecyclerOptions<FeaturedModel> featuredModelFirestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<FeaturedModel>()
                .setQuery(queryFeatured, FeaturedModel.class).build();
        adapterFeatured = new FeaturedAdapter(featuredModelFirestoreRecyclerOptions);
        recyclerViewFea.setHasFixedSize(true);
        recyclerViewFea.setAdapter(adapterFeatured);
        recyclerViewFea.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));

        Query queryShops = firebaseFirestore.collection("ShopsMain").orderBy("shopArrange");
        FirestoreRecyclerOptions<ShopsModel> shopsModelFirestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<ShopsModel>()
                .setQuery(queryShops, ShopsModel.class).build();
        shopsAdapter = new ShopsAdapter(shopsModelFirestoreRecyclerOptions);
        shopsAdapter.notifyDataSetChanged();
        shopsAdapter.setHasStableIds(true);
        recyclerViewShop.setHasFixedSize(true);
        recyclerViewShop.setAdapter(shopsAdapter);
        recyclerViewShop.setItemViewCacheSize(20);
        recyclerViewShop.setLayoutManager(new LinearLayoutManager(this));

        Query query = firebaseFirestore.collection("shopHorizontal").orderBy("shopHorizontalName");
        FirestoreRecyclerOptions<HorizontalModel> horizontalModelFirestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<HorizontalModel>()
                .setQuery(query, HorizontalModel.class).build();

        horizontalAdapter = new HorizontalAdapter(horizontalModelFirestoreRecyclerOptions);

        recyclerViewHorizontal.setHasFixedSize(true);
        recyclerViewHorizontal.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
        recyclerViewHorizontal.setAdapter(horizontalAdapter);

        ActionBarDrawerToggle toggle = new
                ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openNavDrawer, R.string.closeNavDrawer);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();
        toolbar.setNavigationIcon(R.drawable.icon_menu);

        Objects.requireNonNull(getSupportActionBar()).setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        navigationView.setCheckedItem(R.id.orders);

        toolbarTitle = findViewById(R.id.loc_text_toolbar);

        currentLocation();
        if (!Places.isInitialized()) {
            Places.initialize(MainActivity.this, apiKey);
        }
        // Retrieve a PlacesClient (previously initialized - see MainActivity)
        placesClient = Places.createClient(MainActivity.this);

        toolbarTitle.setOnClickListener(v -> {
            bottomSheetDialog = new BottomSheetDialog(MainActivity.this);
            //View bottomSheetView=LayoutInflater.from(new ContextThemeWrapper(getApplicationContext(),R.style.AppTheme)).inflate(R.layout.content_dialog_bottom_sheet, (LinearLayout)findViewById(R.id.bottomSheetLayout));
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
            Intent intent=new Intent(MainActivity.this, FeaturedAllActivity.class);
            startActivity(intent);
        });

        imageButtonFilter.setOnClickListener( filterView -> {

            bottomSheetDialogFilter = new BottomSheetDialog(MainActivity.this);
            bottomSheetDialogFilter.setContentView(R.layout.content_filter_bottom_sheet);
            bottomSheetDialogFilter.show();
            bottomSheetDialogFilter.setCanceledOnTouchOutside(true);


            ImageView ivBottomClose = bottomSheetDialogFilter.findViewById(R.id.imageView_close);
            assert ivBottomClose != null;
            ivBottomClose.setOnClickListener(closeView -> bottomSheetDialogFilter.dismiss());
        });


    }

    public void startAutocomplete(){

        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.OVERLAY,
                Arrays.asList(Place.Field.ID,  Place.Field.NAME, Place.Field.LAT_LNG,Place.Field.ADDRESS))
                .setCountry("IN")
                .build(this);
        startActivityForResult(intent,AUTOCOMPLETE_REQUEST_CODE);
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
        return ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)
                ==PackageManager.PERMISSION_GRANTED;
    }

    private boolean isServicesOk() {

        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int result = googleApiAvailability.isGooglePlayServicesAvailable(this);

        if (result == ConnectionResult.SUCCESS){
            return true;
        }else if (googleApiAvailability.isUserResolvableError(result)){
            Dialog dialog = googleApiAvailability.getErrorDialog(this,result, PLAY_SERVICES_ERROR_CODE);
            dialog.show();
        }else {
            Toast.makeText(this, "Play services are required to use some features ", Toast.LENGTH_SHORT).show();

        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE && grantResults[0]== PackageManager.PERMISSION_GRANTED){
            boolean mLocationPermissionGranted = true;
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isGpsEnabled(){
        LocationManager locationManager= (LocationManager) getSystemService(LOCATION_SERVICE);
        assert locationManager != null;
        providerEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (providerEnabled){
            return true;
        }else {
            AlertDialog alertDialog= new AlertDialog.Builder(this)
                    .setTitle("GPS Permission")
                    .setMessage("GPS is required for this app")
                    .setPositiveButton("Yes",(dialog, which) -> {
                        Intent intent=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
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
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Location location = task.getResult();
                    if (location != null) {
                        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                        try {
                            List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                            if (addressList != null) {
                                Address address = addressList.get(0);

                                finalAddress = address.getAddressLine(0) + address.getLocality();
                                toolbarTitle.setText(finalAddress);
                                Toast.makeText(this, "f:" + finalAddress, Toast.LENGTH_SHORT).show();

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
