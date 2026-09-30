package com.Modules;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Orders {

    private int orderId;
    private Customer customer;
    private ArrayList<OrderItems> orderItems;
    private LocalDateTime orderDate;
    private String status;
    private double totalAmount;
    private String address;
    private String pincode;

    public Orders(int id,Customer customer, ArrayList<OrderItems> items,double total, String address,String pincode) {

        orderId = id;
        this.customer = customer;
        orderItems = items;
        totalAmount = total;

        this.address = address;
        this.pincode = pincode;

        orderDate = LocalDateTime.now();

        status = "PLACED";
    }

    public int getOrderId() {
        return orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public ArrayList<OrderItems> getOrderItems() {
        return orderItems;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public String getStatus() {
        return status;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getAddress() {
        return address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}