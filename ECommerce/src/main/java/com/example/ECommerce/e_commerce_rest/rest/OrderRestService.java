package com.example.ECommerce.e_commerce_rest.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.ECommerce.e_commerce_api.OrderService;
import com.example.ECommerce.e_commerce_api.model.order.Order;
import com.example.ECommerce.e_commerce_api.model.order.OrderCreateRequest;
import com.example.ECommerce.e_commerce_api.model.order.OrderUpdateRequest;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@AllArgsConstructor
public class OrderRestService {
    private static final Logger logger = LoggerFactory.getLogger(OrderRestService.class);
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<List<Order>> getOrders() {
        logger.info("Fetching all orders");
        return orderService.getOrders();
    }

    @GetMapping("/byName")
    public ResponseEntity<List<Order>> getOrdersByName(@RequestParam String name) {
        logger.info("Fetching orders by name: {}", name);
        return orderService.getOrdersByName(name);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Order> findOrderById(@PathVariable(name = "id") final Long id) {
        logger.info("Fetching order with ID: {}", id);
        return orderService.findOrderById(id);
    }

    @PostMapping
    public ResponseEntity<Order> create(@RequestBody final OrderCreateRequest request) {
        logger.info("Creating new order");
        logger.debug("Order create request: {}", request);
        return orderService.create(request);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<Order> update(@Parameter(required = true) @PathVariable("id") final Long id,
            @RequestBody final OrderUpdateRequest request) {
        logger.info("Updating order with ID: {}", id);
        logger.debug("Order update request: {}", request);
        return orderService.update(id, request);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> delete(@Parameter(required = true) @PathVariable("id") final Long id) {
        logger.info("Deleting order with ID: {}", id);
        return orderService.delete(id);
    }
}
