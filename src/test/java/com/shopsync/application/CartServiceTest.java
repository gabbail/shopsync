package com.shopsync.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.shopsync.domain.model.Product;
import com.shopsync.domain.repository.CartRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;

public class CartServiceTest {
    private CartService cartService;
    private CartRepository cartRepo;
    private Product productA;
    private Product productB;

    @BeforeEach
    public void setUp() {
        cartRepo = mock(CartRepository.class);
        cartService = new CartService(cartRepo);
        productA = new Product("Product A", 10.0);
        productB = new Product("Product B", 20.0);
    }

    @Test
    public void givenProductAndQuantity_whenAddProductToCart_thenShouldAddProductToRepository() {
        int quantity = 2;

        cartService.addProductToCart(productA, quantity);

        verify(cartRepo).addProduct(productA, quantity);
    }

    @Test
    public void givenProduct_whenRemoveProductFromCart_thenShouldRemoveProductFromRepository() {
        int quantity = 2; 
        cartService.addProductToCart(productA, quantity);

        cartService.removeProductFromCart(productA.getId().toString());

        verify(cartRepo).removeProduct(productA.getId());
    }

    @Test
    public void givenProductsInCart_whenViewCart_thenShouldReturnCartItems() {
        Map<Product, Integer> expectedCart = new HashMap<>();
        expectedCart.put(productA, 2);
        expectedCart.put(productB, 1);
        when(cartRepo.getCartItems()).thenReturn(expectedCart);

        Map<Product, Integer> actualCart = cartService.viewCart();

        assertEquals(expectedCart, actualCart);
    }

    @Test
    public void givenProductsInCart_whenGetTotalPrice_thenShouldReturnTotalPrice() {
        when(cartRepo.getTotalPrice()).thenReturn(50.0);

        double totalPrice = cartService.getTotalPrice();

        assertEquals(50.0, totalPrice, 0.001);
    }

    @Test
    public void givenNullProduct_whenAddProductToCart_thenShouldThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            cartService.addProductToCart(null, 2);
        });
        assertEquals("Invalid product or quantity.", exception.getMessage());
    }

    @Test
    public void givenZeroQuantity_whenAddProductToCart_thenShouldThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            cartService.addProductToCart(productA, 0);
        });
        assertEquals("Invalid product or quantity.", exception.getMessage());
    }

    @Test
    public void givenNullProduct_whenRemoveProductFromCart_thenShouldThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            cartService.removeProductFromCart("");
        });
        assertEquals("Product id cannot be null.", exception.getMessage());
    }
}

