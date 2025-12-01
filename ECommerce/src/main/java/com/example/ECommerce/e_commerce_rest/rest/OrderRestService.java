package com.example.ECommerce.e_commerce_rest.rest;

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
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<List<Order>> getOrders() throws Exception {
        return orderService.getOrders();
    }

    @GetMapping("/byName")
    public ResponseEntity<List<Order>> getOrdersByName(@RequestParam String name) throws Exception {
        return orderService.getOrdersByName(name);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Order> findOrderById(@PathVariable(name = "id") final Long id) throws Exception {
        return orderService.findOrderById(id);
    }

    @PostMapping
    public ResponseEntity<Order> create(@RequestBody final OrderCreateRequest request) throws Exception {
        return orderService.create(request);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<Order> update(@Parameter(required = true) @PathVariable("id") final Long id,
            @RequestBody final OrderUpdateRequest request) throws Exception {
        return orderService.update(id, request);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> delete(@Parameter(required = true) @PathVariable("id") final Long id)
            throws Exception {
        return orderService.delete(id);
    }
}
