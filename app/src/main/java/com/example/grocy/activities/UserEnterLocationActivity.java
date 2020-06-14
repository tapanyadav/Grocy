package com.example.grocy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grocy.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Objects;

public class UserEnterLocationActivity extends AppCompatActivity implements OnMapReadyCallback {


    private GoogleMap mGoogleMap;

    Button buttonMapConfirmLoc;

    TextView textViewUserLocation;

    String userPlaceAddress;
    LatLng latLngValue;
    Double lat;
    Double lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_enter_location);

        buttonMapConfirmLoc=findViewById(R.id.mapUserButtonConfirmLoc);
        textViewUserLocation=findViewById(R.id.mapUserLocation);


        userPlaceAddress = getIntent().getStringExtra("userAddress");
        latLngValue = (LatLng) Objects.requireNonNull(getIntent().getExtras()).get("latlngValue");

        Toast.makeText(this, "User: "+userPlaceAddress, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Lat lng: "+latLngValue, Toast.LENGTH_SHORT).show();

        lat=latLngValue.latitude;
        lng=latLngValue.longitude;
        textViewUserLocation.setText(userPlaceAddress);

        SupportMapFragment supportMapFragment= (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_fragment);

        assert supportMapFragment != null;
        supportMapFragment.getMapAsync(this);

        buttonMapConfirmLoc.setOnClickListener(v -> {
            Intent intent=new Intent(UserEnterLocationActivity.this,MainActivity.class);
            intent.putExtra("userEnterLocation", userPlaceAddress);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap = googleMap;
        userLocation();
    }

    private void gotoLocation(double lat,double lng){
        LatLng latLng=new LatLng(lat,lng);
        CameraUpdate cameraUpdate= CameraUpdateFactory.newLatLngZoom(latLng,17);
        mGoogleMap.moveCamera(cameraUpdate);
    }

    private void userLocation(){


        gotoLocation(lat,lng);
        mGoogleMap.addMarker(new MarkerOptions().position(latLngValue));
    }
}
