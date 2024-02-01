package com.example.ECommerce.e_commerce_api.model.order;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Getter
@Setter
@Data
public class OrderUpdateRequest {
    private String name;
    private String address;
    private LocalDate date;
    private ArrayList<Item> products;
}
