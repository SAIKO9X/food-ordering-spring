package com.food.ordering.repositories;

import com.food.ordering.model.entities.IngredientCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientsCategoryRepository extends JpaRepository<IngredientCategory, Long> {
}
