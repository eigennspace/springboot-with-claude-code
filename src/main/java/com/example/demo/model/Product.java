package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

/**
 * Kelas Product yang merepresentasikan data produk.
 *
 * <p>Kelas ini berisi informasi dasar tentang produk termasuk ID, nama,
 * deskripsi, harga, dan jumlah stok yang tersedia.
 *
 * @author Harist Islami
 * @version 1.0
 * @since 2025-09-20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Product name is required")
    @Size(min = 1, max = 100, message = "Product name must be between 1 and 100 characters")
    @Column(nullable = false, length = 100)
    private String name;

    @Size(max = 500, message = "Product description must be less than 500 characters")
    @Column(length = 500)
    private String description;

    @NotNull(message = "Product price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Product price must be greater than 0")
    @DecimalMax(value = "99999999.99", message = "Product price must be less than 100,000,000")
    @Column(nullable = false)
    private BigDecimal price;

    @NotNull(message = "Product stock is required")
    @Min(value = 0, message = "Product stock cannot be negative")
    @Column(nullable = false)
    private Integer stock;
}