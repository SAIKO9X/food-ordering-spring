package com.food.ordering.controllers;

import com.food.ordering.exceptions.CustomBadCredentialsException;
import com.food.ordering.model.entities.User;
import com.food.ordering.request.LoginRequest;
import com.food.ordering.response.AuthResponse;
import com.food.ordering.services.implement.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  private AuthServiceImpl authService;

  @PostMapping("/register")
  public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) {
    try {
      AuthResponse authResponse = authService.registerUser(user);
      return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(new AuthResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
    try {
      AuthResponse authResponse = authService.authenticateUser(request);
      return new ResponseEntity<>(authResponse, HttpStatus.OK);
    } catch (CustomBadCredentialsException e) {
      return new ResponseEntity<>(new AuthResponse(e.getMessage()), HttpStatus.UNAUTHORIZED);
    }
  }
}
