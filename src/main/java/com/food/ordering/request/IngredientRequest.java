package com.food.ordering.request;

public record IngredientRequest(Long restaurantId, String name, Long categoryId) {
}
