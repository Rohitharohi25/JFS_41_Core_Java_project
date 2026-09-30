package com.Services;

import com.Modules.Cart;
import com.Modules.Product;

public class CartServices {

    public static boolean addToCart(Cart cart,Product product, int quantity) {

        if (product == null || quantity <= 0 || product.getStock() < quantity) {

            return false;
        }

        cart.add(product, quantity);

        return true;
    }

    public static void removeFromCart(Cart cart, int productId) {

        cart.remove(productId);
    }
}