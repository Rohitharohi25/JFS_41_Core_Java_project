package com.Modules;

public class OrderItems {

    private int orderItemId;
    private Product product;
    private int quantity;
    private double amount;

    public OrderItems(int id, Product product, int quantity) {

        orderItemId = id;
        this.product = product;
        this.quantity = quantity;

        amount = product.getPrice() * quantity;
    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getAmount() {
        return amount;
    }
}