package com.example.ECommerce.e_commerce_api;

import com.example.ECommerce.e_commerce_api.model.product.Product;
import com.example.ECommerce.e_commerce_api.model.product.ProductCreateRequest;
import com.example.ECommerce.e_commerce_api.model.product.ProductUpdateRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * The interface Product service.
 */
public interface ProductService {

    /**
     * Gets products.
     *
     * @return the products
     * @throws Exception the exception
     */
    public ResponseEntity<List<Product>> getProducts() throws Exception;

    /**
     * Gets products by type.
     *
     * @param type the type
     * @return the products by type
     * @throws Exception the exception
     */
    public ResponseEntity<List<Product>> getProductsByType(String type) throws Exception;

    /**
     * Gets products by name.
     *
     * @param name the name
     * @return the products by name
     * @throws Exception the exception
     */
    public ResponseEntity<List<Product>> getProductsByName(String name) throws Exception;

    /**
     * Find product by id response entity.
     *
     * @param productId the product id
     * @return the response entity
     * @throws Exception the exception
     */
    public ResponseEntity<Product> findProductById(final Long productId) throws Exception;

    /**
     * Create response entity.
     *
     * @param request the request
     * @return the response entity
     * @throws Exception the exception
     */
    public ResponseEntity<Product> create(final ProductCreateRequest request) throws Exception;

    /**
     * Update response entity.
     *
     * @param productId the product id
     * @param request   the request
     * @return the response entity
     * @throws Exception the exception
     */
    public ResponseEntity<Product> update(final Long productId, final ProductUpdateRequest request) throws Exception;

    /**
     * Delete response entity.
     *
     * @param productId the product id
     * @return the response entity
     * @throws Exception the exception
     */
    public ResponseEntity<Void> delete(final Long productId) throws Exception;
}
