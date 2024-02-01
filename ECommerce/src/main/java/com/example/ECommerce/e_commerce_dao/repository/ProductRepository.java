package com.example.ECommerce.e_commerce_dao.repository;

import com.example.ECommerce.e_commerce_dao.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findByTypeContaining(String type);

    List<ProductEntity> findByNameContaining(String name);
}