package com.food.ordering.services;

import com.food.ordering.model.entities.User;

public interface UserService {

  public User findUserByJwtToken(String jwt) throws Exception;

  public User findUserByEmail(String email) throws Exception;
}