package com.example.ECommerce.e_commerce_api.model.product;

import lombok.Data;

@Data
public class Product {
    private Long id;
    private String type;
    private String name;
    private Long quantity;
    private Long price;
}
