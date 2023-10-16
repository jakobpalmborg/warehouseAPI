package com.example.warehouseapi;

import com.example.warehouseapi.entities.Category;

import com.example.warehouseapi.service.Warehouse;

import java.time.LocalDateTime;

public class App {
    public static void main(String[] args) {
        // ALL PUBLIC METHODS IN WAREHOUSE:
        // ===============================


        // Create Warehouse
        Warehouse myWarehouse = new Warehouse();

        //Add product
        myWarehouse.addProduct("Gretch Catalina", Category.DRUMS, 9);

        // Add product with manual date
        myWarehouse.addProductWithManualDate("Gibson Les Paul", Category.GUITAR, 3, LocalDateTime.now().minusDays(4));

        // Add product with manual id
        myWarehouse.addProductWithManualId("1", "Fender Stratocaster", Category.GUITAR, 8);

        // Modify product
        myWarehouse.modifyProduct("1", "Gibson Les Paul", Category.GUITAR, 1);

        // Get all products in category
        myWarehouse.getCategory(Category.GUITAR);

        // Get new products
        myWarehouse.getNewProducts(LocalDateTime.now().minusDays(2));

        // Get all categories with products
        myWarehouse.getAllCategoriesWithProducts();

        // Get number of products in each category
        myWarehouse.getNumberOfProductsInCategory(Category.GUITAR);

        // Get first letter of products with product count
        myWarehouse.getLetterOfProductNames();

        // Get all products with max rating within 30 days sorted with new first
        myWarehouse.getMaxRating();



    }
}
