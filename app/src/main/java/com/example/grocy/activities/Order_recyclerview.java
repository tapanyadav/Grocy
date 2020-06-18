package com.example.grocy.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.grocy.Models.OrderModel;
import com.example.grocy.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order_recyclerview extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
    FirestoreRecyclerAdapter adapter;
    DocumentReference documentReference;
//    List<OrderModel> orderModelList=new ArrayList<>();
//    OrdersAdapter ordersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_recyclerview);
        recyclerView=findViewById(R.id.recyclerview);
        firebaseFirestore=FirebaseFirestore.getInstance();


        documentReference = firebaseFirestore.collection("Users").document();
        Query query= documentReference.collection("myOrder");
        FirestoreRecyclerOptions<OrderModel> options= new FirestoreRecyclerOptions.Builder<OrderModel>().setQuery(query, OrderModel.class).build();
        adapter= new FirestoreRecyclerAdapter<OrderModel, OrderViewHolder>(options) {
            @NonNull
            @Override
            public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_layout, parent, false);
                return new OrderViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull OrderViewHolder holder, int position, @NonNull OrderModel model) {
                holder.name.setText(model.getShopName());
                holder.address.setText(model.getShopAddress());
                holder.amount.setText(model.getOrderAmount());
                holder.dateTime.setText(model.getDateTime());
                holder.deliveryStatus.setText(model.getDeliveryStatus());
                holder.items.setText(model.getOrderedItems());
//                holder.image.setText(model.getImageid()+ "");
                Glide.with(holder.image.getContext()).load(model.getShopImage()).into(holder.image);
                System.out.println("---------------------------------------------------------");
                System.out.println(model.toString());
                System.out.println("------------------------");

            }
        };
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);



        //        ordersAdapter=new OrdersAdapter(getApplicationContext(),orderModelList);
//        recyclerView.setHasFixedSize(false);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(ordersAdapter);
//        orderModelList.add(new OrderModel("Gupta Grocery Store", "Shop no 8, Jagat Farm, Gr Noida", "2* 1kg Tuver Dal, 1*5kg Basmati Rice", "8 June 2020 at 02:30pm", R.drawable.groc, "₹800", "Delivered"));
//        orderModelList.add(new OrderModel("Chotu Medical Store", "Shop no 2, Jagat Farm, Gr Noida", "1*Sanitizer, 5*Mask, 1*Horlicks", "5 June 2020 at 10:15am", R.drawable.medical, "₹450", "Delivered"));
//        orderModelList.add(new OrderModel("Ghoda Hardware Shop", "Shop no 11, Jagat Farm, Gr Noida", "1*2m Iron pipe, 5*Iron Water Tap", "28 May 2020 at 06:10pm", R.drawable.hardware, "₹450", "Delivered"));
//        orderModelList.add(new OrderModel("Raghu Fruit Corner", "Thela no 1, C market, Alpha1, Gr Noida", "1*2kg Apple, 1*1 dozen Banana", "20 May 2020 at 01:10pm", R.drawable.fruit, "₹350", "Order Cancelled"));
//        orderModelList.add(new OrderModel("Akku Stationary", "Shop no 6, Jagat Farm, Gr Noida", "1*Classmate Notebook, 5*Pen, 1*Register", "1 June 2020 at 05:35pm", R.drawable.stationary, "₹500", "Transaction Failed"));
//



    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    private class OrderViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView address;
        TextView items;
        TextView dateTime;
        TextView deliveryStatus;
        TextView amount;
        ImageView image;


        public OrderViewHolder(View itemView){
            super(itemView);
            name=itemView.findViewById(R.id.shop_name);
            address=itemView.findViewById(R.id.shop_address);
            items=itemView.findViewById(R.id.ordered_items);
            dateTime=itemView.findViewById(R.id.ordered_date_time);
            deliveryStatus=itemView.findViewById(R.id.delivery_status);
            amount=itemView.findViewById(R.id.amount);
            image=itemView.findViewById(R.id.image);

        }

    }
}
