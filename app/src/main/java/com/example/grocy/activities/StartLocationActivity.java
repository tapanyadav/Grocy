package com.example.grocy.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.grocy.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;

public class StartLocationActivity extends AppCompatActivity {

    //Location

    public static final int REQUEST_CODE = 9001;
    public static final int PLAY_SERVICES_ERROR_CODE = 9002;
    public static final int GPS_REQUEST_CODE = 903;
    public static final String TAG = "Map";

    private FusedLocationProviderClient fusedLocationProviderClient;

    boolean providerEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_location);

        Button buttonLocNext = findViewById(R.id.startLocNext);

        Button buttonLocation= findViewById(R.id.startLocation);
        fusedLocationProviderClient = new FusedLocationProviderClient(this);

        buttonLocation.setOnClickListener(v -> {
            Intent intent = new Intent(StartLocationActivity.this,MapActivity.class);
            startActivity(intent);
            finish();
        });

        initGoogleMap();

        if (isGpsEnabled()){

            Toast.makeText(this, "All set up!", Toast.LENGTH_SHORT).show();
        }

        buttonLocNext.setEnabled(providerEnabled);
        buttonLocNext.setOnClickListener(v -> {
            Intent intent=new Intent(StartLocationActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
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
    }



}
