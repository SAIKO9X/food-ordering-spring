package com.food.ordering.controllers;

import com.food.ordering.model.entities.Restaurant;
import com.food.ordering.model.entities.User;
import com.food.ordering.model.request.CreateRestaurantRequest;
import com.food.ordering.model.response.MessageResponse;
import com.food.ordering.services.RestaurantService;
import com.food.ordering.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/restaurants")
public class AdminController {

  @Autowired
  private RestaurantService restaurantService;

  @Autowired
  private UserService userService;

  @PostMapping
  public ResponseEntity<Restaurant> createRestaurant(@RequestBody CreateRestaurantRequest request, @RequestHeader("Authorization") String jwt) throws Exception {
    User user = userService.findUserByJwtToken(jwt);
    Restaurant restaurant = restaurantService.createRestaurant(request, user);
    return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
  }
}
