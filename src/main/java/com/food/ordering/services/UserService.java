package com.food.ordering.services;

import com.food.ordering.model.entities.Cart;
import com.food.ordering.model.entities.User;
import com.food.ordering.model.response.AuthResponse;
import com.food.ordering.providers.JWTProvider;
import com.food.ordering.repositories.CartRepository;
import com.food.ordering.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JWTProvider jwtProvider;

  @Autowired
  private CartRepository cartRepository;

  public AuthResponse registerUser(User user) throws Exception {
    User emailExists = userRepository.findByEmail(user.getEmail());

    if (emailExists != null) {
      throw new Exception("Email is already used with another account");
    }

    User createdUser = new User();
    createdUser.setEmail(user.getEmail());
    createdUser.setFullName(user.getFullName());
    createdUser.setRole(user.getRole());
    createdUser.setPassword(passwordEncoder.encode(user.getPassword()));
    User savedUser = userRepository.save(createdUser);

    Cart cart = new Cart();
    cart.setCustomer(savedUser);
    cartRepository.save(cart);

    Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
    SecurityContextHolder.getContext().setAuthentication(authentication);

    String jwt = jwtProvider.generateToken(authentication);

    AuthResponse authResponse = new AuthResponse();
    authResponse.setJwt(jwt);
    authResponse.setMessage("User Registered if success");
    authResponse.setRole(savedUser.getRole());

    return authResponse;
  }
}
