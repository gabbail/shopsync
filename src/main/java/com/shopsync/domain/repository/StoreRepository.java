package com.shopsync.domain.repository;

import com.shopsync.domain.model.Product;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StoreRepository {
  Optional<Product> findById(UUID productId);

  List<Product> findAll();

  void save(Product product);

  void delete(UUID productId);
}
