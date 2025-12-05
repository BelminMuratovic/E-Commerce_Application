package com.example.ECommerce.e_commerce_core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import com.example.ECommerce.e_commerce_api.model.product.Product;
import com.example.ECommerce.e_commerce_api.model.product.ProductCreateRequest;
import com.example.ECommerce.e_commerce_api.model.product.ProductUpdateRequest;
import com.example.ECommerce.e_commerce_core.impl.ProductServiceImpl;
import com.example.ECommerce.e_commerce_core.mapper.ProductMapper;
import com.example.ECommerce.e_commerce_dao.model.ProductEntity;
import com.example.ECommerce.e_commerce_dao.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> "jdbc:postgresql://localhost:5433/ecommerce");
        registry.add("spring.datasource.username", () -> "postgres");
        registry.add("spring.datasource.password", () -> "password");
    }

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    private static ProductEntity entity;
    private static Product dto;
    private static List<ProductEntity> entities;
    private static List<Product> dtos;
    private static List<ProductEntity> filteredEntities;
    private static List<Product> filteredDtos;
    private static ProductCreateRequest request;
    private static ProductUpdateRequest updateRequest;

    @BeforeAll
    static void setUp() {
        entity = new ProductEntity(1L, "Fruits", "Banana", 200, 2, null);

        dto = new Product(1L, "Fruits", "Banana", 200, 2, null);

        entities = List.of(
                new ProductEntity(1L, "Fruits", "Banana", 200, 2, null),
                new ProductEntity(2L, "Fruits", "Apple", 200, 2, null));

        dtos = List.of(
                new Product(1L, "Fruits", "Banana", 200, 2, null),
                new Product(1L, "Fruits", "Apple", 200, 2, null));

        filteredEntities = List.of(entities.get(0));

        filteredDtos = List.of(dtos.get(0));

        request = new ProductCreateRequest("Fruits", "Banana", 200, 2, null);

        updateRequest = new ProductUpdateRequest("Fruits", "Banana", 200, 3, null);
    }

    @Test
    void testGetProducts() throws Exception {
        when(productRepository.findAll()).thenReturn(entities);
        when(productMapper.entitiesToDtos(entities)).thenReturn(dtos);

        ResponseEntity<List<Product>> response = productService.getProducts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dtos, response.getBody());
    }

    @Test
    void testGetProductsByName() throws Exception {
        String name = "Banana";
        when(productRepository.findByNameContaining(name)).thenReturn(filteredEntities);
        when(productMapper.entitiesToDtos(filteredEntities)).thenReturn(filteredDtos);

        ResponseEntity<List<Product>> response = productService.getProductsByName(name);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(filteredDtos, response.getBody());
    }

    @Test
    void testGetProductsByType() throws Exception {
        String type = "Fruits";
        when(productRepository.findByTypeContaining(type)).thenReturn(filteredEntities);
        when(productMapper.entitiesToDtos(filteredEntities)).thenReturn(filteredDtos);

        ResponseEntity<List<Product>> response = productService.getProductsByType(type);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(filteredDtos, response.getBody());
    }

    @Test
    void testFindProductById() throws Exception {
        Long id = 1L;
        when(productRepository.findById(id)).thenReturn(Optional.of(entity));
        when(productMapper.entityToDto(entity)).thenReturn(dto);

        ResponseEntity<Product> response = productService.findProductById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    void testCreateProduct() throws Exception {
        when(productMapper.dtoToEntity(request)).thenReturn(entity);
        when(productMapper.entityToDto(entity)).thenReturn(dto);
        when(productRepository.save(entity)).thenReturn(entity);

        ResponseEntity<Product> response = productService.create(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    void testUpdateProduct_Success() throws Exception {
        Long id = 1L;
        Product updatedProduct = new Product(1L, "Fruits", "Banana", 200, 3, null);

        when(productRepository.findById(id)).thenReturn(Optional.of(entity));
        doNothing().when(productMapper).updateEntity(updateRequest, entity);
        when(productMapper.entityToDto(entity)).thenReturn(updatedProduct);

        ResponseEntity<Product> response = productService.update(id, updateRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedProduct, response.getBody());
    }

    @Test
    void testUpdateProduct_NotModified() throws Exception {
        Long id = 1L;
        ProductUpdateRequest request = new ProductUpdateRequest();

        when(productRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<Product> response = productService.update(id, request);

        assertEquals(HttpStatus.NOT_MODIFIED, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testDeleteProduct_Success() throws Exception {
        Long id = 1L;

        ProductEntity existingProduct = new ProductEntity();
        existingProduct.setId(id);

        when(productRepository.findById(id)).thenReturn(Optional.of(existingProduct));
        doNothing().when(productRepository).deleteById(id);
        when(productRepository.existsById(id)).thenReturn(false);

        ResponseEntity<Void> response = productService.delete(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testDeleteProduct_NotModified() throws Exception {
        Long id = 1L;

        ProductEntity existingProduct = new ProductEntity();
        existingProduct.setId(id);

        doNothing().when(productRepository).deleteById(id);
        when(productRepository.findById(id)).thenReturn(Optional.of(existingProduct));
        when(productRepository.existsById(id)).thenReturn(true);

        ResponseEntity<Void> response = productService.delete(id);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }
}
