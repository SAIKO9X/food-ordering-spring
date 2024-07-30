package com.food.ordering.model.request;

public record IngredientRequest(String name, Long categoryId, Long restaurantId) {
}
