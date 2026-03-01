package com.example.productservice;

import com.example.productservice.controller.HealthController;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class HealthControllerTest {

    @Test
    void health_shouldReturnUp() {
        HealthController controller = new HealthController();
        Map<String, Object> response = controller.health();

        assertEquals("UP", response.get("status"));
        assertEquals("product-service", response.get("service"));
        assertNotNull(response.get("timestamp"));
    }
}