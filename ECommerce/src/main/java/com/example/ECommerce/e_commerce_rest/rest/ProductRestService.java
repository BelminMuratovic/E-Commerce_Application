package com.example.ECommerce.e_commerce_rest.rest;

import com.example.ECommerce.e_commerce_api.ProductService;
import com.example.ECommerce.e_commerce_api.model.product.Product;
import com.example.ECommerce.e_commerce_api.model.product.ProductCreateRequest;
import com.example.ECommerce.e_commerce_api.model.product.ProductUpdateRequest;
import com.example.ECommerce.e_commerce_dao.model.ProductEntity;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "product")
@AllArgsConstructor
public class ProductRestService {
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getProducts() throws Exception {
        return productService.getProducts();
    }

    @GetMapping("/byType")
    public ResponseEntity<List<Product>> getProductsByType(@RequestParam String type) throws Exception {
        return productService.getProductsByType(type);
    }

    @GetMapping("/byName")
    public ResponseEntity<List<Product>> getProductsByName(@RequestParam String name) throws Exception {
        return productService.getProductsByName(name);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> findProductById(@PathVariable(name = "id") final Long id) throws Exception {
        return productService.findProductById(id);
    }

    @GetMapping(value = "/get-image/{id}")
    public ResponseEntity<ProductEntity> getImage(@PathVariable(name = "id") final Long id) throws Exception {
        return productService.getImage(id);
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody final ProductCreateRequest request) throws Exception {
        return productService.create(request);
    }

    @PutMapping(value = "/upload-image/{id}")
    public ResponseEntity<Product> uploadImage(@Parameter(required = true) @PathVariable("id") final Long id,
                                               @RequestParam("imageFile") final MultipartFile request) throws Exception {
        return productService.uploadImage(id, request);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<Product> update(@Parameter(required = true) @PathVariable("id") final Long id,
                                          @RequestBody final ProductUpdateRequest request) throws Exception {
        return productService.update(id, request);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Product> delete(@Parameter(required = true) @PathVariable("id") final Long id) throws Exception {
        return productService.delete(id);
    }
}
