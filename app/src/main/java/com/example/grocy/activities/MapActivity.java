package com.example.grocy.activities;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grocy.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    public static final String TAG = "Map";

    private FusedLocationProviderClient fusedLocationProviderClient;
    private GoogleMap mGoogleMap;

    Button buttonMapConfirmLoc;

    TextView textViewLocation;
    String finalAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);


        textViewLocation=findViewById(R.id.mapLocation);

        SupportMapFragment supportMapFragment= (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_fragment);

        assert supportMapFragment != null;
        supportMapFragment.getMapAsync(this);

        fusedLocationProviderClient=new FusedLocationProviderClient(this);


        buttonMapConfirmLoc=findViewById(R.id.mapButtonConfirmLoc);

        buttonMapConfirmLoc.setOnClickListener(v -> {
            Intent intent=new Intent(MapActivity.this,MainActivity.class);
            intent.putExtra("userLiveLocation", finalAddress);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG,"map is showing on the screen.");
        Toast.makeText(this, "map is showing on the screen.", Toast.LENGTH_SHORT).show();

        mGoogleMap = googleMap;

        currentLocation();
    }



    private void gotoLocation(double lat,double lng){
        LatLng latLng=new LatLng(lat,lng);
        CameraUpdate cameraUpdate= CameraUpdateFactory.newLatLngZoom(latLng,16);
        mGoogleMap.moveCamera(cameraUpdate);
    }

//    private void showMarker(double lat,double lng){
//        MarkerOptions markerOptions=new MarkerOptions()
//                .title("Title")
//                .position(new LatLng(lat,lng));
//
//        mGoogleMap.addMarker(markerOptions);
//    }

    private void currentLocation() {

        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(task -> {
            if ( task.isSuccessful()){
                Location location=task.getResult();
                if (location!=null){
                    Geocoder geocoder=new Geocoder(this, Locale.getDefault());
                    try {
                        List<Address> addressList = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);

                        if (addressList != null){
                            Address address=addressList.get(0);

                            gotoLocation(address.getLatitude(),address.getLongitude());
                            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(),location.getLongitude())));

                            Toast.makeText(this, ""+(address.getAddressLine(0)+address.getLocality()), Toast.LENGTH_SHORT).show();

                            finalAddress = address.getAddressLine(0) + address.getLocality();
                            textViewLocation.setText(finalAddress);
                        }

                        assert addressList != null;
                        for (Address address:addressList){
                            Log.d(TAG, "geoLocate: Address"+address.getAddressLine(address.getMaxAddressLineIndex()));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }else {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
