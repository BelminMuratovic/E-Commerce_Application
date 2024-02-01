package com.example.ECommerce.e_commerce_api;

import com.example.ECommerce.e_commerce_api.model.order.Order;
import com.example.ECommerce.e_commerce_api.model.order.OrderCreateRequest;
import com.example.ECommerce.e_commerce_api.model.order.OrderUpdateRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * The interface Order service.
 */
public interface OrderService {


    /**
     * Gets orders.
     *
     * @return the orders
     * @throws Exception the exception
     */
    public ResponseEntity<List<Order>> getOrders() throws Exception;

    /**
     * Gets orders by name.
     *
     * @param name the name
     * @return the orders by name
     * @throws Exception the exception
     */
    public ResponseEntity<List<Order>> getOrdersByName(String name) throws Exception;

    /**
     * Find order by id response entity.
     *
     * @param orderId the order id
     * @return the response entity
     * @throws Exception the exception
     */
    public ResponseEntity<Order> findOrderById(final Long orderId) throws Exception;

    /**
     * Create response entity.
     *
     * @param request the request
     * @return the response entity
     * @throws Exception the exception
     */
    public ResponseEntity<Order> create(final OrderCreateRequest request) throws Exception;

    /**
     * Update response entity.
     *
     * @param orderId the order id
     * @param request the request
     * @return the response entity
     * @throws Exception the exception
     */
    public ResponseEntity<Order> update(final Long orderId, final OrderUpdateRequest request) throws Exception;

    /**
     * Delete response entity.
     *
     * @param orderId the order id
     * @return the response entity
     * @throws Exception the exception
     */
    public ResponseEntity<Order> delete(final Long orderId) throws Exception;
}
