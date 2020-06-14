package com.example.grocy.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.grocy.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
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
    Marker marker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);


        textViewLocation = findViewById(R.id.mapLocation);

        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_fragment);

        assert supportMapFragment != null;
        supportMapFragment.getMapAsync(this);

        fusedLocationProviderClient = new FusedLocationProviderClient(this);


        buttonMapConfirmLoc = findViewById(R.id.mapButtonConfirmLoc);

        //new

//        frameLayout = findViewById(R.id.map_container);
//        circleFrameLayout = frameLayout.findViewById(R.id.pin_view_circle);
//        textView = circleFrameLayout.findViewById(R.id.textView);
//        progressBar = circleFrameLayout.findViewById(R.id.profile_loader);

        buttonMapConfirmLoc.setOnClickListener(v -> {
            Intent intent = new Intent(MapActivity.this, MainActivity.class);
            intent.putExtra("userLiveLocation", finalAddress);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "map is showing on the screen.");
        Toast.makeText(this, "map is showing on the screen.", Toast.LENGTH_SHORT).show();

        mGoogleMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        currentLocation();

//        mGoogleMap.setOnCameraMoveStartedListener(i -> {
//            isMoving = true;
//            textView.setVisibility(View.GONE);
//            progressBar.setVisibility(View.GONE);
//
//            Drawable drawable;
//            drawable = getApplicationContext().getResources().getDrawable(R.drawable.circle_background_moving, null);
//            circleFrameLayout.setBackground(drawable);
//
//            resizeLayout(false);
//        });
//        mGoogleMap.setOnCameraIdleListener(() -> {
//            isMoving = false;
//            textView.setVisibility(View.INVISIBLE);
//            progressBar.setVisibility(View.VISIBLE);
//
//            LatLng pos = mGoogleMap.getCameraPosition().target;
//
//
//            resizeLayout(true);
//
//
//            new Handler().postDelayed(() -> {
//                Drawable drawable;
//                drawable = getApplicationContext().getResources().getDrawable(R.drawable.circle_background, null);
//                if (!isMoving) {
//                    circleFrameLayout.setBackground(drawable);
//                    textView.setVisibility(View.VISIBLE);
//                    progressBar.setVisibility(View.GONE);
//                }
//            }, 1500);
//
//        });

        //MapsInitializer.initialize(this);
//        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                Location location = task.getResult();
//                if (location != null) {
//                    gotoLocation(location.getLatitude(),location.getLongitude());
//                }
//            }else {
//                Toast.makeText(this, "Error in move maps", Toast.LENGTH_SHORT).show();
//            }
//        });

    }

//    private void resizeLayout(boolean backToNormalSize) {
//        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) circleFrameLayout.getLayoutParams();
//        ViewTreeObserver viewTreeObserver = circleFrameLayout.getViewTreeObserver();
//        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                circleFrameLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                circleRadius = circleFrameLayout.getMeasuredWidth();
//            }
//        });
//        if (backToNormalSize) {
//            layoutParams.width = WRAP_CONTENT;
//            layoutParams.height = WRAP_CONTENT;
//            layoutParams.topMargin = 0;
//        } else {
//            layoutParams.topMargin = (int) (circleRadius * 0.3);
//            layoutParams.height = circleRadius - circleRadius / 3;
//            layoutParams.width = circleRadius - circleRadius / 3;
//        }
//        circleFrameLayout.setLayoutParams(layoutParams);
//    }

    private void gotoLocation(double lat, double lng) {
        LatLng latLng = new LatLng(lat, lng);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
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

                            gotoLocation(address.getLatitude(), address.getLongitude());
                            marker = mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("Your Location")
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.icons_24px_black_pin)));

                            Toast.makeText(this, "" + (address.getAddressLine(0) + address.getLocality()), Toast.LENGTH_SHORT).show();

                            finalAddress = address.getSubLocality() + "," + address.getSubAdminArea();
                            textViewLocation.setText(finalAddress);
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
