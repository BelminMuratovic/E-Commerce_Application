package com.example.ECommerce.e_commerce_api.model.order;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Item implements Serializable {
    private String name;
    private Integer quantity;
    private double price;
}
