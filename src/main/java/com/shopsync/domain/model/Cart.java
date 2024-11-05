package com.shopsync.domain.model;

import java.util.HashMap;
import java.util.Map;

public class Cart {

  private Map<Product, Integer> items = new HashMap<>();

  public void addProduct(Product product, int quantity) {
    items.putIfAbsent(product, quantity);
  }

  public int totalItemCount() {
    return items.size();
  }

  public void removeProduct(Product product) {
    items.remove(product);
  }

  public Map<Product, Integer> getItems() {
    return items;
  }

  public double getTotalPrice() {
    return items.entrySet().stream()
        .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
        .sum();
  }
}
