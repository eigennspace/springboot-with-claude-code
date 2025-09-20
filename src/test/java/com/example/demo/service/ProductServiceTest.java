package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit test untuk ProductService.
 *
 * <p>Test class ini menguji service layer untuk Product business logic
 * menggunakan Mockito untuk mocking repository layer.
 *
 * @author Harist Islami
 * @version 1.0
 * @since 2025-09-20
 */
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    public void whenGetAllProducts_thenReturnAllProducts() {
        // Given
        Product product1 = new Product(1L, "Laptop ASUS", "Laptop gaming", 15000000.0, 10);
        Product product2 = new Product(2L, "iPhone 14", "Smartphone", 18000000.0, 8);
        List<Product> products = Arrays.asList(product1, product2);

        when(productRepository.findAll()).thenReturn(products);

        // When
        List<Product> result = productService.getAllProducts();

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("Laptop ASUS");
        assertThat(result.get(1).getName()).isEqualTo("iPhone 14");
    }

    @Test
    public void whenGetProductById_thenReturnProduct() {
        // Given
        Product product = new Product(1L, "Laptop ASUS", "Laptop gaming", 15000000.0, 10);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // When
        Optional<Product> result = productService.getProductById(1L);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Laptop ASUS");
    }

    @Test
    public void whenGetProductByIdNotFound_thenReturnEmpty() {
        // Given
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // When
        Optional<Product> result = productService.getProductById(1L);

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    public void whenCreateProduct_thenReturnSavedProduct() {
        // Given
        Product product = new Product(null, "New Product", "Description", 1000000.0, 5);
        Product savedProduct = new Product(1L, "New Product", "Description", 1000000.0, 5);

        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        // When
        Product result = productService.createProduct(product);

        // Then
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("New Product");
        assertThat(result.getDescription()).isEqualTo("Description");
        assertThat(result.getPrice()).isEqualTo(1000000.0);
        assertThat(result.getStock()).isEqualTo(5);
    }

    @Test
    public void whenUpdateProduct_thenReturnUpdatedProduct() {
        // Given
        Long productId = 1L;
        Product existingProduct = new Product(productId, "Old Name", "Old Description", 1000000.0, 5);
        Product updatedProduct = new Product(productId, "Updated Name", "Updated Description", 2000000.0, 10);

        when(productRepository.existsById(productId)).thenReturn(true);
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        // When
        Product result = productService.updateProduct(productId, updatedProduct);

        // Then
        assertThat(result.getName()).isEqualTo("Updated Name");
        assertThat(result.getDescription()).isEqualTo("Updated Description");
        assertThat(result.getPrice()).isEqualTo(2000000.0);
        assertThat(result.getStock()).isEqualTo(10);
    }

    @Test
    public void whenUpdateProductNotFound_thenThrowException() {
        // Given
        Long productId = 1L;
        Product product = new Product(productId, "Product", "Description", 1000000.0, 5);

        when(productRepository.existsById(productId)).thenReturn(false);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.updateProduct(productId, product);
        });

        assertThat(exception.getMessage()).isEqualTo("Product not found with id: " + productId);
    }

    @Test
    public void whenDeleteProduct_thenProductIsDeleted() {
        // Given
        Long productId = 1L;
        when(productRepository.existsById(productId)).thenReturn(true);

        // When
        productService.deleteProduct(productId);

        // Then
        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    public void whenDeleteProductNotFound_thenThrowException() {
        // Given
        Long productId = 1L;
        when(productRepository.existsById(productId)).thenReturn(false);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.deleteProduct(productId);
        });

        assertThat(exception.getMessage()).isEqualTo("Product not found with id: " + productId);
    }

    @Test
    public void whenFindProductsByName_thenReturnMatchingProducts() {
        // Given
        Product product1 = new Product(1L, "Laptop ASUS", "Laptop gaming", 15000000.0, 10);
        Product product2 = new Product(2L, "Laptop HP", "Laptop business", 12000000.0, 8);
        Product product3 = new Product(3L, "iPhone 14", "Smartphone", 18000000.0, 5);

        List<Product> laptops = Arrays.asList(product1, product2);

        when(productRepository.findByNameContainingIgnoreCase("laptop")).thenReturn(laptops);

        // When
        List<Product> result = productService.findProductsByName("laptop");

        // Then
        assertThat(result).hasSize(2);
        assertThat(result).extracting(Product::getName)
                .containsExactlyInAnyOrder("Laptop ASUS", "Laptop HP");
    }

    @Test
    public void whenFindProductsByNameWithNoMatch_thenReturnEmptyList() {
        // Given
        when(productRepository.findByNameContainingIgnoreCase("nonexistent")).thenReturn(List.of());

        // When
        List<Product> result = productService.findProductsByName("nonexistent");

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    public void whenFindProductsByNameCaseInsensitive_thenReturnMatchingProducts() {
        // Given
        Product product1 = new Product(1L, "Laptop ASUS", "Laptop gaming", 15000000.0, 10);
        Product product2 = new Product(2L, "laptop HP", "Laptop business", 12000000.0, 8);

        List<Product> laptops = Arrays.asList(product1, product2);

        when(productRepository.findByNameContainingIgnoreCase("LAPTOP")).thenReturn(laptops);

        // When
        List<Product> result = productService.findProductsByName("LAPTOP");

        // Then
        assertThat(result).hasSize(2);
        assertThat(result).extracting(Product::getName)
                .containsExactlyInAnyOrder("Laptop ASUS", "laptop HP");
    }
}