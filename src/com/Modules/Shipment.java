package com.Modules;

public class Shipment {

    private int shipmentId;
    private Orders order;
    private String address;
    private String shipmentStatus;

    public Shipment(int id, Orders order, String address) {

        shipmentId = id;
        this.order = order;
        this.address = address;

        shipmentStatus = "PROCESSING";
    }

    public int getShipmentId() {
        return shipmentId;
    }

    public Orders getOrder() {
        return order;
    }

    public String getAddress() {
        return address;
    }

    public String getShipmentStatus() {
        return shipmentStatus;
    }

    public void setShipmentStatus(String status) {
        shipmentStatus = status;
    }

    @Override
    public String toString() {

        return shipmentId + " | Order: " + order.getOrderId()  + " | " + address  + " | " + shipmentStatus;
    }
}