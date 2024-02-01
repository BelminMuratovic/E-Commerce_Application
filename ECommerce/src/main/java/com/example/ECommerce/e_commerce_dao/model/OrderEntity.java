package com.example.ECommerce.e_commerce_dao.model;

import com.example.ECommerce.e_commerce_api.model.order.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class OrderEntity {

    @Id
    @SequenceGenerator(name = "ORDERS_ID_GENERATOR", sequenceName = "ORDERS_SEQ", allocationSize = 50)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDERS_ID_GENERATOR")
    private Long id;
    private String name;
    private String address;
    private LocalDate date;
    private ArrayList<Item> products;
}
