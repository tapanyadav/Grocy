//package com.example.grocy.Adapters;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.grocy.Models.OrderModel;
//import com.example.grocy.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {
//    Context context;
//    List<OrderModel> orderModelList=new ArrayList<>();
//
//
//    public OrdersAdapter(Context context, List<OrderModel> orderModelList) {
//        this.context = context;
//        this.orderModelList = orderModelList;
//    }
//
//    @NonNull
//    @Override
//    public OrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.order_layout,parent,false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull OrdersAdapter.ViewHolder holder, int position) {
//        OrderModel orderModel=orderModelList.get(position);
//        holder.imageView.setImageResource(orderModel.getImageid());
//        holder.textView1.setText(orderModel.getShop());
//        holder.textView2.setText(orderModel.getAddress());
//        holder.textView3.setText(orderModel.getItem());
//        holder.textView4.setText(orderModel.getDate());
//        holder.textView5.setText(orderModel.getAmount());
//        holder.textView6.setText(orderModel.getDelivery_status());
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return orderModelList.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        TextView textView1, textView2, textView3, textView4, textView5, textView6;
//        ImageView imageView;
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            textView1=itemView.findViewById(R.id.shop_name);
//            textView2=itemView.findViewById(R.id.shop_address);
//            textView3=itemView.findViewById(R.id.ordered_items);
//            textView4=itemView.findViewById(R.id.ordered_date_time);
//            textView5=itemView.findViewById(R.id.amount);
//            textView6=itemView.findViewById(R.id.delivery_status);
//            imageView=itemView.findViewById(R.id.shop_image);
//
//        }
//    }
//}
