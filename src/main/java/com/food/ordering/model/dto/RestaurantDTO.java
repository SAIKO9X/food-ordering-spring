package com.food.ordering.model.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.List;

@Embeddable
public record RestaurantDTO(String title, @Column(length = 1000) List<String> images, String description, Long id) {
}
