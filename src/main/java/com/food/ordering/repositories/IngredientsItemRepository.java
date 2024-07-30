package com.food.ordering.repositories;

import com.food.ordering.model.entities.IngredientsItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientsItemRepository extends JpaRepository<IngredientsItem, Long> {
}
