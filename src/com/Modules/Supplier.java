package com.Modules;

public class Supplier {

    private int supplierId;
    private String supplierName;
    private String email;
    private String city;

    public Supplier(int id, String name, String email, String city) {

        supplierId = id;
        supplierName = name;
        this.email = email;
        this.city = city;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getEmail() {
        return email;
    }

    public String getCity() {
        return city;
    }

    @Override
    public String toString() {

        return supplierId + " | " + supplierName + " | " + email + " | " + city;
    }
}