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
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
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
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.maps.GoogleMap;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final int AUTOCOMPLETE_REQUEST_CODE = 1;
    private FirebaseAuth mAuth;

    //location
    public static final int REQUEST_CODE = 9001;
    public static final int PLAY_SERVICES_ERROR_CODE = 9002;
    public static final String TAG = "Map";
    public static final int GPS_REQUEST_CODE = 903;

    private FusedLocationProviderClient fusedLocationProviderClient;

    boolean providerEnabled;

    //navigation drawer
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    BottomSheetDialog bottomSheetDialog, bottomSheetDialogFilter;
    TextView toolbarTitle, textViewFeaturedAll;
    private Toolbar toolbar;
    EditText editTextSearch;
    ImageButton imageButtonFilter;

    EditText editTextLocation;
    PlacesClient placesClient;
    String apiKey = "AIzaSyD3RtCpsidRz7EMJiR2EkWrYzoXFuwaUkI";


    //AutoCompleteTextView autoCompleteTextViewLocation;
    Double lng;
    Double lat;
    String finalAddress;

    //adapters

    private RecyclerView recyclerViewCat, recyclerViewFea, recyclerViewShop, recyclerViewHorizontal, recyclerViewTryHorizontal;
    private ArrayList<CategoriesModel> imageModelArrayList;
    private ArrayList<FeaturedModel> imageModelFeaturedArrayList;
    private ArrayList<ShopsModel> shopsModelArrayList;
    private ArrayList<HorizontalModel> horizontalModelArrayList;
    private CategoriesAdapter adapter;
    private FeaturedAdapter adapterFeatured;
    private ShopsAdapter shopsAdapter;
    private HorizontalAdapter horizontalAdapter;

    private int[] myImageList = new int[]{R.drawable.grocery, R.drawable.pharm, R.drawable.stationary};
    private int[] myFeaturedImageList = new int[]{R.drawable.epic_deals, R.drawable.gupta_groc, R.drawable.chotu_med, R.drawable.tyagi_stat};
    private String[] myImageNameList = new String[]{"Grocery", "Pharmacy", "Stationary"};
    private int[] myShopImageList = new int[]{R.drawable.gupta_shop, R.drawable.chotu_shop, R.drawable.tyagi_shop, R.drawable.raghu_shop};
    private String[] myShopNameList = new String[]{"Gupta Grocery Store", "Chotu Medical Store", "Tyagi Stationary Store", "Raghu Fruits Corner"};
    private String[] myShopTypeList = new String[]{"Daily need items", "Medicines", "Books,Pens,Calculator", "Apple,Oranges,Banana"};
    private String[] myShopLimitList = new String[]{"200 per order | 40 min", "300 per order | 30 min", "100 per order | 50 min", "100 per order | 20 min"};
    private String[] myShopOffList = new String[]{"30% OFF - use code WELCOME30", "10% OFF - use code WELCOME10", "20% OFF - use code WELCOME20", "40% OFF - no code needed"};
    private String[] myShopRatingList = new String[]{"4.5", "4.7", "4.2", "3.5"};
    private int[] myHorizontalImageList = new int[]{R.drawable.stationary, R.drawable.groc, R.drawable.medicines, R.drawable.tea, R.drawable.fruits};
    private int[] myBackgroundImageList = new int[]{R.color.mainAppColor, R.color.yellow, R.color.lightBlue, R.color.lightGreen, R.color.lightPink};
    private String[] myNameStoreHorizontalList = new String[]{"Tyagi Store", "Gupta Store", "Chotu Store", "Ujjwal Tea Shop", "Raghu Fruits Corner"};

//    private int[] myHorizontalImageTryList=new int[]{R.drawable.stationary,R.drawable.groc,R.drawable.medicines};
//    private int[] myBackgroundImageTryList=new int[]{R.color.mainAppColor,R.color.yellow,R.color.lightBlue};
//    private String[] myNameStoreHorizontalTryList=new String[]{"Tyagi Store","Gupta Store","Chotu Store"};

    TextView textViewCurrentLocationDialog;
    Spinner spinner;
    Button submit;

    String setUserLiveLocation;
    String setUserCustomLocation;
    String globalAddress;

    SeekBar seekBar;


    @SuppressLint({"RestrictedApi", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        mAuth = FirebaseAuth.getInstance();
        fusedLocationProviderClient = new FusedLocationProviderClient(MainActivity.this);

        recyclerViewCat = findViewById(R.id.recycler);
        recyclerViewFea = findViewById(R.id.recycler_featured);
        recyclerViewShop = findViewById(R.id.recycler_shops);
        recyclerViewHorizontal = findViewById(R.id.recycler_horizontalShops);
        editTextSearch = findViewById(R.id.etSearch);
        imageButtonFilter = findViewById(R.id.image_button_filter);
        textViewFeaturedAll = findViewById(R.id.tv_content_featured_all);
        // recyclerViewTryHorizontal=findViewById(R.id.recycler_horizontalShopsTry);

        setUserCustomLocation = getIntent().getStringExtra("userEnterLocation");
        setUserLiveLocation = getIntent().getStringExtra("userLiveLocation");


        imageModelArrayList = eatFruits();
        adapter = new CategoriesAdapter(this, imageModelArrayList);
        recyclerViewCat.setAdapter(adapter);
        recyclerViewCat.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

        imageModelFeaturedArrayList = featuredItem();
        adapterFeatured = new FeaturedAdapter(this, imageModelFeaturedArrayList);
        recyclerViewFea.setAdapter(adapterFeatured);
        recyclerViewFea.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

        shopsModelArrayList = shopsItem();
        shopsAdapter = new ShopsAdapter(this, shopsModelArrayList);
        recyclerViewShop.setAdapter(shopsAdapter);
        recyclerViewShop.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        horizontalModelArrayList = horizontalItems();
        horizontalAdapter = new HorizontalAdapter(this, horizontalModelArrayList);
        recyclerViewHorizontal.setAdapter(horizontalAdapter);
        recyclerViewHorizontal.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

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


        if (setUserCustomLocation != null) {
            globalAddress = "c:" + setUserCustomLocation;
            toolbarTitle.setText(globalAddress);
        } else {
            if (setUserLiveLocation != null) {
                globalAddress = "m:" + setUserLiveLocation;
                toolbarTitle.setText(globalAddress);
            } else {
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
                                    globalAddress = "f:" + finalAddress;
                                    toolbarTitle.setText(globalAddress);
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
            seekBar=bottomSheetDialogFilter.findViewById(R.id.seekBar);
            spinner = (Spinner) bottomSheetDialogFilter.findViewById(R.id.planets_spinner);
            AtomicReference<String> selectedSeekBar = new AtomicReference<>("0");
            List<String> list = new ArrayList<String>();
            list.add("list 1");
            list.add("list 2");
            list.add("list 3");
            AtomicReference<String> selectedSpinner = new AtomicReference<>("list 1");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(dataAdapter);
            submit=bottomSheetDialogFilter.findViewById(R.id.submit);
            submit.setOnClickListener(printData->{
                selectedSeekBar.set(""+seekBar.getProgress());
                selectedSpinner.set((String)spinner.getSelectedItem());
                Toast. makeText(MainActivity.this,"SeekBar value: "+selectedSeekBar.toString() +", Spinner value: "+selectedSpinner.toString(), Toast. LENGTH_LONG).show();

                bottomSheetDialogFilter.dismiss();

            });


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

}
