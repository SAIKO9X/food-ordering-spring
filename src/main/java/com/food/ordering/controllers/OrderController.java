package com.food.ordering.controllers;

import com.food.ordering.model.entities.Order;
import com.food.ordering.model.entities.User;
import com.food.ordering.request.OrderRequest;
import com.food.ordering.response.OrderResponse;
import com.food.ordering.services.OrderService;
import com.food.ordering.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class OrderController {

  @Autowired
  private OrderService orderService;

  @Autowired
  private UserService userService;

  @PostMapping("/order")
  public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest request, @RequestHeader("Authorization") String jwt) throws Exception {
    User user = userService.findUserByJwtToken(jwt);
    Order order = orderService.createOrder(request, user);
    OrderResponse orderResponse = new OrderResponse(order);

    return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
  }

  @GetMapping("/order/user")
  public ResponseEntity<List<OrderResponse>> getOrderHistory(@RequestHeader("Authorization") String jwt) throws Exception {
    User user = userService.findUserByJwtToken(jwt);
    List<Order> orders = orderService.getUserOrder(user.getId());
    List<OrderResponse> orderResponses = orders.stream()
      .map(OrderResponse::new)
      .collect(Collectors.toList());

    return new ResponseEntity<>(orderResponses, HttpStatus.OK);
  }
}
