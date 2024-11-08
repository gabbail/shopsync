package com.shopsync.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.shopsync.domain.model.Product;
import com.shopsync.domain.repository.StoreRepository;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class StoreServiceTest {
  @Mock private StoreRepository storeRepository;

  @InjectMocks private StoreService storeService;

  private final double PRODUCT_PRICE = 1000;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void givenNewProduct_whenAdded_thenRepositorySaveIsCalled() {
    String productName = "Laptop";
    Product product = new Product(productName, PRODUCT_PRICE);

    storeService.addProduct(product);

    verify(storeRepository, times(1)).save(product);
  }

  @Test
  public void givenProductId_whenRetrieved_thenCorrectProductIsReturned() {
    Product product = new Product("Laptop", PRODUCT_PRICE);
    when(storeRepository.findById(product.getId())).thenReturn(product);

    Product retrievedProduct = storeService.getProductById(product.getId());

    assertTrue(retrievedProduct != null, "Product should be present");
    assertEquals("Laptop", retrievedProduct.getName(), "Product name should match");
    verify(storeRepository, times(1)).findById(product.getId());
  }

  @Test
  public void whenGettingAllProducts_thenAllProductsAreReturned() {
    Product product1 = new Product("Laptop", PRODUCT_PRICE);
    Product product2 = new Product("Headphones", PRODUCT_PRICE);
    when(storeRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

    List<Product> products = storeService.getAllProducts();

    assertEquals(2, products.size(), "Should return two products");
    verify(storeRepository, times(1)).findAll();
  }

  @Test
  public void givenProductId_whenDeleted_thenRepositoryDeleteIsCalled() {
    String productName = "Laptop";
    Product product = new Product(productName, PRODUCT_PRICE);

    storeService.deleteProduct(product.getId());

    verify(storeRepository, times(1)).delete(product.getId());
  }

  @Test
  public void givenNonExistentProductId_whenRetrieved_thenReturnsEmpty() {
    UUID productId = UUID.randomUUID();
    when(storeRepository.findById(productId)).thenReturn(null);

    Product retrievedProduct = storeService.getProductById(productId);

    assertTrue(retrievedProduct == null, "Product should not be found for nonexistent ID");
    verify(storeRepository, times(1)).findById(productId);
  }
}
