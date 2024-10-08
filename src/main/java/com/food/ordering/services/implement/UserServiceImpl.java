package com.food.ordering.services.implement;

import com.food.ordering.model.entities.User;
import com.food.ordering.providers.JWTProvider;
import com.food.ordering.repositories.UserRepository;
import com.food.ordering.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private JWTProvider jwtProvider;

  @Override
  public User findUserByJwtToken(String jwt) throws Exception {
    String email = jwtProvider.getEmailFromJwtToken(jwt);

    if (email == null) {
      throw new Exception("Invalid or expired token");
    }

    User user = userRepository.findByEmail(email);

    if (user == null) {
      throw new Exception("User not found");
    }

    return user;
  }

  @Override
  public User findUserByEmail(String email) throws Exception {
    User user = userRepository.findByEmail(email);

    if (user == null) {
      throw new Exception("user not found");
    }

    return user;
  }
}
