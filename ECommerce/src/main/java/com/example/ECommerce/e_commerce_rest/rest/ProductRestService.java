package com.example.ECommerce.e_commerce_rest.rest;

import com.example.ECommerce.e_commerce_api.ProductService;
import com.example.ECommerce.e_commerce_api.model.product.Product;
import com.example.ECommerce.e_commerce_api.model.product.ProductCreateRequest;
import com.example.ECommerce.e_commerce_api.model.product.ProductUpdateRequest;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor
@Validated
public class ProductRestService {
    private static final Logger logger = LoggerFactory.getLogger(ProductRestService.class);
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getProducts() {
        logger.info("Fetching all products");
        return productService.getProducts();
    }

    @GetMapping("/byType")
    public ResponseEntity<List<Product>> getProductsByType(@RequestParam String type) {
        logger.info("Fetching products by type: {}", type);
        return productService.getProductsByType(type);
    }

    @GetMapping("/byName")
    public ResponseEntity<List<Product>> getProductsByName(@RequestParam String name) {
        logger.info("Fetching products by name: {}", name);
        return productService.getProductsByName(name);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> findProductById(@PathVariable(name = "id") final Long id) {
        logger.info("Fetching product with ID: {}", id);
        return productService.findProductById(id);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Product> create(
            @RequestParam("type") @NotBlank(message = "Type is required") String type,
            @RequestParam("name") @NotBlank(message = "Name is required") String name,
            @RequestParam("quantity") @NotNull(message = "Quantity is required") Integer quantity,
            @RequestParam("price") @NotNull(message = "Price is required") Integer price,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        ProductCreateRequest request = new ProductCreateRequest(type, name, quantity, price, image);
        logger.info("Creating new product");
        logger.debug("Product create request: {}", request);
        return productService.create(request);
    }

    @PutMapping(value = "{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Product> update(@Parameter(required = true) @PathVariable("id") final Long id,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "quantity", required = false) Integer quantity,
            @RequestParam(value = "price", required = false) Integer price,
            @RequestPart(value = "image", required = false) MultipartFile image) {

        ProductUpdateRequest request = new ProductUpdateRequest(type, name, quantity, price, image);
        logger.info("Updating product with ID: {}", id);
        logger.debug("Product update request: {}", request);
        return productService.update(id, request);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> delete(@Parameter(required = true) @PathVariable("id") final Long id) {
        logger.info("Deleting product with ID: {}", id);
        return productService.delete(id);
    }
}
