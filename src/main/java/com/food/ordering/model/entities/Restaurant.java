package com.food.ordering.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.food.ordering.model.dto.ContactInformation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_restaurant")
@Entity
public class Restaurant {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @OneToOne
  private User owner;

  @OneToOne
  private Address address;

  @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Order> orders = new ArrayList<>();

  @JsonIgnore
  @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
  private List<Food> foods = new ArrayList<>();

  private boolean open;
  private String name;
  private String description;

  @Embedded
  private ContactInformation contact;

  @Column(name = "cuisine_type")
  private String cuisineType;

  @Column(name = "opening_hours")
  private String openingHours;

  @ElementCollection
  @Column(length = 1000)
  private List<String> images;

  @Column(name = "registration_date")
  private LocalDateTime registrationDate;
}
