package com.example.minor_project_01.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddToOrderDto {
    private Long productId;
    private Integer quantity;
}
