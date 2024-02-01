package com.example.ECommerce.e_commerce_core.mapper;

import com.example.ECommerce.e_commerce_api.model.order.Order;
import com.example.ECommerce.e_commerce_api.model.order.OrderCreateRequest;
import com.example.ECommerce.e_commerce_api.model.order.OrderUpdateRequest;
import com.example.ECommerce.e_commerce_dao.model.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderEntity dtoToEntity(OrderCreateRequest order);

    Order entityToDto(OrderEntity orderEntity);

    void updateEntity(OrderUpdateRequest order, @MappingTarget OrderEntity orderEntity);

    List<Order> entitiesToDtos(List<OrderEntity> orderEntity);
}
