package com.example.demo.repository;

import com.example.demo.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit test untuk ProductRepository.
 *
 * <p>Test class ini menguji repository layer untuk Product entity
 * menggunakan H2 in-memory database.
 *
 * @author Harist Islami
 * @version 1.0
 * @since 2025-09-20
 */
@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void whenFindById_thenReturnProduct() {
        // Given
        Product product = new Product();
        product.setName("Laptop ASUS");
        product.setDescription("Laptop gaming");
        product.setPrice(15000000.0);
        product.setStock(10);

        entityManager.persist(product);
        entityManager.flush();

        // When
        Product found = productRepository.findById(product.getId()).orElse(null);

        // Then
        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo(product.getName());
        assertThat(found.getPrice()).isEqualTo(product.getPrice());
    }

    @Test
    public void whenFindByNameContainingIgnoreCase_thenReturnProducts() {
        // Given
        Product laptop = new Product();
        laptop.setName("Laptop ASUS VivoBook");
        laptop.setDescription("Laptop thin and light");
        laptop.setPrice(8500000.0);
        laptop.setStock(15);

        Product phone = new Product();
        phone.setName("iPhone 14 Pro");
        phone.setDescription("Smartphone Apple");
        phone.setPrice(18000000.0);
        phone.setStock(8);

        Product tablet = new Product();
        tablet.setName("Samsung Galaxy Tab");
        tablet.setDescription("Tablet Android");
        tablet.setPrice(8000000.0);
        tablet.setStock(12);

        entityManager.persist(laptop);
        entityManager.persist(phone);
        entityManager.persist(tablet);
        entityManager.flush();

        // When
        List<Product> foundProducts = productRepository.findByNameContainingIgnoreCase("laptop");

        // Then
        assertThat(foundProducts).hasSize(1);
        assertThat(foundProducts.get(0).getName()).containsIgnoringCase("laptop");
    }

    @Test
    public void whenFindByNameContainingIgnoreCaseWithMultipleMatches_thenReturnAllMatchingProducts() {
        // Given
        Product laptop1 = new Product();
        laptop1.setName("Laptop ASUS");
        laptop1.setDescription("Laptop gaming");
        laptop1.setPrice(15000000.0);
        laptop1.setStock(10);

        Product laptop2 = new Product();
        laptop2.setName("Laptop HP");
        laptop2.setDescription("Laptop business");
        laptop2.setPrice(12000000.0);
        laptop2.setStock(8);

        Product phone = new Product();
        phone.setName("iPhone 14");
        phone.setDescription("Smartphone");
        phone.setPrice(18000000.0);
        phone.setStock(5);

        entityManager.persist(laptop1);
        entityManager.persist(laptop2);
        entityManager.persist(phone);
        entityManager.flush();

        // When
        List<Product> foundProducts = productRepository.findByNameContainingIgnoreCase("laptop");

        // Then
        assertThat(foundProducts).hasSize(2);
        assertThat(foundProducts).extracting(Product::getName)
                .containsExactlyInAnyOrder("Laptop ASUS", "Laptop HP");
    }

    @Test
    public void whenFindByNameContainingIgnoreCaseWithNoMatch_thenReturnEmptyList() {
        // Given
        Product product = new Product();
        product.setName("Samsung Galaxy S21");
        product.setDescription("Smartphone Android");
        product.setPrice(10000000.0);
        product.setStock(20);

        entityManager.persist(product);
        entityManager.flush();

        // When
        List<Product> foundProducts = productRepository.findByNameContainingIgnoreCase("iphone");

        // Then
        assertThat(foundProducts).isEmpty();
    }

    @Test
    public void whenSaveProduct_thenProductIsPersisted() {
        // Given
        Product product = new Product();
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(1000000.0);
        product.setStock(5);

        // When
        Product savedProduct = productRepository.save(product);
        Product foundProduct = productRepository.findById(savedProduct.getId()).orElse(null);

        // Then
        assertThat(foundProduct).isNotNull();
        assertThat(foundProduct.getName()).isEqualTo("Test Product");
        assertThat(foundProduct.getDescription()).isEqualTo("Test Description");
        assertThat(foundProduct.getPrice()).isEqualTo(1000000.0);
        assertThat(foundProduct.getStock()).isEqualTo(5);
    }

    @Test
    public void whenDeleteProduct_thenProductIsRemoved() {
        // Given
        Product product = new Product();
        product.setName("To Be Deleted");
        product.setDescription("Will be deleted");
        product.setPrice(500000.0);
        product.setStock(3);

        entityManager.persist(product);
        entityManager.flush();

        Long productId = product.getId();

        // When
        productRepository.deleteById(productId);
        Product foundProduct = productRepository.findById(productId).orElse(null);

        // Then
        assertThat(foundProduct).isNull();
    }
}