package com.example.demo.repository;

import com.example.demo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface untuk Product entity.
 *
 * <p>Interface ini menyediakan akses ke database untuk operasi CRUD
 * pada produk menggunakan Spring Data JPA.
 *
 * @author Harist Islami
 * @version 1.0
 * @since 2025-09-20
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContainingIgnoreCase(String name);
}