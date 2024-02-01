package com.example.ECommerce.e_commerce_dao.repository;

import com.example.ECommerce.e_commerce_dao.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByNameContaining(String name);
}
