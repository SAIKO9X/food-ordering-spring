package com.food.ordering.services;

import com.food.ordering.model.entities.Category;
import com.food.ordering.model.entities.Food;
import com.food.ordering.model.entities.Restaurant;
import com.food.ordering.model.request.CreateFoodRequest;

import java.util.List;

public interface FoodService {

  public Food createFood(CreateFoodRequest request, Category category, Restaurant restaurant);

  void deleteFood(Long foodId) throws Exception;

  public List<Food> getRestaurantsFood(Long restaurantId, boolean isVegetarian, boolean noVegetarian, boolean isSeasonal, String foodCategory);

  public List<Food> searchFood(String keyword);

  public Food findFoodById(Long id) throws Exception;

  public Food updateAvailableStatus(Long foodId) throws Exception;
}
