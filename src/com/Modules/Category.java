package com.Modules;

public class Category {

    private int categoryId;
    private String categoryName;

    public Category(int id, String name) {
        categoryId = id;
        categoryName = name;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    @Override
    public String toString() {

        return categoryId + " | " + categoryName;
    }
}