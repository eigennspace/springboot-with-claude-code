package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

/**
 * ProductController adalah REST controller yang menyediakan endpoint untuk mengelola data produk.
 *
 * <p>Controller ini menyediakan API CRUD untuk operasi produk seperti:
 * - GET semua produk
 * - GET produk berdasarkan ID
 * - POST produk baru
 * - PUT update produk
 * - DELETE produk
 * - GET produk berdasarkan nama
 *
 * @author Harist Islami
 * @version 2.0
 * @since 2025-09-20
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Endpoint untuk mendapatkan semua data produk.
     *
     * @return List<Product> daftar semua produk yang tersedia di database
     */
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    /**
     * Endpoint untuk mendapatkan produk berdasarkan ID.
     *
     * @param id ID produk yang dicari
     * @return ResponseEntity<Product> produk dengan ID yang diberikan
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(product -> ResponseEntity.ok(product))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Endpoint untuk mencari produk berdasarkan nama.
     *
     * @param name nama produk yang dicari (case insensitive)
     * @return List<Product> daftar produk yang namanya mengandung kata kunci
     */
    @GetMapping("/search")
    public List<Product> searchProductsByName(@RequestParam String name) {
        return productService.findProductsByName(name);
    }

    /**
     * Endpoint untuk membuat produk baru.
     *
     * @param product data produk yang akan dibuat
     * @return ResponseEntity<Product> produk yang berhasil dibuat
     */
    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    /**
     * Endpoint untuk mengupdate produk yang sudah ada.
     *
     * @param id ID produk yang akan diupdate
     * @param product data produk baru
     * @return ResponseEntity<Product> produk yang berhasil diupdate
     */
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @Valid @RequestBody Product product) {
        try {
            Product updatedProduct = productService.updateProduct(id, product);
            return ResponseEntity.ok(updatedProduct);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint untuk menghapus produk.
     *
     * @param id ID produk yang akan dihapus
     * @return ResponseEntity<Void> status response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}