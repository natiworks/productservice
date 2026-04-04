package com.ctbe.Natnaelnigatu.service;

import com.ctbe.Natnaelnigatu.dto.ProductRequest;
import com.ctbe.Natnaelnigatu.dto.ProductResponse;
import com.ctbe.Natnaelnigatu.exception.ResourceNotFoundException;
import com.ctbe.Natnaelnigatu.model.Product;
import com.ctbe.Natnaelnigatu.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    // READ ALL
    public List<ProductResponse> findAll() {
        return repo.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    // READ ONE
    public ProductResponse findById(Long id) {
        return toResponse(
                repo.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(id))
        );
    }

    // CREATE
    public ProductResponse create(ProductRequest req) {
        return toResponse(repo.save(toEntity(req)));
    }

    // UPDATE
    public ProductResponse update(Long id, ProductRequest req) {
        Product existing = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        existing.setName(req.getName());
        existing.setPrice(req.getPrice());
        existing.setStockQty(req.getStockQty());
        existing.setCategory(req.getCategory());

        return toResponse(repo.save(existing));
    }

    // DELETE
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException(id);
        }
        repo.deleteById(id);
    }

    // Mapping: Entity → Response
    private ProductResponse toResponse(Product p) {
        return new ProductResponse(
                p.getId(),
                p.getName(),
                p.getPrice(),
                p.getStockQty(),
                p.getCategory()
        );
    }

    // Mapping: Request → Entity
    private Product toEntity(ProductRequest req) {
        return new Product(
                req.getName(),
                req.getPrice(),
                req.getStockQty(),
                req.getCategory()
        );
    }
}