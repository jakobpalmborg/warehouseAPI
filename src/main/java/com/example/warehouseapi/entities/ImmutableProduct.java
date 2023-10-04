package com.example.warehouseapi.entities;


import java.time.LocalDateTime;


public record ImmutableProduct(
        String id,
        String name,
        Category category,
        int rating,
        String creationDate,
        String modificationDate)
{

}
