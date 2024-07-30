package com.food.ordering.controllers;

import com.food.ordering.model.entities.Order;
import com.food.ordering.model.entities.User;
import com.food.ordering.request.OrderRequest;
import com.food.ordering.services.OrderService;
import com.food.ordering.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

  @Autowired
  private OrderService orderService;

  @Autowired
  private UserService userService;

  @PostMapping("/order")
  public ResponseEntity<Order> createOrder(@RequestBody OrderRequest request, @RequestHeader("Authorization") String jwt) throws Exception {
    User user = userService.findUserByJwtToken(jwt);
    Order order = orderService.createOrder(request, user);

    return new ResponseEntity<>(order, HttpStatus.CREATED);
  }

  @GetMapping("/order/user")
  public ResponseEntity<List<Order>> getOrderHistory(@RequestHeader("Authorization") String jwt) throws Exception {
    User user = userService.findUserByJwtToken(jwt);
    List<Order> orders = orderService.getUserOrder(user.getId());

    return new ResponseEntity<>(orders, HttpStatus.OK);
  }
}
