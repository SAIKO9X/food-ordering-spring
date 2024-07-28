package com.food.ordering.model.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tb_ingredients")
public class IngredientsItem {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
}
