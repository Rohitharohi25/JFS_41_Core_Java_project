package com.Services;

import java.util.ArrayList;
import java.util.Arrays;

import com.Modules.Category;
import com.Modules.Product;
import com.Modules.Supplier;

public class ProductServices {

    private static ArrayList<Category> categories = new ArrayList<>();

    private static ArrayList<Supplier> suppliers = new ArrayList<>();

    private static ArrayList<Product> products = new ArrayList<>();

    private static int categoryId = 7;
    private static int supplierId = 104;
    private static int productId = 13;

    static {
    	
        loadDefaultCatalog();
    }

    private static void loadDefaultCatalog() {

        Category electronics =  new Category(1, "Electronics");

        Category fashion =  new Category(2, "Fashion");

        Category home = new Category(3, "Home Appliances");

        Category books = new Category(4, "Books");

        Category beauty = new Category(5, "Beauty");

        Category sports = new Category(6, "Sports");

        categories.addAll(Arrays.asList(electronics, fashion, home, books, beauty, sports));
        
        

        Supplier tech = new Supplier(101, "TechWorld", "tech@world.com", "Hyderabad");

        Supplier style =new Supplier(102,"StyleHub","style@hub.com", "Bengaluru");

        Supplier homeNeeds =new Supplier(103,"HomeNeeds","home@needs.com", "Vijayawada");

        suppliers.addAll(Arrays.asList(tech, style, homeNeeds));
        
        

        products.add(new Product(1,"Smartphone", electronics, tech, 24999, 30));

        products.add(new Product(2, "Laptop",electronics,tech, 59999,15));

        products.add(new Product(3,"Bluetooth Headphones",electronics,tech,2499,25));

        products.add(new Product(4,"Smart Watch", electronics, tech,4999,18));

        products.add(new Product(5, "Men T-Shirt",fashion, style, 799, 40));

        products.add(new Product(6,"Women Kurti",fashion,style, 1499, 35));

        products.add(new Product(7, "Air Fryer",home, homeNeeds, 6999, 12));

        products.add(new Product(8,"Mixer Grinder", home,homeNeeds, 3499,22));

        products.add(new Product( 9,"Java Programming Book", books, homeNeeds, 899, 50));

        products.add(new Product( 10, "Face Wash", beauty, style, 499, 45));

        products.add(new Product(11,"Running Shoes",sports,style,2999, 20));

        products.add(new Product(12,"Yoga Mat", sports, homeNeeds, 999, 28 ));
    }

    public static ArrayList<Category> getCategories() {
        return categories;
    }

    public static ArrayList<Supplier> getSuppliers() {
        return suppliers;
    }

    public static ArrayList<Product> getProducts() {
        return products;
    }

    public static Category findCategory(int id) {

        for (Category category : categories) {

            if (category.getCategoryId() == id) {
                return category;
            }
        }

        return null;
    }

    public static Supplier findSupplier(int id) {

        for (Supplier supplier : suppliers) {

            if (supplier.getSupplierId() == id) {
                return supplier;
            }
        }

        return null;
    }

    public static Product findProduct(int id) {

        for (Product product : products) {

            if (product.getProductId() == id) {
                return product;
            }
        }

        return null;
    }

    public static Category addCategory(String name) {

        Category category = new Category(categoryId++, name);

        categories.add(category);

        return category;
    }

    public static Supplier addSupplier( String name,String email,String city) {

        Supplier supplier =  new Supplier(supplierId++, name, email, city);

        suppliers.add(supplier);

        return supplier;
    }

    public static Product addProduct(String name,int categoryId, int supplierId, double price,int stock) {

        Category category = findCategory(categoryId);

        Supplier supplier =  findSupplier(supplierId);

        if (category == null || supplier == null) {
            return null;
        }

        Product product = new Product(productId++, name,category, supplier, price, stock );

        products.add(product);

        return product;
    }
}