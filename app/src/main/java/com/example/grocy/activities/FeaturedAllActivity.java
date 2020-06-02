package com.example.grocy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocy.Adapters.FeaturedAllAdapter;
import com.example.grocy.Models.FeaturedAllModel;
import com.example.grocy.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class FeaturedAllActivity extends AppCompatActivity implements FeaturedAllAdapter.OnListItemClick {

    ImageButton imageButtonBack,imageButtonFilter;
    BottomSheetDialog bottomSheetDialogFilter;
    private FirebaseFirestore firebaseFirestore;
    private RecyclerView recyclerViewAll;
    FeaturedAllAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_featured_all);

        imageButtonBack=findViewById(R.id.ib_back);
        imageButtonFilter=findViewById(R.id.image_button_filter_all);
        firebaseFirestore=FirebaseFirestore.getInstance();
        recyclerViewAll=findViewById(R.id.recycler_featured_all);

        imageButtonBack.setOnClickListener(v -> {
            Intent intent = new Intent(FeaturedAllActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        imageButtonFilter.setOnClickListener(v -> {
            bottomSheetDialogFilter = new BottomSheetDialog(FeaturedAllActivity.this);
            //View bottomSheetView=LayoutInflater.from(new ContextThemeWrapper(getApplicationContext(),R.style.AppTheme)).inflate(R.layout.content_dialog_bottom_sheet, (LinearLayout)findViewById(R.id.bottomSheetLayout));
            bottomSheetDialogFilter.setContentView(R.layout.content_filter_bottom_sheet);
            bottomSheetDialogFilter.show();
            bottomSheetDialogFilter.setCanceledOnTouchOutside(false);
            ImageView ivBottomClose = bottomSheetDialogFilter.findViewById(R.id.imageView_close);
            assert ivBottomClose != null;
            ivBottomClose.setOnClickListener(v1 -> bottomSheetDialogFilter.dismiss());
        });

        Query query=firebaseFirestore.collection("FeaturedShopsAll");
        FirestoreRecyclerOptions<FeaturedAllModel> options=new FirestoreRecyclerOptions.Builder<FeaturedAllModel>()
                .setQuery(query,FeaturedAllModel.class).build();

        adapter= new FeaturedAllAdapter(options,this);

        recyclerViewAll.setHasFixedSize(true);
        recyclerViewAll.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAll.setAdapter(adapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onItemClick() {

    }
}
