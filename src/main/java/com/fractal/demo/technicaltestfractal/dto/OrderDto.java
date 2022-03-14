package com.fractal.demo.technicaltestfractal.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDto {

    private String id;
    private LocalDateTime date;
    private String costumer;
    private Integer status;
    private Integer quantity;
    private List<ProductDto> listProducts;
    private Double subtotal;
    private Double taxAmount;
    private Double totalAmount;
}
