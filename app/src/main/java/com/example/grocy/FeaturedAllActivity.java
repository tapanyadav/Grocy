package com.example.grocy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.grocy.Models.FeaturedAllModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

public class FeaturedAllActivity extends AppCompatActivity {

    ImageButton imageButtonBack,imageButtonFilter;
    BottomSheetDialog bottomSheetDialogFilter;

    private FirebaseFirestore firebaseFirestore;
    private RecyclerView recyclerViewAll;
    FirestoreRecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_featured_all);

        imageButtonBack=findViewById(R.id.ib_back);
        imageButtonFilter=findViewById(R.id.image_button_filter_all);
        firebaseFirestore=FirebaseFirestore.getInstance();
        recyclerViewAll=findViewById(R.id.recycler_featured_all);

        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FeaturedAllActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        imageButtonFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialogFilter = new BottomSheetDialog(FeaturedAllActivity.this);
                //View bottomSheetView=LayoutInflater.from(new ContextThemeWrapper(getApplicationContext(),R.style.AppTheme)).inflate(R.layout.content_dialog_bottom_sheet, (LinearLayout)findViewById(R.id.bottomSheetLayout));
                bottomSheetDialogFilter.setContentView(R.layout.content_filter_bottom_sheet);
                bottomSheetDialogFilter.show();
                bottomSheetDialogFilter.setCanceledOnTouchOutside(false);
                ImageView ivBottomClose = bottomSheetDialogFilter.findViewById(R.id.imageView_close);
                ivBottomClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialogFilter.dismiss();
                    }
                });
            }
        });

        Query query=firebaseFirestore.collection("FeaturedShopsAll");
        FirestoreRecyclerOptions<FeaturedAllModel> options=new FirestoreRecyclerOptions.Builder<FeaturedAllModel>()
                .setQuery(query,FeaturedAllModel.class).build();

         adapter= new FirestoreRecyclerAdapter<FeaturedAllModel, FeaturedViewHolder>(options) {
            @NonNull
            @Override
            public FeaturedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_content_featured_all,parent,false);
                return new FeaturedViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull FeaturedViewHolder holder, int position, @NonNull FeaturedAllModel model) {

                holder.shopStatus.setText(model.getShopStatus());
                holder.shopRating.setText(model.getShopRating()+ "");
                holder.shopNameAll.setText(model.getShopNameAll());
                holder.shopAddressAll.setText(model.getShopAddressAll());
                holder.shopCategory.setText(model.getShopCategory());
                holder.shopOff.setText(model.getShopOff());
                Picasso.get().load(model.getShopImage()).into(holder.shopImage);

            }
        };
         recyclerViewAll.setHasFixedSize(true);
         recyclerViewAll.setLayoutManager(new LinearLayoutManager(this));
         recyclerViewAll.setAdapter(adapter);
    }

    private class FeaturedViewHolder extends RecyclerView.ViewHolder {

        private TextView shopStatus,shopRating,shopNameAll,shopAddressAll,shopCategory,shopOff;
        private ImageView shopImage;

        public FeaturedViewHolder(@NonNull View itemView) {
            super(itemView);

            shopStatus=itemView.findViewById(R.id.shop_status);
            shopRating=itemView.findViewById(R.id.shop_rating);
            shopNameAll=itemView.findViewById(R.id.shop_name_all);
            shopAddressAll=itemView.findViewById(R.id.shop_address_all);
            shopCategory=itemView.findViewById(R.id.shop_category);
            shopOff=itemView.findViewById(R.id.shop_off);
            shopImage=itemView.findViewById(R.id.iv_featured_recycler_all);
        }
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
}
