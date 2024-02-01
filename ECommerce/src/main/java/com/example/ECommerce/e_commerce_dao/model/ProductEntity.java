package com.example.ECommerce.e_commerce_dao.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class ProductEntity {

    @Id
    @SequenceGenerator(name = "PRODUCT_ID_GENERATOR", sequenceName = "PRODUCT_SEQ", allocationSize = 50)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUCT_ID_GENERATOR")
    private Long id;
    private String type;
    private String name;
    private Long quantity;
    private Long price;
    private String imageName;
    private String imageType;
    @Column(name = "picByte", length = 1000)
    private byte[] picByte;
}
