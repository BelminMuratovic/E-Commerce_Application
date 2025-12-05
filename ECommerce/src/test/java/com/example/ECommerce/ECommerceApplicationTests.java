package com.example.ECommerce;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest
class ECommerceApplicationTests {
	@DynamicPropertySource
	static void configureProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", () -> "jdbc:postgresql://localhost:5433/ecommerce");
		registry.add("spring.datasource.username", () -> "postgres");
		registry.add("spring.datasource.password", () -> "password");
	}

	@Test
	void contextLoads() {
	}

}
