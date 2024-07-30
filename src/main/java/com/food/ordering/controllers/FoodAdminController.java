package com.food.ordering.controllers;

import com.food.ordering.model.entities.Food;
import com.food.ordering.model.entities.Restaurant;
import com.food.ordering.model.request.CreateFoodRequest;
import com.food.ordering.model.response.MessageResponse;
import com.food.ordering.services.FoodService;
import com.food.ordering.services.RestaurantService;
import com.food.ordering.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/foods")
public class FoodAdminController {

  @Autowired
  private FoodService foodService;

  @Autowired
  private UserService userService;

  @Autowired
  private RestaurantService restaurantService;

  @PostMapping
  public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest request) throws Exception {
    Restaurant restaurant = restaurantService.findRestaurantById(request.restaurantId());
    Food food = foodService.createFood(request, request.category(), restaurant);

    return new ResponseEntity<>(food, HttpStatus.CREATED);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<MessageResponse> deleteFood(@PathVariable Long id) throws Exception {
    foodService.deleteFood(id);
    MessageResponse message = new MessageResponse("food deleted successfully");
    return new ResponseEntity<>(message, HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Food> updateFoodAvailabilityStatus(@PathVariable Long id) throws Exception {
    Food food = foodService.updateAvailableStatus(id);
    return new ResponseEntity<>(food, HttpStatus.OK);
  }
}
