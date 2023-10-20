package com.example.warehouseapi.entities;

import java.time.format.DateTimeFormatter;

public record ImmutableProduct(
        String id,
        String name,
        Category category,
        int rating,
        String creationDate,
        String modificationDate)
{

    public static ImmutableProduct convertObjectToRecord(Product product) {
        return new ImmutableProduct(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getRating(),
                product.getCreationDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
                product.getModificationDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
    }

}
