package com.example.warehouseapi.service;

import com.example.warehouseapi.entities.Category;
import com.example.warehouseapi.entities.ImmutableProduct;
import com.example.warehouseapi.entities.Product;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.counting;

public class Warehouse {

    private final ArrayList<Product> productList;

    public Warehouse() {
        productList = new ArrayList<>();
    }

    private ImmutableProduct convertObjectToRecord(Product product) {
        return new ImmutableProduct(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getRating(),
                product.getCreationDate(),
                product.getModificationDate());
    }

    public void addProduct(String name, Category category, int rating) {
        this.productList.add(new Product(name, category, rating));
    }

    public void addProductWithManualDate(String name, Category category, int rating, LocalDateTime creationDate) {
        this.productList.add(new Product(name, category, rating, creationDate));
    }

    public void addProductWithManualId(String id, String name, Category category, int rating) {
        this.productList.add(new Product(id, name, category, rating));
    }

    public void addProductWithManualCreationAndModificationDate(String name, Category category, int rating, LocalDateTime CreationDate, LocalDateTime modificationDate) {
        this.productList.add(new Product(name, category, rating, CreationDate, modificationDate));
    }


    public void modifyProduct(String id, String name, Category category, int rating) {
        for (Product product : productList) {
            if (product.getId().equals(id)) {
                product.setName(name);
                product.setCategory(category);
                product.setRating(rating);
                product.setModificationDate(LocalDateTime.now());
            }
        }
    }

    public List<ImmutableProduct> getAllProducts() {
        return productList.stream().map(this::convertObjectToRecord).toList();
    }

    public Optional<ImmutableProduct> getOneProduct(String id) {
        return productList.stream()
                .filter(product -> product.getId().equals(id))
                .map(this::convertObjectToRecord)
                .findFirst();
    }

    public List<ImmutableProduct> getCategory(Category category) {
        return
                productList.stream()
                        .filter(product -> product.getCategory().equals(category))
                        .sorted(Comparator.comparing(Product::getName))
                        .map(this::convertObjectToRecord)
                        .toList();
    }

    public List<ImmutableProduct> getNewProducts(LocalDateTime time) {
        return
                productList.stream().filter(product -> product.getCreationDate().isAfter(time))
                        .map(this::convertObjectToRecord)
                        .toList();
    }

    public List<ImmutableProduct> getModifiedProducts() {
        return
                productList.stream()
                        .filter(product -> !product.getCreationDate().equals(product.getModificationDate()))
                        .map(this::convertObjectToRecord)
                        .toList();
    }

    public Set<Category> getAllCategoriesWithProducts() {
        return productList.stream()
                .map(Product::getCategory)
                .collect(Collectors.toUnmodifiableSet());
    }

    public int getNumberOfProductsInCategory(Category category) {
        return productList.stream()
                .filter(product -> product.getCategory().equals(category))
                .toList().size();
    }

    public Map<String, Integer> getLetterOfProductNames() {
        return productList.stream()
                .map(Product::getName)
                .filter(Objects::nonNull)
                .filter(s -> !s.isEmpty())
                .map(s -> s.substring(0, 1))
                .collect(Collectors.groupingBy(Function.identity(), collectingAndThen(counting(), Long::intValue)));
    }

    public List<ImmutableProduct> getMaxRating() {
        return
                productList.stream()
                        .filter(product -> product.getRating() > 9)
                        .filter(product -> product.getCreationDate().isAfter(LocalDateTime.now().minusDays(30)))
                        .sorted(Comparator.comparing(Product::getCreationDate))
                        .map(this::convertObjectToRecord)
                        .toList();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
