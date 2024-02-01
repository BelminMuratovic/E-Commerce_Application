package com.example.ECommerce.e_commerce_api.model.order;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;

@Data
public class Order {
    private Long id;
    private String name;
    private String address;
    private LocalDate date;
    private ArrayList<Item> products;
}
