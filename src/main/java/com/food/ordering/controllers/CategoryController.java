package com.food.ordering.controllers;


import com.food.ordering.model.entities.Category;
import com.food.ordering.model.entities.User;
import com.food.ordering.services.CategoryService;
import com.food.ordering.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

  @Autowired
  private CategoryService categoryService;

  @Autowired
  private UserService userService;

  @PostMapping("/admin/categories")
  public ResponseEntity<Category> createCategory(@RequestBody Category category, @RequestHeader("Authorization") String jwt) throws Exception {
    User user = userService.findUserByJwtToken(jwt);

    Category createdCategory = categoryService.createCategory(category.getName(), user.getId());

    return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
  }

  @GetMapping("/categories/restaurants")
  public ResponseEntity<List<Category>> getRestaurantCategory(@RequestHeader("Authorization") String jwt) throws Exception {
    User user = userService.findUserByJwtToken(jwt);

    List<Category> categories = categoryService.findCategoryByRestaurantId(user.getId());

    return new ResponseEntity<>(categories, HttpStatus.OK);
  }
}
