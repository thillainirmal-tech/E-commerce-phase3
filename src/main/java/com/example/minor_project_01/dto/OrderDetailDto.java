package com.example.minor_project_01.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class OrderDetailDto {

    private Long orderId;
    private List<OrderItemDto> orderItems;
    private Double orderTotalPrice;
}
