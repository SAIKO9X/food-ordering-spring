package com.food.ordering.services;

import com.food.ordering.model.entities.Category;

import java.util.List;

public interface CategoryService {

  public Category createCategory(String name, Long userId) throws Exception;

  public List<Category> findCategoryByRestaurantId(Long id) throws Exception;

  public Category findCategoryById(Long id) throws Exception;
}
