package com.shopsync.infrastructure.persistence.inmemory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.shopsync.domain.model.Product;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

public class CartInMemoryRepositoryTest {
    private CartInMemoryRepository cartRepo;
    private Product productA;
    private Product productB;

    @BeforeEach
    public void setUp() {
        cartRepo = new CartInMemoryRepository();
        productA = new Product("Product A", 10.0);
        productB = new Product("Product B", 20.0);
    }

    @Test
    public void givenProductAndQuantity_whenAddProduct_thenCartShouldContainProduct() {
        cartRepo.addProduct(productA, 2);

        Map<Product, Integer> items = cartRepo.getCartItems();
        assertTrue(items.containsKey(productA));
        assertEquals(2, items.get(productA));
    }

    @Test
    public void givenProductInCart_whenRemoveProduct_thenCartShouldNotContainProduct() {
        cartRepo.addProduct(productA, 2);

        cartRepo.removeProduct(productA.getId());

        Map<Product, Integer> items = cartRepo.getCartItems();
        assertFalse(items.containsKey(productA));
    }

    @Test
    public void whenGetTotalPrice_thenShouldReturnSumOfProductPrices() {
        cartRepo.addProduct(productA, 2);
        cartRepo.addProduct(productB, 1);

        double totalPrice = cartRepo.getTotalPrice();

        assertEquals(40.0, totalPrice, 0.001);
    }
}

