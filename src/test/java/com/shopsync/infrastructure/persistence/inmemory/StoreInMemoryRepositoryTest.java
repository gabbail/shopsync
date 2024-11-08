package com.shopsync.infrastructure.persistence.inmemory;

import static org.junit.jupiter.api.Assertions.*;

import com.shopsync.domain.model.Product;
import com.shopsync.domain.repository.StoreRepository;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StoreInMemoryRepositoryTest {
  private StoreRepository storeRepository;

  @BeforeEach
  public void setUp() {
    storeRepository = new StoreInMemoryRepository();
  }

  @Test
  public void givenNewProduct_whenSaved_thenCanBeFoundById() {
    Product product = new Product("Laptop", 899.99);
    storeRepository.save(product);

    Product foundProduct = storeRepository.findById(product.getId());

    assertTrue(foundProduct != null, "Product should be found");
    assertEquals(product.getName(), foundProduct.getName(), "Product name should match");
  }

  @Test
  public void givenProductExists_whenDeleted_thenCannotBeFound() {
    Product product = new Product("Laptop", 899.99);
    storeRepository.save(product);
    assertTrue(
        storeRepository.findById(product.getId()) != null,
        "Product should be present before deletion");

    storeRepository.delete(product.getId());

    assertTrue(
        storeRepository.findById(product.getId()) == null,
        "Product should not be found after deletion");
  }

  @Test
  public void whenMultipleProductsSaved_thenFindAllReturnsAllProducts() {
    Product product1 = new Product("Laptop", 899.99);
    Product product2 = new Product("Headphones", 199.99);
    storeRepository.save(product1);
    storeRepository.save(product2);

    List<Product> products = storeRepository.findAll();

    assertEquals(2, products.size(), "Should return two products");
    assertTrue(products.contains(product1), "Product list should contain Laptop");
    assertTrue(products.contains(product2), "Product list should contain Headphones");
  }

  @Test
  public void givenProductDoesNotExist_whenFindById_thenReturnsEmpty() {
    Product foundProduct = storeRepository.findById(UUID.randomUUID());

    assertTrue(foundProduct == null, "Product should not be found");
  }
}
