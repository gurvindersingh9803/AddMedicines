package com.example.addmedicines;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Orders extends AppCompatActivity {

    RecyclerView recyclerView;
    OrderDetailsAdapter orderDetailsAdapter;
    List<OrderDetailsModal> orderDetailsList;
    DatabaseReference databaseReferenceForOrder;
    ArrayList<String> mylist = new ArrayList<>();
    ValueEventListener mDBListener;
    ImageButton arrow;
    LinearLayout hiddenView;
    CardView cardView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        recyclerView = findViewById(R.id.recy);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Orders.this));
        orderDetailsList = new ArrayList<>();
        orderDetailsAdapter = new OrderDetailsAdapter(this, orderDetailsList);
        recyclerView.setAdapter(orderDetailsAdapter);
        databaseReferenceForOrder = FirebaseDatabase.getInstance().getReference("orders");

        mDBListener = databaseReferenceForOrder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                orderDetailsList.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    OrderDetailsModal upload = new OrderDetailsModal();

                    String userPhoneNumberAsNode = String.valueOf(postSnapshot.getKey());

                    for (DataSnapshot postSnapshot1 : postSnapshot.getChildren()) {



                        upload = postSnapshot1.getValue(OrderDetailsModal.class);

                        String orderId = String.valueOf(postSnapshot1.getKey());

                        Log.i("hhhhhhhhhhhhhhhhhh", (String) postSnapshot1.getKey());
                        upload.setOrderNumber(orderId);
                        //upload.setDate(orderId);

                        for (DataSnapshot postSnapshot2 : postSnapshot1.getChildren()) {

                            upload.setPhoneNumber(userPhoneNumberAsNode);
                            upload.setDate((String) postSnapshot1.child("date").getValue());
                            upload.setOrderStatus((String) postSnapshot1.child("orderStatus").getValue());

                            if (postSnapshot2.exists()) {

                                String med = (String) postSnapshot2.child("medicineName").getValue();

                                if(med != null){


                                    upload.setMedicineName((String) postSnapshot2.child("medicineName").getValue());

                                }
                            }
                        }

                        orderDetailsList.add(0, upload);
                        orderDetailsAdapter.notifyDataSetChanged();
                    }
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Orders.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                //mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });

    }
    }
