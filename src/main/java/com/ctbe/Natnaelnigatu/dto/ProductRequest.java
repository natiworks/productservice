package com.ctbe.Natnaelnigatu.dto;

import jakarta.validation.constraints.*;

public class ProductRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private double price;

    @Min(value = 0, message = "Stock quantity cannot be negative")
    private int stockQty;

    @NotBlank(message = "Category is required")
    private String category;

    // Getters
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStockQty() { return stockQty; }
    public String getCategory() { return category; }

    // Setters
    public void setName(String n) { this.name = n; }
    public void setPrice(double p) { this.price = p; }
    public void setStockQty(int q) { this.stockQty = q; }
    public void setCategory(String c) { this.category = c; }
}