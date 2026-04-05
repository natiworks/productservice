package com.ctbe.Natnaelnigatu.controller;

import com.ctbe.Natnaelnigatu.dto.ProductRequest;
import com.ctbe.Natnaelnigatu.dto.ProductResponse;
import com.ctbe.Natnaelnigatu.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "Products", description = "Product catalogue CRUD operations")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    // GET ALL → 200 OK
    @GetMapping
    @Operation(summary = "List all products")
    public ResponseEntity<List<ProductResponse>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    // GET BY ID → 200 OK | 404
    @GetMapping("/{id}")
    @Operation(summary = "Get a product by ID")
    public ResponseEntity<ProductResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    // CREATE → 201 Created
    @PostMapping
    @Operation(summary = "Create a new product")
    public ResponseEntity<ProductResponse> create(
            @Valid @RequestBody ProductRequest request) {

        ProductResponse created = service.create(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(location).body(created);
    }

    // UPDATE → 200 OK | 404
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing product")
    public ResponseEntity<ProductResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {

        return ResponseEntity.ok(service.update(id, request));
    }

    // DELETE → 204 No Content | 404
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}