package com.Modules;

import java.util.ArrayList;

public class Cart {

    private ArrayList<CartItem> items = new ArrayList<>();

    public ArrayList<CartItem> getItems() {
        return items;
    }

    public void add(Product product, int quantity) {

        for (CartItem item : items) {

            if (item.getProduct().getProductId() == product.getProductId()) {

                item.setQuantity(item.getQuantity() + quantity);

                return;
            }
        }

        items.add(new CartItem(product, quantity));
    }

    public void remove(int productId) {

        items.removeIf(i -> i.getProduct().getProductId() == productId);
    }

    public double getTotal() {

        double total = 0;

        for (CartItem item : items) {

            total += item.getAmount();
        }

        return total;
    }

    public boolean isEmpty() {

        return items.isEmpty();
    }

    public void clear() {

        items.clear();
    }
}