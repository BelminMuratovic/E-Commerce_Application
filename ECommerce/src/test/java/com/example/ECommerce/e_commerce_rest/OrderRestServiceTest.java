package com.example.ECommerce.e_commerce_rest;

import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.ECommerce.e_commerce_api.OrderService;
import com.example.ECommerce.e_commerce_api.ProductService;
import com.example.ECommerce.e_commerce_api.model.order.Order;
import com.example.ECommerce.e_commerce_api.model.order.OrderCreateRequest;
import com.example.ECommerce.e_commerce_api.model.order.OrderUpdateRequest;
import com.example.ECommerce.e_commerce_rest.rest.OrderRestService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.http.MediaType;

@WebMvcTest(OrderRestService.class)
public class OrderRestServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private OrderService orderService;

    @MockitoBean
    private ProductService productService;

    private static List<Order> orders;
    private static List<Order> filteredOrders;
    private static Order order1;
    private static Order order2;
    private static OrderCreateRequest request;
    private static OrderUpdateRequest updateRequest;
    private static Order updatedOrder;

    @BeforeAll
    static void setUp() {
        order1 = new Order(1L, "Name1", "Address1", LocalDate.now(), List.of());
        order2 = new Order(2L, "Name2", "Address2", LocalDate.now(), List.of());

        orders = List.of(order1, order2);
        filteredOrders = List.of(order1);

        request = new OrderCreateRequest("Name1", "Address1", LocalDate.now(), List.of());
        updateRequest = new OrderUpdateRequest("Name2", null, null, null);
        updatedOrder = new Order(1L, "Name2", "Address1", LocalDate.now(), List.of());
    }

    @Test
    void testGetOrders() throws Exception {
        when(orderService.getOrders()).thenReturn(ResponseEntity.ok(orders));

        mockMvc.perform(get("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Name1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Name2"));
    }

    @Test
    void testGetOrdersByName() throws Exception {
        String name = "Name1";
        when(orderService.getOrdersByName(name)).thenReturn(ResponseEntity.ok(filteredOrders));

        mockMvc.perform(get("/api/v1/orders/byName")
                .param("name", name)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Name1"));
    }

    @Test
    void testFindOrderById() throws Exception {
        Long id = 1L;
        when(orderService.findOrderById(id)).thenReturn(ResponseEntity.ok(order1));

        mockMvc.perform(get("/api/v1/orders/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Name1"));
    }

    @Test
    void testCreateOrder() throws Exception {
        when(orderService.create(request)).thenReturn(ResponseEntity.ok(order1));

        mockMvc.perform(post("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Name1"));
    }

    @Test
    void testUpdateOrder() throws Exception {
        Long id = 1L;
        when(orderService.update(id, updateRequest)).thenReturn(ResponseEntity.ok(updatedOrder));

        mockMvc.perform(put("/api/v1/orders/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Name2"))
                .andExpect(jsonPath("$.address").value("Address1"));
    }

    @Test
    void testDeleteOrder() throws Exception {
        Long id = 1L;
        when(orderService.delete(id)).thenReturn(ResponseEntity.noContent().build());

        mockMvc.perform(delete("/api/v1/orders/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
