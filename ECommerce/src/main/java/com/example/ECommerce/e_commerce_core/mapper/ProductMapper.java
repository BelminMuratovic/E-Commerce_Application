package com.example.ECommerce.e_commerce_core.mapper;

import com.example.ECommerce.e_commerce_api.model.product.Product;
import com.example.ECommerce.e_commerce_api.model.product.ProductCreateRequest;
import com.example.ECommerce.e_commerce_api.model.product.ProductUpdateRequest;
import com.example.ECommerce.e_commerce_dao.model.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductEntity dtoToEntity(ProductCreateRequest product);

    Product entityToDto(ProductEntity productEntity);

    void updateEntity(ProductUpdateRequest product, @MappingTarget ProductEntity productEntity);

    List<Product> entitiesToDtos(List<ProductEntity> productEntity);
}

