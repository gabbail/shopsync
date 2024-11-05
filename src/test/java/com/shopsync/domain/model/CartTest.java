package com.shopsync.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CartTest {
  private Cart cart;
  private final String PRODUCT_NAME = "Phone";
  private final double PRODUCT_PRICE = 500.0;
  private final Product PRODUCT = new Product(PRODUCT_NAME, PRODUCT_PRICE);

  @BeforeEach
  public void Init() {
    cart = new Cart();
  }

  @Test
  public void givenCartEmpty_whenAddItem_thenCartContainsProduct() {
    cart.addProduct(PRODUCT, 1);

    int totalItemCount = cart.totalItemCount();
    assertEquals(1, totalItemCount);
  }

  @Test
  public void givenCartNotEmpty_whenRemoveItem_thenCartContainsProduct() {
    cart.addProduct(PRODUCT, 1);

    cart.removeProduct(PRODUCT);

    int totalItemCount = cart.totalItemCount();
    assertEquals(0, totalItemCount);
  }

  @Test
  public void givenCartWithMultipleItems_whenGetTotalCost_thenReturnsCorrectSum() {
    int product1Quantity = 2;
    int product2Quantity = 3;

    Product product1 = new Product(PRODUCT_NAME, PRODUCT_PRICE);
    Product product2 = new Product(PRODUCT_NAME, PRODUCT_PRICE);
    cart.addProduct(product1, product1Quantity);
    cart.addProduct(product2, product2Quantity);

    double actualTotalCost = cart.getTotalPrice();

    double expectedTotal =
        product1.getPrice() * product1Quantity + product2.getPrice() * product2Quantity;

    assertEquals(
        expectedTotal, actualTotalCost, "Total cost should be the sum of prices of items in cart");
  }
}
