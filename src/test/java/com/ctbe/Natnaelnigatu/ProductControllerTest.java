package com.ctbe.Natnaelnigatu;

import com.ctbe.Natnaelnigatu.model.Product;
import com.ctbe.Natnaelnigatu.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest          // Loads FULL context, real H2 database
@AutoConfigureMockMvc    // Injects configured MockMvc
@Transactional           // Rolls back after every test
class ProductControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ProductRepository repo;
    @Autowired ObjectMapper mapper;

    private Long savedId;

    @BeforeEach
    void setUp() {
        // Save a real product to H2 before each test
        Product p = repo.save(new Product("Test Laptop", 999.0, 10, "Electronics"));
        savedId = p.getId();
    }

    // ■■ TEST 1: GET all → 200 OK ■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    @Test
    void getAll_returns200() throws Exception {
        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThan(0))));
    }

    // ■■ TEST 2: GET by id → 200 OK ■■■■■■■■■■■■■■■■■■■■■■■■■
    @Test
    void getById_returns200_whenExists() throws Exception {
        mockMvc.perform(get("/api/v1/products/" + savedId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Test Laptop")))
                .andExpect(jsonPath("$.category", is("Electronics")));
    }

    // ■■ TEST 3: POST → 201 Created + Location header ■■■■■■■
    @Test
    void create_returns201_withLocation() throws Exception {
        String body = """
            {"name":"Headphones","price":89.99,"stockQty":50,"category":"Audio"}
            """;

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is("Headphones")));
    }

    // ■■ TEST 4: PUT → 200 OK ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
    @Test
    void update_returns200() throws Exception {
        String body = """
            {"name":"Pro Laptop","price":1299.0,"stockQty":5,"category":"Electronics"}
            """;

        mockMvc.perform(put("/api/v1/products/" + savedId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Pro Laptop")))
                .andExpect(jsonPath("$.price", is(1299.0)));
    }

    // ■■ TEST 5: DELETE → 204 No Content ■■■■■■■■■■■■■■■■■■■■
    @Test
    void delete_returns204() throws Exception {
        mockMvc.perform(delete("/api/v1/products/" + savedId))
                .andExpect(status().isNoContent());
    }

    // ■■ TEST 6: GET unknown id → 404 + $.detail check ■■■■■■
    @Test
    void getById_returns404_whenNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/products/9999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail", containsString("9999")));
    }

    // ■■ TEST 7: DELETE unknown id → 404 ■■■■■■■■■■■■■■■■■■■■
    @Test
    void delete_returns404_whenNotFound() throws Exception {
        mockMvc.perform(delete("/api/v1/products/9999"))
                .andExpect(status().isNotFound());
    }

    // ■■ TEST 8: POST blank name → 400 + $.detail check ■■■■■
    @Test
    void create_returns400_whenNameBlank() throws Exception {
        String invalid = """
            {"name":"","price":10.0,"stockQty":1,"category":"Tech"}
            """;

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalid))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail", containsString("Name is required")));
    }

    // ■■ BONUS TEST 9: POST negative price → 400 ■■■■■■■■■■■■
    @Test
    void create_returns400_whenPriceInvalid() throws Exception {
        String invalid = """
            {"name":"Widget","price":-1,"stockQty":1,"category":"Tech"}
            """;

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalid))
                .andExpect(status().isBadRequest());
    }
}
