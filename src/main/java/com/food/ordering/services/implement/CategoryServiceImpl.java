package com.food.ordering.services.implement;

import com.food.ordering.model.entities.Category;
import com.food.ordering.model.entities.Restaurant;
import com.food.ordering.repositories.CategoryRepository;
import com.food.ordering.services.CategoryService;
import com.food.ordering.services.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

  @Autowired
  private RestaurantService restaurantService;

  @Autowired
  private CategoryRepository categoryRepository;

  @Override
  public Category createCategory(String name, Long userId) throws Exception {
    Restaurant restaurant = restaurantService.findRestaurantByUserId(userId);

    Category category = new Category();
    category.setName(name);
    category.setRestaurant(restaurant);

    return categoryRepository.save(category);
  }

  @Override
  public List<Category> findCategoryByRestaurantId(Long id) throws Exception {
    Restaurant restaurant = restaurantService.findRestaurantByUserId(id);
    return categoryRepository.findByRestaurantId(restaurant.getId());
  }

  @Override
  public Category findCategoryById(Long id) throws Exception {
    Optional<Category> category = categoryRepository.findById(id);

    if (category.isEmpty()) {
      throw new Exception("category not found");
    }

    return category.get();
  }
}
