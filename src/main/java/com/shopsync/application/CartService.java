package com.shopsync.application;

import java.util.Map;

import com.shopsync.domain.model.Cart;
import com.shopsync.domain.model.Product;

public class CartService {
  private Cart cart;

  public CartService() {
    this.cart = new Cart();
  }

  public void addProduct(Product product, int quantity) {
    if (product == null || quantity <= 0) {
      throw new IllegalArgumentException("Invalid product or quantity.");
    }
    cart.addProduct(product, quantity);
  }

  public void removeProduct(Product product) {
    if (product == null) {
      throw new IllegalArgumentException("Product cannot be null.");
    }
    cart.removeProduct(product);
  }

  public Map<Product, Integer> viewCart() {
    return cart.getItems();
  }

  public double getTotalPrice() {
    return cart.getTotalPrice();
  }
}
