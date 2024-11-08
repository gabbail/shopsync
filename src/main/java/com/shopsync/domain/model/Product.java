package com.shopsync.domain.model;

import java.util.UUID;

public class Product {
  private UUID id;
  private String name;
  private double price;

  public Product(String name, double price) {
    this.id = UUID.randomUUID();
    this.name = name;
    this.price = price;
  }

  public Product(String id, String name, double price) {
    this.id = UUID.fromString(id);
    this.name = name;
    this.price = price;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public double getPrice() {
    return price;
  }

  public UUID getId() {
    return id;
  }
}
