package com.example.minor_project_01.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantity;
    private Double price;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Order order;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
/*

Order1:
- Laptop HP A01, 3 ----> Laptop HP A01

Order2:
- Laptop HP A01, 1 ----> Laptop HP A01

 */