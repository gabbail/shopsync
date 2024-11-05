package com.shopsync.application;

import com.shopsync.domain.model.Product;
import com.shopsync.domain.repository.StoreRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class StoreService {
  private final StoreRepository storeRepository;

  public StoreService(StoreRepository storeRepository) {
    this.storeRepository = storeRepository;
  }

  public void addProduct(Product product) {
    storeRepository.save(product);
  }

  public Optional<Product> getProductById(UUID productId) {
    return storeRepository.findById(productId);
  }

  public List<Product> getAllProducts() {
    return storeRepository.findAll();
  }

  public void deleteProduct(UUID productId) {
    storeRepository.delete(productId);
  }
}
