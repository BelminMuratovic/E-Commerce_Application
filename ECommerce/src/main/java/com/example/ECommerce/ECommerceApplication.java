package com.example.ECommerce;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.ECommerce")
public class ECommerceApplication {
    private static final Logger logger = LoggerFactory.getLogger(ECommerceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ECommerceApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void logStartup() {
        logger.info("Application started successfully!");
    }
}
