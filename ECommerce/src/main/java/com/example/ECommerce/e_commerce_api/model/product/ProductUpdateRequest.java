package com.example.ECommerce.e_commerce_api.model.product;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateRequest implements Serializable {
    private String type;
    private String name;
    private Integer quantity;
    private Integer price;
    private MultipartFile image;
}
