package com.example.warehouseapi.service;

import com.example.warehouseapi.entities.Category;
import com.example.warehouseapi.entities.ImmutableProduct;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface Iwarehouse {

    void addProduct(String name, Category category, int rating);

    void addProductWithManualDate(String name, Category category, int rating, LocalDateTime creationDate);

    void addProductWithManualId(String id, String name, Category category, int rating);

    void addProductWithManualCreationAndModificationDate(String name, Category category, int rating, LocalDateTime CreationDate, LocalDateTime modificationDate);

    void modifyProduct(String id, String name, Category category, int rating);

    List<ImmutableProduct> getAllProducts();

    Optional<ImmutableProduct> getOneProduct(String id);

    List<ImmutableProduct> getCategory(Category category);

    List<ImmutableProduct> getNewProducts(LocalDateTime time);

    List<ImmutableProduct> getModifiedProducts();

    Set<Category> getAllCategoriesWithProducts();

    int getNumberOfProductsInCategory(Category category);

    Map<String, Integer> getLetterOfProductNames();

    List<ImmutableProduct> getMaxRating();


}
