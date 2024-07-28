package com.food.ordering.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.food.ordering.model.dto.RestaurantDTO;
import com.food.ordering.model.enums.USER_ROLE;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tb_user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @JsonIgnore
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
  private List<Order> orders = new ArrayList<>();

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Address> addresses = new ArrayList<>();

  @Enumerated
  private USER_ROLE role;

  @ElementCollection
  private List<RestaurantDTO> favorites = new ArrayList<>();

  private String fullName;
  private String email;
  private String password;
}
