package com.example.grocy.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.grocy.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;

public class StartLocationActivity extends AppCompatActivity {

    HashMap<String, Object> userDefaultData = new HashMap<>();
    //Location
    private static final int AUTOCOMPLETE_REQUEST_CODE = 1;

    public static final int REQUEST_CODE = 9001;
    public static final int PLAY_SERVICES_ERROR_CODE = 9002;
    public static final int GPS_REQUEST_CODE = 903;
    public static final String TAG = "Map";

    BottomSheetDialog bottomSheetDialogStartLocation;

    boolean providerEnabled;
    TextView textViewCurrentLocationDialog;
    EditText editTextLocation;
    PlacesClient placesClient;
    FirebaseFirestore firebaseFirestore;
    String apiKey = "AIzaSyD3RtCpsidRz7EMJiR2EkWrYzoXFuwaUkI";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_location);

        Button buttonLocNext = findViewById(R.id.startLocNext);
        firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        String userStartId = getIntent().getStringExtra("userStartId");
        Button buttonLocation = findViewById(R.id.startLocation);
        if (!Places.isInitialized()) {
            Places.initialize(StartLocationActivity.this, apiKey);
        }
        // Retrieve a PlacesClient (previously initialized - see StartLocationActivity)
        placesClient = Places.createClient(StartLocationActivity.this);

        buttonLocation.setOnClickListener(v -> {
            userDefaultData.put("noOfBookmarks", "0");
            userDefaultData.put("userLevel", "1");
            userDefaultData.put("userDetailedStatus", "Bronze");
            userDefaultData.put("reviewCount", 0);
            userDefaultData.put("photoCount", 0);
            userDefaultData.put("ordersCount", 0);
            userDefaultData.put("profilePic", "https://firebasestorage.googleapis.com/v0/b/grocy-6c5b5.appspot.com/o/profile_images%2Fuser_profile.jpg?alt=media&token=607a6dd4-b477-4293-9aaf-1b43f929a450");

            assert userStartId != null;
            firebaseFirestore.collection("Users").document(userStartId).update(userDefaultData).addOnCompleteListener(task1 -> {
                if (task1.isSuccessful()) {
                    Toast.makeText(this, "Default data added", Toast.LENGTH_SHORT).show();
                }
            });
            bottomSheetDialogStartLocation = new BottomSheetDialog(StartLocationActivity.this);
            //View bottomSheetView=LayoutInflater.from(new ContextThemeWrapper(getApplicationContext(),R.style.AppTheme)).inflate(R.layout.content_dialog_bottom_sheet, (LinearLayout)findViewById(R.id.bottomSheetLayout));
            bottomSheetDialogStartLocation.setContentView(R.layout.content_dialog_bottom_sheet);
            bottomSheetDialogStartLocation.show();
            bottomSheetDialogStartLocation.setCanceledOnTouchOutside(false);

            textViewCurrentLocationDialog = bottomSheetDialogStartLocation.findViewById(R.id.textViewCurrentLoc);

            assert textViewCurrentLocationDialog != null;
            textViewCurrentLocationDialog.setOnClickListener(v12 -> {

                initGoogleMap();

                if (isGpsEnabled()) {
                    Toast.makeText(StartLocationActivity.this, "All set up!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(StartLocationActivity.this, MapActivity.class);
                    startActivity(intent);
                }
            });

            editTextLocation = bottomSheetDialogStartLocation.findViewById(R.id.editTextLoc);

            assert editTextLocation != null;
            editTextLocation.setOnClickListener(v1 -> startAutocomplete());
            ImageView ivBottomClose = bottomSheetDialogStartLocation.findViewById(R.id.imageView_close);
            assert ivBottomClose != null;
            ivBottomClose.setOnClickListener(v1 -> bottomSheetDialogStartLocation.dismiss());
        });

        initGoogleMap();

        if (isGpsEnabled()){

            Toast.makeText(this, "All set up!", Toast.LENGTH_SHORT).show();
        }

        buttonLocNext.setEnabled(providerEnabled);
        buttonLocNext.setOnClickListener(v -> {

            userDefaultData.put("noOfBookmarks", "0");
            userDefaultData.put("userLevel", "1");
            userDefaultData.put("userDetailedStatus", "Bronze");
            userDefaultData.put("reviewCount", 0);
            userDefaultData.put("photoCount", 0);
            userDefaultData.put("ordersCount", 0);
            userDefaultData.put("profilePic", "https://firebasestorage.googleapis.com/v0/b/grocy-6c5b5.appspot.com/o/profile_images%2Fuser_profile.jpg?alt=media&token=607a6dd4-b477-4293-9aaf-1b43f929a450");

            firebaseFirestore.collection("Users").document(userStartId).update(userDefaultData).addOnCompleteListener(task1 -> {
                if (task1.isSuccessful()) {
                    Toast.makeText(this, "Default data added", Toast.LENGTH_SHORT).show();
                }
            });

            Intent intent = new Intent(StartLocationActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
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
            //boolean mLocationPermissionGranted = true;
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

        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.i(TAG, "Place: " + place.getName() + " , " + place.getId());

                latlngValue = place.getLatLng();
                placeAddress = place.getAddress();

                Intent intent = new Intent(StartLocationActivity.this, UserEnterLocationActivity.class);
                intent.putExtra("latlngValue", latlngValue);
                intent.putExtra("userAddress", placeAddress);
                startActivity(intent);
                Toast.makeText(this, "Place: " + place, Toast.LENGTH_SHORT).show();


            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                assert data != null;
                Status status = Autocomplete.getStatusFromIntent(data);
                assert status.getStatusMessage() != null;
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {

            }
        }
    }



}
