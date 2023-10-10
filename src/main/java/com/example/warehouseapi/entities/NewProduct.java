package com.example.warehouseapi.entities;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record NewProduct(
        @NotEmpty
        String name,

        @Pattern(regexp = "^(guitar|GUITAR|bass|BASS|drums|DRUMS|keyboard|KEYBOARD)$")
        String category,

        @Min(value = 1, message = "Rating should be 1-10")
        @Max(value = 10, message = "Rating should be 1-10")
        int rating) {
}
