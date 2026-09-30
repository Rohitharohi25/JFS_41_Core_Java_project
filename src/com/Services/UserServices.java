package com.Services;

import java.util.ArrayList;

import com.Modules.Admin;
import com.Modules.Customer;

public class UserServices {

    private static ArrayList<Customer> customers = new ArrayList<>();

    private static Admin admin = new Admin( 1, "admin", "admin@shop.com", "1234");


    public static Customer register( int id, String name,  String email, String password, String mobile, String city) {

        for (Customer customer : customers) {
            if (customer.getUserId() == id) {
                return null;
            }
        }

        Customer customer = new Customer(id, name, email, password, mobile, city);

        customers.add(customer);

        return customer;
    }

    public static Customer loginCustomer(String email,String password) {

        for (Customer customer : customers) {

            if (customer.getMailId() .equalsIgnoreCase(email) && customer.getPassword().equals(password)) {

                return customer;
            }
        }

        return null;
    }

    public static boolean loginAdmin(String email, String password) {

        return admin.getMailId().equalsIgnoreCase(email)&& admin.getPassword().equals(password);
    }

    public static ArrayList<Customer> getCustomers() {

        return customers;
    }
}