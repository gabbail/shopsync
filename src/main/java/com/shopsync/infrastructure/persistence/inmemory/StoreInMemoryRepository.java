package com.shopsync.infrastructure.persistence.inmemory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.shopsync.domain.model.Product;
import com.shopsync.domain.repository.StoreRepository;

public class StoreInMemoryRepository implements StoreRepository {
  private final Map<UUID, Product> store = new HashMap<>();

  @Override
  public Optional<Product> findById(UUID productId) {
    return Optional.ofNullable(store.get(productId));
  }

  @Override
  public List<Product> findAll() {
    return new ArrayList<>(store.values());
  }

  @Override
  public void save(Product product) {
    store.put(product.getId(), product);
  }

  @Override
  public void delete(UUID productId) {
    store.remove(productId);
  }
}
