package com.shopsync.application;

import java.util.Map;
import java.util.UUID;

import com.shopsync.domain.model.Product;
import com.shopsync.domain.repository.CartRepository;

public class CartService {
  private final CartRepository cartRepository;

  public CartService(CartRepository cartRepository) {
    this.cartRepository = cartRepository;
  }

  public void addProductToCart(Product product, int quantity) {
    if (product == null || quantity <= 0) {
      throw new IllegalArgumentException("Invalid product or quantity.");
    }
    cartRepository.addProduct(product, quantity);
  }

  public void removeProductFromCart(String id) {
    if (id == "") {
      throw new IllegalArgumentException("Product id cannot be null.");
    }
    cartRepository.removeProduct(UUID.fromString(id));
  }

  public Map<Product, Integer> viewCart() {
    return cartRepository.getCartItems();
  }

  public double getTotalPrice() {
    return cartRepository.getTotalPrice();
  }
}
