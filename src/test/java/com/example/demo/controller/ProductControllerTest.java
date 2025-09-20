package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit test untuk ProductController.
 *
 * <p>Test class ini menguji controller layer untuk Product API endpoints
 * menggunakan MockMvc dan Mockito untuk mocking service layer.
 *
 * @author Harist Islami
 * @version 2.0
 * @since 2025-09-20
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void whenGetAllProducts_thenReturnProductList() throws Exception {
        // Given
        Product product1 = new Product(1L, "Laptop ASUS", "Laptop gaming", 15000000.0, 10);
        Product product2 = new Product(2L, "iPhone 14", "Smartphone", 18000000.0, 8);
        List<Product> products = Arrays.asList(product1, product2);

        given(productService.getAllProducts()).willReturn(products);

        // When & Then
        mockMvc.perform(get("/api/products")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Laptop ASUS")))
                .andExpect(jsonPath("$[1].name", is("iPhone 14")));
    }

    @Test
    public void whenGetProductById_thenReturnProduct() throws Exception {
        // Given
        Product product = new Product(1L, "Laptop ASUS", "Laptop gaming", 15000000.0, 10);

        given(productService.getProductById(1L)).willReturn(Optional.of(product));

        // When & Then
        mockMvc.perform(get("/api/products/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Laptop ASUS")))
                .andExpect(jsonPath("$.price", is(15000000.0)));
    }

    @Test
    public void whenGetProductByIdNotFound_thenReturn404() throws Exception {
        // Given
        given(productService.getProductById(1L)).willReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/api/products/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenSearchProductsByName_thenReturnMatchingProducts() throws Exception {
        // Given
        Product product1 = new Product(1L, "Laptop ASUS", "Laptop gaming", 15000000.0, 10);
        Product product2 = new Product(2L, "Laptop HP", "Laptop business", 12000000.0, 8);
        List<Product> products = Arrays.asList(product1, product2);

        given(productService.findProductsByName("laptop")).willReturn(products);

        // When & Then
        mockMvc.perform(get("/api/products/search?name=laptop")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Laptop ASUS")))
                .andExpect(jsonPath("$[1].name", is("Laptop HP")));
    }

    @Test
    public void whenCreateProduct_thenReturnCreatedProduct() throws Exception {
        // Given
        Product productToCreate = new Product(null, "New Product", "Description", 1000000.0, 5);
        Product createdProduct = new Product(1L, "New Product", "Description", 1000000.0, 5);

        given(productService.createProduct(any())).willReturn(createdProduct);

        // When & Then
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productToCreate)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("New Product")))
                .andExpect(jsonPath("$.price", is(1000000.0)));
    }

    @Test
    public void whenCreateProductWithInvalidData_thenReturn400() throws Exception {
        // Given
        Product invalidProduct = new Product(null, "", "", -1.0, -1);

        // When & Then
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidProduct)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenUpdateProduct_thenReturnUpdatedProduct() throws Exception {
        // Given
        Product productToUpdate = new Product(1L, "Updated Product", "Updated Description", 2000000.0, 10);
        Product updatedProduct = new Product(1L, "Updated Product", "Updated Description", 2000000.0, 10);

        given(productService.updateProduct(eq(1L), any())).willReturn(updatedProduct);

        // When & Then
        mockMvc.perform(put("/api/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productToUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Updated Product")))
                .andExpect(jsonPath("$.price", is(2000000.0)));
    }

    @Test
    public void whenUpdateProductNotFound_thenReturn404() throws Exception {
        // Given
        Product productToUpdate = new Product(999L, "Non-existent", "Description", 1000.0, 1);

        given(productService.updateProduct(eq(999L), any()))
                .willThrow(new RuntimeException("Product not found with id: 999"));

        // When & Then
        mockMvc.perform(put("/api/products/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productToUpdate)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenDeleteProduct_thenReturn204() throws Exception {
        // Given
        doNothing().when(productService).deleteProduct(1L);

        // When & Then
        mockMvc.perform(delete("/api/products/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void whenDeleteProductNotFound_thenReturn404() throws Exception {
        // Given
        doThrow(new RuntimeException("Product not found with id: 999"))
                .when(productService).deleteProduct(999L);

        // When & Then
        mockMvc.perform(delete("/api/products/999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenGetAllProductsEmpty_thenReturnEmptyList() throws Exception {
        // Given
        given(productService.getAllProducts()).willReturn(List.of());

        // When & Then
        mockMvc.perform(get("/api/products")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void whenSearchProductsWithNoResults_thenReturnEmptyList() throws Exception {
        // Given
        given(productService.findProductsByName("nonexistent")).willReturn(List.of());

        // When & Then
        mockMvc.perform(get("/api/products/search?name=nonexistent")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void whenCreateProductWithMissingRequiredField_thenReturn400() throws Exception {
        // Given
        Product invalidProduct = new Product(null, null, "Description", 1000.0, 5);

        // When & Then
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidProduct)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenUpdateProductWithInvalidId_thenReturn404() throws Exception {
        // Given
        Product productToUpdate = new Product(null, "Updated", "Description", 1000.0, 5);

        // When & Then
        mockMvc.perform(put("/api/products/invalid")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productToUpdate)))
                .andExpect(status().isBadRequest());
    }
}