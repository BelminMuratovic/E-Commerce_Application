package com.example.ECommerce.e_commerce_core;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.ECommerce.e_commerce_api.model.order.Order;
import com.example.ECommerce.e_commerce_api.model.order.OrderCreateRequest;
import com.example.ECommerce.e_commerce_api.model.order.OrderUpdateRequest;
import com.example.ECommerce.e_commerce_core.impl.OrderServiceImpl;
import com.example.ECommerce.e_commerce_core.mapper.OrderMapper;
import com.example.ECommerce.e_commerce_dao.model.OrderEntity;
import com.example.ECommerce.e_commerce_dao.repository.OrderRepository;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    private static OrderEntity entity;
    private static Order dto;
    private static List<OrderEntity> entities;
    private static List<Order> dtos;
    private static List<OrderEntity> filteredEntities;
    private static List<Order> filteredDtos;
    private static OrderCreateRequest request;
    private static OrderUpdateRequest updateRequest;

    @BeforeAll
    static void setUp() {
        entity = new OrderEntity(1L, "Name 1", "Address 1", LocalDate.now(), List.of());

        dto = new Order(1L, "Name 1", "Address 1", LocalDate.now(), List.of());

        entities = List.of(
                new OrderEntity(1L, "Name 1", "Address 1", LocalDate.now(), List.of()),
                new OrderEntity(2L, "Name 2", "Address 2", LocalDate.now(), List.of()));

        dtos = List.of(
                new Order(1L, "Name 1", "Address 1", LocalDate.now(), List.of()),
                new Order(2L, "Name 2", "Address 2", LocalDate.now(), List.of()));

        filteredEntities = List.of(entities.get(0));

        filteredDtos = List.of(dtos.get(0));

        request = new OrderCreateRequest("Name 1", "Address 1", LocalDate.now(), List.of());

        updateRequest = new OrderUpdateRequest("Updated Name", "Updated Address", LocalDate.now(), List.of());
    }

    @Test
    void testGetOrders() throws Exception {
        when(orderRepository.findAll()).thenReturn(entities);
        when(orderMapper.entitiesToDtos(entities)).thenReturn(dtos);

        ResponseEntity<List<Order>> response = orderService.getOrders();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dtos, response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testGetOrdersByName() throws Exception {
        String name = "Name 1";
        when(orderRepository.findByNameContaining(name)).thenReturn(filteredEntities);
        when(orderMapper.entitiesToDtos(filteredEntities)).thenReturn(filteredDtos);

        ResponseEntity<List<Order>> response = orderService.getOrdersByName(name);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(filteredDtos, response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testFindOrderById() throws Exception {
        Long id = 1L;
        when(orderRepository.findById(id)).thenReturn(Optional.of(entity));
        when(orderMapper.entityToDto(entity)).thenReturn(dto);

        ResponseEntity<Order> response = orderService.findOrderById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    void testCreateOrder() throws Exception {
        when(orderMapper.dtoToEntity(request)).thenReturn(entity);
        when(orderMapper.entityToDto(entity)).thenReturn(dto);
        when(orderRepository.save(entity)).thenReturn(entity);

        ResponseEntity<Order> response = orderService.create(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    void testUpdateOrder_Success() throws Exception {
        Long id = 1L;
        Order updatedOrder = new Order(1L, "Updated Name", "Updated Address", LocalDate.now(), List.of());

        when(orderRepository.findById(id)).thenReturn(Optional.of(entity));
        doNothing().when(orderMapper).updateEntity(updateRequest, entity);
        when(orderMapper.entityToDto(entity)).thenReturn(updatedOrder);

        ResponseEntity<Order> response = orderService.update(id, updateRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedOrder, response.getBody());
        assertEquals(LocalDate.now(), entity.getDate());
    }

    @Test
    void testUpdateOrder_NotModified() throws Exception {
        Long id = 1L;
        OrderUpdateRequest request = new OrderUpdateRequest();

        when(orderRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<Order> response = orderService.update(id, request);

        assertEquals(HttpStatus.NOT_MODIFIED, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testDeleteOrder_Success() throws Exception {
        Long id = 1L;

        doNothing().when(orderRepository).deleteById(id);
        when(orderRepository.existsById(id)).thenReturn(false);

        ResponseEntity<Void> response = orderService.delete(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testDeleteOrder_NotModified() throws Exception {
        Long id = 1L;

        OrderEntity existingEntity = new OrderEntity();
        existingEntity.setId(id);

        doNothing().when(orderRepository).deleteById(id);
        when(orderRepository.existsById(id)).thenReturn(true);

        ResponseEntity<Void> response = orderService.delete(id);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }
}
