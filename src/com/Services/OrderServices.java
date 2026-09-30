package com.Services;

import java.util.ArrayList;

import com.Modules.*;

public class OrderServices {

    private static ArrayList<Orders> orders = new ArrayList<>();

    private static ArrayList<Payment> payments = new ArrayList<>();

    private static ArrayList<Shipment> shipments = new ArrayList<>();

    private static ArrayList<Review> reviews = new ArrayList<>();

    private static int orderId = 5001;
    private static int paymentId = 8001;
    private static int shipmentId = 9001;
    private static int reviewId = 7001;
    private static int orderItemId = 6001;

    public static Orders placeOrder(Customer customer, Cart cart, String address, String pincode, String paymentMode) {

        if (cart.isEmpty()) {
            return null;
        }

        ArrayList<OrderItems> items = new ArrayList<>();

        for (CartItem cartItem : cart.getItems()) {

            if (cartItem.getProduct().getStock() < cartItem.getQuantity()) {

                return null;
            }

            OrderItems orderItem = new OrderItems(orderItemId++, cartItem.getProduct(), cartItem.getQuantity());

            items.add(orderItem);
        }

        // Reduce stock after successful checkout
        for (CartItem cartItem : cart.getItems()) {

            Product product = cartItem.getProduct();

            product.setStock(product.getStock() - cartItem.getQuantity());
        }

        Orders order = new Orders(orderId++,customer, items, cart.getTotal(),address, pincode);

        orders.add(order);

        Payment payment = new Payment(paymentId++, order, paymentMode,order.getTotalAmount());

        payments.add(payment);

        Shipment shipment = new Shipment(shipmentId++,order, address + ", Pincode: " + pincode);

        shipments.add(shipment);

        // Cart becomes empty after successful order
        cart.clear();

        return order;
    }

    public static Orders findOrder(int id) {

        for (Orders order : orders) {

            if (order.getOrderId() == id) {
                return order;
            }
        }

        return null;
    }

    public static Payment getPaymentForOrder(int id) {

        for (Payment payment : payments) {

            if (payment.getOrder().getOrderId() == id) {
                return payment;
            }
        }

        return null;
    }

    public static Shipment getShipmentForOrder(int id) {

        for (Shipment shipment : shipments) {

            if (shipment.getOrder().getOrderId() == id) {
                return shipment;
            }
        }

        return null;
    }

    public static ArrayList<Orders> getOrders() {
        return orders;
    }

    public static ArrayList<Payment> getPayments() {
        return payments;
    }

    public static ArrayList<Shipment> getShipments() {
        return shipments;
    }

    public static ArrayList<Review> getReviews() {
        return reviews;
    }

    public static boolean addReview(Customer customer, Product product, int rating, String text) {

        if (rating < 1 || rating > 5) {
            return false;
        }

        Review review = new Review(reviewId++, customer, product,rating,text);

        reviews.add(review);

        return true;
    }

    public static void updateOrderStatus(int id,String status) {

        Orders order = findOrder(id);

        if (order != null) {
        	order.setStatus(status);
        }
    }

    public static void updateShipmentStatus(int id,String status) {

        for (Shipment shipment : shipments) {

            if (shipment.getShipmentId() == id) {

                shipment.setShipmentStatus(status);

                if (status.equalsIgnoreCase("SHIPPED")) {
                    shipment.getOrder().setStatus("SHIPPED");
                }

                else if (status.equalsIgnoreCase("OUT FOR DELIVERY")) {

                    shipment.getOrder().setStatus("OUT FOR DELIVERY");
                }

                else if (status.equalsIgnoreCase("DELIVERED")) {

                    shipment.getOrder().setStatus("DELIVERED");
                }
            }
        }
    }
}