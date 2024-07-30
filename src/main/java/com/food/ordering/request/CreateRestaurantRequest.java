package com.food.ordering.request;

import com.food.ordering.dto.ContactInformation;
import com.food.ordering.model.entities.Address;

import java.util.List;

public record CreateRestaurantRequest(
  Long id,
  String name,
  String description,
  String cuisineType,
  Address address,
  ContactInformation contact,
  String openingHours,
  List<String> images
) {
}
