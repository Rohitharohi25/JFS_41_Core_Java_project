package com.Modules;

public class Product {

    private int productId;
    private String productName;
    private Category category;
    private Supplier supplier;
    private double price;
    private int stock;

    public Product(int id, String name, Category category, Supplier supplier, double price, int stock) {

        productId = id;
        productName = name;
        this.category = category;
        this.supplier = supplier;
        this.price = price;
        this.stock = stock;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public Category getCategory() {
        return category;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {

        return productId + " | " + productName + " | " + category.getCategoryName() + " | ₹" + String.format("%.2f", price)  + " | Stock: " + stock + " | " + supplier.getSupplierName();
    }
}