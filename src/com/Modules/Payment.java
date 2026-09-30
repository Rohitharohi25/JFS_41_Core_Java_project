package com.Modules;

public class Payment {

    private int paymentId;
    private Orders order;
    private String paymentMode;
    private String paymentStatus;
    private double amount;

    public Payment(int id, Orders order,String mode,double amount) {

        paymentId = id;
        this.order = order;
        paymentMode = mode;
        this.amount = amount;

        if (mode.equalsIgnoreCase("COD")) {
        	paymentStatus = "PENDING";
        } else {
            paymentStatus = "PAID";
        }
    }

    public int getPaymentId() {
        return paymentId;
    }

    public Orders getOrder() {
        return order;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public double getAmount() {
        return amount;
    }

    public void setPaymentStatus(String status) {
        paymentStatus = status;
    }

    @Override
    public String toString() {

        return paymentId+ " | Order: " + order.getOrderId() + " | " + paymentMode + " | ₹" + String.format("%.2f", amount) + " | " + paymentStatus;
    }
}