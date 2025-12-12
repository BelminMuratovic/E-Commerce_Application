package com.example.ECommerce.e_commerce_core.impl;

import com.example.ECommerce.e_commerce_api.OrderService;
import com.example.ECommerce.e_commerce_api.model.order.Order;
import com.example.ECommerce.e_commerce_api.model.order.OrderCreateRequest;
import com.example.ECommerce.e_commerce_api.model.order.OrderUpdateRequest;
import com.example.ECommerce.e_commerce_core.mapper.OrderMapper;
import com.example.ECommerce.e_commerce_dao.model.OrderEntity;
import com.example.ECommerce.e_commerce_dao.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    final private OrderRepository orderRepository;
    final private OrderMapper orderMapper;

    @Override
    public ResponseEntity<List<Order>> getOrders() {
        List<OrderEntity> orderEntities = orderRepository.findAll();
        List<Order> dtos = orderMapper.entitiesToDtos(orderEntities);
        logger.info("Fetched {} orders", dtos.size());
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Order>> getOrdersByName(String name) {
        List<OrderEntity> orderEntities = orderRepository.findByNameContaining(name);
        List<Order> dtos = orderMapper.entitiesToDtos(orderEntities);
        logger.info("Fetched {} orders for name '{}'", dtos.size(), name);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Order> findOrderById(final Long orderId) {
        OrderEntity orderEntity = orderRepository.findById(orderId).orElse(null);
        if (orderEntity != null) {
            logger.info("Order found: {}", orderEntity.getName());
            Order dto = orderMapper.entityToDto(orderEntity);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else {
            logger.warn("Order not found with ID: {}", orderId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<Order> create(final OrderCreateRequest request) {
        OrderEntity orderEntity = orderMapper.dtoToEntity(request);
        orderEntity.setDate(LocalDate.now());
        orderRepository.save(orderEntity);
        logger.info("Order created with ID: {}", orderEntity.getId());
        return new ResponseEntity<>(orderMapper.entityToDto(orderEntity), HttpStatus.CREATED);
    }

    @Override
    @Transactional
    public ResponseEntity<Order> update(final Long orderId, final OrderUpdateRequest request) {
        OrderEntity orderEntity = orderRepository.findById(orderId).orElse(null);
        if (orderEntity != null && request != null) {
            orderMapper.updateEntity(request, orderEntity);
            orderEntity.setDate(LocalDate.now());
            logger.info("Order ID {} updated successfully", orderId);
            return new ResponseEntity<>(orderMapper.entityToDto(orderEntity), HttpStatus.OK);
        } else {
            logger.warn("Order ID {} not updated (not found or request null)", orderId);
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<Void> delete(final Long orderId) {
        orderRepository.deleteById(orderId);
        boolean stillExists = orderRepository.existsById(orderId);
        if (!stillExists) {
            logger.info("Order ID {} deleted successfully", orderId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            logger.error("Order ID {} still exists after deletion attempt", orderId);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
