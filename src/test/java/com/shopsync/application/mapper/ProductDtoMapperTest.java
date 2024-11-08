package com.shopsync.application.mapper;

import org.junit.jupiter.api.Test;

import com.shopsync.application.dto.ProductDto;
import com.shopsync.domain.model.Product;

import static org.junit.jupiter.api.Assertions.*;

public class ProductDtoMapperTest {

    private final String PRODUCT_NAME = "Product name";
    private final double PRODUCT_PRICE = 10;
    private final Product PRODUCT = new Product(PRODUCT_NAME, PRODUCT_PRICE);
    private final ProductDto PRODUCT_DTO = new ProductDto("", PRODUCT_NAME, PRODUCT_PRICE);

    @Test
    public void givenProduct_whenMappedToDto_thenDtoHasSameValues() {
        ProductDto productDto = ProductDtoMapper.toDto(PRODUCT);

        assertNotNull(productDto);
        assertEquals(PRODUCT.getName(), productDto.getName());
        assertEquals(PRODUCT.getPrice(), productDto.getPrice());
    }

    @Test
    public void givenProductDto_whenMappedToEntity_thenEntityHasSameValues() {
        Product product = ProductDtoMapper.toEntity(PRODUCT_DTO);

        assertNotNull(product);
        assertEquals(PRODUCT_DTO.getName(), product.getName());
        assertEquals(PRODUCT_DTO.getPrice(), product.getPrice());
    }

    @Test
    public void givenNullProduct_whenMappedToDto_thenThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> ProductDtoMapper.toDto(null));
    }

    @Test
    public void givenNullProductDto_whenMappedToEntity_thenThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> ProductDtoMapper.toEntity(null));
    }
}

