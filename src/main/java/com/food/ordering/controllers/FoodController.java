package com.food.ordering.controllers;

import com.food.ordering.model.entities.Food;
import com.food.ordering.services.FoodService;
import com.food.ordering.services.RestaurantService;
import com.food.ordering.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/foods")
public class FoodController {

  @Autowired
  private FoodService foodService;

  @Autowired
  private UserService userService;

  @Autowired
  private RestaurantService restaurantService;

  @GetMapping("/search")
  public ResponseEntity<List<Food>> searchFood(@RequestParam String name) throws Exception {
    List<Food> foods = foodService.searchFood(name);
    return new ResponseEntity<>(foods, HttpStatus.CREATED);
  }

  @GetMapping("/restaurant/{restaurantId}")
  public ResponseEntity<List<Food>> getRestaurantFood(@RequestParam boolean vegetarian, @RequestParam boolean seasonal, @RequestParam boolean noVegetarian, @RequestParam(required = false) String food_category, @PathVariable Long restaurantId) throws Exception {
    List<Food> foods = foodService.getRestaurantsFood(restaurantId, vegetarian, seasonal, noVegetarian, food_category);
    return new ResponseEntity<>(foods, HttpStatus.CREATED);
  }
}