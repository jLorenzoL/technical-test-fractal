package com.fractal.demo.technicaltestfractal.dto;

import lombok.Data;

@Data
public class ProductDto {

    private String id;

    private String name;

    private Double unitPrice;

    private boolean state;

    private Integer category;

}
