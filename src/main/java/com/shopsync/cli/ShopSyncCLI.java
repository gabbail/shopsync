package com.shopsync.cli;

import com.shopsync.application.CartService;
import com.shopsync.application.StoreService;
import com.shopsync.domain.model.Product;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

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
        case "addProductToCart":
          addProductToCart(commandParts);
          break;
        case "deleteProductFromCart":
          deleteProductFromCart(commandParts);
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
    System.out.println("delete [id]                         - Delete a product by ID");
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

    Product product = new Product(name, price);

    storeService.addProduct(product);
    System.out.println("Product added: " + name + " with price " + price);
  }

  private void viewProduct(String[] commandParts) {
    if (commandParts.length < 2) {
      System.out.println("Usage: view [id]");
      return;
    }

    String productId = commandParts[1];
    Optional<Product> product = storeService.getProductById(UUID.fromString(productId));

    if (product.isPresent()) {
      System.out.println("Product ID: " + product.get().getId());
      System.out.println("Name: " + product.get().getName());
      System.out.println("Price: " + product.get().getPrice());
    } else {
      System.out.println("Product with ID " + productId + " not found.");
    }
  }

  private void listProducts() {
    System.out.println("All Products:");
    for (Product product : storeService.getAllProducts()) {
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
      System.out.println("Usage: delete [id]");
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

    Optional<Product> productToAdd = storeService.getProductById(UUID.fromString(productId));
    if (productToAdd != null) {
      Product product = productToAdd.get();
      cartService.addProduct(product, quantity);
      System.out.println(quantity + " of " + product.getName() + " added to the cart.");
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

    Optional<Product> productToDelete = storeService.getProductById(UUID.fromString(productId));
    if (productToDelete != null) {
      Product product = productToDelete.get();
      cartService.removeProduct(product);
      System.out.println("Product " + product.getName() + " removed from the cart.");
    } else {
      System.out.println("Product not found in the cart.");
    }
  }

  private void viewCart() {
    System.out.println("Items in your cart:");
    for (Map.Entry<Product, Integer> entry : cartService.viewCart().entrySet()) {
      Product product = entry.getKey();
      Integer quantity = entry.getValue();
      System.out.println(
          "- ID: "
              + product.getId()
              + ", Name: "
              + product.getName()
              + ", Price: "
              + product.getPrice()
              + ", Quantity: "
              + quantity);
    }

    double totalPrice = cartService.getTotalPrice();
    System.out.println("Total price: " + totalPrice);
  }
}
