package com.example.ECommerce.e_commerce_core.mapper;

import com.example.ECommerce.e_commerce_api.model.order.Item;
import com.example.ECommerce.e_commerce_api.model.order.Order;
import com.example.ECommerce.e_commerce_api.model.order.OrderCreateRequest;
import com.example.ECommerce.e_commerce_api.model.order.OrderUpdateRequest;
import com.example.ECommerce.e_commerce_dao.model.OrderEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-06T20:31:31+0100",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251023-0518, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Override
    public OrderEntity dtoToEntity(OrderCreateRequest order) {
        if ( order == null ) {
            return null;
        }

        OrderEntity orderEntity = new OrderEntity();

        if ( order.getAddress() != null ) {
            orderEntity.setAddress( order.getAddress() );
        }
        if ( order.getDate() != null ) {
            orderEntity.setDate( order.getDate() );
        }
        if ( order.getName() != null ) {
            orderEntity.setName( order.getName() );
        }
        ArrayList<Item> arrayList = order.getProducts();
        if ( arrayList != null ) {
            orderEntity.setProducts( new ArrayList<Item>( arrayList ) );
        }

        return orderEntity;
    }

    @Override
    public Order entityToDto(OrderEntity orderEntity) {
        if ( orderEntity == null ) {
            return null;
        }

        Order order = new Order();

        if ( orderEntity.getAddress() != null ) {
            order.setAddress( orderEntity.getAddress() );
        }
        if ( orderEntity.getDate() != null ) {
            order.setDate( orderEntity.getDate() );
        }
        if ( orderEntity.getId() != null ) {
            order.setId( orderEntity.getId() );
        }
        if ( orderEntity.getName() != null ) {
            order.setName( orderEntity.getName() );
        }
        ArrayList<Item> arrayList = orderEntity.getProducts();
        if ( arrayList != null ) {
            order.setProducts( new ArrayList<Item>( arrayList ) );
        }

        return order;
    }

    @Override
    public void updateEntity(OrderUpdateRequest order, OrderEntity orderEntity) {
        if ( order == null ) {
            return;
        }

        if ( order.getAddress() != null ) {
            orderEntity.setAddress( order.getAddress() );
        }
        else {
            orderEntity.setAddress( null );
        }
        if ( order.getDate() != null ) {
            orderEntity.setDate( order.getDate() );
        }
        else {
            orderEntity.setDate( null );
        }
        if ( order.getName() != null ) {
            orderEntity.setName( order.getName() );
        }
        else {
            orderEntity.setName( null );
        }
        if ( orderEntity.getProducts() != null ) {
            ArrayList<Item> arrayList = order.getProducts();
            if ( arrayList != null ) {
                orderEntity.getProducts().clear();
                orderEntity.getProducts().addAll( arrayList );
            }
        }
        else {
            ArrayList<Item> arrayList = order.getProducts();
            if ( arrayList != null ) {
                orderEntity.setProducts( new ArrayList<Item>( arrayList ) );
            }
        }
    }

    @Override
    public List<Order> entitiesToDtos(List<OrderEntity> orderEntity) {
        if ( orderEntity == null ) {
            return null;
        }

        List<Order> list = new ArrayList<Order>( orderEntity.size() );
        for ( OrderEntity orderEntity1 : orderEntity ) {
            list.add( entityToDto( orderEntity1 ) );
        }

        return list;
    }
}
