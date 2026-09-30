package com.Modules;

public class Review {

    private int reviewId;
    private Customer customer;
    private Product product;
    private int rating;
    private String reviewText;

    public Review(int id, Customer customer, Product product, int rating, String text) {

        reviewId = id;
        this.customer = customer;
        this.product = product;
        this.rating = rating;
        reviewText = text;
    }

    public int getReviewId() {
        return reviewId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Product getProduct() {
        return product;
    }

    public int getRating() {
        return rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    @Override
    public String toString() {

        return reviewId + " | " + product.getProductName()  + " | " + customer.getUsername() + " | Rating: " + rating + " | " + reviewText;
    }
}