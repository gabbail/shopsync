package com.shopsync.domain.repository;

import com.shopsync.domain.model.Product;
import java.util.Map;
import java.util.UUID;

public interface CartRepository {
  void addProduct(Product product, int quantity);

  void removeProduct(UUID id);

  Map<Product, Integer> getCartItems();

  double getTotalPrice();
}
