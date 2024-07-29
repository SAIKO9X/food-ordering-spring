package com.food.ordering.model.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.util.List;

@Embeddable
@Data
public class RestaurantDTO {

  Long id;
  String title;
  String description;

  @Column(length = 1000)
  List<String> images;
}
