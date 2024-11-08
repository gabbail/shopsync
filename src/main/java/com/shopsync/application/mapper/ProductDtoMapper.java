package com.shopsync.application.mapper;

import com.shopsync.application.dto.ProductDto;
import com.shopsync.domain.model.Product;

public class ProductDtoMapper {

    public static ProductDto toDto(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null.");
        }
        return new ProductDto(product.getId().toString(), product.getName(), product.getPrice());
    }

    public static Product toEntity(ProductDto productDto) {
        if (productDto == null) {
            throw new IllegalArgumentException("ProductDto cannot be null.");
        }

        if (productDto.getId() != "")
            return new Product(productDto.getId(), productDto.getName(), productDto.getPrice());
        else
            return new Product(productDto.getName(), productDto.getPrice());
    }
}
