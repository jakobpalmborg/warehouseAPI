package com.example.warehouseapi.service;

import com.example.warehouseapi.entities.Category;
import com.example.warehouseapi.entities.ImmutableProduct;
import com.example.warehouseapi.entities.Product;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.counting;

@ApplicationScoped
public class Warehouse implements Iwarehouse {


    private final CopyOnWriteArrayList<Product> productList;

    public Warehouse() {
        productList = new CopyOnWriteArrayList<>();
    }

    @Override
    public void addProduct(String name, Category category, int rating) {
        this.productList.add(new Product(name, category, rating));
    }

    @Override
    public void addProductWithManualDate(String name, Category category, int rating, LocalDateTime creationDate) {
        this.productList.add(new Product(name, category, rating, creationDate));
    }

    @Override
    public void addProductWithManualId(String id, String name, Category category, int rating) {
        this.productList.add(new Product(id, name, category, rating));
    }

    @Override
    public void addProductWithManualCreationAndModificationDate(String name, Category category, int rating, LocalDateTime CreationDate, LocalDateTime modificationDate) {
        this.productList.add(new Product(name, category, rating, CreationDate, modificationDate));
    }

    @Override
    public void modifyProduct(String id, String name, Category category, int rating) {
        for (Product product : productList) {
            if (product.getId().equals(id)) {
                Product tempProduct = new Product(product);
                tempProduct.setName(name);
                tempProduct.setCategory(category);
                tempProduct.setRating(rating);
                tempProduct.setModificationDate(LocalDateTime.now());
                productList.replaceAll(p -> p.getId().equals(id) ? tempProduct : p);
            }
        }
    }

    @Override
    public List<ImmutableProduct> getAllProducts() {
        return productList.stream().map(ImmutableProduct::convertObjectToRecord).toList();
    }

    @Override
    public Optional<ImmutableProduct> getOneProduct(String id) {
        return productList.stream()
                .filter(product -> product.getId().equals(id))
                .map(ImmutableProduct::convertObjectToRecord)
                .findFirst();
    }

    @Override
    public List<ImmutableProduct> getCategory(Category category) {
        return
                productList.stream()
                        .filter(product -> product.getCategory().equals(category))
                        .sorted(Comparator.comparing(Product::getName))
                        .map(ImmutableProduct::convertObjectToRecord)
                        .toList();
    }

    @Override
    public List<ImmutableProduct> getNewProducts(LocalDateTime time) {
        return
                productList.stream().filter(product -> product.getCreationDate().isAfter(time))
                        .map(ImmutableProduct::convertObjectToRecord)
                        .toList();
    }

    @Override
    public List<ImmutableProduct> getModifiedProducts() {
        return
                productList.stream()
                        .filter(product -> !product.getCreationDate().equals(product.getModificationDate()))
                        .map(ImmutableProduct::convertObjectToRecord)
                        .toList();
    }

    @Override
    public Set<Category> getAllCategoriesWithProducts() {
        return productList.stream()
                .map(Product::getCategory)
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public int getNumberOfProductsInCategory(Category category) {
        return productList.stream()
                .filter(product -> product.getCategory().equals(category))
                .toList().size();
    }

    @Override
    public Map<String, Integer> getLetterOfProductNames() {
        return productList.stream()
                .map(Product::getName)
                .filter(Objects::nonNull)
                .filter(s -> !s.isEmpty())
                .map(s -> s.substring(0, 1))
                .collect(Collectors.groupingBy(Function.identity(), collectingAndThen(counting(), Long::intValue)));
    }

    @Override
    public List<ImmutableProduct> getMaxRating() {
        return
                productList.stream()
                        .filter(product -> product.getRating() > 9)
                        .filter(product -> product.getCreationDate().isAfter(LocalDateTime.now().minusDays(30)))
                        .sorted(Comparator.comparing(Product::getCreationDate))
                        .map(ImmutableProduct::convertObjectToRecord)
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
