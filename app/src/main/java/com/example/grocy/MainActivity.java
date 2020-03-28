package com.example.grocy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth mAuth;

    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    //navigation drawer
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    BottomSheetDialog bottomSheetDialog;
    Button btn_loc;
    TextView toolbarTitle;
    private Toolbar toolbar;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        btn_loc = findViewById(R.id.loc_main_btn);
        mAuth = FirebaseAuth.getInstance();

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
