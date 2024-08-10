package com.food.ordering.services;

import com.food.ordering.model.entities.Order;
import com.food.ordering.response.PaymentResponse;

public interface PaymentService {

  public PaymentResponse createPaymentLink(Order Order);
}
