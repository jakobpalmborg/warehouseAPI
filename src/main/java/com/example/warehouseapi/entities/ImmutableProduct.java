package com.example.warehouseapi.entities;
public record ImmutableProduct(
        String id,
        String name,
        Category category,
        int rating,
        String creationDate,
        String modificationDate)
{

}
