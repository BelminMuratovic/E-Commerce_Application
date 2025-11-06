package com.example.ECommerce.e_commerce_core.mapper;

import com.example.ECommerce.e_commerce_api.model.product.Product;
import com.example.ECommerce.e_commerce_api.model.product.ProductCreateRequest;
import com.example.ECommerce.e_commerce_api.model.product.ProductUpdateRequest;
import com.example.ECommerce.e_commerce_dao.model.ProductEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-06T20:31:32+0100",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251023-0518, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductEntity dtoToEntity(ProductCreateRequest product) {
        if ( product == null ) {
            return null;
        }

        ProductEntity productEntity = new ProductEntity();

        if ( product.getName() != null ) {
            productEntity.setName( product.getName() );
        }
        if ( product.getPrice() != null ) {
            productEntity.setPrice( product.getPrice() );
        }
        if ( product.getQuantity() != null ) {
            productEntity.setQuantity( product.getQuantity() );
        }
        if ( product.getType() != null ) {
            productEntity.setType( product.getType() );
        }

        return productEntity;
    }

    @Override
    public Product entityToDto(ProductEntity productEntity) {
        if ( productEntity == null ) {
            return null;
        }

        Product product = new Product();

        if ( productEntity.getId() != null ) {
            product.setId( productEntity.getId() );
        }
        if ( productEntity.getName() != null ) {
            product.setName( productEntity.getName() );
        }
        if ( productEntity.getPrice() != null ) {
            product.setPrice( productEntity.getPrice() );
        }
        if ( productEntity.getQuantity() != null ) {
            product.setQuantity( productEntity.getQuantity() );
        }
        if ( productEntity.getType() != null ) {
            product.setType( productEntity.getType() );
        }

        return product;
    }

    @Override
    public void updateEntity(ProductUpdateRequest product, ProductEntity productEntity) {
        if ( product == null ) {
            return;
        }

        if ( product.getName() != null ) {
            productEntity.setName( product.getName() );
        }
        else {
            productEntity.setName( null );
        }
        if ( product.getPrice() != null ) {
            productEntity.setPrice( product.getPrice() );
        }
        else {
            productEntity.setPrice( null );
        }
        if ( product.getQuantity() != null ) {
            productEntity.setQuantity( product.getQuantity() );
        }
        else {
            productEntity.setQuantity( null );
        }
        if ( product.getType() != null ) {
            productEntity.setType( product.getType() );
        }
        else {
            productEntity.setType( null );
        }
    }

    @Override
    public List<Product> entitiesToDtos(List<ProductEntity> productEntity) {
        if ( productEntity == null ) {
            return null;
        }

        List<Product> list = new ArrayList<Product>( productEntity.size() );
        for ( ProductEntity productEntity1 : productEntity ) {
            list.add( entityToDto( productEntity1 ) );
        }

        return list;
    }
}
