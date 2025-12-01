package com.example.ECommerce.e_commerce_api.model.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Item implements Serializable {
    private String name;
    private Integer quantity;
    private double price;
}
