package com.shopsync.cli;

import com.shopsync.application.CartService;
import com.shopsync.application.StoreService;
import com.shopsync.application.dto.ProductDto;
import com.shopsync.application.mapper.ProductDtoMapper;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;
import java.util.stream.Collectors;

public class ShopSyncCLI {
    private final StoreService storeService;
    private final CartService cartService;
    private final Scanner scanner;

    public ShopSyncCLI(StoreService storeService, CartService cartService) {
        this.storeService = storeService;
        this.cartService = cartService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Welcome to ShopSync CLI! Type 'help' for a list of commands.");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            String[] commandParts = input.split(" ");
            String command = commandParts[0];

            switch (command) {
                case "help":
                    displayHelp();
                    break;
                case "addProduct":
                    addProduct(commandParts);
                    break;
                case "viewProduct":
                    viewProduct(commandParts);
                    break;
                case "listProducts":
                    listProducts();
                    break;
                case "deleteProduct":
                    deleteProduct(commandParts);
                    break;
                case "addProductToCart":
                    addProductToCart(commandParts);
                    break;
                case "deleteProductFromCart":
                    deleteProductFromCart(commandParts);
                    break;
                case "viewCart":
                    viewCart();
                    break;
                case "exit":
                    System.out.println("Exiting ShopSync CLI. Goodbye!");
                    return;
                default:
                    System.out.println("Unknown command. Type 'help' for a list of commands.");
            }
        }
    }

    private void displayHelp() {
        System.out.println("Available commands:");
        System.out.println("addProduct [name] [price]           - Add a new product");
        System.out.println("viewProduct [id]                    - View a product by ID");
        System.out.println("listProducts                        - List all products");
        System.out.println("deleteProduct [id]                  - Delete a product by ID");
        System.out.println("addProductToCart [id] [quantity]    - Add a product to the cart");
        System.out.println(
                "deleteProductFromCart [id]          - Delete a product from the cart by ID");
        System.out.println("viewCart                            - List all products in cart");
        System.out.println("exit                                - Exit the CLI");
    }

    private void addProduct(String[] commandParts) {
        if (commandParts.length < 3) {
            System.out.println("Usage: add [name] [price]");
            return;
        }

        String name = commandParts[1];
        double price;

        try {
            price = Double.parseDouble(commandParts[2]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid price format. Please enter a valid number.");
            return;
        }

        ProductDto productDto = new ProductDto("", name, price);

        storeService.addProduct(ProductDtoMapper.toEntity(productDto));
        System.out.println("Product added: " + name + " with price " + price);
    }

    private void viewProduct(String[] commandParts) {
        if (commandParts.length < 2) {
            System.out.println("Usage: view [id]");
            return;
        }

        String productId = commandParts[1];
        ProductDto productDto = ProductDtoMapper.toDto(storeService.getProductById(UUID.fromString(productId)));

        if (productDto != null) {
            System.out.println("Product ID: " + productDto.getId());
            System.out.println("Name: " + productDto.getName());
            System.out.println("Price: " + productDto.getPrice());
        } else {
            System.out.println("Product with ID " + productId + " not found.");
        }
    }

    private void listProducts() {
        System.out.println("All Products:");
        List<ProductDto> productDtos = storeService.getAllProducts().stream()
                .map(ProductDtoMapper::toDto)
                .collect(Collectors.toList());

        for (ProductDto product : productDtos) {
            System.out.println(
                    "- ID: "
                            + product.getId()
                            + ", Name: "
                            + product.getName()
                            + ", Price: "
                            + product.getPrice());
        }
    }

    private void deleteProduct(String[] commandParts) {
        if (commandParts.length < 2) {
            System.out.println("Usage: deleteProduct [id]");
            return;
        }

        String productId = commandParts[1];
        storeService.deleteProduct(UUID.fromString(productId));
        System.out.println("Product with ID " + productId + " has been deleted.");
    }

    private void addProductToCart(String[] commandParts) {
        if (commandParts.length < 3) {
            System.out.println("Usage: addProductToCart [id] [quantity]");
            return;
        }

        String productId = commandParts[1];
        int quantity;

        try {
            quantity = Integer.parseInt(commandParts[2]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid quantity format. Please enter a valid number.");
            return;
        }

        ProductDto productDto = ProductDtoMapper.toDto(storeService.getProductById(UUID.fromString(productId)));
        if (productDto != null) {
            cartService.addProductToCart(ProductDtoMapper.toEntity(productDto), quantity);
            System.out.println(quantity + " of " + productDto.getName() + " added to the cart.");
        } else {
            System.out.println("Product not found.");
        }
    }

    private void deleteProductFromCart(String[] commandParts) {
        if (commandParts.length < 2) {
            System.out.println("Usage: deleteProductFromCart [id]");
            return;
        }

        String productId = commandParts[1];

        ProductDto productDto = ProductDtoMapper.toDto(storeService.getProductById(UUID.fromString(productId)));
        if (productDto != null) {
            cartService.removeProductFromCart(productDto.getId());
            System.out.println("Product " + productDto.getName() + " removed from the cart.");
        } else {
            System.out.println("Product not found in the cart.");
        }
    }

    private void viewCart() {
        System.out.println("Items in your cart:");
        Map<ProductDto, Integer> cartItemsDto = cartService.viewCart().entrySet().stream()
                .collect(
                        Collectors.toMap(
                                entry -> ProductDtoMapper.toDto(entry.getKey()), Map.Entry::getValue));

        cartItemsDto.forEach(
                (productDto, quantity) -> {
                    System.out.println(
                            "Product: "
                                    + productDto.getName()
                                    + ", Price: "
                                    + productDto.getPrice()
                                    + ", Quantity: "
                                    + quantity);
                });

        double totalPrice = cartService.getTotalPrice();
        System.out.println("Total price: " + totalPrice);
    }
}
