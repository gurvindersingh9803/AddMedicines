package com.example.addmedicines;

import android.util.Log;

public class OrderDetailsModal {

    String orderNumber;
    String medicineName;
    int count;
    String date;

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    String orderStatus;
    String phoneNumber;
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public OrderDetailsModal(){

    }

    public String getMedicineName() {


        return medicineName;
    }

    public void setMedicineName(String medicineName) {

        this.medicineName = medicineName;
    }


    public OrderDetailsModal(String phoneNumber){

        this.phoneNumber = phoneNumber;
    }

    public String getOrderNumber() {

        return orderNumber;
    }

    public int getCount() {
        return count;
    }




    public String getDate() {

       // Log.i("ddddddddddddd",date);
        return date;
    }


    public String getPhoneNumber() {
        Log.i("pppppppppp", String.valueOf(phoneNumber));

        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {

        this.phoneNumber = phoneNumber;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;



    }
}
