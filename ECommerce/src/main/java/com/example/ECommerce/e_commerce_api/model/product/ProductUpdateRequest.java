package com.example.ECommerce.e_commerce_api.model.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductUpdateRequest {
    private String type;
    private String name;
    private Long quantity;
    private Long price;
}
