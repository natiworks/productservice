package com.ctbe.Natnaelnigatu;

import com.ctbe.Natnaelnigatu.dto.ProductResponse;
import com.ctbe.Natnaelnigatu.exception.ResourceNotFoundException;
import com.ctbe.Natnaelnigatu.model.Product;
import com.ctbe.Natnaelnigatu.repository.ProductRepository;
import com.ctbe.Natnaelnigatu.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;  // fake database

    @InjectMocks
    private ProductService productService;        // class under test

    @Test
    void findById_returnsProductResponse_whenProductExists() {

        // Arrange — 4-arg constructor: name, price, stockQty, category
        Product laptop = new Product("Laptop", 1200.0, 15, "Electronics");
        laptop.setId(1L);

        // Repository still returns Optional<Product> — that hasn't changed
        when(productRepository.findById(1L)).thenReturn(Optional.of(laptop));

        // Act — service now returns ProductResponse (not Optional<Product>)
        ProductResponse result = productService.findById(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Laptop");
        assertThat(result.getPrice()).isEqualTo(1200.0);
        assertThat(result.getStockQty()).isEqualTo(15);
        assertThat(result.getCategory()).isEqualTo("Electronics");
    }

    @Test
    void findById_throwsException_whenProductNotFound() {

        // Arrange — repo returns empty Optional
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert — service now throws ResourceNotFoundException, not returns empty
        assertThatThrownBy(() -> productService.findById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }
}