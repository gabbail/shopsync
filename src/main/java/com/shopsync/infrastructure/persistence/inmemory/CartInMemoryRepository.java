package com.shopsync.infrastructure.persistence.inmemory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.shopsync.domain.model.Product;
import com.shopsync.domain.repository.CartRepository;

public class CartInMemoryRepository implements CartRepository {
    private final Map<Product, Integer> cartItems = new HashMap<>();

    @Override
    public void addProduct(Product product, int quantity) {
        if (product == null || quantity <= 0) {
            throw new IllegalArgumentException("Invalid product or quantity.");
        }
        cartItems.put(product, cartItems.getOrDefault(product, 0) + quantity);
    }

    @Override
    public void removeProduct(UUID id) {
        Product productToRemove = null;
        for (Product product : cartItems.keySet()) {
            if (product.getId().equals(id)) {
                productToRemove = product;
                break;
            }
        }

        if (productToRemove != null) {
            cartItems.remove(productToRemove);
        }
    }

    @Override
    public Map<Product, Integer> getCartItems() {
        return new HashMap<>(cartItems);
    }

    @Override
    public double getTotalPrice() {
        return cartItems.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
    }
}

