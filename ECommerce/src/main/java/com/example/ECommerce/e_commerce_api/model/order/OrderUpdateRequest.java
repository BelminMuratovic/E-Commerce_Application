package com.example.ECommerce.e_commerce_api.model.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderUpdateRequest implements Serializable {
    private String name;
    private String address;
    private LocalDate date;
    private List<Item> products;
}
