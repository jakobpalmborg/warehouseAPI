package com.example.warehouseapi.entities;

import java.time.LocalDateTime;
import java.util.UUID;

public class Product {
    String id;
    String name;
    Category category;
    int rating;
    LocalDateTime creationDate;
    LocalDateTime modificationDate;

    public Product(String name, Category category, int rating) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.category = category;
        this.rating = rating;
        this.creationDate = LocalDateTime.now();
        this.modificationDate = LocalDateTime.now();
    }

    public Product(String name, Category category, int rating, LocalDateTime creationDate) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.category = category;
        this.rating = rating;
        this.creationDate = creationDate;
        this.modificationDate = LocalDateTime.now();
    }

    public Product(String name, Category category, int rating, LocalDateTime creationDate, LocalDateTime modificationDate) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.category = category;
        this.rating = rating;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
    }

    public Product(String id, String name, Category category, int rating) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.rating = rating;
        this.creationDate = LocalDateTime.now();
        this.modificationDate = LocalDateTime.now();
    }

    public Product(Product x) {
        this.copy(x);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public int getRating() {
        return rating;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public LocalDateTime getModificationDate() {
        return modificationDate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setModificationDate(LocalDateTime modificationDate) {
        this.modificationDate = modificationDate;
    }

    public void copy(Product x) {
        this.setId(x.getId());
        this.setName(x.getName());
        this.setCategory(x.getCategory());
        this.setCreationDate(x.getCreationDate());
        this.setModificationDate(x.getModificationDate());
        this.setRating(x.getRating());
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", rating=" + rating +
                ", creationDate=" + creationDate +
                ", modificationDate=" + modificationDate +
                '}';
    }
}
