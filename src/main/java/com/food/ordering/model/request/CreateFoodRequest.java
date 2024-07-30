package com.food.ordering.model.request;

import com.food.ordering.model.entities.Category;

import java.util.List;

public record CreateFoodRequest(
  String name,
  String description,
  double price,
  Category category,
  List<String> images,
  Long restaurantId,
  boolean isVegetarian,
  boolean isSeasonal,
  boolean available
) {
}
