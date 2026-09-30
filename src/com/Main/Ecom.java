package com.Main;

import java.util.Scanner;
import com.Modules.*;
import com.Services.*;

public class Ecom {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("Welcome to  E-Commerce");


        while (true) {

            System.out.println("\n1. Admin Login" + "\n2. Customer Registration" + "\n3. Customer Login" + "\n4. Exit");

            int choice = readInt("Enter choice: ");

            if (choice == 1) {

                adminLogin();

            } else if (choice == 2) {

                register();

            } else if (choice == 3) {

                customerLogin();

            } else if (choice == 4) {

                System.out.println("Thank you for shopping with us!");

                break;

            } else {

                System.out.println("Invalid choice.");
            }
        }
    }

    static void register() {

        System.out.println("\nCustomer Registration ");

        int id = readInt("Customer id: ");
        
        String name = read("Customer name: ");

        String email = read("Email: ");

        String password = read("Password: ");

        String mobile =  read("Mobile: ");

        String city = read("City: ");

        Customer customer = UserServices.register(id, name, email, password, mobile, city );

        if (customer == null) {

            System.out.println("Customer ID already exists.");

        } else {

            System.out.println("\nCustomer registered successfully!");
     //       System.out.println("Customer ID: " + customer.getUserId());
        }
    }

    static void customerLogin() {

    	   
        String email = read("Email: ");

        String password = read("Password: ");

        Customer customer = UserServices.loginCustomer( email, password);

        if (customer == null) {

            System.out.println("Invalid email or password.");

            return;
        }

        System.out.println("\nWelcome, " + customer.getUsername() + "!");

        customerMenu(customer);
    }

    static void adminLogin() {

        String email = read("Admin Email: ");

        String password = read("Password: ");

        if (!UserServices.loginAdmin(email,password)) {

            System.out.println("Invalid admin credentials.");

            return;
        }

        System.out.println("\nAdmin login successful.");

        adminMenu();
    }

    static void customerMenu(Customer customer) {

        Cart cart = new Cart();

        while (true) {

            System.out.println("\nCustomer menu");

            System.out.println("1. View Categories" + "\n2. View Products"  + "\n3. View Product Details" + "\n4. Add Product to Cart" + "\n5. View Cart" + "\n6. Remove Product from Cart" + "\n7. Checkout" + "\n8. My Orders" + "\n9. Track Order" + "\n10. Add Review" + "\n11. Logout");

            int choice = readInt("Enter choice: ");

            switch (choice) {

            case 1:
                viewCategories();
                break;

            case 2:
                viewProducts();
                break;

            case 3:
                productDetails();
                break;

            case 4:
                addToCart(cart);
                break;

            case 5:
                viewCart(cart);
                break;

            case 6:
                removeFromCart(cart);
                break;

            case 7:
                checkout(customer, cart);
                break;

            case 8:
                myOrders(customer);
                break;

            case 9:
                trackOrder(customer);
                break;

            case 10:
                addReview(customer);
                break;

            case 11:

                System.out.println("Logged out successfully.");

                return;

            default:

                System.out.println( "Invalid choice.");
            }
        }
    }

    static void viewCategories() {

        System.out.println("\n Categories ");

        ProductServices.getCategories() .forEach(System.out::println);
    }

    static void viewProducts() {

        System.out.println("\n Products ");

        for (Product product : ProductServices.getProducts()) {

            System.out.println(product);
        }
    }

    static void productDetails() {

        int id = readInt("Enter Product ID: ");

        Product product = ProductServices.findProduct(id);

        if (product == null) {

            System.out.println("Product not found.");

            return;
        }

        System.out.println("\nProduct id: " + product.getProductId());

        System.out.println( "Name: "  + product.getProductName());

        System.out.println("Category: " + product.getCategory() .getCategoryName());

        System.out.println("Supplier: " + product.getSupplier() .getSupplierName());

        System.out.println("Price: ₹" + product.getPrice());

        System.out.println("Available Stock: " + product.getStock());
    }

    static void addToCart(Cart cart) {

        int id = readInt("\nEnter Product id: ");

        Product product = ProductServices.findProduct(id);

        if (product == null) {

            System.out.println("Product not found.");

            return;
        }

        int quantity = readInt("Enter Quantity: ");

        if (CartServices.addToCart(cart,product,quantity)) {

            System.out.println("Product added to cart successfully.");

        } else {

            System.out.println("Unable to add product." + " Check quantity and stock.");
        }
    }

    static void viewCart(Cart cart) {

        if (cart.isEmpty()) {

            System.out.println("Your cart is empty.");

            return;
        }

        System.out.println("\nYour cart ");

        for (CartItem item : cart.getItems()) {

            System.out.println(item.getProduct().getProductId() + " | " + item.getProduct().getProductName() + " | Qty: " + item.getQuantity() + " | ₹" + item.getAmount());
        }

        System.out.println("Cart Total: ₹" + cart.getTotal());
    }

    static void removeFromCart(Cart cart) {

        if (cart.isEmpty()) {

            System.out.println("Your cart is empty.");

            return;
        }

        viewCart(cart);

        int id = readInt("Enter Product ID to remove: ");

        CartServices.removeFromCart(cart, id);

        System.out.println("Product removed from cart.");
    }

    static void checkout(Customer customer, Cart cart) {

        if (cart.isEmpty()) {

            System.out.println("Your cart is empty." + " Add products before checkout.");

            return;
        }

        viewCart(cart);

        System.out.println(" Checkout ");

        String address = read("Delivery Address: ");

        String pincode = read("Pincode: ");

        System.out.println("Payment Methods:" + "\n1. UPI"  + "\n2. Card" + "\n3. COD");

        int paymentChoice = readInt("Choose payment method: ");

        String paymentMode;

        if (paymentChoice == 1) {

            paymentMode = "UPI";

        } else if (paymentChoice == 2) {

            paymentMode = "CARD";

        } else if (paymentChoice == 3) {

            paymentMode = "COD";

        } else {

            System.out.println("Invalid payment method.");

            return;
        }

        Orders order = OrderServices.placeOrder(customer, cart,address, pincode, paymentMode);

        if (order == null) {

            System.out.println("Order could not be placed." + " Please check stock.");

            return;
        }

        Payment payment = OrderServices.getPaymentForOrder(order.getOrderId());

        Shipment shipment = OrderServices.getShipmentForOrder(order.getOrderId());

        System.out.println("\nOrder placed");

        System.out.println("Order id: " + order.getOrderId());

        System.out.println( "Total Amount: ₹"  + order.getTotalAmount());

        System.out.println( "Payment id: " + payment.getPaymentId()  + " | " + payment.getPaymentMode()  + " | " + payment.getPaymentStatus());

        System.out.println( "Shipment id: " + shipment.getShipmentId() + " | " + shipment.getShipmentStatus());

        System.out.println( "Order Status: " + order.getStatus());

        System.out.println("Your order has been placed successfully!");
    }

    static void myOrders(Customer customer) {

        System.out.println("\n My Orders");

        boolean found = false;

        for (Orders order : OrderServices.getOrders()) {

            if (order.getCustomer() == customer) {

                found = true;

                System.out.println( "Order " + order.getOrderId()  + " | ₹" + order.getTotalAmount()  + " | " + order.getStatus()  + " | " + order.getOrderDate());

                for (OrderItems item : order.getOrderItems()) {

                    System.out.println( "   - " + item.getProduct() .getProductName() + " x " + item.getQuantity());
                }
            }
        }

        if (!found) {

            System.out.println("No orders found.");
        }
    }

    static void trackOrder(Customer customer) {

        int id = readInt("Enter Order ID: ");

        Orders order = OrderServices.findOrder(id);

        if (order == null || order.getCustomer() != customer) {

            System.out.println("Order not found.");

            return;
        }

        Shipment shipment = OrderServices.getShipmentForOrder(id);

        System.out.println("Order Status: " + order.getStatus());

        System.out.println( "Shipment ID: "  + shipment.getShipmentId());

        System.out.println("Shipment Status: "+ shipment.getShipmentStatus());

        System.out.println("Delivery Address: "  + shipment.getAddress());
    }

    static void addReview(
            Customer customer) {

        int id = readInt("Enter Product ID: ");

        Product product = ProductServices.findProduct(id);

        if (product == null) {

            System.out.println("Product not found.");

            return;
        }

        int rating = readInt("Rating (1-5): ");

        String text = read("Review: ");

        if (OrderServices.addReview(customer, product, rating,text)) {

            System.out.println( "Review added successfully.");

        } else {

            System.out.println( "Rating must be between 1 and 5.");
        }
    }

    static void adminMenu() {

        while (true) {

            System.out.println("\nAdmin menu");

            System.out.println("1. Add Category" + "\n2. Add Supplier"  + "\n3. Add Product" + "\n4. View Products" + "\n5. View Customers"  + "\n6. View Orders" + "\n7. Update Order Status"  + "\n8. Update Shipment Status" + "\n9. View Payments"+ "\n10. View Shipments" + "\n11. Reports" + "\n12. Logout");

            int choice = readInt("Enter choice: ");

            switch (choice) {

            case 1:
                addCategory();
                break;

            case 2:
                addSupplier();
                break;

            case 3:
                addProduct();
                break;

            case 4:
                viewProducts();
                break;

            case 5:
                UserServices.getCustomers() .forEach(System.out::println);
                break;

            case 6:
                viewAllOrders();
                break;

            case 7:
                updateOrder();
                break;

            case 8:
                updateShipment();
                break;

            case 9:
                OrderServices.getPayments() .forEach(System.out::println);
                break;

            case 10:
                OrderServices.getShipments() .forEach(System.out::println);
                break;

            case 11:
                reports();
                break;

            case 12:

                System.out.println( "Admin logged out.");

                return;

            default:

                System.out.println("Invalid choice.");
            }
        }
    }

    static void addCategory() {

        String name = read("Category Name: ");

        System.out.println("Category added: " + ProductServices.addCategory(name));
    }

    static void addSupplier() {

        String name = read("Supplier Name: ");

        String email = read("Email: ");

        String city = read("City: ");

        System.out.println("Supplier added: " + ProductServices.addSupplier(name,email,city));
    }

    static void addProduct() {

        viewCategories();

        int categoryId = readInt("Category ID: ");

        viewSuppliers();

        int supplierId = readInt("Supplier ID: ");

        String name = read("Product Name: ");

        double price = readDouble("Price: ");

        int stock = readInt("Stock: ");

        Product product = ProductServices.addProduct( name, categoryId, supplierId, price,  stock);
        

        if (product == null) {

            System.out.println("Invalid category or supplier.");

        } else {

            System.out.println("Product added successfully: " + product);
        }
    }

    static void viewSuppliers() {

        System.out.println( "\n Suppliers ");

        ProductServices.getSuppliers() .forEach(System.out::println);
    }

    static void viewAllOrders() {

        if (OrderServices.getOrders().isEmpty()) {

            System.out.println( "No orders yet.");

            return;
        }

        for (Orders order : OrderServices.getOrders()) {

            System.out.println( "Order " + order.getOrderId()  + " | Customer: " + order.getCustomer() .getUsername() + " | ₹"  + order.getTotalAmount()  + " | " + order.getStatus());

            for (OrderItems item : order.getOrderItems()) {

                System.out.println(  "   " + item.getProduct()  .getProductName() + " x "  + item.getQuantity());
            }
        }
    }

    static void updateOrder() {

        int id = readInt("Order id: ");

        System.out.println( "1. Placed" + "  2. Confirmed" + "  3. Shipped" + "  4. Out for Delivery"  + "  5. Delivered" + "  6. Cancelled");

        int choice = readInt("Choose status: ");

        String[] statuses = { "Placed", "Confirmed", "Shipped", "Out for Delivery", "Delivered", "Cancelled"};

        if (choice < 1 ||choice > 6) {

            System.out.println("Invalid status.");

            return;
        }

        OrderServices.updateOrderStatus(id,statuses[choice - 1]);

        System.out.println("Order status updated.");
    }

    static void updateShipment() {

        int id = readInt("Shipment id: ");

        System.out.println( "1. Processing"  + "  2. Shipped" + "  3. Out for Delivery" + "  4. Delivered");

        int choice =  readInt("Choose status: ");

        String[] statuses = { "Processing","Shipped", "Out for Delivery", "Delivered"};

        if (choice < 1 ||choice > 4) {

            System.out.println( "Invalid status.");

            return;
        }

        OrderServices.updateShipmentStatus(id, statuses[choice - 1]);

        System.out.println("Shipment status updated.");
    }

    static void reports() {

        while (true) {

            System.out.println("\nReports ");

            System.out.println("1. Product & Category Reports");
            System.out.println("2. Customer Reports");
            System.out.println("3. Supplier Reports");
            System.out.println("4. Order & Sales Reports");
            System.out.println("5. Payment & Shipment Reports");
            System.out.println("6. Review Reports");
            System.out.println("7. Return to Admin Menu");

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    productCategoryReports();
                    break;

                case 2:
                    customerReports();
                    break;

                case 3:
                    supplierReports();
                    break;

                case 4:
                    orderSalesReports();
                    break;

                case 5:
                    paymentShipmentReports();
                    break;

                case 6:
                    reviewReports();
                    break;

                case 7:
                    System.out.println("Returning to Admin Menu...");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
    
    
    
    static void productCategoryReports() {

        while (true) {

            System.out.println("\nProduct & Category Reports");

            System.out.println("1. Display All Products");
            System.out.println("2. Show All Categories");
            System.out.println("3. Products Above ₹10,000");
            System.out.println("4. Products With Stock Less Than 20");
            System.out.println("5. Electronics Products");
            System.out.println("6. Product Count By Category");
            System.out.println("7. Average Product Price");
            System.out.println("8. Category-Wise Average Product Price");
            System.out.println("9. Top 5 Expensive Products");
            System.out.println("10. Products With No Stock");
            System.out.println("11. Products Never Ordered");
            System.out.println("12. Return to Reports");

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    ReportServices.run(1);
                    break;

                case 2:
                    ReportServices.run(3);
                    break;

                case 3:
                    ReportServices.run(4);
                    break;

                case 4:
                    ReportServices.run(5);
                    break;

                case 5:
                    ReportServices.run(9);
                    break;

                case 6:
                    ReportServices.run(11);
                    break;

                case 7:
                    ReportServices.run(12);
                    break;

                case 8:
                    ReportServices.run(15);
                    break;

                case 9:
                    ReportServices.run(16);
                    break;

                case 10:
                    ReportServices.run(19);
                    break;

                case 11:
                    ReportServices.run(24);
                    break;

                case 12:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
    
    static void customerReports() {

        while (true) {

            System.out.println("\nCustomer Reports");

            System.out.println("1. Display All Customers");
            System.out.println("2. Customers From Hyderabad");
            System.out.println("3. Total Orders By Each Customer");
            System.out.println("4. Customers With More Than 5 Orders");
            System.out.println("5. Customer Names With Their Orders");
            System.out.println("6. Customers Who Never Placed An Order");
            System.out.println("7. Customer Who Spent The Highest Amount");
            System.out.println("8. Return to Reports");

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    ReportServices.run(2);
                    break;

                case 2:
                    ReportServices.run(10);
                    break;

                case 3:
                    ReportServices.run(14);
                    break;

                case 4:
                    ReportServices.run(18);
                    break;

                case 5:
                    ReportServices.run(21);
                    break;

                case 6:
                    ReportServices.run(25);
                    break;

                case 7:
                    ReportServices.run(30);
                    break;

                case 8:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
    
    
    static void supplierReports() {

        while (true) {

            System.out.println("\nSupplier Reports");

            System.out.println("1. Display All Suppliers");
            System.out.println("2. Supplier-Wise Product Count");
            System.out.println("3. Supplier Supplying Maximum Products");
            System.out.println("4. Return to Reports");

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    ReportServices.run(7);
                    break;

                case 2:
                    ReportServices.run(13);
                    break;

                case 3:
                    ReportServices.run(26);
                    break;

                case 4:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
    
    
    static void orderSalesReports() {

        while (true) {

            System.out.println("\nOrder & Sales Reports");

            System.out.println("1. Completed Orders");
            System.out.println("2. Shipped Orders");
            System.out.println("3. Revenue Generated By Each Category");
            System.out.println("4. Order Details With Product Names");
            System.out.println("5. Category-Wise Total Sales Amount");
            System.out.println("6. Highest-Selling Product");
            System.out.println("7. Return to Reports");

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    ReportServices.run(6);
                    break;

                case 2:
                    ReportServices.run(8);
                    break;

                case 3:
                    ReportServices.run(17);
                    break;

                case 4:
                    ReportServices.run(22);
                    break;

                case 5:
                    ReportServices.run(27);
                    break;

                case 6:
                    ReportServices.run(28);
                    break;

                case 7:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
    
    static void paymentShipmentReports() {

        while (true) {

            System.out.println("\nPayment & Shipment Reports");

            System.out.println("1. Payment Details For Every Order");
            System.out.println("2. Return to Reports");

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    ReportServices.run(23);
                    break;

                case 2:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
    
    
    static void reviewReports() {

        while (true) {

            System.out.println("\nReview Reports ");

            System.out.println("1. Review Count For Each Product");
            System.out.println("2. Product With Highest Average Rating");
            System.out.println("3. Return to Reports");

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    ReportServices.run(20);
                    break;

                case 2:
                    ReportServices.run(29);
                    break;

                case 3:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
    
    
    static int readInt(String message) {

        while (true) {

            try {

                System.out.print(message);

                return Integer.parseInt(sc.nextLine().trim());

            } catch (Exception e) {

                System.out.println("Please enter a valid number.");
            }
        }
    }

    static double readDouble(String message) {

        while (true) {

            try {

                System.out.print(message);

                return Double.parseDouble(sc.nextLine().trim());

            } catch (Exception e) {

                System.out.println("Please enter a valid amount.");
            }
        }
    }

    static String read(String message) {

        System.out.print(message);

        return sc.nextLine().trim();
    }
}