package com.fractal.demo.technicaltestfractal.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "coll_order")
public class OrderEntity {

    @Id
    private String id;

    private LocalDateTime date;

    private String customer;

    private Integer status;

    private Integer quantity;

    private List<ProductEntity> listProductEntity;

    private Double subtotal;

    @Field(value = "tax_amount")
    private Double taxAmount;

    @Field(value = "total_amount")
    private Double totalAmount;

}
