package com.ctbe.Natnaelnigatu;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import com.ctbe.Natnaelnigatu.model.Product;
import com.ctbe.Natnaelnigatu.repository.ProductRepository;
@SpringBootApplication
public class ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(ProductRepository repository) {
        return args -> {
            // fixed: 4-arg constructor (name, price, stockQty, category)
            repository.save(new Product("Laptop",   1200.00, 15, "Electronics"));
            repository.save(new Product("Monitor",   300.00,  8, "Electronics"));
            repository.save(new Product("Keyboard",   50.00, 30, "Peripherals"));
        };
    }
}