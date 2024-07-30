package com.food.ordering.services.implement;

import com.food.ordering.model.entities.Cart;
import com.food.ordering.model.entities.CartItem;
import com.food.ordering.model.entities.Food;
import com.food.ordering.model.entities.User;
import com.food.ordering.repositories.CartItemRepository;
import com.food.ordering.repositories.CartRepository;
import com.food.ordering.request.AddCartItemRequest;
import com.food.ordering.services.CartService;
import com.food.ordering.services.FoodService;
import com.food.ordering.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

  @Autowired
  private CartRepository cartRepository;

  @Autowired
  private UserService userService;

  @Autowired
  private CartItemRepository cartItemRepository;

  @Autowired
  private FoodService foodService;


  @Override
  public CartItem addItemToCart(AddCartItemRequest request, String jwt) throws Exception {
    User user = userService.findUserByJwtToken(jwt);
    Food food = foodService.findFoodById(request.foodId());
    Cart cart = cartRepository.findByCustomerId(user.getId());

    for (CartItem cartItem : cart.getItems()) {
      if (cartItem.getFood().equals(food)) {
        int newQuantity = cartItem.getQuantity() + request.quantity();
        return updateCartItemQuantity(cartItem.getId(), newQuantity);
      }
    }

    CartItem newCartItem = new CartItem();
    newCartItem.setFood(food);
    newCartItem.setCart(cart);
    newCartItem.setQuantity(request.quantity());
    newCartItem.setIngredient(request.ingredients());
    newCartItem.setTotalPrice(request.quantity() * food.getPrice());

    CartItem savedCart = cartItemRepository.save(newCartItem);

    cart.getItems().add(savedCart);

    return savedCart;
  }

  @Override
  public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {
    Optional<CartItem> cartItem = cartItemRepository.findById(cartItemId);

    if (cartItem.isEmpty()) {
      throw new Exception("cart item not found");
    }

    CartItem item = cartItem.get();
    item.setQuantity(quantity);

    item.setTotalPrice(item.getFood().getPrice() * quantity);

    return cartItemRepository.save(item);
  }

  @Override
  public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {
    User user = userService.findUserByJwtToken(jwt);
    Cart cart = cartRepository.findByCustomerId(user.getId());

    Optional<CartItem> cartItem = cartItemRepository.findById(cartItemId);

    if (cartItem.isEmpty()) {
      throw new Exception("cart item not found");
    }

    CartItem item = cartItem.get();

    cart.getItems().remove(item);

    return cartRepository.save(cart);
  }

  @Override
  public double calculateCartTotals(Cart cart) throws Exception {
    double total = 0.0;

    for (CartItem cartItem : cart.getItems()) {
      total += cartItem.getFood().getPrice() * cartItem.getQuantity();
    }

    return total;
  }

  @Override
  public Cart findCartById(Long cartId) throws Exception {
    Optional<Cart> cart = cartRepository.findById(cartId);

    if (cart.isEmpty()) {
      throw new Exception("cart not found with id: " + cartId);
    }

    return cart.get();
  }

  @Override
  public Cart findCartByUserId(String jwt) throws Exception {
    User user = userService.findUserByJwtToken(jwt);
    return cartRepository.findByCustomerId(user.getId());
  }

  @Override
  public Cart clearCart(String jwt) throws Exception {
    User user = userService.findUserByJwtToken(jwt);
    Cart cart = cartRepository.findByCustomerId(user.getId());
    cart.getItems().clear();

    return cartRepository.save(cart);
  }
}
