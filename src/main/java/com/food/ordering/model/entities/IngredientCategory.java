package com.food.ordering.model.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tb_ingredient_category")
public class IngredientCategory {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @JsonIgnore
  @ManyToOne
  private Restaurant restaurant;

  @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
  private List<IngredientsItem> ingredients = new ArrayList<>();

  private String name;
}
