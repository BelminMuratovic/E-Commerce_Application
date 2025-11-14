package com.example.ECommerce.e_commerce_core.impl;

import com.example.ECommerce.e_commerce_api.OrderService;
import com.example.ECommerce.e_commerce_api.model.order.Order;
import com.example.ECommerce.e_commerce_api.model.order.OrderCreateRequest;
import com.example.ECommerce.e_commerce_api.model.order.OrderUpdateRequest;
import com.example.ECommerce.e_commerce_core.mapper.OrderMapper;
import com.example.ECommerce.e_commerce_dao.model.OrderEntity;
import com.example.ECommerce.e_commerce_dao.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    final private OrderRepository orderRepository;
    final private OrderMapper orderMapper;

    @Override
    public ResponseEntity<List<Order>> getOrders() throws Exception {
        List<OrderEntity> orderEntities = orderRepository.findAll();
        List<Order> dtos = orderMapper.entitiesToDtos(orderEntities);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    public ResponseEntity<List<Order>> getOrdersByName(String name) throws Exception {
        List<OrderEntity> orderEntities = orderRepository.findByNameContaining(name);
        List<Order> dtos = orderMapper.entitiesToDtos(orderEntities);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Order> findOrderById(final Long orderId) throws Exception {
        OrderEntity orderEntity = orderRepository.findById(orderId).orElse(null);
        if (orderEntity != null) {
            Order dto = orderMapper.entityToDto(orderEntity);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<Order> create(final OrderCreateRequest request) throws Exception {
        OrderEntity orderEntity = orderMapper.dtoToEntity(request);
        orderEntity.setDate(LocalDate.now());
        orderRepository.save(orderEntity);
        return new ResponseEntity<>(orderMapper.entityToDto(orderEntity), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<Order> update(final Long orderId, final OrderUpdateRequest request) throws Exception {
        OrderEntity orderEntity = orderRepository.findById(orderId).orElse(null);
        if (orderEntity != null && request != null) {
            orderMapper.updateEntity(request, orderEntity);
            orderEntity.setDate(LocalDate.now());
            return new ResponseEntity<>(orderMapper.entityToDto(orderEntity), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<Order> delete(final Long orderId) throws Exception {
        orderRepository.deleteById(orderId);
        OrderEntity orderEntity = orderRepository.findById(orderId).orElse(null);
        if (orderEntity == null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }
}
