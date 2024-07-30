package com.food.ordering.services.implement;

import com.food.ordering.model.entities.Category;
import com.food.ordering.model.entities.Food;
import com.food.ordering.model.entities.Restaurant;
import com.food.ordering.model.request.CreateFoodRequest;
import com.food.ordering.repositories.FoodRepository;
import com.food.ordering.services.FoodService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FoodServiceImpl implements FoodService {

  @Autowired
  private FoodRepository foodRepository;


  @Override
  public Food createFood(CreateFoodRequest request, Category category, Restaurant restaurant) {
    Food food = new Food();
    food.setFoodCategory(category);
    food.setRestaurant(restaurant);
    food.setDescription(request.description());
    food.setImages(request.images());
    food.setName(request.name());
    food.setPrice(request.price());
    food.setIngredients(request.ingredients());
    food.setSeasonal(request.isSeasonal());
    food.setVegetarian(request.isVegetarian());

    Food savedFood = foodRepository.save(food);
    restaurant.getFoods().add(savedFood);

    return savedFood;
  }

  @Override
  public void deleteFood(Long foodId) throws Exception {
    Food food = findFoodById(foodId);
    food.setRestaurant(null);
    foodRepository.save(food);
  }

  @Override
  public List<Food> getRestaurantsFood(Long restaurantId, boolean isVegetarian, boolean noVegetarian, boolean isSeasonal, String foodCategory) {
    List<Food> foods = foodRepository.findByRestaurantId(restaurantId);

    if (isVegetarian) {
      foods = filterByVegetarian(foods);
    }

    if (noVegetarian) {
      foods = filterByNoVegetarian(foods);
    }

    if (isSeasonal) {
      foods = filterByIsSeasonal(foods);
    }

    if (foodCategory != null && !foodCategory.isEmpty()) {
      foods = filterByCategory(foods, foodCategory);
    }

    return foods;
  }


  @Override
  public List<Food> searchFood(String keyword) {
    return foodRepository.searchFood(keyword);
  }

  @Override
  public Food findFoodById(Long foodId) throws Exception {
    Optional<Food> food = foodRepository.findById(foodId);

    if (food.isEmpty()) {
      throw new Exception("food not exists");
    }

    return food.get();
  }

  @Override
  public Food updateAvailableStatus(Long foodId) throws Exception {
    Food food = findFoodById(foodId);
    food.setAvailable(!food.isAvailable());
    return foodRepository.save(food);
  }

  private List<Food> filterByVegetarian(List<Food> foods) {
    return foods.stream().filter(Food::isVegetarian).collect(Collectors.toList());
  }

  private List<Food> filterByNoVegetarian(List<Food> foods) {
    return foods.stream().filter(food -> !food.isVegetarian()).collect(Collectors.toList());
  }

  private List<Food> filterByCategory(List<Food> foods, String foodCategory) {
    return foods.stream().filter(food -> {
      if (food.getFoodCategory() != null) {
        return food.getFoodCategory().getName().equals(foodCategory);
      }

      return false;
    }).collect(Collectors.toList());
  }

  private List<Food> filterByIsSeasonal(List<Food> foods) {
    return foods.stream().filter(Food::isSeasonal).collect(Collectors.toList());
  }
}
