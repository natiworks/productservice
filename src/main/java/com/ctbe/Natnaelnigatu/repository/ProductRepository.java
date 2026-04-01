package com.ctbe.Natnaelnigatu.repository;

import com.ctbe.Natnaelnigatu.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Spring Data generates: SELECT * FROM products WHERE LOWER(name) LIKE %?%

    List<Product> findByNameContainingIgnoreCase(String keyword);

    // Spring Data generates: SELECT * FROM products WHERE category = ?

    List<Product> findByCategory(String category);
}