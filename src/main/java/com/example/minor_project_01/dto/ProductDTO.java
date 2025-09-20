package com.example.minor_project_01.dto;

import com.example.minor_project_01.entity.Product;
import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String description;

    private Double price;
    private Integer stock;
    private Boolean active;

    private String imageUrl;
    private Long categoryId;


    public static ProductDTO buildDTOFromProduct(Product product){
        ProductDTO productDTO = ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .imageUrl(product.getImageUrl())
                .price(product.getPrice())
                .stock(product.getStock())
                .active(product.getActive())
                .categoryId(product.getCategory().getId())
                .description(product.getDescription())
                .imageUrl(product.getImageUrl())
                .build();
        return productDTO;
    }

}
