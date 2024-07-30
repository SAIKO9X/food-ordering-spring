package com.food.ordering.model.request;

public record IngredientRequest(Long restaurantId, String name, Long categoryId) {
}
