package com.example.grocy.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.grocy.Adapters.OrdersAdapter;
import com.example.grocy.Models.OrderModel;
import com.example.grocy.R;

import java.util.ArrayList;
import java.util.List;

public class Order_recyclerview extends AppCompatActivity {
    RecyclerView recyclerView;
    List<OrderModel> orderModelList=new ArrayList<>();
    OrdersAdapter ordersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_recyclerview);
        recyclerView=findViewById(R.id.recyclerview);
        ordersAdapter=new OrdersAdapter(getApplicationContext(),orderModelList);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(ordersAdapter);
        orderModelList.add(new OrderModel("Gupta Grocery Store", "Shop no 8, Jagat Farm, Gr Noida", "2* 1kg Tuver Dal, 1*5kg Basmati Rice", "8 June 2020 at 02:30pm", R.drawable.groc, "₹800", "Delivered"));
        orderModelList.add(new OrderModel("Chotu Medical Store", "Shop no 2, Jagat Farm, Gr Noida", "1*Sanitizer, 5*Mask, 1*Horlicks", "5 June 2020 at 10:15am", R.drawable.medical, "₹450", "Delivered"));
        orderModelList.add(new OrderModel("Ghoda Hardware Shop", "Shop no 11, Jagat Farm, Gr Noida", "1*2m Iron pipe, 5*Iron Water Tap", "28 May 2020 at 06:10pm", R.drawable.hardware, "₹450", "Delivered"));
        orderModelList.add(new OrderModel("Raghu Fruit Corner", "Thela no 1, C market, Alpha1, Gr Noida", "1*2kg Apple, 1*1 dozen Banana", "20 May 2020 at 01:10pm", R.drawable.fruit, "₹350", "Order Cancelled"));
        orderModelList.add(new OrderModel("Akku Stationary", "Shop no 6, Jagat Farm, Gr Noida", "1*Classmate Notebook, 5*Pen, 1*Register", "1 June 2020 at 05:35pm", R.drawable.stationary, "₹500", "Transaction Failed"));




    }
}
