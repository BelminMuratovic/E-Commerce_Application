package com.example.ECommerce.e_commerce_rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.ECommerce.e_commerce_api.OrderService;
import com.example.ECommerce.e_commerce_api.ProductService;
import com.example.ECommerce.e_commerce_api.model.product.Product;
import com.example.ECommerce.e_commerce_api.model.product.ProductCreateRequest;
import com.example.ECommerce.e_commerce_api.model.product.ProductUpdateRequest;
import com.example.ECommerce.e_commerce_rest.rest.ProductRestService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductRestService.class)
public class ProductRestServiceTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    @MockitoBean
    private ProductService productService;

    private static List<Product> products;
    private static List<Product> filteredProducts;
    private static Product product1;
    private static Product product2;
    private static Product updatedProduct;

    @BeforeAll
    static void setUp() {
        product1 = new Product(1L, "Fruits", "Banana", 100, 2, "banana.jpeg");
        product2 = new Product(2L, "Vegetables", "Tomato", 50, 5, "tomato.jpg");

        products = List.of(product1, product2);
        filteredProducts = List.of(product1);

        updatedProduct = new Product(1L, "Fruits", "Banana", 100, 5, "banana.jpeg");
    }

    @Test
    void testGetProducts() throws Exception {
        when(productService.getProducts()).thenReturn(ResponseEntity.ok(products));

        mockMvc.perform(get("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Banana"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Tomato"));
    }

    @Test
    void testGetProductsByType() throws Exception {
        String type = "Fruits";
        when(productService.getProductsByType(type)).thenReturn(ResponseEntity.ok(filteredProducts));

        mockMvc.perform(get("/api/v1/products/byType")
                .param("type", type)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Banana"));
    }

    @Test
    void testGetProductsByName() throws Exception {
        String name = "Banana";
        when(productService.getProductsByName(name)).thenReturn(ResponseEntity.ok(filteredProducts));

        mockMvc.perform(get("/api/v1/products/byName")
                .param("name", name)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Banana"));
    }

    @Test
    void testFindProductById() throws Exception {
        Long id = 1L;
        when(productService.findProductById(id)).thenReturn(ResponseEntity.ok(product1));

        mockMvc.perform(get("/api/v1/products/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Banana"));
    }

    @Test
    void testCreateProduct() throws Exception {
        when(productService.create(any(ProductCreateRequest.class)))
                .thenReturn(ResponseEntity.ok(product1));

        mockMvc.perform(multipart("/api/v1/products")
                .param("type", "Fruits")
                .param("name", "Banana")
                .param("quantity", "100")
                .param("price", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Banana"));
    }

    @Test
    void testUpdateProduct() throws Exception {
        Long id = 1L;
        when(productService.update(eq(id), any(ProductUpdateRequest.class)))
                .thenReturn(ResponseEntity.ok(updatedProduct));

        mockMvc.perform(multipart("/api/v1/products/{id}", id)
                .param("price", "5")
                .with(request -> {
                    request.setMethod("PUT");
                    return request;
                }))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Banana"))
                .andExpect(jsonPath("$.price").value(5));
    }

    @Test
    void testDeleteProduct() throws Exception {
        Long id = 1L;
        when(productService.delete(id)).thenReturn(ResponseEntity.noContent().build());

        mockMvc.perform(delete("/api/v1/products/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
