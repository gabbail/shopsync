package com.shopsync.application.mapper;

import com.shopsync.application.dto.ProductDto;
import com.shopsync.domain.model.Product;

public class ProductMapper {

  public static ProductDto toDto(Product product) {
    if (product == null) {
      return null;
    }
    return new ProductDto(product.getName(), product.getPrice());
  }

  public static Product toEntity(ProductDto productDto) {
    if (productDto == null) {
      return null;
    }
    return new Product(productDto.getName(), productDto.getPrice());
  }
}
