package com.example.addmedicines;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailsAdapter extends RecyclerView.Adapter<com.example.addmedicines.OrderDetailsAdapter.OrderDetailsViewHolder> {

    List<OrderDetailsModal> orderDetailsList;
    Context mCtx;
    DatabaseReference databaseReference;
    ValueEventListener mDBListener;
    ArrayList<String> textViews;

    public OrderDetailsAdapter(@NonNull Context mCtx, List<OrderDetailsModal> orderDetailsList) {
        super();
        this.orderDetailsList = orderDetailsList;
        this.mCtx = mCtx;

    }

    @NonNull
    @Override
    public OrderDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mCtx).inflate(R.layout.single_order_details,parent,false);
        return new OrderDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderDetailsViewHolder holder, final int position) {

        final OrderDetailsModal orderDetailsModal;

        orderDetailsModal = orderDetailsList.get(position);

        holder.date.setText("Ordered on : " + orderDetailsModal.getDate());
        holder.orderNumber.setText("Order number : " +orderDetailsModal.getOrderNumber());

        if(orderDetailsModal.getOrderStatus().equals("orderPlaced")){
            holder.status.setText("Status: "+orderDetailsModal.getOrderStatus());
            holder.status.setTextColor(Color.BLACK);
        }else if(orderDetailsModal.getOrderStatus().equals("OrderConfirmed")){
            holder.status.setText("Status: "+orderDetailsModal.getOrderStatus());
            holder.status.setTextColor(Color.BLUE);
        }else if(orderDetailsModal.getOrderStatus().equals("OutForDelivery")){
            holder.status.setText("Status: "+orderDetailsModal.getOrderStatus());
            holder.status.setTextColor(Color.GREEN);
        }else if(orderDetailsModal.getOrderStatus().equals("Delivered")){
            holder.status.setText("Status: "+orderDetailsModal.getOrderStatus());
            holder.status.setTextColor(Color.RED);
        }

        //holder.med_name.setText("Medicine name : " +orderDetailsModal.getMedicineName());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mCtx,orderDetailsModal.getOrderStatus(),Toast.LENGTH_LONG).show();

                }
            });

            holder.confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                   //databaseReferenceForOrder = FirebaseDatabase. ().getReference("orders");
                    Toast.makeText(mCtx,orderDetailsModal.getPhoneNumber(),Toast.LENGTH_LONG).show();
                    databaseReference = FirebaseDatabase.getInstance().getReference("orders");
                    databaseReference.child(orderDetailsModal.getPhoneNumber()).child(orderDetailsModal.getOrderNumber()).child("orderStatus").setValue("OrderConfirmed");

                }
            });

        holder.out_for_delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //databaseReferenceForOrder = FirebaseDatabase. ().getReference("orders");
                Toast.makeText(mCtx,orderDetailsModal.getPhoneNumber(),Toast.LENGTH_LONG).show();
                databaseReference = FirebaseDatabase.getInstance().getReference("orders");
                databaseReference.child(orderDetailsModal.getPhoneNumber()).child(orderDetailsModal.getOrderNumber()).child("orderStatus").setValue("OutForDelivery");


            }
        });
        holder.delivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //databaseReferenceForOrder = FirebaseDatabase. ().getReference("orders");
                Toast.makeText(mCtx,orderDetailsModal.getPhoneNumber(),Toast.LENGTH_LONG).show();
                databaseReference = FirebaseDatabase.getInstance().getReference("orders");
                databaseReference.child(orderDetailsModal.getPhoneNumber()).child(orderDetailsModal.getOrderNumber()).child("orderStatus").setValue("Delivered");


            }
        });




        holder.arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // If the CardView is already expanded, set its visibility
                //  to gone and change the expand less icon to expand more.
                if (holder.hiddenView.getVisibility() == View.VISIBLE) {


                        TransitionManager.beginDelayedTransition(holder.cardView, new AutoTransition());
                        holder.hiddenView.setVisibility(View.GONE);
                        holder.arrow.setImageResource(R.drawable.ic_baseline_expand_more_24);



                }

                else {

                    TransitionManager.beginDelayedTransition(holder.cardView,
                            new AutoTransition());
                    holder.hiddenView.setVisibility(View.VISIBLE);
                    holder.arrow.setImageResource(R.drawable.ic_baseline_expand_less_24);
                }
            }
        });
    }

    @Override
    public int getItemCount() {

        return orderDetailsList.size();
    }

    public class OrderDetailsViewHolder extends RecyclerView.ViewHolder {

        TextView orderNumber,date,med_name,status;
        ImageView  indicator;
        ConstraintLayout linearLayout;
        Button confirm,out_for_delivery,delivered;
        ImageButton arrow;
        LinearLayout hiddenView;
        CardView cardView;

        public OrderDetailsViewHolder(@NonNull View itemView) {
            super(itemView);

            linearLayout = itemView.findViewById(R.id.liner);
            orderNumber = itemView.findViewById(R.id.orderNumber);
            //med_name = itemView.findViewById(R.id.med_name);
            date = itemView.findViewById(R.id.date);
            cardView = itemView.findViewById(R.id.base_cardview);
            arrow = itemView.findViewById(R.id.arrow_button);
            hiddenView = itemView.findViewById(R.id.hidden_view);
            confirm = itemView.findViewById(R.id.confirm_order);
            out_for_delivery = itemView.findViewById(R.id.out_for_delivery);
            delivered = itemView.findViewById(R.id.delivered);
            status = itemView.findViewById(R.id.status);



        }
    }
}
