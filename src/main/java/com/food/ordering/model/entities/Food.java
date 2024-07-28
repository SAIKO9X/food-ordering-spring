package com.food.ordering.model.entities;


import jakarta.persistence.*;
import jdk.jfr.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tb_food")
public class Food {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne
  @Column(name = "food_category")
  private Category foodCategory;

  @ManyToOne
  private Restaurant restaurant;

  @ManyToMany
  private List<IngredientsItem> ingredients = new ArrayList<>();

  private String name;
  private String description;
  private double price;
  private boolean available;

  @Column(name = "is_vegetarian")
  private boolean isVegetarian;

  @Column(name = "is_seasonal")
  private boolean isSeasonal;

  @Column(length = 1000)
  @ElementCollection
  private List<String> images;


  @Column(name = "creation_date")
  private Date creationDate;
}
