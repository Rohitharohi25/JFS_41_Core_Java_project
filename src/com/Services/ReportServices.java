package com.Services;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.Modules.*;

public class ReportServices {

    public static void run(int n) {

        ArrayList<Product> products = ProductServices.getProducts();

        ArrayList<Customer> customers = UserServices.getCustomers();

        ArrayList<Orders> orders = OrderServices.getOrders();

        ArrayList<Supplier> suppliers = ProductServices.getSuppliers();

        ArrayList<Category> categories = ProductServices.getCategories();
        
        

        switch (n) {

        case 1:
            products.forEach(System.out::println);
            break;

        case 2:
            customers.forEach(System.out::println);
            break;

        case 3:
            categories.forEach(System.out::println);
            break;

        case 4:
            products.stream().filter(p -> p.getPrice() > 10000) .forEach(System.out::println);
            break;

        case 5:
            products.stream().filter(p -> p.getStock() < 20).forEach(System.out::println);
            break;

        case 6:
            orders.stream() .filter(o -> o.getStatus() .equalsIgnoreCase("DELIVERED")) .forEach(ReportServices::showOrder);
            break;

        case 7:
            suppliers.forEach(System.out::println);
            break;

        case 8:
            orders.stream() .filter(o -> o.getStatus() .equalsIgnoreCase("SHIPPED")) .forEach(ReportServices::showOrder);
            break;

        case 9:
            products.stream().filter(p -> p.getCategory().getCategoryName().equalsIgnoreCase("Electronics")) .forEach(System.out::println);
            break;

        case 10:
            customers.stream() .filter(c -> c.getCity().equalsIgnoreCase("Hyderabad")).forEach(System.out::println);
            break;

        case 11:
            for (Category category : categories) {

                long count = products.stream() .filter(p -> p.getCategory() == category) .count();

                System.out.println(category.getCategoryName() + " : " + count);
            }
            break;

        case 12:
            System.out.println("Average product price: ₹" + average(products));
            break;

        case 13:
            for (Supplier supplier : suppliers) {

                long count = products.stream() .filter(p -> p.getSupplier() == supplier) .count();

                System.out.println(supplier.getSupplierName() + " : " + count);
            }
            break;

        case 14:
            for (Customer customer : customers) {

                long count = orders.stream() .filter(o -> o.getCustomer() == customer) .count();

                System.out.println(customer.getUsername() + " : " + count + " orders");
            }
            break;

        case 15:
            for (Category category : categories) {

                double total = 0;
                int count = 0;

                for (Product product : products) {

                    if (product.getCategory() == category) {
                        total += product.getPrice();
                        count++;
                    }
                }

                double average = count == 0 ? 0 : total / count;

                System.out.println(category.getCategoryName() + " : ₹" + average);
            }
            break;

        case 16:
            products.stream() .sorted((a, b) -> Double.compare( b.getPrice(), a.getPrice())) .limit(5) .forEach(System.out::println);
            break;

        case 17:
            for (Category category : categories) {

                System.out.println(category.getCategoryName() + " : ₹" + categoryRevenue(category, orders));
            }
            break;

        case 18:
            for (Customer customer : customers) {

                long count = orders.stream() .filter(o -> o.getCustomer() == customer) .count();

                if (count > 5) {
                    System.out.println(customer);
                }
            }
            break;

        case 19:
            products.stream() .filter(p -> p.getStock() == 0) .forEach(System.out::println);
            break;

        case 20:
            for (Product product : products) {

                long count = OrderServices.getReviews() .stream() .filter(r -> r.getProduct() == product).count();

                System.out.println(product.getProductName() + " : " + count + " reviews");
            }
            break;

        case 21:
            for (Orders order : orders) {

                System.out.println(order.getCustomer() .getUsername() + " -> Order " + order.getOrderId());
            }
            break;

        case 22:
            for (Orders order : orders) {
                showOrder(order);
            }
            break;

        case 23:
            OrderServices.getPayments() .forEach(System.out::println);
            break;

        case 24:
            products.stream().filter(p -> !ordered(p, orders)).forEach(System.out::println);
            break;

        case 25:
            for (Customer customer : customers) {

                boolean found = orders.stream() .anyMatch(o -> o.getCustomer() == customer);

                if (!found) {
                    System.out.println(customer);
                }
            }
            break;

        case 26:
            suppliers.stream().max(Comparator.comparingLong( s -> products.stream() .filter(p -> p.getSupplier() == s) .count())).ifPresent(System.out::println);
            break;

        case 27:
            for (Category category : categories) {

                System.out.println(category.getCategoryName()  + " : ₹"  + categoryRevenue(category, orders));
            }
            break;

        case 28:
            products.stream().max( Comparator.comparingInt(p -> soldQuantity( p,orders))) .ifPresent(p -> System.out.println(  p + " | Sold: " + soldQuantity( p, orders)));
            break;

        case 29:
            products.stream().max( Comparator.comparingDouble(  ReportServices::rating)) .ifPresent( p -> System.out.println( p   + " | Average Rating: " + rating(p)));
            break;

        case 30:
            customers.stream() .max(Comparator.comparingDouble( c -> spent(c, orders))) .ifPresent( c -> System.out.println(c  + " | Spent: ₹" + spent(c, orders)));
            break;

        default:
            System.out.println("Invalid report number.");
        }
    }

    private static void showOrder(Orders order) {

        System.out.println( "Order " + order.getOrderId()  + " | " + order.getCustomer().getUsername() + " | ₹" + order.getTotalAmount()  + " | "  + order.getStatus());
    }

    private static double average(List<Product> products) {

        return products.stream().mapToDouble(Product::getPrice).average().orElse(0);
    }

    private static double categoryRevenue(Category category, List<Orders> orders) {

        double total = 0;

        for (Orders order : orders) {

            for (OrderItems item : order.getOrderItems()) {

                if (item.getProduct() .getCategory() == category) {

                    total += item.getAmount();
                }
            }
        }

        return total;
    }

    private static boolean ordered(Product product,List<Orders> orders) {

        for (Orders order : orders) {

            for (OrderItems item :order.getOrderItems()) {

                if (item.getProduct() == product) {
                    return true;
                }
            }
        }

        return false;
    }

    private static int soldQuantity(Product product,List<Orders> orders) {

        int total = 0;

        for (Orders order : orders) {

            for (OrderItems item : order.getOrderItems()) {

                if (item.getProduct() == product) {
                	total += item.getQuantity();
                }
            }
        }

        return total;
    }

    private static double rating(Product product) {

        return OrderServices.getReviews() .stream().filter(r -> r.getProduct() == product).mapToInt(Review::getRating).average().orElse(0);
    }

    private static double spent( Customer customer,List<Orders> orders) {

        return orders.stream() .filter(o -> o.getCustomer() == customer) .mapToDouble(Orders::getTotalAmount).sum();
    }
}
